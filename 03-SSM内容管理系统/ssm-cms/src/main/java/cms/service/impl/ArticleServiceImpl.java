package cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cms.dao.ArticleDao;
import cms.entity.Article;
import cms.entity.Link;
import cms.service.ArticleService;

/**
 * 帖子Service实现类
 * @author LGX
 *
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public List<Article> getNewest() {
		return articleDao.getNewest();
	}

	@Override
	public List<Article> getRecommend() {
		return articleDao.getRecommend();
	}

	@Override
	public List<Article> getSlide() {
		return articleDao.getSlide();
	}

	@Override
	public List<Article> getIndex(Integer typeId) {
		return articleDao.getIndex(typeId);
	}

	@Override
	public Article getById(Integer id) {
		return articleDao.getById(id);
	}

	@Override
	public Article getLastArticle(Integer id) {
		return articleDao.getLastArticle(id);
	}

	@Override
	public Article getNextArticle(Integer id) {
		return articleDao.getNextArticle(id);
	}

	@Override
	public Integer update(Article article) {
		return articleDao.update(article);
	}

}
