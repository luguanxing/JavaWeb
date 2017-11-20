# JavaWeb个人博客

## 需求分析

```
使用SSM实现一个网站CMS内容管理系统

```
		

## 开发日志
```
步骤1-搭建web框架
	源码分包src/main/java
	cms
		cms.controller
		cms.dao
		cms.entity
		cms.realm
		cms.service
		cms.utils
	配置分文件夹src/main/resources
	mappers
		dao的实现文件
	spring
		spring-web
		spring-service
		spring-web
		spring-realm
	mybatis-config.xml
	log4j.properties
	
案例2-完成登录验证功能
	配置realm验证和加密，写dao、service、controller，导入前端页面
	
案例3-完成主页前端
	index.jsp
	common/
		header.jsp
		menu.jsp
		link.jsp
		footer.jsp

```
