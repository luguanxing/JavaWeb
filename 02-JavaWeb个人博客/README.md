# JavaWeb个人博客
<br><br>

## 需求分析
```
实现一个个人博客
前台功能:可以展示自己信息、文章、项目，也可以让用户评论
后台功能:可以增删改查有关数据
```

<br><br>


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
```
