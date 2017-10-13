package dao.impl;

import constant.Constant;
import dao.CommentDao;
import domain.Comment;
import domain.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/10/9.
 */
public class CommentDaoImpl implements CommentDao {
	
	@Override
	public List<Comment> findByPage(PageBean<Comment> pageBean) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_comment ORDER BY date DESC LIMIT ?,?";
		return queryRunner.query(sql, new BeanListHandler<>(Comment.class), pageBean.getStartIndex(), pageBean.getPageSize());
	}

	@Override
	public int getTotalRecord() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT COUNT(*) FROM t_comment";
		return ((Long)queryRunner.query(sql, new ScalarHandler())).intValue();
	}

	@Override
	public void addComment(Comment c) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO t_comment (nickname, COMMENT, DATE) VALUES (?, ?, ?);";
		queryRunner.update(sql, c.getNickname(), c.getComment(), c.getDate());
	}

}
