package service;

import domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */
public interface CategoryService {

	List<Category> getAll() throws Exception;

	String getAllByJson() throws Exception;

	void save(Category category) throws Exception;

	void update(Category category) throws Exception;

	void delete(String cid) throws Exception;

	Category getById(String cid) throws Exception;
	
}
