package cms.service;

import java.util.List;
import java.util.Map;

import cms.entity.Link;

/**
 * 友情连接Service
 * @author LGX
 *
 */
public interface LinkService {

	/**
	 * 根据条件查询友情连接
	 * @return
	 */
	public List<Link> list(Map<String, Object> map);
	
}
