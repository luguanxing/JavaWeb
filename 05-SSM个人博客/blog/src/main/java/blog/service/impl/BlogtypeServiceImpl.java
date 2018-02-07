package blog.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import blog.dao.BlogtypeDao;
import blog.entity.Blog;
import blog.entity.Blogtype;
import blog.entity.BlogtypeExample;
import blog.entity.BlogtypeExample.Criteria;
import blog.service.BlogService;
import blog.service.BlogtypeService;
import blog.utils.JsonUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class BlogtypeServiceImpl implements BlogtypeService {

	@Autowired
	private BlogtypeDao blogtypeDao;
	
	@Autowired
	private BlogService blogService;
	
    @Autowired
    private JedisPool jedisPool;
	
	@Override
	public List<Blogtype> getBlogtypesBySortNo() {
		//先查缓存，有直接返回，否则从数据库查并放到缓存中
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String json = jedis.get("blogtype");
			if (StringUtils.isNotBlank(json)) {
				List<Blogtype> blogtypes = JsonUtils.jsonToList(json, Blogtype.class);
				return blogtypes;	//返回也要注意关闭连接
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		
		//取出所有
		BlogtypeExample example = new BlogtypeExample();
		example.setOrderByClause("orderNo asc");
		Criteria criteria = example.createCriteria();
		List<Blogtype> blogtypes = blogtypeDao.selectByExample(example);
		
		try {
			jedis = jedisPool.getResource();
			jedis.set("blogtype", JsonUtils.objectToJson(blogtypes));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		return blogtypes;
	}

	@Override
	public Blogtype getBlogtypeById(Integer blogtypeIdNum) {
		List<Blogtype> blogtypes = getBlogtypesBySortNo();
		for (Blogtype blogtype : blogtypes) {
			if (blogtype.getId() == blogtypeIdNum)
				return blogtype;
		}
		return null;
	}

	@Override
	public Blogtype getBlogtypeByTypeName(String typeName) {
		List<Blogtype> blogtypes = getBlogtypesBySortNo();
		for (Blogtype blogtype : blogtypes) {
			if (typeName.equals(blogtype.getTypeName()))
				return blogtype;
		}
		return null;
	}

	@Override
	public List<Blogtype> getBlogtypes(Integer pageNum, Integer pageSize) {
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		//执行查询
		BlogtypeExample example = new BlogtypeExample();
		example.setOrderByClause("orderNo asc");
		Criteria criteria = example.createCriteria();
		List<Blogtype> blogtypes = blogtypeDao.selectByExample(example);
		return blogtypes;
	}

	@Override
	public Boolean addBlogtype(Blogtype blogtype) {
		Jedis jedis = null;
		try {
			blogtypeDao.insert(blogtype);
			//修改完成后删除缓存就是缓存同步
			jedis = jedisPool.getResource();
			jedis.del("blogtype");
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
	public Boolean updateBlogtype(Blogtype blogtype) {
		Jedis jedis = null;
		try {
			blogtypeDao.updateByPrimaryKey(blogtype);
			//修改完成后删除缓存就是缓存同步
			jedis = jedisPool.getResource();
			jedis.del("blogtype");
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
	public Boolean deleteBlogtype(Integer id) {
		Jedis jedis = null;
		try {
			//该typeId下不为空不能删除
			List<Blog> blogsBytypeId = blogService.getBlogsBytypeId(id, 0, 1);
			if (!blogsBytypeId.isEmpty())
				return false;
			blogtypeDao.deleteByPrimaryKey(id);
			//修改完成后删除缓存就是缓存同步
			jedis = jedisPool.getResource();
			jedis.del("blogtype");
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
