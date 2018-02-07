<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <div class="aside-widget">
        <header>
            <h3>
            	<i class="fa fa-user"></i>
            	博主信息
            </h3>
        </header>
        <div class="body">
               <center>
               	<a href="${pageContext.request.contextPath}/detail.html">
               		<img style="width:80%" src="${blogger.image}" alt="${blogger.username}">
               	</a>
                <h3>
                	<a href="${pageContext.request.contextPath}/detail.html">${blogger.username}</a>
                </h3>
                <span class="meta">${blogger.nickname}</span><br>
                <span class="meta">${blogger.sign}</span>
               </center>
        </div>
    </div>

  