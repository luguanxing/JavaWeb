package dao;

import domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */
public interface CategoryDao {

	List<Category> getAll() throws Exception;

	void save(Category category) throws Exception;

	void update(Category category) throws Exception;

	void delete(String cid) throws Exception;

	Category getById(String cid) throws Exception;
	
}
