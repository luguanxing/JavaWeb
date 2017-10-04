<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>网上商城管理中心</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<style type="text/css">
			body {
				color: white;
			}
		</style>
	</head>
	<body style="background: #278296">
		
		<form method="post" action="${pageContext.request.contextPath }/adminLogin" name='theForm' onsubmit="return validate()">
			<table cellspacing="0" cellpadding="0" style="margin-top: 100px" align="center">
				<tr>
					<td style="padding-left: 50px">
						<table>
							<center><span style="color: red">${msg}</span></center>
							<tr>
								<td>管理员姓名：</td>
								<td><input type="text" name="username"/></td>
							</tr>
							<tr>
								<td>管理员密码：</td>
								<td><input type="password" name="password"/></td>
							</tr>
							<tr>
								<td>验证码：</td>
								<td><input type="text" name="inputCaptcha"/></td>
								<td>
									<a style="cursor: pointer" onclick="changeCaptcha()">
										<img id="captcha" src="${pageContext.request.contextPath}/getCaptcha"/>
									</a>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><input type="submit" value="进入管理中心" class="button"/></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">

			function changeCaptcha() {
				//加上时间戳避免读缓冲
				var timestamp = (new Date()).valueOf();
				$("#captcha").attr("src", "/getCaptcha?t="+timestamp);
			}

		</script>
	</body>