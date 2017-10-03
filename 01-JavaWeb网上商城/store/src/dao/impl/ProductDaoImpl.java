package dao.impl;

import constant.Constant;
import dao.ProductDao;
import domain.PageBean;
import domain.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findHot() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_product WHERE is_hot = ? AND pflag = ? ORDER BY pdate DESC LIMIT ?";
		return queryRunner.query(sql, new BeanListHandler<>(Product.class), Constant.PRODUCT_IS_HOT, Constant.PRODUCT_IS_UP, Constant.PRODUCT_COUNT);
	}

	@Override
	public List<Product> findNew() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_product WHERE pflag = ? ORDER BY pdate DESC LIMIT ?";
		return queryRunner.query(sql, new BeanListHandler<>(Product.class), Constant.PRODUCT_IS_UP, Constant.PRODUCT_COUNT);
	}

	@Override
	public Product getById(String pid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_product WHERE pid = ? LIMIT 1";
		return queryRunner.query(sql, new BeanHandler<>(Product.class), pid);
	}

	@Override
	public List<Product> findByPage(PageBean<Product> pageBean, String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_product WHERE cid = ? AND pflag = ? ORDER BY pdate DESC LIMIT ?,?";
		return queryRunner.query(sql, new BeanListHandler<>(Product.class), cid, Constant.PRODUCT_IS_UP, pageBean.getStartIndex(), pageBean.getPageSize());
	}

	@Override
	public int getTotalRecord(String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT COUNT(*) FROM t_product WHERE cid = ? AND pflag = ?";
		return ((Long)queryRunner.query(sql, new ScalarHandler(), cid, Constant.PRODUCT_IS_UP)).intValue();
	}

	@Override
	public void addProduct(Product p) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO t_product VALUES(?,?,?,?,?,?,?,?,?,?)";
		queryRunner.update(sql, p.getPid(), p.getPname(), p.getMarket_price(),
				p.getShop_price(), p.getPimage(), p.getPdate(), p.getIs_hot(),
				p.getPdesc(), p.getPflag(), p.getCategory().getCid());
	}

	@Override
	public List<Product> findAll() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_product WHERE pflag = ? ORDER BY pdate DESC";
		return queryRunner.query(sql, new BeanListHandler<>(Product.class), Constant.PRODUCT_IS_UP);
	}

}
