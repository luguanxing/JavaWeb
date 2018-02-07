package blog.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import blog.dao.LinkDao;
import blog.entity.Link;
import blog.entity.LinkExample;
import blog.entity.LinkExample.Criteria;
import blog.service.LinkService;
import blog.utils.JsonUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class LinkServiceImpl implements LinkService {

	@Autowired
	private LinkDao linkDao;
	
    @Autowired
    private JedisPool jedisPool;
	
	@Override
	public List<Link> getLinksBySortNo() {
		//先查缓存，有直接返回，否则从数据库查并放到缓存中
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String json = jedis.get("links");
			if (StringUtils.isNotBlank(json)) {
				List<Link> links = JsonUtils.jsonToList(json, Link.class);
				return links;	//返回也要注意关闭连接
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		
		LinkExample example = new LinkExample();
		example.setOrderByClause("orderNo asc");
		Criteria criteria = example.createCriteria();
		List<Link> links = linkDao.selectByExample(example);
		
		try {
			jedis = jedisPool.getResource();
			jedis.set("links", JsonUtils.objectToJson(links));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		return links;
	}

	@Override
	public List<Link> getLinks(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		LinkExample example = new LinkExample();
		example.setOrderByClause("orderNo asc");
		Criteria criteria = example.createCriteria();
		List<Link> links = linkDao.selectByExample(example);
		return links;
	}

	@Override
	public Integer getLinksCount() {
		LinkExample example = new LinkExample();
		Integer count = linkDao.countByExample(example);
		return count;
	}

	@Override
	public Boolean addLink(Link link) {
		Jedis jedis = null;
		try {
			linkDao.insert(link);
			//修改完成后删除缓存就是缓存同步
			jedis = jedisPool.getResource();
			jedis.del("links");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	@Override
	public Boolean updateLink(Link link) {
		Jedis jedis = null;
		try {
			linkDao.updateByPrimaryKey(link);
			//修改完成后删除缓存就是缓存同步
			jedis = jedisPool.getResource();
			jedis.del("links");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

	@Override
	public Boolean deleteLink(Integer id) {
		Jedis jedis = null;
		try {
			linkDao.deleteByPrimaryKey(id);
			//修改完成后删除缓存就是缓存同步
			jedis = jedisPool.getResource();
			jedis.del("links");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (jedis != null)
				jedis.close();
		}
	}

}
