package cms.service;

import java.util.List;
import java.util.Map;

import cms.entity.ArcType;

/**
 * 帖子类别Service
 * @author LGX
 *
 */
public interface ArcTypeService {

	/**
	 * 根据条件分页条件查询帖子类别
	 * @param map
	 * @return
	 */
	public List<ArcType> list(Map<String, Object> map);
	
}
