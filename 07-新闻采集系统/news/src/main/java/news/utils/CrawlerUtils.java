package news.utils;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CrawlerUtils {
	
	private static Logger logger = Logger.getLogger(CrawlerUtils.class);

	//基本功能函数：请求后立刻返回http请求目标响应的数据String类型
	public static String httpGet(String url, String objName) {
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
	
	//基本功能函数：请求后等待数据加载一会后才返回响应的数据String类型
	public static String httpGetAndWait(String url, String objName, Integer waitTime) {
		logger.info("========");
		logger.info("开始爬取" + objName);
		logger.info("爬取地址" + url);
		//实例化web客户端,并设置浏览器请求头
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		try {
			//获取页面元素
			HtmlPage htmlPage = webClient.getPage(url);
			//等待脚本将数据加载
			Thread.sleep(waitTime);
			//输出完成数据加载后的页面
			return htmlPage.asXml();
		} catch (Exception e) {
			logger.error("访问" + objName + "出错", e);
			e.printStackTrace();
			return null;
		} finally {
			logger.info("结束爬取" + objName);
			logger.info("========");
			webClient.close();
		}
	}
	
	//基本功能函数：下载一张图片并将其名字替换成本地新名字
	public static String downloadImage(String imgUrl, String FileRootPath, String WebRootPath) {
		logger.info("================");
		logger.info("开始爬取图片");
		logger.info("爬取地址" + imgUrl);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.createDefault();
			RequestConfig config = RequestConfig.custom().setConnectTimeout(10000) // 连接到服务器超时时间
					.setSocketTimeout(10000) // 从服务器获取内容超时时间
					.build();
			HttpGet httpGet = new HttpGet(imgUrl);
			httpGet.setHeader("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
			httpGet.setConfig(config);
			response = httpClient.execute(httpGet);
			if (response != null) {
				HttpEntity entity = response.getEntity();
				if (response.getStatusLine().getStatusCode() == 200) {
					InputStream inputStream = entity.getContent();
					String ext = entity.getContentType().getValue().split("/")[1].split(";")[0];
					//名字为32位字符串
					int extIndex = imgUrl.lastIndexOf(".");
					int nameStart =imgUrl.lastIndexOf("/");
					//图片保存目录
					String saveFilePath = FileRootPath + "/" + DateUtil.getCurrentDatePath()
										+ imgUrl.substring(nameStart, extIndex)
										+ "." + ext;
					//保存图片
					FileUtils.copyToFile(inputStream, new File(saveFilePath));
					//web访问保存目录
					String webPath = WebRootPath + "/" + DateUtil.getCurrentDatePath()
										+ imgUrl.substring(nameStart, extIndex)
										+ "." + ext;
					return webPath;
				} else {
					logger.error("访问图片返回状态错误");
					return null;
				}
			} else {
				logger.error("获取图片内容为空");
				return null;
			}
		} catch (Exception e) {
			logger.error("访问图片出错", e);
			e.printStackTrace();
			return null;
		} finally {
			logger.info("结束爬取图片");
			logger.info("================");
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

}
