package cms.service;

import cms.entity.Manager;

/**
 * 管理员Service接口
 * @author LGX
 *
 */
public interface ManagerService {

	/**
	 * 通过用户名查询用户
	 * @param username
	 * @return
	 */
	public Manager getByUsername(String username);
	
}
