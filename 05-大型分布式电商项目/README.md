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
		1.dubbo中间件实现远程调用,即e3-manager(8080)提供服务给e3-web(8081)调用
		2.dubbo主机192.168.253.133:2181
		3.xml约束http://code.alibabatech.com/schema/dubbo/dubbo.xsd改为
			https://raw.githubusercontent.com/alibaba/dubbo/master/dubbo-config/
		4.发布服务:
			dubbo-config-spring/src/main/resources/META-INF/dubbo.xsd
			<!-- 在e3-manager-service的applicationContext-dao.xml使用dubbo发布服务 -->
			<dubbo:application name="e3-manager" />
			<dubbo:registry protocol="zookeeper" address="192.168.253.133:2181" />
			<dubbo:protocol name="dubbo" port="20880" />
			<dubbo:service interface="e3mall.service.ItemService" ref="itemServiceImpl" timeout="600000"/>
		5.引用服务:
			<!-- 在e3-web的springmvc.xml引用dubbo服务 -->
			<dubbo:application name="e3-web"/>
			<dubbo:registry protocol="zookeeper" address="192.168.253.133:2181"/>	
			<dubbo:reference interface="e3mall.service.ItemService" id="itemService" />
		6.启动方式:在bin目录下./zkServer.sh start
	(3)测试工程改造效果
		加log4j可以解决启动tomcat卡住问题并找到原因:
		注意：centos需要关闭防火墙。
		systemctl stop firewalld.service #停止firewall
		systemctl disable firewalld.service #禁止firewall开机启动
		service iptables stop #关闭防火墙	
		chkconfig iptables off #永久关闭修改配置开机不启动防火墙
		要实现序列化接口才能远程传递对象
	(4)debug设置和dubbo监控
		eclipse要调试需要设置debug-config添加源码后才可调试
		dubbo-admin-2.5.4.war不支持jdk1.8，要改成dubbo-admin-2.5.7
		启动方式:在tomcat上run即可
		帐号密码均为root
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
		
day05-完成前台展示
	原来e3-web改为e3-web-manager，作为前台管理
	新建工程e3-web-portal，作为前台展示
	新建工程e3-content，作为内容服务
	修改工程e3-manager，作为商品服务
		|--e3-parent
			|--e3-common
			|--e3-manager (8080,可不用tomcat)
				|--e3-manager-dao
				|--e3-manager-pojo
				|--e3-manager-interface
				|--e3-manager-service
			|--e3-content (8083,可不用tomcat)
				|--e3-content-interface
				|--e3-content-service
				|--e3-manager-dao
				|--e3-manager-pojo
			|--e3-web-manager (8081)
			|--e3-web-portal (8082)
	只发布服务的e3-manager和e3-content只使用spring容器，用tomcat作用不大只是方便聚合和部署
		new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		System.in.read();
		这样运行了spring容器和跑tomcat是一样的
	内容分类管理:
		添加分类节点,添加内容，均返回E3Result
	内容管理:
		withBLOBs带大文本数据
		
