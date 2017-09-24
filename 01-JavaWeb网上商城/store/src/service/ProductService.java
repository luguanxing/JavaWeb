package service;

import domain.PageBean;
import domain.Product;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public interface ProductService {
	
	List<Product> findHot() throws Exception;

	List<Product> findNew() throws Exception;

	Product getById(String pid) throws Exception;

	PageBean<Product> findByPage(int pageNumber, int pageSize, String cid) throws Exception;
	
}
