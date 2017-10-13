package dao;

import domain.Article;
import domain.PageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */
public interface ArticleDao {
	
	List<Article> findByPage(PageBean<Article> pageBean) throws Exception;

	int getTotalRecord() throws Exception;

	Article getById(String aid) throws Exception;

	void update(Article article) throws Exception;
	
}