day06-redis
	新建虚拟机安装redis 192.168.25.130
	编译安装启动
	复制安装目录下的redis.config到运行bin目录下改为daemonize yes运行时再带上config名字参数即可
	搭建redis集群:
		将数据按crc算法计算后哈希分布给按多台服务器(可有主备),所有服务器总共有16384个槽
		至少要3台服务器(不带备份机)存数据以便机群进行投票容错
		伪分布式:
			在一台机上配置6台redis,修改配置文件启动redis,安装ruby
			M: 422ab3b2ed93cabcab1ebac630f9a90d6728b857 192.168.25.130:7001
			   slots:0-5460 (5461 slots) master
			M: 058aa8f7a9e2d5161f8a516206b3db19283d37c4 192.168.25.130:7002
			   slots:5461-10922 (5462 slots) master
			M: 2ed7ba0ce228bf720029017747ebef44269f285f 192.168.25.130:7003
			   slots:10923-16383 (5461 slots) master
			S: f6eb70f5d705df41b05d4f5dc8a1c482c08b1f40 192.168.25.130:7004
			   replicates 422ab3b2ed93cabcab1ebac630f9a90d6728b857
			S: 781a2e4d70b39a309d673433a16d400b457c1cbb 192.168.25.130:7005
			   replicates 058aa8f7a9e2d5161f8a516206b3db19283d37c4
			S: 9e4d8ffc4b34182086ee26630f17313ca3e6cb8f 192.168.25.130:7006
			   replicates 2ed7ba0ce228bf720029017747ebef44269f285f
			测试
			[root@localhost redis-cluster]# redis01/redis-cli -p 7002 -c
			127.0.0.1:7002> set a 123
			-> Redirected to slot [15495] located at 192.168.25.130:7003
		jedis连接单机版和集群版
			jedis连接时注意关防火墙
			用策略模式提取接口解耦
			接口JedisClient
			实现类JedisClientPool(单机)、JedisClientCluster(集群)
			注意在applicationContext-redis中一般只配置一个实现类的bean,以便使用get(JedisClient.class)
				就不需要写@resource(name=实现类id)了，即在xml中单机版和集群版不能共存，使用单机版时注释
				集群版的配置。使用集群版，把单机版注释。
		缓存一般放在service,这样不同的表现层都能调用
			(1)查询
				if (有缓存) {
					用缓存响应数据
				} else {
					查询数据库响应数据
					添加到缓存
				}
				为了防止数据key冲突,一般使用hashtable,里面hkey为id,hval将数据为string
			(2)缓存同步
				增删改更新时不一定要写缓存,还可以删缓存,下次再读就有缓存了,注意要精准删除不要删整个表
		
day07-solr
	新建虚拟机安装solr 192.168.25.131
		|--e3-parent
			|--e3-common
			|--e3-manager (8080,可不用tomcat)
				|--e3-manager-dao
				|--e3-manager-pojo
				|--e3-manager-interface
				|--e3-manager-service
			|--e3-content (8083,可不用tomcat)
				|--e3-content-interface
				|--e3-content-service
				|--e3-manager-dao
				|--e3-manager-pojo
			|--e3-search (8084,可不用tomcat)
				|--e3-search-interface
				|--e3-search-service
				|--e3-manager-dao
				|--e3-manager-pojo
			|--e3-web-manager (8081)
			|--e3-web-portal (8082)
			|--e3-web-search (8085)
	关防火墙后测试连接
	启动后配置中文分词器、配置业务域
		1.添加jar包到solr目录下
		2.复制几个配置文件到WEB-INF/classes下
		3.改solrhome/collection1/conf/schema.xml的配置
			(1)添加ik分词器对应的filedType
			(2)添加业务系统Field，并使用分词的filedType和其它基本类型的filedType
	新建工程e3-search，作为搜索服务
		1.dao查询:手写接口和对应的mapper.xml配置，而sql语句进行关联查询选出搜索的部分
		2.service写入索引:使用solrj
		3.注意当接口和实现类分开时要在pom中配置resources否则会出现
			org.apache.ibatis.binding.BindingException: Invalid bound statement (not found)
			但配置后会改变默认源文件目录,所以src/main/java和src/main/resources都要写上
	使用solrj完成dao,service,controller
		显示图片时分割url取第一张图片
		
