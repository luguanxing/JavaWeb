package dao.impl;

import dao.CategoryDao;
import domain.Category;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DataSourceUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/9/24.
 */
public class CategoryDaoImpl implements CategoryDao {
	
	@Override
	public List<Category> findAll() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_category";
		return queryRunner.query(sql, new BeanListHandler<>(Category.class));
	}

	@Override
	public void add(Category category) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO t_category VALUES (?, ?)";
		queryRunner.update(sql, category.getCid(), category.getCname());
	}

}
