package dao.impl;

import dao.OrderDao;
import domain.Order;
import domain.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import utils.DataSourceUtils;

/**
 * Created by Administrator on 2017/9/28.
 */
public class OrderDaoImpl implements OrderDao {
	
	@Override
	public void save(Order o) throws Exception {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "INSERT INTO t_orders VALUES (?,?,?,?,?,?,?,?)";
		queryRunner.update(DataSourceUtils.getConnection(), sql,
				o.getOid(), o.getOrdertime(), o.getTotal(), o.getState(),
				o.getAddress(), o.getName(), o.getTelephone(), o.getUser().getUid());
	}

	@Override
	public void save(OrderItem oi) throws Exception {
		QueryRunner queryRunner = new QueryRunner();
		String sql = "INSERT INTO orderitem VALUES (?,?,?,?,?)";
		queryRunner.update(DataSourceUtils.getConnection(), sql,
				oi.getItemid(), oi.getCount(), oi.getSubtotal(),
				oi.getProduct().getPid(), oi.getOrder().getOid());
	}
	
}
