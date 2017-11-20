package cms.service;

import java.util.List;

import cms.entity.Article;
import cms.entity.Link;

/**
 * 帖子Service 
 * @author LGX
 *
 */
public interface ArticleService {

	/**
	 * 查询最新的7条帖子
	 * @return
	 */
	public List<Article> getNewest();
	
	/**
	 * 获取最新7条推荐帖子
	 * @return
	 */
	public List<Article> getRecommend();
	
	/**
	 * 获取最新的5条幻灯片
	 * @return
	 */
	public List<Article> getSlide();
	
}
