package dao;

import domain.Admin;

/**
 * Created by Administrator on 2017/10/16.
 */
public interface AdminDao {
	
	Admin getAdmin(String username, String password) throws Exception;
	
}
