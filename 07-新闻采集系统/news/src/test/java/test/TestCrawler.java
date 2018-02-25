package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import news.entity.New;
import news.service.CrawlerService;

//junit启动spring容器注解，不用手动创建容器了
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试  
@ContextConfiguration   ({"/spring/spring-*.xml"}) //加载配置文件  
public class TestCrawler {

	@Autowired
	private CrawlerService crawlerService;

	@Test
	public void testGetUrlsAndComments() {
		List<New> news = crawlerService.getNewsUrlsAndComments();
		for (New newObj : news) {
			System.out.println(newObj.getUrl());
			System.out.println(newObj.getCommentCount());
			System.out.println();
		}
	}
	
	@Test
	public void testGetNew() {
		New newObj = new New();
		newObj.setUrl("http://news.163.com/18/0223/07/DBAJ2SNL0001875P.html");
		newObj.setCommentCount(1000);
		newObj = crawlerService.getNewFromUrlAndComment(newObj);
		System.out.println(newObj);
	}
	
	@Test
	public void testSaveNew() {
		New newObj = new New();
		newObj.setUrl("http://news.163.com/18/0223/00/DB9Q8VPB0001875O.html");
		newObj.setCommentCount(999);
		newObj = crawlerService.getNewFromUrlAndComment(newObj);
		crawlerService.saveNewToLocal(newObj);
	}
	
	@Test
	public void testUpdateNew() {
		New newObj = new New();
		newObj.setUrl("http://news.163.com/18/0224/11/DBDH4O070001875P.html");
		newObj.setCommentCount(999);
		//第一次爬取假设只有999点击量
		newObj = crawlerService.getNewFromUrlAndComment(newObj);
		crawlerService.saveNewToLocal(newObj);
		newObj.setCommentCount(99999);
		//第一次爬取假设只有99999点击量，应为更新
		newObj = crawlerService.getNewFromUrlAndComment(newObj);
		crawlerService.saveNewToLocal(newObj);
	}
	
	@Test
	public void testSaveNewsFromIndex() {
		List<New> news = crawlerService.getNewsUrlsAndComments();
		for (New newObj : news) {
			System.out.println("================");
			System.out.println("获取" + newObj.getUrl());
			newObj = crawlerService.getNewFromUrlAndComment(newObj);
			if (newObj == null) {
				System.out.println("不是正常的新闻格式，进行忽略");
			} else {
				System.out.println("保存或更新" + newObj.getUrl());
				crawlerService.saveNewToLocal(newObj);
			}
			System.out.println("================");
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		List<New> news = new ArrayList();
		news.add(new New());
		news.add(new New());
		news.add(new New());
		for (New newObj : news) {
			newObj = changeLocal(newObj);
		}
		System.out.println(news);
	}
	
	public static New changeLocal(New newObj) {
		newObj.setId(123);
		return newObj;
	}
	
}
