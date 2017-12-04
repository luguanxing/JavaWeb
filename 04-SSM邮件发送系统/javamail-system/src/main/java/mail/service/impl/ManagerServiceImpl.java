package mail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mail.dao.ManagerDao;
import mail.entity.Manager;
import mail.service.ManagerService;

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

	@Override
	public Integer update(Manager manager) {
		return managerDao.update(manager);
	}
	
}
