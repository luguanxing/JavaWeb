package mail.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mail.dao.ScheduleDao;
import mail.entity.Schedule;
import mail.service.ScheduleService;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	private ScheduleDao scheduleDao;
	
	@Override
	public List<Schedule> list(Map<String, Object> map) {
		return scheduleDao.list(map);
	}

	@Override
	public Schedule findById(Integer id) {
		return scheduleDao.findById(id);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return scheduleDao.getTotal(map);
	}

	@Override
	public Integer add(Schedule schedule) {
		return scheduleDao.add(schedule);
	}

	@Override
	public Integer update(Schedule schedule) {
		return scheduleDao.update(schedule);
	}

	@Override
	public Integer delete(Integer id) {
		return scheduleDao.delete(id);
	}

	@Override
	public void deleteTime() {
		scheduleDao.deleteTime();
	}

}
