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

}
