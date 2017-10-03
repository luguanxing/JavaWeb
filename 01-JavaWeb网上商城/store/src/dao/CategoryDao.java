package dao;

import domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public interface CategoryDao {
	
	List<Category> findAll() throws Exception;

	void add(Category category) throws Exception;

	void delete(String cid) throws Exception;

	Category findById(String cid) throws Exception;

	void editCategory(String cid, String cname) throws Exception;
	
}
