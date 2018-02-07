<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <title>${htmlTitle}</title>
	    <meta name="viewport" content="width=device-width">
		<%@ include file="/WEB-INF/tags/css"%>
		<%@ include file="/WEB-INF/tags/js"%>
	</head>
	<body>
	
		<%@ include file="/WEB-INF/tags/header.jsp"%>
	
	    <div class="widewrapper main">
	    
	        <div class="container">
	            <div class="row">
	                <div class="col-md-9 blog-main">

	                    <article class="blog-teaser aside-widget">
		                    <header style="text-align:left">
					            <h3>
					            	<i class="fa fa-book"></i>
					            	博客内容
					            </h3>
					        </header>
	                    	<center class="meta">
								<h1>${blog.title}</h1>
		                        <div class="body">
		                            <div class="meta">
		                                <i class="fa fa-tags"></i>
		                                	<b>博客类型</b> : ${blogtypeName} 
		                                <i class="fa fa-calendar"></i>
		                                	<b>发布时间</b> :
		                                	<fmt:formatDate value="${blog.releaseDate}" type="date" pattern="yyyy年MM月dd日  HH:mm:ss"/>
		                            </div>
		                        </div>
		                        <div class="body">
		                            <div style="border:1px solid #ddd; padding: 20px;">
		                               <h3>${blog.summary}</h3>
		                            </div>
		                        </div>
	                        </center>
	                        <div style="padding:25px; overflow-x:auto;">
	                        	${blog.content}
	                        </div>
	                    </article>
	                	
	                </div>
	                
	                <aside class="col-md-3 blog-aside">

						<!-- 博主信息 -->
						<%@ include file="/WEB-INF/tags/blogger.jsp"%>
						
						<!-- 推荐博客 -->
						<%@ include file="/WEB-INF/tags/priorityblog.jsp"%>

						<!-- 博客类型-->
						<%@ include file="/WEB-INF/tags/blogtype.jsp"%>
						
						<!-- 友情链接-->
						<%@ include file="/WEB-INF/tags/link.jsp"%>

	                </aside>
	            </div>
	        </div>
	    </div>
	    
		<%@ include file="/WEB-INF/tags/footer.jsp"%>
		
	</body> 
	
</html>