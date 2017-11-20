package cms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cms.dao.LinkDao;
import cms.entity.Link;
import cms.service.LinkService;

/**
 * 友情连接service实现类
 * @author LGX
 *
 */
@Service("linkService")
public class LinkServiceImpl implements LinkService {

	@Autowired
	private LinkDao linkDao;
	
	
	@Override
	public List<Link> list(Map<String, Object> map) {
		return linkDao.list(map);
	}

}
