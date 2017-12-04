package mail.service;

import java.util.List;
import java.util.Map;

import mail.entity.Schedule;

/**
 * 计划表Service接口
 * @author Administrator
 *
 */
public interface ScheduleService {

	/**
	 * 根据条件分页条件查询计划
	 * @param map
	 * @return
	 */
	public List<Schedule> list(Map<String, Object> map);
	
	/**
	 * 根据id查询计划
	 * @param id
	 * @return
	 */
	public Schedule findById(Integer id);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);
	
	/**
	 * 添加计划
	 * @param MailSource
	 * @return
	 */
	public Integer add(Schedule schedule);
	
	/**
	 * 修改计划
	 * @param MailSource
	 * @return
	 */
	public Integer update(Schedule schedule);
	
	/**
	 * 删除计划
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
	/**
	 * 清除所有lastsendtime
	 * @param id
	 * @return
	 */
	public void deleteTime();
	
}
