package service.impl;

import dao.AdminDao;
import domain.Admin;
import service.AdminService;
import utils.BeanFactory;

/**
 * Created by Administrator on 2017/10/16.
 */
public class AdminServiceImpl implements AdminService {

	@Override
	public Boolean isAdmin(String username, String password) throws Exception {
		AdminDao dao = (AdminDao) BeanFactory.getBean("AdminDao");
		Admin admin = dao.getAdmin(username, password);
		return (admin != null);
	}
	
}
