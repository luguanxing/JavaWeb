package dao.impl;

import dao.IndexDao;
import domain.Index;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.DataSourceUtils;

/**
 * Created by Administrator on 2017/10/14.
 */
public class IndexDaoImpl implements IndexDao {

	@Override
	public Index IndexContent() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_index LIMIT 1";
		return queryRunner.query(sql, new BeanHandler<>(Index.class));
	}

	@Override
	public void update(Index index) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE t_index SET content = ?";
		queryRunner.update(sql, index.getContent());
	}

}
