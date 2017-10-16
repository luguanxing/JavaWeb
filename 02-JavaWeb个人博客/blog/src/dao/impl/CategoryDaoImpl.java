package dao.impl;

import dao.CategoryDao;
import domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DataSourceUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */
public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> getAll() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_category ORDER BY t_category.order DESC";
		return queryRunner.query(sql, new BeanListHandler<>(Category.class));
	}

	@Override
	public void save(Category c) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO t_category (cid, name, t_category.order) VALUES(?, ?, ?)";
		queryRunner.update(sql, c.getCid(), c.getName(), c.getOrder());
	}

	@Override
	public void update(Category c) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE t_category SET name = ?, t_category.order = ? WHERE cid = ?";
		queryRunner.update(sql,c.getName(), c.getOrder(), c.getCid());
	}

	@Override
	public void delete(String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "DELETE FROM t_category WHERE cid = ?";
		queryRunner.update(sql, cid);
	}

	@Override
	public Category getById(String cid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_category WHERE cid = ?";
		return queryRunner.query(sql, new BeanHandler<>(Category.class), cid);
	}

}
