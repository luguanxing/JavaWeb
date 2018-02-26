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
	public List<New> getNewsUrlsAndComments() {
		String indexHtml = CrawlerUtils.httpGetAndWait(NEWS_URL, "热门新闻排行", 2000);
		if (indexHtml == null || indexHtml.isEmpty())
			return null;
		logger.info("================");
		logger.info("开始解析主页");
		List<New> news = new ArrayList();
		Document document = Jsoup.parse(indexHtml);
		Elements elements_urls = document.select(".area-half").get(3)	//新闻主页
				.select(".tabContents").first()	//今日跟帖排行
				.select("a");	//新闻链接
		Elements elements_comments = document.select(".area-half").get(3)	//新闻主页
				.select(".tabContents").first()	//今日跟帖排行
				.select(".cBlue");	//评论数
		Integer index = 0;
		for (Element element_link : elements_urls) {
			New newObj = new New();
			String url = element_link.attr("href");
			String commentText = "0";
			try {
				commentText = elements_comments.get(index).text();
			} catch (Exception e) {
				logger.warn("获取跟帖数量失败", e);
				System.out.println("获取跟帖数量失败");
			}
			Integer commentCount = 0;
			try {
				commentCount = Integer.parseInt(commentText);
			} catch (Exception e) {
				logger.warn("解析跟帖数量失败", e);
				System.out.println("解析跟帖数量失败");
			}
			newObj.setUrl(url);
			newObj.setCommentCount(commentCount);
			news.add(newObj);
			index++;
		}
		logger.info("结束解析主页");
		logger.info("================");
		return news;
	}

	//从新闻链接获取新闻数据并填充返回
	@Override
	public New getNewFromUrlAndComment(New newObj) {
		//获取新闻主体页面
		String newHtml = CrawlerUtils.httpGet(newObj.getUrl(), "新闻");
		if (newHtml == null || newHtml.isEmpty())
			return null;
		//解析新闻主体页面
		logger.info("========================");
		logger.info("开始解析新闻");
		try {
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
			newObj.setTitle(title);
			newObj.setPublishDateAndSrc(publishDateAndSrc);
			newObj.setCrawlerDate(new Date());
			newObj.setContentText(contentText);
			newObj.setContent(content);
			newObj.setImageList(imageList);
			return newObj;
		} catch (Exception e) {
			logger.warn("解析新闻出错", e);
			System.out.println("解析新闻出错");
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
			New newOldObj = newService.getNewById(id);
			//判断两个Integer不能直接==
			if (newOldObj.getCommentCount() == newObj.getCommentCount().intValue())
				return;		//如果评论数字一样说明比较新，不更新
		}

		//先保存图片,并且替换名字(这部较慢，能避免就避免)
		List<String> imageList = newObj.getImageList();
		for (String imgUrl : imageList) {
			String localImgName = CrawlerUtils.downloadImage(imgUrl, FileRootPath, WebRootPath);
			String newContent = newObj.getContent().replaceAll(imgUrl, localImgName);
			newObj.setContent(newContent);
		}

		if (id != null) {	//更新数据库
			newObj.setId(id);
			//注意更新时也要替换ContentText内的图片，这也是为什么这段在下面的原因(靠，那缓存好像起不到什么作用了，反正都会写数据库)
			newService.updateNew(newObj);
			logger.warn("新闻已存在进行更新");
			return;
		} else {	//这个是添加到数据库
			newService.addNew(newObj);
			logger.warn("新闻保存成功");
		}
	}

}
