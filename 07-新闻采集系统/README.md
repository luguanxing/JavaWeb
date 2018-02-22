# 新闻采集系统

### 开发日记
```
day01-框架搭建
	基本web框架
	基本爬虫部分
	本来博客模块不应该用spring框架，而且应该另写模块，但考虑到新闻更新速度较慢，故为方便写成service
	public interface CrawlerService {
		//返回http请求目标响应的String类型
		String httpGet(String url, String objName);
		//从主页获取所有新闻的链接
		List<String> getNewsLinksFromIndexHtml(String indexHtml);
		//从新闻链接获取新闻数据并封装成New
		New getNewFromUrl(String url);
		//将New保存到本地，包括数据库并且将其图片进行替换
		void saveNewToLocal(New newObj);
	}


```


