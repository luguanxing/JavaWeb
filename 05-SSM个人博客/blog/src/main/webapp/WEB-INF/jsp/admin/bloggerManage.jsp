<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>主页信息管理页面</title>
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
				$("#profile").val(editor);
				$("#fm").submit();
			}
		
		</script>
	</head>
	<body style="margin: 1px">
		<div id="p" class="easyui-panel" title="修改博主信息" style="padding: 5px">
			<form id="fm" action="${pageContext.request.contextPath}/admin/blogger/modifyBlogger.do" method="post" enctype="multipart/form-data">
			<table cellspacing="10px">
					<tr>
						<td width="80px">博主名称：</td>
						<td><input type="text" value="${blogger.username}" id="username" name="username" style="width: 200px"/>
					</tr>
					<tr>
						<td width="80px">昵称：</td>
						<td><input type="text" value="${blogger.nickname}" id="nickname" name="nickname" style="width: 200px"/>
					</tr>
					<tr>
						<td width="80px">个性签名：</td>
						<td><input type="text" value="${blogger.sign}" id="sign" name="sign" style="width: 300px"/>
					</tr>
					<tr id="hdtp">
						<td>头像链接：</td>
						<td>
							<img style="width:100px; height:100px;" src="${blogger.image}" alt="${blogger.username}"><br/><br/>
							<input type="text"" value="${blogger.image}"  id="image" name="image" style="width: 300px"/>
						</td>
					</tr>
					<tr>
						<td valign="top">主页简介：</td>
						<td>
							<script id="editor" type="text/plain" style="width:1000px;height:500px;">
								${blogger.profile}
							</script>
							<input type="hidden" id="profile" name="profile"/>
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