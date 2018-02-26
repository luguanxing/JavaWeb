<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

		<h1 style="display:none;">新闻采集系统</h1>
		<div class="m-hd">
			<a href="${pageContext.request.contextPath}/index.html">
				<div class="m-logo"></div>
			</a>
			<a href="javascript:void(0)?type=pc"></a>
		</div>
		<a href="${pageContext.request.contextPath}/index.html">
			<img class="m-ad lazyloaded" src="${pageContext.request.contextPath}/static/news/img/banner20160706.jpg">
		</a>

		<script>

	    	function isEmpty(v) {
	    	    switch (typeof v) {
	    	    case 'undefined':
	    	        return true;
	    	    case 'string':
	    	        if (v.replace(/(^[ \t\n\r]*)|([ \t\n\r]*$)/g, '').length == 0) return true;
	    	        break;
	    	    case 'boolean':
	    	        if (!v) return true;
	    	        break;
	    	    case 'number':
	    	        if (0 === v || isNaN(v)) return true;
	    	        break;
	    	    case 'object':
	    	        if (null === v || v.length === 0) return true;
	    	        for (var i in v) {
	    	            return false;
	    	        }
	    	        return true;
	    	    }
	    	    return false;
	    	}
		
			function search() {
				var keyword = document.getElementById("searchText").value;
	    		if (isEmpty(keyword)) {
	    			alert("搜索内容为空");
	    		} else {
	    			window.location.href = "${pageContext.request.contextPath}/search/" + keyword + ".html";
	    		}
			}
			
		</script>