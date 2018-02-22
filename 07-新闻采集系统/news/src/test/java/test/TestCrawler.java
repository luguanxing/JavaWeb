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
	public void testGetIndex() {
		String html = crawlerService.httpGet("http://news.163.com/", "网易新闻主页");
		System.out.println(html);
	}
	
	@Test
	public void testGetLinks() {
		String indexHtml = crawlerService.httpGet("http://news.163.com/", "网易新闻主页");
		List<String> links = crawlerService.getNewsLinksFromIndexHtml(indexHtml);
		for (String link : links) {
			System.out.println(link);
			System.out.println();
		}
	}
	
	@Test
	public void testGetNew() {
		New newObj = crawlerService.getNewFromUrl("http://news.163.com/18/0222/20/DB9ATUMI0001875P.html");
		System.out.println(newObj);
	}
	
}
