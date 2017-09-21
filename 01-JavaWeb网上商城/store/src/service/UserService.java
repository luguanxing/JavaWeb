package service;

import domain.User;

/**
 * Created by Administrator on 2017/9/19.
 */
public interface UserService {
	
	void regist(User user) throws Exception;

	String isRegistable(User user);

	User active(String code) throws Exception;

	User login(String username, String password) throws Exception;
	
}
