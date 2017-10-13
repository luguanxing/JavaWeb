package dao.impl;

import dao.RoadDao;
import domain.RoadItem;
import org.apache.commons.dbutils.QueryRunner;
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
	
}
