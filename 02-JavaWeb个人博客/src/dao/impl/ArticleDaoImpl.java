package dao.impl;

import dao.ArticleDao;
import domain.Article;
import domain.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DataSourceUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
public class ArticleDaoImpl implements ArticleDao {

	@Override
	public List<Article> findByPage(PageBean<Article> pageBean) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_article ORDER BY date DESC LIMIT ?,?";
		return queryRunner.query(sql, new BeanListHandler<>(Article.class), pageBean.getStartIndex(), pageBean.getPageSize());
	}

	@Override
	public int getTotalRecord() throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT COUNT(*) FROM t_article";
		return ((Long)queryRunner.query(sql, new ScalarHandler())).intValue();
	}

	@Override
	public Article getById(String aid) throws Exception {
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM t_article where aid = ?";
		return queryRunner.query(sql, new BeanHandler<>(Article.class), aid);
	}

}
