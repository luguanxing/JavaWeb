package cms.dao;

import java.util.List;
import java.util.Map;

import cms.entity.Link;

/**
 * 友情连接DAO
 * @author LGX
 *
 */
public interface LinkDao {

	/**
	 * 根据条件查询友情链接
	 * @return
	 */
	public List<Link> list(Map<String, Object> map);
	
}
