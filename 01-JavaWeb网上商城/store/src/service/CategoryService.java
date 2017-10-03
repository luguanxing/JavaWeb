package service;

import domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public interface CategoryService {
	
	String findAll() throws Exception;

	List<Category> findList() throws Exception;

	void add(Category category) throws Exception;
}
