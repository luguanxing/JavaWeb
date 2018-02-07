package blog.service;

import blog.entity.Blogger;

/**
 * 博主Service
 * @author Administrator
 *
 */
public interface BloggerService {

	public Blogger getByUsername(String username);
	
	public Blogger getFirstBlogger();
	
	public Boolean updateFirstBlogger(Blogger blogger);
	
}