day08-SlorCloud
	搭建solr集群解决tomcat多并发
		zookeeper集群:
			(1)创建solr-cloud/solr01,solr02,solr03
			(2)创建data文件夹
			(3)改/conf下的zoo.cfg
				1.设置data目录
				2.配置节点和内部通讯端口和投票端口
					server.1=192.168.25.131:2881:3881
					server.2=192.168.25.131:2882:3882
					server.3=192.168.25.131:2883:3883
				3.写批处理运行查看状态follower,leader
		solr集群:
			(1)创建solr-cloud/tomcat01,tomcat02,tomcat03,tomcat04并分别在里面装入solr应用和对应库
			(2)创建solr-cloud/solrhome01,solrhome02,solrhome03,solrhome04
			(3)修改tomcat01-04对应的端口和里边solr应用的solrhome
			(4)修改solrhome01-04里边的solr.xml的IP和运行solr的tomcat端口号
			(5)修改tomcat01-04/bin里的catalina.sh来关联solr集群和zookeeper集群
			(6)上传solrhome01/collection1/conf里的配置文件到zookeeper来统一管理配置文件
					使用solr客户端/example/scripts/cloud-scripts/zkcli.sh
			(7)写批处理运行tomcat01-04
		进入192.168.25.131:8080/solr在页面上配置云,输入url管理
	solrj管理集群:
		创建一个集群的连接,应该使用CloudSolrServer,但需要初始化参数zookeeper地址列表
		设置一个defaultCollection属性(即逻辑结构图上的collection)
		之后操作和solrj一致
	中间件:
		商品添加后要同步索引，如何解决?
			(1)直接添加业务逻辑:耦合严重,业务拆分不明确
			(2)调用服务:仍有耦合,且启动有先后顺序要求
			(3)消息队列:解决耦合问题
		新建虚拟机安装activemq 192.168.25.132
			注意centos7使用较新的activemq5.15.2能成功
			帐号密码均为admin
		消息格式:TextMessage等
		消息形式:
			点对点模式(消息不处理会留在服务端)
			发布/订阅模式(发完就完了,不关心有没有处理,消息不留在服务端->但也可设置subscribers保存)
	全局异常处理器:
		异常方向:dao->service->controller
		实现HandlerExceptionResolver处理全局异常
		在springmvc.xml(applicationContext.xml)中配置处理器
	写日志:
		理论上slf4j通用性更好,实际上大部分情况还是用log4j
		使用log4j写日志步骤
			(1)private static final Logger logger = LoggerFactory.getLogger(当前类.class);
			(2)处理器可显示logger.error("系统发生异常", ex);
			(3)log4j.properties配置输出

day09-ActiveMq整合Spring,同步索引,详情页面
	使用的centos虚拟机较多应按ctrl+alt+f2切换成dos命令行界面以便节省资源
	ActiveMq整合Spring:
		e3-content-service:
			(1)添加依赖
			(2)在applicationContext-activemq.xml配置生产者
			(3)初始化jmsTemplate发送消息,可自定发送的消息形式
		e3-search-service:
			(1)添加依赖
			(2)在applicationContext-activemq.xml配置消费者
			(3)容器初始化后自动接收消息
	业务逻辑:
		管理模块添加商品后发送id,搜索模块再查出同步
		使用topic不用queue因为可以给多个消费者(索引、缓存等)接收
		内部匿名类使用外部局部变量最好加final
		消息传递可能比事务提交还快,可以手动sleep或者等事务完成后在controller完成
	详情页面:
		新建工程e3-web-iten，作为详情页面
			1.新建Item继承TbItem用于分解图片字符串,只在该工程用不用放到common不涉及网络传输
			2.详情页面在e3-manager-service加缓存以便其它模块调用
			3.不应该把所有商品详情都放进缓存,而是将最热点的商品留在缓存中(可按排名或设置过期时间)
				由于不能设置redis中hash的key的过期时间,所以不能放在hash中
				只能放在string中,但归类时要加上前缀
				ITEM_INFO:123456:BASE
				ITEM_INFO:123456:DESC
				有时需要缓存同步时只需删掉缓存下次即自动更新
	使用freemarker网页静态化:
		freemarker模板引擎类似NodeJs的Jade,都是静态页面,只需要将里面model内容填空
		使用步骤:(view+model)
			1.创建模板文件
			2.创建configuration对象
			3.设置保存目录和编码格式(一般UTF-8)
			4.加载模板文件,创建模板对象
			5.创建数据集model,可以是pojo或map(推荐使用map)
			6.创建writer对象,指定输出文件路径和文件名
			7.生成静态页面
			8.关闭流
	删除数据库中重复记录:
		DELETE FROM t_test
		WHERE id
		NOT IN (
			SELECT id
			FROM (
				SELECT MIN(id), id /*, a, COUNT(a)*/
				FROM t_test
				GROUP BY a
			) t
		)
		先用min(id)选除没有重复的数据,在把其它的删除
			
