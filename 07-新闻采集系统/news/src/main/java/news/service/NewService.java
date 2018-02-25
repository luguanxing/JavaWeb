package news.service;

import java.util.List;

import news.entity.New;

public interface NewService {

	New getNewById(Integer id);
	
	Integer getNewsCount();
	
	List<New> getNewsByCrawlerDate(Integer pageNum, Integer pageSize);
	
	List<New> getNewsByPublishDateAndSrc(Integer pageNum, Integer pageSize);
	
	List<New> getNewsByCommentCount(Integer pageNum, Integer pageSize);
	
	List<New> getHotNews(Integer pageNum, Integer pageSize);
	
//	List<New> getSearchNews(String keyword, Integer startRow, Integer pageSize);
//	
//	Integer getSearchNewsCount(String keyword);
	
	Integer getNewIdByUrl(String url);
	
	Boolean addNew(New newObj);
	
	Boolean updateNew(New newObj);
	
	Boolean deleteNewById(Integer id);

	Boolean importRedis();

	Boolean importSolr();
	
}
