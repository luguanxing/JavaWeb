# JavaWeb个人博客

## 需求分析

```
实现一个个人博客
前台功能:可以展示自己信息、文章、项目，也可以让用户评论
后台功能:可以增删改查有关数据
```

<br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/0.jpg?raw=true)
<br><br><br>
#### 前台-主页展示


<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/1.jpg?raw=true)
#### 前台-展示学习路线

<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/2.jpg?raw=true)
#### 前台-展示自己的文章

<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/3.jpg?raw=true)
#### 前台-打开自己的文章

<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/4.jpg?raw=true)
#### 前台-展示自己的分类项目

<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/5.jpg?raw=true)
#### 前台-打开自己的分类项目

<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/6.jpg?raw=true)
#### 前台-展示自己的博客


<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/a.jpg?raw=true)
#### 后台-编辑修改主页内容

<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/b.jpg?raw=true)
#### 后台-查看路线列表

<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/c.jpg?raw=true)
#### 后台-添加路线列表

<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/d.jpg?raw=true)
#### 后台-查看项目列表

<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/e.jpg?raw=true)
#### 后台-编辑项目(数据回显示)

<br><br><br><br><br>
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/02-JavaWeb%E4%B8%AA%E4%BA%BA%E5%8D%9A%E5%AE%A2/pictures/f.jpg?raw=true)
#### 后台-评论管理


## 开发日志
```
案例1-显示项目分类
	header.jsp:加载/index显示项目分类条->使用异步的ajax方法,因为每页都有,设置request会过于繁琐
	CategoryServlet:调用service返回list,包装后以json格式设置转发
	CategoryService:调用dao
	CategoryDao:查询数据并返回

案例2-标记index
	header.jsp:每个页面header.jsp对应显示高亮,使用JSTL表达式判断显示样式
	CategoryServlet:设置index

案例3-路线图设计
	(1)解决js、css冲突:将所有模块的js、css区分，如主页面的在main，路线图的在road
	(2)设计roaditem:
		rid
		title
		content
		date
		year
		查询时查出所有数据，根据year放到不同map中
	(3)jsp显示
		取出map里的每个项
			map键为时间
			map值为List，再遍历List即可
			
案例4-评论
	展示留言(分页)
		/comment?pageNumber=?
		jsp: 页面上点击页数
		servlet: 获取页数，设置调用service获取内容转发
		service: 调用dao
		dao: limitto
	添加留言
		post表单到/comment?addComment
		
案例5-文章
	设计article:
		aid(uuid)
		date(正常文字)
		click(正常文字)
		title(正常文字)
		subtitle(正常文字)
		content(html码)
	展示留言(分页)
		/article?pageNumber=?
		jsp: 每个article只显示标题和子标题，页面上点击页数
		servlet: 获取页数，设置调用service获取内容转发
		service: 调用dao
		dao: limitto
	打开文章
		/article?method=read&aid=
		jsp:页面上点击打开文章
		servlet: 获取文章aid，设置调用service获取内容转发
		service: 调用dao
		dao: 读内容
		
案例6-项目
		/project?pageNumber=?&type=?&sortby=?
	展示留言(分页)
		/article?pageNumber=?
		jsp: 每个article只显示标题，图片，日期点，击数
		servlet: 获取页数，设置调用service获取内容转发
		service: 调用dao
		dao: limitto
	打开项目
		/project?method=detail&pid=
		jsp:页面上点击打开项目
		servlet: 获取项目pid，设置调用service获取内容转发
		service: 调用dao
		dao: 读内容
	解决bootstrap居中问题，12块先分成3块，3块组合成36块。。。
	
案例7-主页
	展示主页介绍(富文本)
	展示最近项目及其图片
	
案例8-后台登录
	过滤器验证管理员是否登录，主要保护:后台接口、富文本接口、后台页面
		注意服务器内部转发不会被拦截，只有用户请求才会被拦截，所以后台可以任意转发
	/admin对应的AdminServlet?method包含管理员的所有功能，所有用户请求由过滤器进行登录验证
	/login对应的AdminLoginServlet包含功能去登录界面，验证登录，不需要保护
	因为admin模块包含css、js等文件，所以不放在WEB-INF下了，而是单独一个模块并使用过滤器保护该模块下的所有文件

案例9-修改主页内容
	修改主页内容admin?method=editIndexUI，编辑器内加载当前index内容
	修改admin?method=editIndex
	
案例10-后台路线、文章、项目、评论的管理功能
	管理功能包括：列表、添加、编辑(数据显示)、删除功能
	
```
