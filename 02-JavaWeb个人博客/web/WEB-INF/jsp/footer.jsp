<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/10/6
  Time: 20:20
  底部模块
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div class="copyright">
	<p>Copyright &copy; 2017. <a href="https://github.com/luguanxing" target="_blank" title="luguanxing">luguanxing</a></p>
</div>

<a href="#home" id="toTop" class="scroll" style="display: block;">
	<span id="toTopHover" style="opacity: 1;"> </span>
</a>

<script type="text/javascript">
	$(document).ready(function() {
		$().UItoTop({ easingType: 'easeOutQuart' });
	});
</script>