day10-网页静态化
	使用freemarker网页静态化:
		使用语法:
			(1)访问pojo属性和el表达式类似
			(2)列表<#list stuList as stu>
			(3)序号${stu_index}
			(4)判断
				<#if stu_index % 2 == 0>
				<#else>
				</#if>
			(5)日期:
				普通格式${date?datetime} ${date?date ${date?time}
				自定义格式${date?string("yyyy/MM/DD HH:mm:ss")}
			(6)空值:
				空值默认显示:${var!"i am null"}
				判断空值:
					<#if var??>
					<#else>
					</#if>
			(7)include包含页面:
				<#include "hello.ftl">
		整合spring:
			添加依赖
			配置springmvc.xml
		生成静态页面:
			生成静态页面的时机：商品添加后，生成静态页面。可以使用Activemq，订阅topic（商品添加）
			输出文件的名称：商品id+“.html”
			输出文件的路径：工程外部的任意目录。
			网页访问：使用nginx访问网页。在此方案下tomcat只有一个作用就是生成静态页面。
			工程部署：可以把e3-item-web部署到多个服务器上。
		流程总结:
			e3-manager添加商品 -> 发布topic -> tomcat容器接收并生成静态页面 -> 之后由nginx访问静态资源
			可以添加备份机监听topic并且nginx负载均衡实现高可用
			发布topic还可以同步静态页面的修改等
	ActiveMq同步静态页面:
		配置listener,业务逻辑是生成静态页面
		<load-on-startup>1</load-on-startup>启动时监听
		注意html输出目录要先创建好否则报错
		使用nginx配置到freemarker输出目录，注意nginx客户端不能有中文,再把css/js/images等文件也复制过去
		搜索点击item时修改跳转链接去掉localhost:8086端口就变成去localhost:80静态页面
	单点登录sso(Single Sign on)系统:
		用redis服务器(含key-value特性、过时特性)解决session共享问题
		分布式事务:
			使用Mq手动应答(不成功消息不被消费)保证数据的一致性
		搭建工程:
			在e3-web-sso中springmvc拦截/应配置资源映射
			
day11-sso注册和单点登录,token,Ajax跨域请求(jsonp)
	单点登录:在多个应用系统中，用户只需要登录一次就可以访问所有相互信任的应用系统
	修改工程
		|--e3-parent
			|--e3-common
			|--e3-manager (8080,可不用tomcat)
				|--e3-manager-dao
				|--e3-manager-pojo
				|--e3-manager-interface
				|--e3-manager-service
			|--e3-content (8083,可不用tomcat)
				|--e3-content-interface
				|--e3-content-service
				|--e3-manager-dao
				|--e3-manager-pojo
			|--e3-search (8084,可不用tomcat)
				|--e3-search-interface
				|--e3-search-service
				|--e3-manager-dao
				|--e3-manager-pojo
			|--e3-sso (8087,可不用tomcat)
				|--e3-sso-interface
				|--e3-sso-service
			|--e3-web-manager (8081)
			|--e3-web-portal (8082)
			|--e3-web-search (8085)
			|--e3-web-item (8086)
			|--e3-web-sso (8088)
	数据有效性校验:
		请求的url：/user/check/{param}/{type}
		参数：从url中取参数1、String param（要校验的数据）2、Integer type（校验的数据类型）
		响应的数据：json数据。e3Result，封装的数据校验的结果true：成功false：失败。
	用户注册:
		前端ajax检测有效性
		后端还要再检测一遍，插入数据库
	用户登录:
		判断用户名密码是否正确,不正确返回失败
		手动模仿session,要有key-value形式而且能区分
			key由uuid生成token作为sessionid, value对应用户信息(不写入密码)
			设置过期时间,存入redis
			token写入cookie(表现层实现),返回登录成功
		判断登录状态
			从cookie取token
			查询token(key)
			重置session过期时间
		存在仿造cookie问题 => 可采用cas架构
		cookie设置域名以便跨二级域名
	从token显示用户信息:
		在controller取token显示用户信息不好,因为换到不同页面都要做重复处理
		应使用ajax访问表现层,再调用dubbo调用service
			请求的url：/user/token/{token}
			参数：String token需要从url中取。
			返回值：json数据。使用e3Result包装Tbuser对象。
		ajax跨域问题:
			不能用ajax访问另一个域名IP或端口访问数据
			No 'Access-Control-Allow-Origin' header is present on the requested resource.
		jsonp解决ajax跨域问题:
			利用可以跨域加载js文件,请求时带上回调参数,服务器拼装执行回调函数和数据的js代码再返回客户端执行
			注意返回contentType为json,使用produces=MediaType.APPLICATION_JSON_UTF8_VALUE
		spring4.1以后也可使用mappingJacksonValue自动包装
	
day12-购物车实现
	新建购物车工程
		|--e3-parent
			|--e3-common
			|--e3-manager (8080,可不用tomcat)
				|--e3-manager-dao
				|--e3-manager-pojo
				|--e3-manager-interface
				|--e3-manager-service
			|--e3-content (8083,可不用tomcat)
				|--e3-content-interface
				|--e3-content-service
				|--e3-manager-dao
				|--e3-manager-pojo
			|--e3-search (8084,可不用tomcat)
				|--e3-search-interface
				|--e3-search-service
				|--e3-manager-dao
				|--e3-manager-pojo
			|--e3-sso (8087,可不用tomcat)
				|--e3-sso-interface
				|--e3-sso-service
			|--e3-cart (8089,可不用tomcat)
				|--e3-cart-interface
				|--e3-cart-service
			|--e3-web-manager (8081)
			|--e3-web-portal (8082)
			|--e3-web-search (8085)
			|--e3-web-item (8086)
			|--e3-web-sso (8088)
			|--e3-web-cart (8090)
	购物车实现:
		不登录也可以加购物车(用户体验好),数据放入cookie(不占用服务端资源,但容量有限,不能同步)
		购物车实现增删改查
			请求的url：/cart/方法/{itemId}
			参数：商品id 商品数量
			业务逻辑：
			1、从cookie中查询商品列表。
			2、判断商品在商品列表中是否存在。
			3、如果存在，商品数量修改。
			4、不存在，根据商品id查询商品信息，得TbItem对象(内部属性定义有改动)。
			5、把商品修改到购车列表。
			6、把购车商品列表写入cookie。
		ajax请求响应返回406说明少了jackson包(添加依赖)或者请求了html方式(拦截action)
		登录后购物车则放到redis中(key为用户id,value为购物车的hash表(id-json)能减少操作次数)
	判断用户登录并将购物车到redis进行同步更新操作:
		拦截器实现判断登录(登不登录都放行可用购物车)
			从cookie中取token。
			没有token，需要跳转到登录页面。
			有token。调用sso系统的服务，根据token查询用户信息。
			如果查不到用户信息。用户登录已经过期。需要跳转到登录页面。
			查询到用户信息。放行。
		拦截器检查到用户信息则在request中设置user
		controller再从request取user判断是否登录和是谁登录,并调用redis相关serivce提供服务
		controller把cookie和redis中购物车合并,再删cookie购物车
	
```	
