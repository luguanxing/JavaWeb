package test;

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
		List<String> urlsAndComments = crawlerService.getNewsUrlsAndComments();
		for (String urlAndComment : urlsAndComments) {
			System.out.println(urlAndComment);
			System.out.println();
		}
	}
	
	@Test
	public void testGetNew() {
		New newObj = crawlerService.getNewFromUrlAndComment("http://news.163.com/18/0223/07/DBAJ2SNL0001875P.html:123");
		System.out.println(newObj);
	}
	
	@Test
	public void testSaveNew() {
		New newObj = crawlerService.getNewFromUrlAndComment("http://news.163.com/18/0223/00/DB9Q8VPB0001875O.html:999");
		crawlerService.saveNewToLocal(newObj);
	}
	
	@Test
	public void testUpdateNew() {
		//第一次爬取假设只有999点击量
		New newObj = crawlerService.getNewFromUrlAndComment("http://news.163.com/18/0224/11/DBDH4O070001875P.html:999");
		crawlerService.saveNewToLocal(newObj);
		//第一次爬取假设只有99999点击量
		New newObj2 = crawlerService.getNewFromUrlAndComment("http://news.163.com/18/0224/11/DBDH4O070001875P.html:99999");
		crawlerService.saveNewToLocal(newObj2);
	}
	
	@Test
	public void testSaveNewsFromIndex() {
		List<String> urlsAndComments = crawlerService.getNewsUrlsAndComments();
		for (String urlAndComment : urlsAndComments) {
			System.out.println("================");
			System.out.println("获取" + urlAndComment);
			New newObj = crawlerService.getNewFromUrlAndComment(urlAndComment);
			System.out.println("保存" + urlAndComment);
			crawlerService.saveNewToLocal(newObj);
			System.out.println("================");
			System.out.println();
		}
	}
	
}
