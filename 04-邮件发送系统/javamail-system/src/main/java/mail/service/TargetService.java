package mail.service;

import java.util.List;
import java.util.Map;

import mail.entity.Target;

/**
 * 发送目标Service接口
 * @author LGX
 *
 */
public interface TargetService {

	/**
	 * 根据条件分页条件查询发送目标
	 * @param map
	 * @return
	 */
	public List<Target> list(Map<String, Object> map);
	
	/**
	 * 根据id查询发送目标
	 * @param id
	 * @return
	 */
	public Target findById(Integer id);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);
	
	/**
	 * 添加发送目标
	 * @param MailSource
	 * @return
	 */
	public Integer add(Target target);
	
	/**
	 * 修改发送目标
	 * @param MailSource
	 * @return
	 */
	public Integer update(Target target);
	
	/**
	 * 删除发送目标
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
}
