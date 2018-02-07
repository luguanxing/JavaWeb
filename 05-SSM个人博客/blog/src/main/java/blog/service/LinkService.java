package blog.service;

import java.util.List;

import blog.entity.Link;

/**
 * 友情链接Service
 * @author Administrator
 *
 */
public interface LinkService {

	public List<Link> getLinksBySortNo();
	
	public List<Link> getLinks(Integer pageNum, Integer pageSize);
	
	public Integer getLinksCount();
	
	public Boolean addLink(Link link);

	public Boolean updateLink(Link link);

	public Boolean deleteLink(Integer id);
	
}
