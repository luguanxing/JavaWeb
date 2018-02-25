package news.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import news.entity.New;
import news.service.CrawlerService;

@Service
public class MyTimingCrawler {

	private static Logger logger = Logger.getLogger(MyTimingCrawler.class);
	
	@Autowired
	private CrawlerService crawlerService;
	
	public void startCrawl() {  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.warn(sdf.format(new Date()) + " 进行爬取");
		List<New> news = crawlerService.getNewsUrlsAndComments();
		for (New newObj : news) {
			logger.warn("================");
			logger.warn("获取" + newObj.getUrl());
			newObj = crawlerService.getNewFromUrlAndComment(newObj);
			if (newObj == null) {
				logger.warn("不是正常的新闻格式，进行忽略");
			} else {
				logger.warn("保存或更新" + newObj.getUrl());
				crawlerService.saveNewToLocal(newObj);
			}
			logger.warn("================");
			logger.warn("");
		}
		logger.warn(sdf.format(new Date()) + " 爬取结束");
		logger.warn("");
		logger.warn("");
	}
}
