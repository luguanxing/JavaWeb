package dao.impl;

import dao.OrderDao;
import domain.Order;
import domain.OrderItem;
import domain.PageBean;
import domain.Product;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.util.List;
import java.util.Map;

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

	@Override
	public int getTotalRecord(String uid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT COUNT(*) FROM t_orders WHERE uid = ?";
		return ((Long)queryRunner.query(sql, new ScalarHandler(), uid)).intValue();
	}

	@Override
	public List<Order> findMyOrderByPage(PageBean<Order> pagebean, String uid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		
		//查询所有订单(基本信息)
		String sql = "SELECT * FROM t_orders WHERE uid = ? ORDER BY ordertime DESC LIMIT ?,?";
		List<Order> orders = queryRunner.query(sql, new BeanListHandler<>(Order.class), uid, pagebean.getStartIndex(), pagebean.getPageSize());
		
		//遍历订单集合查询订单项及其商品共和信息map
		for (Order order : orders) {
			sql = "SELECT * FROM orderitem oi, t_product p WHERE oi.pid = p.pid AND oi.oid = ?";
			List<Map<String, Object>> maplist = queryRunner.query(sql, new MapListHandler(), order.getOid());
			//遍历maplist 获取订单项详情并封装，并将其加入当前订单的订单向列表中
			for (Map<String, Object> map : maplist) {
				//封装成orderitem, product
				OrderItem oi = new OrderItem();
				BeanUtils.populate(oi, map);
				Product p = new Product();
				BeanUtils.populate(p, map);	//手动封装product
				oi.setProduct(p);
				
				//加入到order的订单项列表中
				order.getItems().add(oi);
			}
		}
		return orders;
	}

	@Override
	public Order getById(String oid) throws Exception {
		//查询订单基本信息
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_orders WHERE oid = ? LIMIT 1";
		Order order = queryRunner.query(sql, new BeanHandler<>(Order.class), oid);
		
		//查询订单的订单项
		sql = "SELECT * FROM orderitem oi, t_product p WHERE oi.pid = p.pid AND oi.oid = ?";
		List<Map<String, Object>> maplist = queryRunner.query(sql, new MapListHandler(), order.getOid());
		//遍历maplist 获取订单项详情并封装，并将其加入当前订单的订单向列表中
		for (Map<String, Object> map : maplist) {
			//封装成orderitem, product
			OrderItem oi = new OrderItem();
			BeanUtils.populate(oi, map);
			Product p = new Product();
			BeanUtils.populate(p, map);	//手动封装product
			oi.setProduct(p);

			//加入到order的订单项列表中
			order.getItems().add(oi);
		}
		return order;
	}

	@Override
	public void update(Order order) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE t_orders SET state = ?, address = ?, name = ?, telephone = ? WHERE oid = ?";
		queryRunner.update(sql, order.getState(), order.getAddress(), order.getName(), order.getTelephone(), order.getOid());
	}

	@Override
	public String getUidByOid(String oid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT uid FROM t_orders WHERE oid = ?";
		return (String) queryRunner.query(sql, new ScalarHandler(), oid);
	}

}
