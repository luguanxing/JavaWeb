package news.service;

import java.util.List;

import news.entity.New;

//本来博客模块不应该用spring框架，而且应该另写模块，但考虑到新闻更新速度较慢，故为方便写成service
public interface CrawlerService {

	//从主页获取所有新闻的链接+评论数(直接从网页难以获取AJAX)
	List<String> getNewsUrlsAndComments();

	//从某条新闻链接获取新闻数据并封装成New
	New getNewFromUrlAndComment(String urlAndComment);
	
	//将New保存到本地，包括数据库并且将其图片进行替换
	void saveNewToLocal(New newObj);
	
}
