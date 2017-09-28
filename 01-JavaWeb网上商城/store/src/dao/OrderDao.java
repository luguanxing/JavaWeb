package dao;

import domain.Order;
import domain.OrderItem;

/**
 * Created by Administrator on 2017/9/28.
 */
public interface OrderDao {
	
	void save(Order order) throws Exception;

	void save(OrderItem orderItem) throws Exception;
	
}
