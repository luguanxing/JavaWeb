package blog.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.dao.BloggerDao;
import blog.entity.Blogger;
import blog.entity.BloggerExample;
import blog.entity.BloggerExample.Criteria;
import blog.service.BloggerService;
import blog.utils.JsonUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class BloggerServiceImpl implements BloggerService {

	@Autowired
	private BloggerDao bloggerDao;
	
    @Autowired
    private JedisPool jedisPool;
	
	@Override
	public Blogger getByUsername(String username) {
		BloggerExample example = new BloggerExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<Blogger> bloggers = bloggerDao.selectByExample(example);
		if (bloggers.isEmpty())
			return null;
		return bloggers.get(0);
	}

	@Override
	public Blogger getFirstBlogger() {
		//先查缓存，有直接返回，否则从数据库查并放到缓存中
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String json = jedis.get("blogger");
			if (StringUtils.isNotBlank(json)) {
				Blogger blogger = JsonUtils.jsonToPojo(json, Blogger.class);
				return blogger;	//返回也要注意关闭连接
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		
		BloggerExample example = new BloggerExample();
		Criteria criteria = example.createCriteria();
		List<Blogger> bloggers = bloggerDao.selectByExampleWithBLOBs(example);	//缓存中包括网页介绍的BLOB
		if (bloggers.isEmpty())
			return null;
		Blogger blogger = bloggers.get(0);
		blogger.setPassword(null);	//验证密码不从这里取，其它时候都不应取出密码
		
		try {
			jedis = jedisPool.getResource();
			jedis.set("blogger", JsonUtils.objectToJson(blogger));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		return blogger;
	}

	@Override
	public Boolean updateFirstBlogger(Blogger blogger) {
		Jedis jedis = null;
		try {
			bloggerDao.updateByPrimaryKeySelective(blogger);
			//修改完成后删除缓存就是缓存同步
			jedis = jedisPool.getResource();
			jedis.del("blogger");
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
