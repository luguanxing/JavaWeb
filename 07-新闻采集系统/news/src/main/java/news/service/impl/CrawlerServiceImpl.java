package news.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import news.entity.New;
import news.service.CrawlerService;
import redis.clients.jedis.JedisPool;

@Service
public class CrawlerServiceImpl implements CrawlerService {
	
	private static Logger logger = Logger.getLogger(CrawlerServiceImpl.class);
	
    @Autowired
    private JedisPool jedisPool;
    
	@Autowired
	private SolrServer solrServer;

	@Override
	public String httpGet(String url, String objName) {
		logger.info("========");
		logger.info("开始爬取" + objName);
		logger.info("爬取地址" + url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		try {
			// 创建httpClient客户端实例
			httpClient = HttpClients.createDefault();
			// 设置请求服务器的配置
			RequestConfig config = RequestConfig.custom().setConnectTimeout(10000) // 连接到服务器超时时间
					.setSocketTimeout(10000) // 从服务器获取内容超时时间
					.build();
			// 创建httpGet请求实例,设置头信息和其它配置
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
			httpGet.setConfig(config);
			// 执行httpGet请求实例获取返回报文
			response = httpClient.execute(httpGet);
			if (response != null) {
				// 获取报文内容获取信息
				HttpEntity entity = response.getEntity();
				if (response.getStatusLine().getStatusCode() == 200) {
					String html = EntityUtils.toString(entity, "utf-8");
					return html;
				} else {
					logger.error("访问" + objName + "返回状态错误");
					return null;
				}
			} else {
				logger.error("获取" + objName + "内容为空");
				return null;
			}
		} catch (Exception e) {
			logger.error("访问" + objName + "出错", e);
			e.printStackTrace();
			return null;
		} finally {
			logger.info("结束爬取" + objName);
			logger.info("========");
			// 关闭资源
			try {
				if (response != null)
					response.close();
				if (httpClient != null)
					httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<String> getNewsLinksFromIndexHtml(String indexHtml) {
		logger.info("================");
		logger.info("开始解析主页");
		if (indexHtml == null || indexHtml.isEmpty())
			return null;
		List<String> links = new ArrayList();
		Document document = Jsoup.parse(indexHtml);
		Elements elements_links = document.select("#js_top_news a");
		for (Element element_link : elements_links) {
			String title = element_link.text();
			String href = element_link.attr("href");
			/*
			 	if (链接在缓存中)
			 		continue;
			 	else
			 		放入缓存中;
			 */
			links.add(href);
		}
		logger.info("结束解析主页");
		logger.info("================");
		return links;
	}

	@Override
	public New getNewFromUrl(String url) {
		String newHtml = httpGet(url, url);
		if (newHtml == null || newHtml.isEmpty())
			return null;
		logger.info("========================");
		logger.info("开始解析博客");
		if (newHtml == null || newHtml.isEmpty())
			return null;
		try {
			New newObj = new New();
			Document document = Jsoup.parse(newHtml);
			String title = document.select("#epContentLeft h1").first().text();
			String publishDateAndSrc = document.select(".post_time_source").first().text();
			String content = document.select("#endText").first().html();
			String contentText = document.select("#endText").first().text();
			newObj.setTitle(title);
			newObj.setUrl(url);
			newObj.setPublishDateAndSrc(publishDateAndSrc);
			newObj.setCrawlerDate(new Date());
			newObj.setContentText(contentText);
			newObj.setContent(content);
			return newObj;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("解析博客出错", e);
		} finally {
			logger.info("结束解析博客");
			logger.info("========================");
		}
		return null;
	}

	@Override
	public void saveNewToLocal(New newObj) {
		// TODO Auto-generated method stub
		
	}

}
