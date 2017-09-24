package dao;

import domain.PageBean;
import domain.Product;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public interface ProductDao {
	List<Product> findHot() throws Exception;

	List<Product> findNew() throws Exception;

	Product getById(String pid) throws Exception;

	List<Product> findByPage(PageBean<Product> pageBean, String cid) throws Exception;

	int getTotalRecord(String cid) throws Exception;
}
