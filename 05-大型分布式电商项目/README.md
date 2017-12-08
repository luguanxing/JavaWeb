# 大型分布式电商项目

```
宜立方网上商城是一个综合性的B2C平台，类似京东商城、天猫商城。会员可以在商城浏览商品、下订单，以及参加各种活动。
管理员、运营可以在平台后台管理系统中管理商品、订单、会员等。
客服可以在后台管理系统中处理用户的询问以及投诉。

后台管理系统：管理商品、订单、类目、商品规格属性、用户管理以及内容发布等功能。
前台系统：用户可以在前台系统中进行注册、登录、浏览商品、首页、下单等操作。
会员系统：用户可以在该系统中查询已下的订单、收藏的商品、我的优惠券、团购等信息。
订单系统：提供下单、查询订单、修改订单状态、定时处理订单。
搜索系统：提供商品的搜索功能。
单点登录系统：为多个系统之间提供用户登录凭证以及查询登录用户的信息。

```
<br/><br/><br/>

# 架构
![img](https://github.com/luguanxing/JavaWeb-Apps/blob/master/05-%E5%A4%A7%E5%9E%8B%E5%88%86%E5%B8%83%E5%BC%8F%E7%94%B5%E5%95%86%E9%A1%B9%E7%9B%AE/pictures/jiagou.jpg?raw=true)
<br/><br/><br/>

# 开发流程
```
day01-搭建基本框架和SSM
	(1)搭建框架
		|--e3-parent：父工程，打包方式pom，管理jar包的版本号。项目中所有工程都应该继承父工程。
			|--e3-common：通用的工具类通用的pojo。打包方式jar
			|--e3-manager：服务层工程。聚合工程。Pom工程
			|--e3-manager-dao：打包方式jar
			|--e3-manager-pojo：打包方式jar
			|--e3-manager-interface：打包方式jar
			|--e3-manager-service：打包方式：jar
			|--e3-manager-web：表现层工程。打包方式war
	(2)搭建SSM环境框架，使用逆向工程生成mapper


day02-拆分工程
	(1)修改架构
	e3-manager作为服务层
	提出e3-manager-web作为表现层，改名为e3-web
	|--e3-parent：父工程，打包方式pom，管理jar包的版本号。项目中所有工程都应该继承父工程。
			|--e3-common：打包方式jar
			|--e3-manager：打包方式war
				|--e3-manager-dao：打包方式jar
				|--e3-manager-pojo：打包方式jar
				|--e3-manager-interface：打包方式jar
				|--e3-manager-service：打包方式：war
			|--e3-web：打包方式war
	(2)搭建中间环境zookeeper和服务提供容器dubbo
		dubbo中间件实现远程调用,即e3-manager(8080)提供服务给e3-web(8081)调用
		dubbo主机192.168.253.133:2181
		xml约束http://code.alibabatech.com/schema/dubbo/dubbo.xsd改为
		https://raw.githubusercontent.com/alibaba/dubbo/master/dubbo-config/
		dubbo-config-spring/src/main/resources/META-INF/dubbo.xsd
		<!-- 在e3-manager-service的applicationContext-dao.xml使用dubbo发布服务 -->
		<dubbo:application name="e3-manager" />
		<dubbo:registry protocol="zookeeper" address="192.168.253.133:2181" />
		<dubbo:protocol name="dubbo" port="20880" />
		<dubbo:service interface="e3mall.service.ItemService" ref="itemServiceImpl" timeout="600000"/>
		<!-- 在e3-web的springmvc.xml引用dubbo服务 -->
		<dubbo:application name="e3-web"/>
		<dubbo:registry protocol="zookeeper" address="192.168.253.133:2181"/>	
		<dubbo:reference interface="e3mall.service.ItemService" id="itemService" />
		启动方式:在bin目录下./zkServer.sh start
	(3)测试工程改造效果
		加log4j可以解决启动tomcat卡住问题并找到原因:
		注意：centos需要关闭防火墙。
		systemctl stop firewalld.service #停止firewall
		systemctl disable firewalld.service #禁止firewall开机启动
		关闭防火墙
		service iptables stop
		永久关闭修改配置开机不启动防火墙：
		chkconfig iptables off
		要实现序列化接口才能远程传递对象
	(4)debug设置和dubbo监控
		eclipse要调试需要设置debug-config添加源码后才可调试
		dubbo-admin-2.5.4.war不支持jdk1.8，要改成dubbo-admin-2.5.7
		启动方式:在tomcat上run即可
	(5)显示商品列表
		easyui查列表附带参数page和rows，应返回json格式数据，包含total和该页的rows
		逆向工程生成的mapper不带分页功能，需要插件PageHelper(基于拦截器修改mybatis的sql语句)
			使用PageHelper-fix需要添加导入的pom工程并安装
			PageInfo能包含分页信息(总记录数、总页数、当前页码、前后页、是否开头末尾等)
			xxxMapper.selectByExample(example)能包含该页的数据
		e3-web依赖引入pagehelper解决没有Page类警告信息


day03-商品分类和nginx
	(1)商品类别选择
		初始化tree请求的url：
			/item/cat/list
		参数：
			初始化tree时只需要把第一级节点展示，子节点异步加载(点开才加载)。
			long id：父节点id
			text：文字
			state：如果节点下有子节点“closed”，如果没有子节点“open”
			返回值：json。数据格式
			[{    
				"id": 1,    
				"text": "Node 1",    
				"state": "closed"
			},...] 
		后端建立通用类EasyUITreeNode并且返回列表
	(2)分布式
		解决集群/分布式图片存储位置不通用问题
		安装nginx服务器:192.168.253.134
			nginx输出目录:/usr/local/ngingx
			配置不同端口:改conf/nginx里面server配置
			(本地改host后访问域名对应nginx服务器的ip)
			配置域名: nginx根据ip+输入的域名反向代理转发到不同的webapp和虚拟主机,还可以实现负载均衡
			本地请求->(已改host将sina.com对应192.168.253.134):
				Request URL:http://www.sina.com/
				Request Method:GET
				Status Code:200 OK
				Remote Address:192.168.253.134:80
				Referrer Policy:no-referrer-when-downgrade
			响应->(根据请求中的www.sina.com转发到tomcat并且负载均衡):
				upstream sina {
				  server localhost:8081;
				  server localhost:8082 weight=2;
				}
				server {
					listen       80;
					server_name  www.sina.com;
					location / {
						proxy_pass http://sina;
					}
				}
		启动方式:在sbin目录下./nginx 更新方式:./nginx -s -reload

day04-图片上传FastDFS、富文本编辑器、商品添加功能
	1.FastDFS能在分布式环境下解决图片存储问题
		(1)FastDFS架构:
			tracker(群)类似中介,能了解storage群的信息
			storage群储存文件,里面能分组,组之间内容不相等,组内内容自动备份
		(2)FastDFS存储流程:
			客户端上传文件后存储服务器将文件ID返回给客户端，此文件ID用于以后访问该文件的索引信息。
			文件索引信息包括：组名，虚拟磁盘路径，数据两级目录，文件名。
			安装FastDFS:
				运行虚拟机，为了不改内部配置将所有网段全部从192.168.253.x改为192.168.25.x
				原来的zookeeper+dubbo服务器:192.163.253.133->192.168.25.128
				原来的nginx服务器:192.163.253.134->192.168.25.129
				FastDFS服务器:192.168.25.133
			使用FastDFS:
				添加fastdfs的maven工程并安装到本地仓库
				使用api上传,上传后返回url回显数据
				用http+nginx访问192.168.25.133/group1/M00/00/00/wKgZhVopX86AfjmVAAAjLcz8y9M768.jpg
	2.图片上传兼容性问题:
		使用text/plain最好,application/json不好,最好用JsonUtils转换返回String不需再转换直接是text/plain
		返回中文@RequestMapping(value="/upload", produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	3.商品添加功能
		返回结果新建pojo表示状态
		使用KindEditor富文本
		Controller接收pojo对象和String，交给service封装完善后插入数据库，注意dubbo如果会尝试3次
```
