package blog.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import blog.dao.BlogDao;
import blog.entity.Blog;
import blog.entity.BlogExample;
import blog.entity.BlogExample.Criteria;
import blog.service.BlogService;
import blog.utils.JsonUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogDao blogDao;
	
	@Value("${NEWEST_BLOGS_NUM}")
	private Integer NEWEST_BLOGS_NUM;
	
	@Value("${PRIORITY_BLOGS_NUM}")
	private Integer PRIORITY_BLOGS_NUM;
	
    @Autowired
    private JedisPool jedisPool;
    
	@Autowired
	private SolrServer solrServer;
	
	//存取时间格式要一致
	static private String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	@Override
	public List<Blog> getPriorityBlogs() {
		//先查缓存，有直接返回，否则从数据库查并放到缓存中
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			String json = jedis.get("priorityblogs");
			if (StringUtils.isNotBlank(json)) {
				List<Blog> blogs = JsonUtils.jsonToList(json, Blog.class);
				return blogs;	//返回也要注意关闭连接
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		
		//设置分页信息
		PageHelper.startPage(0, PRIORITY_BLOGS_NUM);
		//执行查询
		BlogExample example = new BlogExample();
		example.setOrderByClause("priority desc");
		Criteria criteria = example.createCriteria();
		List<Blog> blogs = blogDao.selectByExample(example);
		
		try {
			jedis = jedisPool.getResource();
			jedis.set("priorityblogs", JsonUtils.objectToJson(blogs));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null)
				jedis.close(); //注意关闭连接否则内存泄漏
		}
		return blogs;
	}

	@Override
	public List<Blog> getBlogs(Integer pageNum, Integer pageSize) {
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		//执行查询
		BlogExample example = new BlogExample();
		example.setOrderByClause("releaseDate desc");
		Criteria criteria = example.createCriteria();
		List<Blog> blogs = blogDao.selectByExample(example);
		return blogs;
	}
	
	@Override
	public List<Blog> getBlogsBytypeId(Integer typeId, Integer pageNum, Integer pageSize) {
		//设置分页信息
		PageHelper.startPage(pageNum, pageSize);
		//执行查询
		BlogExample example = new BlogExample();
		example.setOrderByClause("releaseDate desc");
		Criteria criteria = example.createCriteria();
		criteria.andTypeIdEqualTo(typeId);
		List<Blog> blogs = blogDao.selectByExample(example);
		return blogs;
	}

	@Override
	public Integer getBlogsCount() {
		//执行查询
		BlogExample example = new BlogExample();
		int blogscount = blogDao.countByExample(example);
		return blogscount;
	}

	@Override
	public Integer getBlogsCount(Integer typeIdNum) {
		//执行查询
		BlogExample example = new BlogExample();
		Criteria criteria = example.createCriteria();
		criteria.andTypeIdEqualTo(typeIdNum);
		int blogscount = blogDao.countByExample(example);
		return blogscount;
	}

	@Override
	public Blog getBlogById(Integer blogIdNum) {
		//执行查询
		Blog blog = blogDao.selectByPrimaryKey(blogIdNum);
		return blog;
	}

	@Override
	public List<Blog> getSearchBlogs(String keyword, Integer startRow, Integer pageSize) {
		//设置查询条件
		SolrQuery query = new SolrQuery();
		List<Blog> blogs = new ArrayList();
		query.set("q", keyword);
		query.setStart(startRow);
		query.setRows(pageSize);
		query.set("df", "blog_keyword");
		query.setHighlight(true);
		query.addHighlightField("blog_title");
		query.addHighlightField("blog_summary");
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
				Blog blog = new Blog();
				blog.setId(Integer.parseInt(solrDocument.get("id").toString()));
				//取高亮
				List<String> titleList = highlighting.get(solrDocument.get("id")).get("blog_title");
				if (titleList != null && !titleList.isEmpty()) {
					String hightLightTitle = titleList.get(0);
					blog.setTitle(hightLightTitle);
				} else {
					String defaultTitle = solrDocument.get("blog_title").toString();
					blog.setTitle(defaultTitle);
				}
				if (solrDocument.get("blog_image") != null) {
					blog.setImage(solrDocument.get("blog_image").toString());
				} else {
					blog.setImage(null);
				}
				if (solrDocument.get("blog_releaseDate") != null) {
					String date_str = solrDocument.get("blog_releaseDate").toString();
					TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
					Date date = new SimpleDateFormat(TIME_FORMAT).parse(date_str);
					blog.setReleaseDate(date);
				} else {
					blog.setReleaseDate(null);
				}
				List<String> summaryList = highlighting.get(solrDocument.get("id")).get("blog_summary");
				if (summaryList != null && !summaryList.isEmpty()) {
					String hightLightSummary = summaryList.get(0);
					blog.setSummary(hightLightSummary);
				} else {
					String defaultSummary = solrDocument.get("blog_summary").toString();
					blog.setSummary(defaultSummary);
				}
				blogs.add(blog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blogs;
	}

	@Override
	public Integer getSearchBlogsCount(String keyword) {
		//设置查询条件
		SolrQuery query = new SolrQuery();
		query.set("q", keyword);
		query.set("df", "blog_keyword");
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

	@Override
	public Boolean importAllBlogs() {
		//将所有blogs导入索引
		try {
			BlogExample example = new BlogExample();
			List<Blog> blogs = blogDao.selectByExampleWithBLOBs(example);
			for (Blog blog : blogs) {
				SolrInputDocument document = new SolrInputDocument();
				document.setField("id", blog.getId());
				document.setField("blog_title", blog.getTitle());
				document.setField("blog_image", blog.getImage());
				document.setField("blog_summary", blog.getSummary());
				TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
				document.setField("blog_releaseDate", new SimpleDateFormat(TIME_FORMAT).format(blog.getReleaseDate()));
				document.setField("blog_content", blog.getContent());
				solrServer.add(document);
			}
			solrServer.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Boolean addBlog(Blog blog) {
		Jedis jedis = null;
		try {
			//注意要配置mybatis返回主键
			blogDao.insert(blog);
			//修改完成后删除缓存就是缓存同步
			jedis = jedisPool.getResource();
			jedis.del("priorityblogs");
			//还要同步索引
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", blog.getId());
			document.setField("blog_title", blog.getTitle());
			document.setField("blog_image", blog.getImage());
			document.setField("blog_summary", blog.getSummary());
			TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
			document.setField("blog_releaseDate", new SimpleDateFormat(TIME_FORMAT).format(blog.getReleaseDate()));
			document.setField("blog_content", blog.getContent());
			solrServer.add(document);
			solrServer.commit();
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
	public Boolean deleteBlog(Integer id) {
		Jedis jedis = null;
		try {
			blogDao.deleteByPrimaryKey(id);
			//修改完成后删除缓存就是缓存同步
			jedis = jedisPool.getResource();
			jedis.del("priorityblogs");
			//还要同步索引
			solrServer.deleteById(id.toString());
			solrServer.commit();
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
	public Boolean updateBlog(Blog blog) {
		Jedis jedis = null;
		try {
			//记得WithBLOBs否则内容不更新
			blogDao.updateByPrimaryKeyWithBLOBs(blog);
			//修改完成后删除缓存就是缓存同步
			jedis = jedisPool.getResource();
			jedis.del("priorityblogs");
			//还要同步索引,注意重复添加就是更新
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", blog.getId());
			document.setField("blog_title", blog.getTitle());
			document.setField("blog_image", blog.getImage());
			document.setField("blog_summary", blog.getSummary());
			TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
			document.setField("blog_releaseDate", new SimpleDateFormat(TIME_FORMAT).format(blog.getReleaseDate()));
			document.setField("blog_content", blog.getContent());
			solrServer.add(document);
			solrServer.commit();
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
