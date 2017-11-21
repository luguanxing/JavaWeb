package cms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cms.dao.ArcTypeDao;
import cms.entity.ArcType;
import cms.service.ArcTypeService;

/**
 * 帖子类别Service实现类
 * @author LGX
 *
 */
@Service("arcTypeService")
public class ArcTypeServiceImpl implements ArcTypeService {

	@Autowired
	private ArcTypeDao arcTypeDao;
	
	@Override
	public List<ArcType> list(Map<String, Object> map) {
		return arcTypeDao.list(map);
	}

	@Override
	public ArcType findById(Integer id) {
		return arcTypeDao.findById(id);
	}

}
