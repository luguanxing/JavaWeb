package service.impl;

import dao.CategoryDao;
import domain.Category;
import service.CategoryService;
import utils.BeanFactory;
import utils.JsonUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */
public class CategoryServiceImpl implements CategoryService {
	
	@Override
	public List<Category> getAll() throws Exception {
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return dao.getAll();
	}

	@Override
	public String getAllByJson() throws Exception {
		List<Category> list = this.getAll();
		if (list != null && list.size() > 0) {
			return JsonUtil.list2json(list);
		}
		return null;
	}

}
