package service;

import domain.Order;
import domain.PageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */
public interface OrderService {
	
	void save(Order order) throws Exception;

	PageBean<Order> findMyOrderByPage(int pageNumber, int pageSize, String uid) throws Exception;

	Order getById(String oid) throws Exception;

	void update(Order order) throws Exception;

	boolean isMyOrder(String oid, String uid) throws Exception;

	List<Order> findAllByState(String state) throws Exception;
}
