package news.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import news.dao.NewDao;
import news.entity.New;
import news.entity.NewExample;
import news.entity.NewExample.Criteria;
import news.service.NewService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class NewServiceImpl implements NewService {

	private static Logger logger = Logger.getLogger(NewServiceImpl.class);
	
	@Autowired
	private NewDao newDao;

    @Autowired
    private JedisPool jedisPool;
    
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public Integer getNewIdByUrl(String url) {
		//先判断缓存中有没有该新闻
		Integer id = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String newId = jedis.hget("news", url);
			if (StringUtils.isNotBlank(newId)) {
				try {
					id = Integer.parseInt(newId);
					System.out.println("链接已在缓存");
					return id;
				} catch (Exception e) {
					logger.error("缓存解析id出错", e);
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			logger.error("读缓存出错", e);
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		
		//缓存没有再判断数据库中有没有新闻
		NewExample example = new NewExample();
		Criteria criteria = example.createCriteria();
		criteria.andUrlEqualTo(url);
		List<New> list = newDao.selectByExample(example);
		if (!list.isEmpty()) {
			//缓存没有数据库有说明还要保存到缓存
			System.out.println("链接已数据库，同步缓存");
			id = list.get(0).getId();
			try {
				jedis = jedisPool.getResource();
				jedis.hset("news", url, id.toString());
			} catch (Exception e) {
				logger.error("缓存保存id出错", e);
				e.printStackTrace();
				return null;
			} finally {
				if (jedis != null)
					jedis.close(); //注意关闭连接否则内存泄漏
			}
			return id;
		} else {
			return null;
		}
	}

	@Override
	public Boolean addNew(New newObj) {
		Integer result = newDao.insert(newObj);
		
		Jedis jedis = null;
		//获取主键后添加到缓存
		try {
			jedis = jedisPool.getResource();
			jedis.hset("news", newObj.getUrl(), newObj.getId().toString());
		} catch (Exception e) {
			logger.info("写缓存出错", e);
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		//同步索引
		try {
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", newObj.getId());
			document.setField("new_title", newObj.getTitle());
			document.setField("new_contentText", newObj.getContentText());
			document.setField("new_publishDateAndSrc", newObj.getPublishDateAndSrc());
			document.setField("new_commentCount", newObj.getCommentCount());
			solrServer.add(document);
			solrServer.commit();
		} catch (Exception e) {
			logger.info("写索引出错", e);
			e.printStackTrace();
		}
		
		return result > 0;
	}

	@Override
	public Boolean updateNew(New newObj) {
		Integer result = newDao.updateByPrimaryKey(newObj);
		//同步索引
		try {
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", newObj.getId());
			document.setField("new_title", newObj.getTitle());
			document.setField("new_contentText", newObj.getContentText());
			document.setField("new_publishDateAndSrc", newObj.getPublishDateAndSrc());
			document.setField("new_commentCount", newObj.getCommentCount());
			solrServer.add(document);
			solrServer.commit();
		} catch (Exception e) {
			logger.info("写索引出错", e);
			e.printStackTrace();
		}
		return result > 0;
	}

	@Override
	public New getNewById(Integer id) {
		New newObj = newDao.selectByPrimaryKey(id);
		return newObj;
	}

	@Override
	public List<New> getNewsByCrawlerDate(Integer pageNum, Integer pageSize) {
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		//执行查询
		NewExample example = new NewExample();
		example.setOrderByClause("crawlerDate DESC");
		List<New> news = newDao.selectByExample(example);
		return news;
	}
	
	@Override
	public List<New> getNewsByPublishDateAndSrc(Integer pageNum, Integer pageSize) {
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		//执行查询
		NewExample example = new NewExample();
		example.setOrderByClause("publishDateAndSrc DESC");
		List<New> news = newDao.selectByExample(example);
		return news;
	}
	
	@Override
	public List<New> getNewsByCommentCount(Integer pageNum, Integer pageSize) {
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		//执行查询
		NewExample example = new NewExample();
		example.setOrderByClause("commentCount DESC");
		List<New> news = newDao.selectByExample(example);
		return news;
	}

	@Override
	public List<New> getHotNews(Integer pageNum, Integer pageSize) {
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		//执行查询
		NewExample example = new NewExample();
		example.setOrderByClause("commentCount DESC");
		List<New> news = newDao.selectByExample(example);
		return news;
	}

	@Override
	public Boolean deleteNewById(Integer id) {
		New newObj = newDao.selectByPrimaryKey(id);
		//删除索引
		try {
			solrServer.deleteById(id.toString());
			solrServer.commit();
		} catch (Exception e) {
			logger.info("写索引出错", e);
			e.printStackTrace();
		}
		//删除缓存
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hdel("news", newObj.getUrl());
		} catch (Exception e) {
			logger.error("缓存保存id出错", e);
			e.printStackTrace();
			return null;
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		int result = newDao.deleteByPrimaryKey(id);
		return result > 0;
	}

	@Override
	public Integer getNewsCount() {
		//执行查询
		NewExample example = new NewExample();
		List<New> news = newDao.selectByExample(example);
		return news.size();
	}

	@Override
	public Boolean importRedis() {
		NewExample example = new NewExample();
		List<New> news = newDao.selectByExample(example);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			for (New newObj : news) {
				jedis.hset("news", newObj.getUrl(), newObj.getId().toString());
			}
			return true;
		} catch (Exception e) {
			logger.error("缓存导入出错", e);
			e.printStackTrace();
			return false;
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
	}

	@Override
	public Boolean importSolr() {
		NewExample example = new NewExample();
		List<New> news = newDao.selectByExample(example);
		try {
			for (New newObj : news) {
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id", newObj.getId());
				document.setField("new_title", newObj.getTitle());
				document.setField("new_contentText", newObj.getContentText());
				document.setField("new_publishDateAndSrc", newObj.getPublishDateAndSrc());
				document.setField("new_commentCount", newObj.getCommentCount());
				solrServer.add(document);
			}
			solrServer.commit();
			return true;
		} catch (Exception e) {
			logger.info("写索引出错", e);
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<New> getSearchNews(String keyword, Integer startRow, Integer pageSize) {
		//设置查询条件
		SolrQuery query = new SolrQuery();
		List<New> news = new ArrayList();
		query.set("q", keyword);
		query.setStart(startRow);
		query.setRows(pageSize);
		query.set("df", "new_keyword");
		query.setHighlight(true);
		query.addHighlightField("new_title");
		query.addHighlightField("new_contentText");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		try {
			//执行查询,获取QueryResponse对象
			QueryResponse queryResponse = solrServer.query(query);
			//取文档列表,取总记录数
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			//遍历文档列表,取出域内容
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			for (SolrDocument solrDocument : solrDocumentList) {
				New newObj = new New();
				newObj.setId(Integer.parseInt(solrDocument.get("id").toString()));
				//取高亮
				List<String> titleList = highlighting.get(solrDocument.get("id")).get("new_title");
				if (titleList != null && !titleList.isEmpty()) {
					String hightLightTitle = titleList.get(0);
					newObj.setTitle(hightLightTitle);
				} else {
					String defaultTitle = solrDocument.get("new_title").toString();
					newObj.setTitle(defaultTitle);
				}
				newObj.setPublishDateAndSrc(solrDocument.get("new_publishDateAndSrc").toString());
				newObj.setCommentCount(Integer.parseInt(solrDocument.get("new_commentCount").toString()));
				news.add(newObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	@Override
	public Integer getSearchNewsCount(String keyword) {
		//设置查询条件
		SolrQuery query = new SolrQuery();
		query.set("q", keyword);
		query.set("df", "new_keyword");
		Long result = 0L;
		try {
			//执行查询,获取QueryResponse对象
			QueryResponse queryResponse = solrServer.query(query);
			//取文档列表,取总记录数
			SolrDocumentList solrDocumentList = queryResponse.getResults();
			result = solrDocumentList.getNumFound();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.intValue();
	}

}
