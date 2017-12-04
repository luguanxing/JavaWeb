<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>写邮件页面</title>
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
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/colorPicker/js/jquery.bigcolorpicker.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/static/colorPicker/css/jquery.bigcolorpicker.css" type="text/css" />
		<script type="text/javascript">
		
			function checkChange(){
				if(document.getElementById("isSlide").checked){
					$("#hdtp").show();
				}else{
					$("#hdtp").hide();
				}
			}
			
			function submitData(){
				var target=$("#target").val();
				var emailSourceid=$("#emailSourceid").combobox("getValue");
				var title=$("#title").val();
				var content=UE.getEditor('editor').getContent();
				console.log(content);
				
				if(title==null || title==''){
					alert("请输入标题");
				}else if(target==null || target==''){
					alert("请选择发送目标");
				}else if(emailSourceid==null || emailSourceid==''){
					alert("请选择邮件源");
				}else if(content==null || content==''){
					alert("请输入内容");
				}else{
					$("#content").val(content);
					$("#fm").submit();
				}
			}
		
		</script>
	</head>
	<body style="margin: 1px">
		<div id="p" class="easyui-panel" title="编写邮件" style="padding: 5px">
			<form id="fm" action="${pageContext.request.contextPath}/admin/email/send.do" method="post" enctype="multipart/form-data">
			<table cellspacing="10px">
					<tr>
						<td valign="top">发送目标：</td>
						<td>
							<input type="text" id="target" name="target" style="width: 400px"/>
						</td>
					</tr>
					<tr>
						<td>邮件源：</td>
						<td>
							<select class="easyui-combobox" style="width: 250px" id="emailSourceid" name="emailSourceid" editable="false" panelHeight="auto">
								<option value="">请选择邮件源...</option>
								<c:forEach var="mailSource" items="${mailSourceList }">
									<script>
										console.log("${mailSource}");
									</script>
									<option value="${mailSource.id }">${mailSource.email }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td width="80px">邮件标题：</td>
						<td><input type="text" id="title" name="title" style="width: 400px"/></td>
					</tr>
					<tr id="hdtp"">
						<td>附加文件：</td>
						<td>
							<input type="file" id="uploadFile" name="uploadFile"/>
						</td>
					</tr>
					<tr>
						<td valign="top">邮件内容：</td>
						<td>
							<script id="editor" type="text/plain" style="width:1000px;height:500px;"></script>
							<input type="hidden" id="content" name="content"/>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<a href="javascript:submitData()" class="easyui-linkbutton" data-options="iconCls:'icon-submit'">发布帖子</a>
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