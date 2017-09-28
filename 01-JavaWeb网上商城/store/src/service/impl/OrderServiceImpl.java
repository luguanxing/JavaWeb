package service.impl;

import dao.OrderDao;
import domain.Order;
import domain.OrderItem;
import service.OrderService;
import utils.BeanFactory;
import utils.DataSourceUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/9/28.
 */
public class OrderServiceImpl implements OrderService {

	@Override
	public void save(Order order) throws Exception {
		try {
			//获取dao
			OrderDao dao = (OrderDao) BeanFactory.getBean("OrderDao");

			//开启事务
			DataSourceUtils.startTransaction();

			//向t_order插入一条
			dao.save(order);

			//向t_orderItem插入多条
			for(OrderItem orderItem : order.getItems()) {
				dao.save(orderItem);
			}

			//事务控制
			DataSourceUtils.commitAndClose();
			
		} catch (SQLException e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}
	
}
