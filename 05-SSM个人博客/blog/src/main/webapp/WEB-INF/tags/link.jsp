<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<div class="aside-widget">
	    <header>
	        <h3>
	        	<i class="fa fa-link"></i>
	       		 友情链接
	        </h3>
	    </header>
	    <div class="body clearfix">
	        <ul class="tags">
	        	<c:forEach items="${links}" var="link">
	        		<li>
	        			<a href="${link.linkUrl}">${link.linkName}</a>
	        		</li>
	        	</c:forEach>
	        </ul>
	    </div>
	</div>