package dao;

import domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */
public interface CategoryDao {

	List<Category> getAll() throws Exception;
	
}
