package mail.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mail.dao.TargetDao;
import mail.entity.Target;
import mail.service.TargetService;

@Service("targetService")
public class TargetServiceImpl implements TargetService {

	@Autowired
	private TargetDao targetDao;
	
	@Override
	public List<Target> list(Map<String, Object> map) {
		return targetDao.list(map);
	}

	@Override
	public Target findById(Integer id) {
		return targetDao.findById(id);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return targetDao.getTotal(map);
	}

	@Override
	public Integer add(Target target) {
		return targetDao.add(target);
	}

	@Override
	public Integer update(Target target) {
		return targetDao.update(target);
	}

	@Override
	public Integer delete(Integer id) {
		return targetDao.delete(id);
	}

}
