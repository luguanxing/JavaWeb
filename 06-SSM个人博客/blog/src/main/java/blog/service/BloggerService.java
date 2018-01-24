package blog.service;

import blog.entity.Blogger;

/**
 * 博主Service
 * @author Administrator
 *
 */
public interface BloggerService {

	public Blogger getByUsername(String username);
	
}
