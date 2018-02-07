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
	                	<c:forEach items="${blogs}" var="blog" varStatus="vs">
	                		<c:if test="${vs.index % 3 == 0}">
	                			<div class="row">
	                		</c:if>
		                		<div class="col-md-4 col-sm-4">
		                            <article class="blog-teaser">
		                                <header>
		                                	<c:if test="${not empty blog.image}">
		                                		<a target="_blank" href="${pageContext.request.contextPath}/article/${blog.id}.html">
		                                			<img src="${blog.image}" alt="">
		                                		</a>
		                                	</c:if>
		                                    <c:if test="${empty blog.image}">
		                                    	<a target="_blank" href="${pageContext.request.contextPath}/article/${blog.id}.html">
		                                			<img src="${pageContext.request.contextPath}/static/images/blogimage.jpg" alt="">
		                                		</a>
		                                	</c:if>
		                                    <h3>
		                                    	<a target="_blank" href="${pageContext.request.contextPath}/article/${blog.id}.html">${blog.title}</a>
		                                    </h3>
		                                    <span class="meta"><fmt:formatDate value="${blog.releaseDate}" type="date" pattern="yyyy年MM月dd日  HH:mm:ss"/></span>
		                                    <hr>
		                                </header>
		                                <div class="body">
		                                   ${blog.summary}
		                                </div>
		                                <div class="clearfix">
		                                    <a target="_blank" href="${pageContext.request.contextPath}/article/${blog.id}.html" class="btn btn-clean-one">阅读</a>
		                                </div>
		                            </article>
		                		</div>
		                	<c:if test="${vs.last}">
		                		</div>
		                	</c:if>
		                	<c:if test="${not vs.last}">
		                		<c:if test="${vs.index % 3 == 2}">
		                			</div>
		                		</c:if>
		                	</c:if>
	                	</c:forEach> 
	                	
	                	<!-- 分页 -->
						<%@ include file="/WEB-INF/tags/paginator.jsp"%>
	                	
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