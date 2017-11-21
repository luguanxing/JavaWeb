package cms.dao;

import java.util.List;
import java.util.Map;

import cms.entity.ArcType;
import cms.entity.Article;
import cms.entity.Link;

/**
 * 帖子DAO
 * @author LGX
 *
 */
public interface ArticleDao {

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
	
	/**
	 * 根据帖子类别查找最新8条数据
	 * @param typeId
	 * @return
	 */
	public List<Article> getIndex(Integer typeId);
	
	/**
	 * 通过id查询帖子
	 * @param id
	 * @return
	 */
	public Article getById(Integer id);
	
	/**
	 * 获取上一个帖子
	 * @param id
	 * @return
	 */
	public Article getLastArticle(Integer id);
	
	/**
	 * 获取下一个帖子
	 * @param id
	 * @return
	 */
	public Article getNextArticle(Integer id);
	
	/**
	 * 更新帖子
	 * @param article
	 * @return
	 */
	public Integer update(Article article);
	
}
