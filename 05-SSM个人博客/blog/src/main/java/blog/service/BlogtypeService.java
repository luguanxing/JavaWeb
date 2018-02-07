package blog.service;

import java.util.List;

import blog.entity.Blogtype;

public interface BlogtypeService {

	public List<Blogtype> getBlogtypesBySortNo();
	
	public Blogtype getBlogtypeById(Integer blogtypeIdNum);
	
	public Blogtype getBlogtypeByTypeName(String typeName);
	
	public List<Blogtype> getBlogtypes(Integer pageNum, Integer pageSize);
	
	public Boolean addBlogtype(Blogtype blogtype);
	
	public Boolean updateBlogtype(Blogtype blogtype);
	
	public Boolean deleteBlogtype(Integer id);
	
}
