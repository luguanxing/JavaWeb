package news.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import news.utils.CrawlerUtils;
import news.entity.New;
import news.service.CrawlerService;
import news.service.NewService;


@Service
public class CrawlerServiceImpl implements CrawlerService {
	
	private static Logger logger = Logger.getLogger(CrawlerServiceImpl.class);
	
	private static final String NEWS_URL = "http://news.163.com/rank/";
	
	@Value("${FileRootPath}")
	private String FileRootPath;
	
	@Value("${WebRootPath}")
	private String WebRootPath;
	
	@Autowired
	private NewService newService;

	//从主页获取所有新闻的链接+评论数(直接从网页难以获取AJAX)
	@Override
	public List<String> getNewsUrlsAndComments() {
		String indexHtml = CrawlerUtils.httpGetAndWait(NEWS_URL, "热门新闻排行", 2000);
		if (indexHtml == null || indexHtml.isEmpty())
			return null;
		logger.info("================");
		logger.info("开始解析跟帖榜");
		List<String> urlsAndComments = new ArrayList();
		Document document = Jsoup.parse(indexHtml);
		Elements elements_urls = document.select(".area-half").get(3)	//新闻跟帖榜
				.select(".tabContents").first()	//今日跟帖排行
				.select("a");	//新闻链接
		Elements elements_comments = document.select(".area-half").get(3)	//新闻跟帖榜
				.select(".tabContents").first()	//今日跟帖排行
				.select(".cBlue");	//评论数
		Integer index = 0;
		for (Element element_link : elements_urls) {
			String href = element_link.attr("href");
			String comment = "0";
			try {
				comment = elements_comments.get(index).text();
			} catch (Exception e) {
				logger.error("获取跟帖数量失败");
				e.printStackTrace();
			}
			urlsAndComments.add(href+":"+comment);
			index++;
		}
		logger.info("结束解析跟帖榜");
		logger.info("================");
		return urlsAndComments;
	}

	//从某条新闻链接获取新闻数据并封装成New
	@Override
	public New getNewFromUrlAndComment(String urlAndComment) {
		//先从urlAndComment拆分出url和comment
		int commentIndex = urlAndComment.lastIndexOf(":");
		String url = urlAndComment.substring(0, commentIndex);
		String commentText =  urlAndComment.substring(commentIndex + 1, urlAndComment.length());
		//获取新闻主体页面
		String newHtml = CrawlerUtils.httpGet(url, "新闻");
		if (newHtml == null || newHtml.isEmpty())
			return null;
		//解析新闻主体页面
		logger.info("========================");
		logger.info("开始解析新闻");
		try {
			New newObj = new New();
			Document document = Jsoup.parse(newHtml);
			String title = document.select("#epContentLeft h1").first().text();
			String publishDateAndSrc = document.select(".post_time_source").first().text();
			String content = document.select("#endText").first().html();
			String contentText = document.select("#endText").first().text();
			Elements imgElements = document.select("#endText").first().select("img");
			List<String> imageList = new ArrayList();
			for (Element imgElement : imgElements) {
				//图片地址要去掉参数
				String href = imgElement.attr("src");
				href = href.split("\\?")[0];
				imageList.add(href);
			}
			Integer commentCount = 0;
			try {
				commentCount = Integer.parseInt(commentText);
			} catch (Exception e) {
				logger.error("解析跟帖数量失败");
				e.printStackTrace();
			}
			newObj.setTitle(title);
			newObj.setUrl(url);
			newObj.setPublishDateAndSrc(publishDateAndSrc);
			newObj.setCrawlerDate(new Date());
			newObj.setContentText(contentText);
			newObj.setContent(content);
			newObj.setImageList(imageList);
			newObj.setCommentCount(commentCount);
			return newObj;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("解析新闻出错", e);
			return null;
		} finally {
			logger.info("结束解析新闻");
			logger.info("========================");
		}
	}
	
	//将New同步到本地(缓存+数据库+索引)，包括数据库并且将其图片进行替换
	@Override
	public void saveNewToLocal(New newObj) {
		//不排除新闻被和谐
		if (newObj == null)
			return;
		
		//若链接已在缓存或数据库中则更新状态并返回，否则添加
		Integer id = newService.getNewIdByUrl(newObj.getUrl());
		if (id != null) {
			newObj.setId(id);
			newService.updateNew(newObj);
			logger.warn("新闻已存在进行更新");
			return;
		}

		//先保存图片,并且替换名字
		List<String> imageList = newObj.getImageList();
		for (String imgUrl : imageList) {
			String localImgName = CrawlerUtils.downloadImage(imgUrl, FileRootPath, WebRootPath);
			String newContent = newObj.getContent().replaceAll(imgUrl, localImgName);
			newObj.setContent(newContent);
		}
		
		//后存到数据库获取主键
		newService.addNew(newObj);
		logger.warn("新闻保存成功");
	}

}
