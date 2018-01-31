package e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import e3mall.common.jedis.JedisClient;
import e3mall.common.pojo.EasyUIDataGridResult;
import e3mall.common.utils.E3Result;
import e3mall.common.utils.JsonUtils;
import e3mall.content.service.ContentService;
import e3mall.mapper.TbContentMapper;
import e3mall.pojo.TbContent;
import e3mall.pojo.TbContentExample;
import e3mall.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;
	
	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Override
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbContentExample example = new TbContentExample();
		if (categoryId != 0) {
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
		}
		List<TbContent> contentList = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<>(contentList);
		//封装并分页结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(contentList);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public E3Result addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		//缓存同步
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		return E3Result.ok();
	}

	@Override
	public List<TbContent> getContentListByCid(Long cid) {
		try {
			String json = jedisClient.hget(CONTENT_LIST, cid.toString());
			if (StringUtils.isNotBlank(json)) {
				List<TbContent> jsonToList = JsonUtils.jsonToList(json, TbContent.class);
				return jsonToList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> contentList = contentMapper.selectByExampleWithBLOBs(example);
		
		try {
			jedisClient.hset(CONTENT_LIST, cid.toString(), JsonUtils.objectToJson(contentList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contentList;
	}

}
