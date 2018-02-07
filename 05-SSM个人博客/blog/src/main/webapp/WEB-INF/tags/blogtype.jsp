<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<div class="aside-widget">
	    <header>
	        <h3>
	        	<i class="fa fa-tags"></i>
	        	博客类型
	        </h3>
	    </header>
	    <div class="body clearfix">
	        <ul class="tags">
	        	<c:forEach items="${blogtypes}" var="blogtype">
	        		<li>
	        			<a href="${pageContext.request.contextPath}/blogtype/${blogtype.typeName}.html">${blogtype.typeName}</a>
	        		</li>
	        	</c:forEach>
	        </ul>
	    </div>
	</div>
  