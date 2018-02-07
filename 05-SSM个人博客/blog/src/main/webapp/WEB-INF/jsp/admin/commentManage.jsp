<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>评论管理页面</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript">
		
			var url;
			
			function resetValue(){
				$("#name").val("");
				$("#url").val("");
				$("#sortNo").val("");
			}
			
			function closeLinkDialog(){
				$("#dlg").dialog("close");
				resetValue();
			}
		
			function deleteComment(){
				var selectedRows=$("#dg").datagrid("getSelections");
				if(selectedRows.length==0){
					$.messager.alert("系统提示","请选择要删除的评论！");
					return;
				}
				var strIds=[];
				for(var i=0;i<selectedRows.length;i++){
					strIds.push(selectedRows[i].id);
				}
				var ids=strIds.join(",");
				$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
					if(r){
						$.post("${pageContext.request.contextPath}/admin/comment/delete.do",{ids:ids},function(result){
							if(result.success){
								$.messager.alert("系统提示","数据已成功删除！");
								$("#dg").datagrid("reload");							
							}else{
								$.messager.alert("系统提示","数据删除失败！");
							}
						},"json");
					}
				});
			}
			
			function contentFormatter(value, row, index) {
				return "<xmp>" + value + "</xmp>";
			}
			
			function dateFormatter(value, row, index) {
				var date = (new Date(value.time)).Format("yyyy-MM-dd hh:mm:ss");
				return date;
			}
			
			function nicknameFormatter(value, row, index) {
				return "<xmp>" + value + "</xmp>";
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
		<table id="dg" title="评论管理" class="easyui-datagrid"
		fitColumns="true" pagination="true" rownumbers="true"
		url="${pageContext.request.contextPath}/admin/comment/list.do" fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th field="cb" checkbox="true" align="center"></th>
					<th field="userip" width="60" align="center">ip地址</th>
					<th field="nickname" width="50" align="center" formatter="nicknameFormatter">昵称</th>
					<th field="content" width="150" align="center" formatter="contentFormatter">评论内容</th>
					<th field="commentDate" width="50" align="center" formatter="dateFormatter">评论日期 </th>
					<th field="state" width="20" align="center">评论状态 </th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<div>
			    <a href="javascript:deleteComment()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			</div>
		</div>
				
		<div id="dlg-buttons">
			<a href="javascript:saveLink()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
			<a href="javascript:closeLinkDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
		</div>
	</body>
</html>