package dao;

import domain.Order;
import domain.OrderItem;
import domain.PageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */
public interface OrderDao {
	
	void save(Order order) throws Exception;

	void save(OrderItem orderItem) throws Exception;

	int getTotalRecord(String uid) throws Exception;

	List<Order> findMyOrderByPage(PageBean<Order> pagebean, String uid) throws Exception;

	Order getById(String oid) throws Exception;

	void update(Order order) throws Exception;

	String getUidByOid(String oid) throws Exception;

	List<Order> findAllByState(String state) throws Exception;
}
