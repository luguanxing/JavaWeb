package mail.dao;

import java.util.List;
import java.util.Map;

import mail.entity.EmailSource;

/**
 * 邮件源别DAO
 * @author LGX
 *
 */
public interface EmailSourceDao {

	/**
	 * 根据条件分页条件查询邮件源
	 * @param map
	 * @return
	 */
	public List<EmailSource> list(Map<String, Object> map);
	
	/**
	 * 根据id查询邮件源
	 * @param id
	 * @return
	 */
	public EmailSource findById(Integer id);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);
	
	/**
	 * 添加邮件源
	 * @param MailSource
	 * @return
	 */
	public Integer add(EmailSource MailSource);
	
	/**
	 * 修改邮件源
	 * @param MailSource
	 * @return
	 */
	public Integer update(EmailSource MailSource);
	
	/**
	 * 删除邮件源
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
}
