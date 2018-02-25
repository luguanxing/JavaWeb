package news.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
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

}
