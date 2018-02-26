<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改新闻页面</title>
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
				var editor=UE.getEditor('editor').getContent();
				$("#content").val(editor);
				$("#fm").submit();
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
		<div id="p" class="easyui-panel" title="修改新闻" style="padding: 5px">
			<form id="fm" action="${pageContext.request.contextPath}/admin/new/update.do" method="post" enctype="multipart/form-data">
				<table cellspacing="10px">
					<tr>
						<td width="80px">新闻id：</td>
						<td><input value="${newObj.id}" disabled="disabled" style="width: 50px"/></td>
					</tr>
					<tr>
						<td width="80px">新闻标题：</td>
						<td><input value="<c:out value="${newObj.title}"/>" type="text" id="title" name="title" style="width: 400px"/></td>
					</tr>
					<tr>
						<td width="80px">新闻链接：</td>
						<td><input value="${newObj.url}" type="text" id="url" name="url" style="width: 300px"/></td>
					</tr>
					<tr>
						<td valign="top">新闻摘要：</td>
						<td>
							<textarea rows="3" cols="60" id="contentText" name="contentText">${newObj.contentText}</textarea>
						</td>
					</tr>
					<tr>
						<td width="80px">新闻评论数：</td>
						<td><input value="${newObj.commentCount}" type="number" id="commentCount" name="commentCount" style="width: 300px"/></td>
					</tr>
					<tr>
						<td valign="top">新闻发布时间和来源：</td>
						<td>
							<textarea rows="3" cols="60" id="publishDateAndSrc" name="publishDateAndSrc">${newObj.publishDateAndSrc}</textarea>
						</td>
					</tr>
					<tr>
						<td valign="top">新闻爬取日期：</td>
						<td>
							<input class="easyui-datetimebox" id="crawlerDateInput" name="crawlerDateInput" data-options="required:true,showSeconds:false" style="width:150px">
						</td>
						<script>
							$(function(){
								var new_time = new Date(parseInt("${newObj.crawlerDate.time}"));
								var strDate = new_time.getFullYear()+"-";
								strDate += new_time.getMonth()+1+"-";
								strDate += new_time.getDate()+" ";
								strDate += new_time.getHours()+":";
								strDate += new_time.getMinutes()+":";
								strDate += new_time.getSeconds();
								console.log(strDate);
								$("#crawlerDateInput").datebox("setValue", strDate); 
							});
						</script>
					</tr>
					<tr>
						<td valign="top">新闻内容：</td>
						<td>
							<script id="editor" type="text/plain" style="width:1000px;height:500px;">${newObj.content}</script>
							<input type="hidden" id="content" name="content"/>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<!-- id也要提交 -->
							<input type="hidden" id="id" name="id" value="${newObj.id }"/>
							<a href="javascript:submitData()" class="easyui-linkbutton" data-options="iconCls:'icon-submit'">更新新闻</a>
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