package news.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import news.entity.New;
import news.service.CrawlerService;

@Service
public class MyTimer {

	private static Logger logger = Logger.getLogger(MyTimer.class);
	
	@Autowired
	private CrawlerService crawlerService;
	
	public void startCrawl(){  
		System.out.println(System.currentTimeMillis());
//		List<String> urlsAndComments = crawlerService.getNewsUrlsAndComments();
//		for (String urlAndComment : urlsAndComments) {
//			System.out.println(System.currentTimeMillis());
//			logger.warn("======================================");
//			logger.warn("获取" + urlAndComment);
//			New newObj = crawlerService.getNewFromUrlAndComment(urlAndComment);
//			logger.warn("保存" + urlAndComment);
//			crawlerService.saveNewToLocal(newObj);
//			logger.warn("======================================");
//			logger.warn("");
//		}
	}
	
}
