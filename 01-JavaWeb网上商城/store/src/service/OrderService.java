package service;

import domain.Order;

/**
 * Created by Administrator on 2017/9/28.
 */
public interface OrderService {
	
	void save(Order order) throws Exception;
	
}
