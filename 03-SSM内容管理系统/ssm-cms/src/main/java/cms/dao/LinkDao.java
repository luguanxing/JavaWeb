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
	
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	
	/**
	 * 添加友情链接
	 * @param link
	 * @return
	 */
	public Integer add(Link link);
	
	/**
	 * 修改友情链接
	 * @param link
	 * @return
	 */
	public Integer update(Link link);
	
	/**
	 * 删除友情链接
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
}
