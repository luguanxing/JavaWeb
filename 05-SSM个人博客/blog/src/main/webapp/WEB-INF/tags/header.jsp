<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <header>
        <div class="widewrapper masthead">
            <div class="container">
                <a href="${pageContext.request.contextPath}/index.html" id="logo">
                    <img src="${pageContext.request.contextPath}/static/images/logo.jpg" alt="Luguanxing">
                </a>

                <div id="mobile-nav-toggle" class="pull-right">
                    <a href="#" data-toggle="collapse" data-target=".clean-nav .navbar-collapse">
                        <i class="fa fa-bars"></i>
                    </a>
                </div>

                <nav class="pull-right clean-nav">
                    <div class="collapse navbar-collapse">
                        <ul class="nav nav-pills navbar-nav">
                            <li>
                                <a href="${pageContext.request.contextPath}/index.html">主页</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/detail.html">关于</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/comment.html">留言</a>
                            </li>                        
                        </ul>
                    </div>
                </nav>        

            </div>
        </div>

        <div class="widewrapper subheader">
            <div class="container">
                <div class="clean-breadcrumb">
                    <a href="javascript:void(0)">${indexTitle}</a>
                </div>

                <div class="clean-searchbox">
                    <div method="get" accept-charset="utf-8">
                        <input class="searchfield" id="searchbox" type="text" placeholder="搜索博客内容">
                        <button id="searchButton" class="searchbutton" type="button" onclick="search()">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </header>
    
    <script>
	    
	    $("#searchbox").keydown(function(e) {  
	        if (e.keyCode == 13) {  
	        	//按下回车键搜索
	        	$("#searchButton").click();
	        }  
	   });
    
    	function search() {
    		var keyword = $("#searchbox").val();
    		if (isEmpty(keyword)) {
    			alert("搜索内容为空");
    		} else {
    			window.location.href = "${pageContext.request.contextPath}/search/" + keyword + ".html";
    		}
    	}
    	
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
    	
    </script>
    