package cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cms.dao.ManagerDao;
import cms.entity.Manager;
import cms.service.ManagerService;

/**
 * 管理员Service实现类
 * @author LGX
 *
 */
@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

	@Autowired
	private ManagerDao managerDao;

	@Override
	public Manager getByUsername(String username) {
		return managerDao.getByUsername(username);
	}
	
}
