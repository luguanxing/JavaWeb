#SSM个人博客（极简, redis, solr）

###开发日志

```
day01-搭建项目框架、完成登录功能想、搭建前台
	搭建SSM框架
	实体类使用mybatis逆向工程生成
	使用shiro完成登录认证
	搭建前台页面和调整格式
	
day02-添加缓存，完善实体类
	完成博客、博主信息、友情连接、博客类别数据库表设计以及读取和显示
	测试redis，将一部分缓存数据放入其中(blogger、blogtype、links、priorityblogs)
	将redis整合，优先从redis中取数据(在Service中改)，否则从数据库取再放入缓存
	注意每次申请连接都要在finally中释放连接jedis.close();否则出现内存泄漏->页面卡死不响应
	
day03-完善博客显示
	分页插件使用bootstrap-paginator
	使用mybatis生成器的分页插件mybatis-pagehelper
	
day04-完成博客类别
	点击博客类别，以分页方式显示该类别下的所有博客
	rest风格/{typeId}/{page}
	检查类别和分页的完善(健壮性)
	
day05-博客显示、网页简介、留言墙
	点击一篇博客能够显示出来、网页简介类似
	分页显示所有用户的留言
	能够评论留言并在检查后加到留言墙上

day06-搜索功能
	搭建solr服务器
		1.war包放/usr/local/solr/到tomcat的webapp目录下
		2.解压并删除war包
		3.拷贝几个jar包(/example/lib/ext内)到webapp/WEB-INF/lib下
		4.把solrhome放到/usr/local/solr/solrhome中
			(注意这时/usr/local/solr有tomcat和solrhome两个文件夹)
		5.修改配置文件webapp/WEB-INF/web.xml->删除注释，配置<env-entry>路径
		6.配置FieldType，业务域，分词器
			(1)将IKAnalyzer2012FF_u1.jar放到webapp/WEB-INF/lib下
			(2)将中文分词器的扩展词典和配置文件放到webapp/WEB-INF/classes下(没有目录则创建)
			(3)往solrhome\collection1\conf\schemal.xml中添加fieldType和使用fieldType的业务域(id不用定义)
				<!-- IKAnalyzer-->
				<fieldType name="text_ik" class="solr.TextField">
				  <analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
				</fieldType>

				<field name="blog_title" type="text_ik" indexed="true" stored="true"/>
				<field name="blog_image" type="string" indexed="false" stored="true" />
				<field name="blog_summary" type="text_ik" indexed="true" stored="true"/>
				<field name="blog_releaseDate" type="string" indexed="false" stored="true" />
				<field name="blog_content" type="text_ik" indexed="true" stored="true"/>

				<field name="blog_keyword" type="text_ik" indexed="true" stored="false" multiValued="true"/>
				<copyField source="blog_title" dest="blog_keyword"/>
				<copyField source="blog_summary" dest="blog_keyword"/>
				<copyField source="blog_content" dest="blog_keyword"/>
		7.重启tomcat，使用solrj导入数据
	使用solrj导入数据(测试类)
	完成搜索和高亮功能

day07-完成后台基本页面
	完成后台基本模块搭建
	使用富文本编辑器ueditor
	完成blogger修改功能，注意每次更新都要同步缓存(删除缓存)

	
```