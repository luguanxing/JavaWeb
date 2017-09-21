package dao;

import domain.User;

/**
 * Created by Administrator on 2017/9/19.
 */
public interface UserDao {
	
	void save(User user) throws Exception;

	User getByCode(String code) throws Exception;

	void update(User user) throws Exception;

	User getUserByUsernameAndPassword(String username, String password) throws Exception;

	User getUserByUsername(String username) throws Exception;
}
