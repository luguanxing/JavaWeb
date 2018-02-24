package news.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import news.dao.AdminerDao;
import news.entity.Adminer;
import news.entity.AdminerExample;
import news.entity.AdminerExample.Criteria;
import news.service.AdminerService;

@Service
public class AdminerServiceImpl implements AdminerService {

	@Autowired
	private AdminerDao adminerDao;
	
	@Override
	public Adminer getFirstAdminer() {
		AdminerExample example = new AdminerExample();
		Criteria criteria = example.createCriteria();
		//只有一位管理员，直接选第一位
		List<Adminer> adminers = adminerDao.selectByExample(example);
		if (!adminers.isEmpty()) {
			return adminers.get(0);
		}
		return null;
	}

	@Override
	public Boolean updateFirstAdminer(Adminer adminer) {
		int count = adminerDao.updateByPrimaryKey(adminer);
		return count > 0;
	}
	
	@Override
	public Adminer getByUsername(String username) {
		AdminerExample example = new AdminerExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<Adminer> adminers = adminerDao.selectByExample(example);
		if (adminers.isEmpty())
			return null;
		return adminers.get(0);
	}

	@Override
	public Integer update(Adminer adminer) {
		return adminerDao.updateByPrimaryKey(adminer);
	}

}
