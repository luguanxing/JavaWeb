package cms.dao;

import cms.entity.Manager;

/**
 * 管理员DAO接口
 * @author LGX
 *
 */
public interface ManagerDao {

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
