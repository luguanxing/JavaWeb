package dao.impl;

import dao.CategoryDao;
import domain.Category;
import org.apache.commons.dbutils.QueryRunner;
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
	
}
