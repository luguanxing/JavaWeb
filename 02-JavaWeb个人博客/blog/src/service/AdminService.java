package service;

import domain.Admin;

/**
 * Created by Administrator on 2017/10/16.
 */
public interface AdminService {

	Boolean isAdmin(String username, String password) throws Exception;
	
}
