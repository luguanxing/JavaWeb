package service;

import domain.Category;

import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */
public interface CategoryService {

	List<Category> getAll() throws Exception;

	String getAllByJson() throws Exception;
	
}
