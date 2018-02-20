<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>博客管理页面</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript">
			
			function formatTitle(val,row){
				return "&nbsp;<a target='_blank' href='${pageContext.request.contextPath}/article/"+row.id+".html'>"+val+"</a>";
			}
			
			function imageFormatter(val,row){
				if (val != "")
					return "<img style='width:50px; height:50px' src='"+val+"'></img>";
				else
					return "<img style='width:50px; height:50px' src='${pageContext.request.contextPath}/static/images/blogimage.jpg'></img>";
			}
			
			function openBlogModifyTab(){
				var selectedRows=$("#dg").datagrid("getSelections");
				if(selectedRows.length!=1){
					$.messager.alert("系统提示","请选择一个要修改的博客！");
					return;
				}
				var row=selectedRows[0];
				window.parent.openTab("修改博客","editBlog.html?id="+row.id,"icon-writeArticle");
			}
			
			function deleteBlog(){
				var selectedRows=$("#dg").datagrid("getSelections");
				if(selectedRows.length==0){
					$.messager.alert("系统提示","请选择要删除的博客！");
					return;
				}
				var strIds=[];
				for(var i=0;i<selectedRows.length;i++){
					strIds.push(selectedRows[i].id);
				}
				var ids=strIds.join(",");
				$.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
					if(r){
						$.post("${pageContext.request.contextPath}/admin/blog/delete.do",{ids:ids},function(result){
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
			
			function refreshBlog() {
				$("#dg").datagrid("reload");
			}
			
			function blogtypeFormatter (value, row, index) {
				<c:forEach items="${blogtypes}" var="blogtype" >
					if ("${blogtype.id}" == value)
						return"${blogtype.typeName}";
				</c:forEach>
				return value;
			}
			
			function dateFormatter(value, row, index) {
				var date = (new Date(value.time)).Format("yyyy-MM-dd hh:mm:ss");
				return date;
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
		<table id="dg" title="博客管理" class="easyui-datagrid"
		fitColumns="true" pagination="true" rownumbers="true"
		url="${pageContext.request.contextPath}/admin/blog/list.do" fit="true" toolbar="#tb">
			<thead>
				<tr>
					<th field="cb" checkbox="true" align="center"></th>
					<th field="id" width="20" align="center">编号</th>
					<th field="image" width="30" align="center" formatter="imageFormatter">图片</th>
					<th field="title" width="50" align="center" formatter="formatTitle">标题</th>
					<th field="summary" width="100" align="center">摘要</th>
					<th field="releaseDate" width="50" align="center" formatter="dateFormatter">发布日期</th>
					<th field="typeId" width="50" align="center" formatter="blogtypeFormatter">博客类别</th>
					<th field="priority" width="20" align="center">优先级</th>
				</tr>
			</thead>
		</table>
		<div id="tb">
			<div>
				<a href="javascript:refreshBlog()" class="easyui-linkbutton" iconCls="icon-reload" plain="true">刷新</a>
				<a href="javascript:openBlogModifyTab()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
			    <a href="javascript:deleteBlog()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			</div>
		</div>
	</body>
</html>