package dao;

import domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public interface CategoryDao {
	
	List<Category> findAll() throws Exception;

	void add(Category category) throws Exception;
}
