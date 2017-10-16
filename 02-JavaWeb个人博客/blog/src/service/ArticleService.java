package service;

import domain.Article;
import domain.PageBean;

/**
 * Created by Administrator on 2017/10/10.
 */
public interface ArticleService {
	
	PageBean<Article> findByPage(int pageNumber, int articlePageCount) throws Exception;

	Article getById(String aid) throws Exception;

	void update(Article article) throws Exception;

	void save(Article article) throws Exception;

	void delete(String aid) throws Exception;
}
