package dao.impl;

import dao.UserDao;
import domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.DataSourceUtils;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/9/19.
 */
public class UserDaoImpl implements UserDao {
	
	@Override
	public void save(User user) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO t_user VALUES(?,?,?,?,?,?,?,?,?,?)";
		queryRunner.update(sql, user.getUid(), user.getUsername(), user.getPassword(),
				user.getName(), user.getEmail(), user.getTelephone(), user.getBirthday(),
				user.getSex(), user.getState(), user.getCode());
		
	}

	@Override
	public User getByCode(String code) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_user WHERE code = ? LIMIT 1";
		return queryRunner.query(sql ,new BeanHandler<>(User.class), code);
	}

	@Override
	public void update(User user) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE t_user SET password = ?, telephone = ?, birthday = ?, state = ?, code = ? WHERE uid = ?";
		queryRunner.update(sql, user.getPassword(), user.getTelephone(), user.getBirthday(), user.getState(), user.getCode(), user.getUid());
	}

	@Override
	public User getUserByUsernameAndPassword(String username, String password) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_user WHERE username = ? AND password = ? LIMIT 1";
		return queryRunner.query(sql, new BeanHandler<>(User.class), username, password);
	}

	@Override
	public User getUserByUsername(String username) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_user WHERE username = ? LIMIT 1";
		return queryRunner.query(sql, new BeanHandler<>(User.class), username);
	}


}

