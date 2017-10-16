package dao.impl;

import dao.RoadDao;
import domain.RoadItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DataSourceUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/10/7.
 */
public class RoadDaoImpl implements RoadDao {

	@Override
	public List getAll() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_roaditem ORDER BY date DESC";
		return queryRunner.query(sql, new BeanListHandler<>(RoadItem.class));
	}

	@Override
	public RoadItem getById(String rid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_roaditem WHERE rid = ?";
		return queryRunner.query(sql, new BeanHandler<>(RoadItem.class), rid);
	}

	@Override
	public void save(RoadItem ri) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO t_roaditem (rid, title, content, DATE, YEAR) VALUES (?, ?, ?, ?, ?)";
		queryRunner.update(sql, ri.getRid(), ri.getTitle(), ri.getContent(), ri.getDate(), ri.getYear());
	}

	@Override
	public void update(RoadItem ri) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE t_roaditem SET title = ?,  content = ? ,DATE = ? ,YEAR = ?  WHERE rid = ?";
		queryRunner.update(sql, ri.getTitle(), ri.getContent(), ri.getDate(), ri.getYear(), ri.getRid());
	}

	@Override
	public void deleteById(String rid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "DELETE FROM t_roaditem WHERE rid = ? ;";
		queryRunner.update(sql, rid);
	}

}
