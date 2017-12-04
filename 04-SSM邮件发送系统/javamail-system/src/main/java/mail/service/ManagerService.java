package mail.service;

import mail.entity.Manager;

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
	
	/**
	 * 更新管理员信息
	 * @param manager
	 * @return
	 */
	public Integer update(Manager manager);
	
}
