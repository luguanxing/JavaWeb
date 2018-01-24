package blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.dao.BloggerDao;
import blog.entity.Blogger;
import blog.entity.BloggerExample;
import blog.entity.BloggerExample.Criteria;
import blog.service.BloggerService;

@Service
public class BloggerServiceImpl implements BloggerService {

	@Autowired
	private BloggerDao bloggerDao;
	
	@Override
	public Blogger getByUsername(String username) {
		BloggerExample example = new BloggerExample();
		Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<Blogger> bloggers = bloggerDao.selectByExample(example);
		if (bloggers.isEmpty())
			return null;
		return bloggers.get(0);
	}

}
