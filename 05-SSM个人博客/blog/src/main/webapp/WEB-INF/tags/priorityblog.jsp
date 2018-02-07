<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<div class="aside-widget">
	    <header>
	        <h3>
	        	<i class="fa fa-book"></i>
	        	推荐博客
	        </h3>
	    </header>
	    <div class="body">
	        <ul class="clean-list">
	        	<c:forEach items="${priorityBlogs}" var="blog">
	             <li>
	             	<i class="fa fa-book" style="font-size: 20px;"></i>
	             	<a target="_blank" href="${pageContext.request.contextPath}/article/${blog.id}.html">${blog.title}</a>
	             </li>
	        	</c:forEach>
	        </ul>
	    </div>
	</div>

  