package mail.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mail.dao.EmailSourceDao;
import mail.entity.EmailSource;
import mail.service.EmailSourceService;

@Service("mailSourceService")
public class EmailSourceServiceImpl implements EmailSourceService {

	@Autowired
	private EmailSourceDao mailSourceDao;
	
	@Override
	public List<EmailSource> list(Map<String, Object> map) {
		return mailSourceDao.list(map);
	}

	@Override
	public EmailSource findById(Integer id) {
		return mailSourceDao.findById(id);
	}

	@Override
	public Long getTotal(Map<String, Object> map) {
		return mailSourceDao.getTotal(map);
	}

	@Override
	public Integer add(EmailSource MailSource) {
		return mailSourceDao.add(MailSource);
	}

	@Override
	public Integer update(EmailSource MailSource) {
		return mailSourceDao.update(MailSource);
	}

	@Override
	public Integer delete(Integer id) {
		return mailSourceDao.delete(id);
	}

}
