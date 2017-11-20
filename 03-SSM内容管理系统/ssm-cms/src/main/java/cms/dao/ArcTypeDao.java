package cms.dao;

import java.util.List;
import java.util.Map;

import cms.entity.ArcType;

/**
 * 帖子类别DAO
 * @author LGX
 *
 */
public interface ArcTypeDao {

	/**
	 * 根据条件分页条件查询帖子类别
	 * @param map
	 * @return
	 */
	public List<ArcType> list(Map<String, Object> map);
	
	/**
	 * 根据id查询帖子 类别
	 * @param id
	 * @return
	 */
	public ArcType findById(Integer id);
	
}
