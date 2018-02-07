package blog.service;

import java.util.List;

import blog.entity.Blog;

public interface BlogService {

	public List<Blog> getPriorityBlogs();
	
	public List<Blog> getBlogs(Integer pageNum, Integer pageSize);
	
	public List<Blog> getBlogsBytypeId(Integer typeId, Integer pageNum, Integer pageSize);
	
	public Integer getBlogsCount();

	public Integer getBlogsCount(Integer typeIdNum);

	public Blog getBlogById(Integer blogIdNum);
	
	List<Blog> getSearchBlogs(String keyword, Integer startRow, Integer pageSize);
	
	Integer getSearchBlogsCount(String keyword);
	
	Boolean importAllBlogs();
	
	Boolean addBlog(Blog blog);
	
	Boolean deleteBlog(Integer id);
	
	Boolean updateBlog(Blog blog);
	
}
