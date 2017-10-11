package dao.impl;

import dao.ProjectDao;
import domain.PageBean;
import domain.Project;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/10/11.
 */
public class ProjectDaoImpl implements ProjectDao {
	
	@Override
	public List<Project> findByPage(PageBean<Project> pageBean, String type) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_project WHERE type = ? ORDER BY date DESC LIMIT ?,?";
		return queryRunner.query(sql, new BeanListHandler<>(Project.class), type, pageBean.getStartIndex(), pageBean.getPageSize());
	}

	@Override
	public int getTotalRecord() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT COUNT(*) FROM t_project";
		return ((Long)queryRunner.query(sql, new ScalarHandler())).intValue();
	}

	@Override
	public Project getById(String pid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_project where pid = ?";
		return queryRunner.query(sql, new BeanHandler<>(Project.class), pid);
	}

	@Override
	public void update(Project p) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE blog.t_project SET title = ? , description = ? , imagepath = ? , DATE = ? ," +
				" TYPE = ? , click = ? , link = ? WHERE pid = ?";
		queryRunner.update(sql, p.getTitle(), p.getDescription(), p.getImagepath(),
				p.getDate(), p.getType(), p.getClick(), p.getLink(), p.getPid());
	}

}
