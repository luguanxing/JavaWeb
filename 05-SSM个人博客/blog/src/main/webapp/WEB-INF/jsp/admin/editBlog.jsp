<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改博客页面</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
		
		<script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
		<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
		<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
		<script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
		
		<script type="text/javascript">
		
			function checkChange(){
				if(document.getElementById("isSlide").checked){
					$("#hdtp").show();
				}else{
					$("#hdtp").hide();
				}
			}
			
			function submitData(){
				var title=$("#title").val();
				var typeId=$("#typeId").combobox("getValue");
				var summary=$("#summary").val();
				var editor=UE.getEditor('editor').getContent();
				
				if(title==null || title==''){
					alert("请输入标题");
				}else if(typeId==null || typeId==''){
					alert("请选择博客类别");
				}else if(summary==null || summary==''){
					alert("请输入摘要");
				}else if(editor==null || editor==''){
					alert("请输入内容");
				}else{
					$("#content").val(editor);
					$("#fm").submit();
				}
			}
		
			Date.prototype.Format = function(fmt)     
			{   
			  var o = {     
			    "M+" : this.getMonth()+1,                 //月份     
			    "d+" : this.getDate(),                    //日  
			    "h+" : this.getHours(),                   //小时     
			    "m+" : this.getMinutes(),                 //分     
			    "s+" : this.getSeconds(),                 //秒     
			    "q+" : Math.floor((this.getMonth()+3)/3), //季度     
			    "S"  : this.getMilliseconds()             //毫秒     
			  };     
			  if(/(y+)/.test(fmt))     
			    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));     
			  for(var k in o)     
			    if(new RegExp("("+ k +")").test(fmt))     
			  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));     
			  return fmt;     
			};
			
		</script>
	</head>
	<body style="margin: 1px">
		<div id="p" class="easyui-panel" title="修改博客" style="padding: 5px">
			<form id="fm" action="${pageContext.request.contextPath}/admin/blog/update.do" method="post" enctype="multipart/form-data">
			<table cellspacing="10px">
					<tr>
						<td width="80px">博客id：</td>
						<td><input value="${blog.id}" disabled="disabled" style="width: 50px"/></td>
					</tr>
					<tr>
						<td width="80px">博客标题：</td>
						<td><input value="${blog.title}" type="text" id="title" name="title" style="width: 400px"/></td>
					</tr>
					<tr>
						<td>所属类别：</td>
						<td>
							<select class="easyui-combobox" style="width: 154px" id="typeId" name="typeId" editable="false" panelHeight="auto">
								<option value="">请选择博客类别...</option>
								<c:forEach var="blogtype" items="${blogtypes }">
									<c:if test="${blogtype.id == blog.typeId}">
										<option value="${blogtype.id }" selected =selected" >${blogtype.typeName }</option>
									</c:if>
									<c:if test="${blogtype.id != blog.typeId}">
										<option value="${blogtype.id }">${blogtype.typeName }</option>
									</c:if>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="80px">博客图片链接：</td>
						<td>
							<c:if test="${not empty blog.image}">
	                       		<img style="width:100px; height:100px;" src="${blog.image}" alt="">
	                       	</c:if>
	                        <c:if test="${empty blog.image}">
	                       		<img style="width:100px; height:100px;" src="${pageContext.request.contextPath}/static/images/blogimage.jpg" alt="">
	                       	</c:if>
	                       	<br/>
							<input value="${blog.image}" type="text" id="image" name="image" style="width: 400px"/>
						</td>
					</tr>
					<tr>
						<td>博客优先级：</td>
						<td>
							<input value="${blog.priority}" type="text" id="priority" value="0" name="priority" class="easyui-numberbox" required="true" style="width: 60px"/>&nbsp;(类别根据排序序号从大到小排序)&nbsp;
						</td>
					</tr>
					<tr>
						<td valign="top">博客摘要：</td>
						<td>
							<textarea rows="3" cols="40" id="summary" name="summary">${blog.summary}</textarea>
						</td>
					</tr>
					<tr>
						<td valign="top">博客发布日期：</td>
						<td>
							<input class="easyui-datetimebox" id="releaseDateInput" name="releaseDateInput" data-options="required:true,showSeconds:false" style="width:150px">
						</td>
						<script>
							$(function(){
								var blog_time = new Date(parseInt("${blog.releaseDate.time}"));
								var strDate = blog_time.getFullYear()+"-";
								strDate += blog_time.getMonth()+1+"-";
								strDate += blog_time.getDate()+" ";
								strDate += blog_time.getHours()+":";
								strDate += blog_time.getMinutes()+":";
								strDate += blog_time.getSeconds();
								console.log(strDate);
								$("#releaseDateInput").datebox("setValue", strDate); 
							});
						</script>
					</tr>
					<tr>
						<td valign="top">博客内容：</td>
						<td>
							<script id="editor" type="text/plain" style="width:1000px;height:500px;">${blog.content}</script>
							<input type="hidden" id="content" name="content"/>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<!-- id也要提交 -->
							<input type="hidden" id="id" name="id" value="${blog.id }"/>
							<a href="javascript:submitData()" class="easyui-linkbutton" data-options="iconCls:'icon-submit'">更新博客</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		
		<script type="text/javascript">
		    //实例化编辑器
		    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		    var ue = UE.getEditor('editor');
		</script>
	</body>
</html>