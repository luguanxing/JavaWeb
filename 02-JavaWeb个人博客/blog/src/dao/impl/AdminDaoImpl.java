package dao.impl;

import dao.AdminDao;
import domain.Admin;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.DataSourceUtils;

/**
 * Created by Administrator on 2017/10/16.
 */
public class AdminDaoImpl implements AdminDao {

	@Override
	public Admin getAdmin(String username, String password) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_admin where username = ? AND password = ?";
		return queryRunner.query(sql, new BeanHandler<>(Admin.class), username, password);
	}

}
