package service.impl;

import dao.CategoryDao;
import domain.Category;
import service.CategoryService;
import utils.BeanFactory;
import utils.JsonUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public class CategoryServiceImpl implements CategoryService {
	
	@Override
	public List<Category> findList() throws Exception {
		//调用DAO 查询所有分类
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		List<Category> list = dao.findAll();
		return list;
	}

	@Override
	public String findAll() throws Exception {
		//调用DAO 查询所有分类
		List<Category> list = this.findList();
		
		//转化成JSON
		if (list != null && list.size() > 0) {
			return JsonUtil.list2json(list);
		}
		return null;
	}


	@Override
	public void add(Category category) throws Exception {
		//添加分类
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		dao.add(category);
	}

	@Override
	public void delete(String cid) throws Exception {
		//删除分类
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		dao.delete(cid);
	}

	@Override
	public Category findById(String cid) throws Exception {
		//查询分类
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return dao.findById(cid);
	}

	@Override
	public void editCategory(String cid, String cname) throws Exception {
		//修改分类
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		dao.editCategory(cid, cname);
	}
	
}
