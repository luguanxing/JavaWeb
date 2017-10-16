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
	public List<Project> findByPage(PageBean<Project> pageBean) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_project ORDER BY date DESC LIMIT ?,?";
		return queryRunner.query(sql, new BeanListHandler<>(Project.class), pageBean.getStartIndex(), pageBean.getPageSize());
	}

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
		String sql = "UPDATE t_project SET title = ? , description = ? , imagepath = ? , DATE = ? ," +
				" TYPE = ? , click = ? , link = ? WHERE pid = ?";
		queryRunner.update(sql, p.getTitle(), p.getDescription(), p.getImagepath(),
				p.getDate(), p.getType(), p.getClick(), p.getLink(), p.getPid());
	}

	@Override
	public List<Project> findTheLastest(int projectIndexCount) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_project ORDER BY date DESC LIMIT ?";
		return queryRunner.query(sql, new BeanListHandler<>(Project.class), projectIndexCount);
	}

	@Override
	public void save(Project p) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO t_project (pid, title, description, imagepath, DATE, TYPE, click, link)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		queryRunner.update(sql, p.getPid(), p.getTitle(), p.getDescription(), p.getImagepath(),
				p.getDate(), p.getType(), p.getClick(), p.getLink());
	}

	@Override
	public void delete(String pid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "DELETE FROM blog.t_project WHERE pid = ?";
		queryRunner.update(sql, pid);
	}

}
