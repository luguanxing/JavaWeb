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
					            	<i class="fa fa-user"></i>
					            	${indexTitle}
					            </h3>
					        </header>
	                        <div style="padding:25px; overflow-x:auto;">
	                        	${blogger.profile}
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