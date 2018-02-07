<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
	.pagination>.active>a {
	 background-color:#ccc;
	 border-color:#ddd;
	}
	.pagination>li>a:hover {
	 background-color:#ccc !important;
	 border-color:#ddd !important;
	}
</style>
<script src="${pageContext.request.contextPath}/static/common/bootstrap-paginator.min.js"></script>
<div id="example" style="text-align:center;">
	<ul style="cursor:pointer;" id="pageLimit"></ul>
</div> 	
<script>
	try {
	    $('#pageLimit').bootstrapPaginator({
		    currentPage: ${currentPage},//当前的请求页面。
		    totalPages: ${totalPages},//一共多少页。
		    size:"normal",//应该是页眉的大小。
		    bootstrapMajorVersion: 3,//bootstrap的版本要求。
		    alignment:"right",
		    numberOfPages:${numberOfPages},//一页列出多少数据。
		    itemTexts: function (type, page, current) {//如下的代码是将页眉显示的中文显示我们自定义的中文。
		        switch (type) {
			        case "first": return "首页";
			        case "prev": return "上一页";
			        case "next": return "下一页";
			        case "last": return "末页";
			        case "page": return page;
		        }
		    },
	        shouldShowPage:function(type, page, current) {	//控制按钮显示
	            switch(type)
	            {
	                case "prev":
	                	if (current == 1)
	                		return false;
	                    return true;
	                case "next":
	                	if (current == page)
	                		return false;
	                    return true;
	                default:
	                    return true;
	            }
	        },
	        pageUrl: function(type, page, current){
	            return "${pageContext.request.contextPath}/${pageUrl}/"+ page + ".html";
	        }
		});
	} catch (err) {
		$("#example").html("没有找到相关内容");
	}
</script>
  