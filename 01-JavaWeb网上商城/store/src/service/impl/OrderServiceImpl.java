package service.impl;

import dao.OrderDao;
import domain.Order;
import domain.OrderItem;
import domain.PageBean;
import service.OrderService;
import utils.BeanFactory;
import utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;

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

	@Override
	public PageBean<Order> findMyOrderByPage(int pageNumber, int pageSize, String uid) throws Exception {
		//创建pagebean
		PageBean<Order> pagebean = new PageBean<>(pageNumber, pageSize);
		
		//查询和设置总条数
		OrderDao dao = (OrderDao) BeanFactory.getBean("OrderDao");
		int totalRecord = dao.getTotalRecord(uid);
		pagebean.setTotalRecord(totalRecord);
		
		//设置当前页数据
		List<Order> data = dao.findMyOrderByPage(pagebean, uid);
		pagebean.setData(data);
		return pagebean;
	}

	@Override
	public Order getById(String oid) throws Exception {
		OrderDao dao = (OrderDao) BeanFactory.getBean("OrderDao");
		return dao.getById(oid);
	}

	@Override
	public void update(Order order) throws Exception {
		OrderDao dao = (OrderDao) BeanFactory.getBean("OrderDao");
		dao.update(order);
	}

	@Override
	public boolean isMyOrder(String oid, String uid) throws Exception {
		OrderDao dao = (OrderDao) BeanFactory.getBean("OrderDao");
		String db_uid = dao.getUidByOid(oid);
		return db_uid.equals(uid);
	}


}
