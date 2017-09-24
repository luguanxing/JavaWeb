package service.impl;

import dao.CategoryDao;
import dao.impl.CategoryDaoImpl;
import domain.Category;
import service.CategoryService;
import utils.JsonUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public class CategoryServiceImpl implements CategoryService {
	
	@Override
	public String findAll() throws Exception {
		//调用DAO 查询所有分类
		CategoryDao dao = new CategoryDaoImpl();
		List<Category> list = dao.findAll();
		
		//转化成JSON
		if (list != null && list.size() > 0) {
			return JsonUtil.list2json(list);
		}
		return null;
	}
	
}
