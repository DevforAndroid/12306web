<%@page import="com.util.TextUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String username = "";
	String autoLogin = "";
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie c: cookies) {
			if ("username".equals(c.getName())) {
				username = c.getValue();
			} else if ("autoLogin".equals(c.getName())) {
				autoLogin = c.getValue();
			}
		}
	}
	String action = request.getParameter("action");
	if (!"logout".equals(action)) { // 退出时不执行自动登陆
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(autoLogin)) {
			response.sendRedirect(request.getContextPath() + "/login?action=autoLogin");
		}
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>12306购票系统</title>
<link href="css/css.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function UserRegistration(){
		window.location.href = "UserRegistration.html";
	}
</script>
<script language="javascript">
	function reloadcode() {
		document.getElementById("codeImg").src = "code?" + new Date();
	}
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
 <form name="form1" method="post" action="login?action=login">
   <table width="933" border="0" align="center" cellpadding="0" cellspacing="0" style="margin:120px;">
  <tr>
    <td height="412" valign="top" background="images/bg_img1.jpg">
    <table height="300" border="0" cellspacing="0">
      <tr>
        <td width="538">&nbsp;</td>
        <td height="130" colspan="6">&nbsp;</td>
        </tr>
      <tr>
        <td rowspan="9">&nbsp;</td>
        <td width="98" height="20" align="right"><img src="images/text_yh.gif" width="60" height="18"></td>
        <td width="16">&nbsp;</td>
        <td width="136">
        	<input name="username" type="text" id="username" size="18" value="<%= username %>" />
        </td>
        <td width="55">&nbsp;</td>
        <td width="44">&nbsp;</td>
        <td width="32">&nbsp;</td>
      </tr>
      <tr>
        <td height="20" align="right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20" align="right"><img src="images/text_password.gif" width="60" height="18"></td>
        <td>&nbsp;</td>
        <td><input name="password" type="password" id="password" size="18" /></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20" align="right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20" align="right"><img src="images/text_yzm.gif" width="60" height="18"></td>
        <td>&nbsp;</td>
        <td><input name="code" type="text" id="code" size="18" /></td>
        <td>
        	<span class="text_cray1">
        		<img id="codeImg" src="code" alt="点击换一张" style="cursor: hand" onclick="javascript:reloadcode()" height="20" />
        	</span>
        </td>
        <td>
        	<img src="images/text_sx.gif" width="32" height="18" onclick="javascript:reloadcode()" />
        </td>
        <td align="left">&nbsp;</td>
      </tr>
      <tr>
        <td height="30">&nbsp;</td>
        <td>&nbsp;</td>
        <td valign="bottom"><table width="100%" border="0" cellspacing="0">
          <tr>
            <td width="26" align="left">
            	<input name="autoLogin" type="checkbox" value="1" style=" margin:0 auto;"/></td>
            <td width="170"><img src="images/text_zddl.gif" width="60" height="18"></td>
          </tr>
        </table></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="2">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20">&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="2"><table width="200" border="0" cellspacing="0">
          <tr>
            <td width="78"><input type="submit"  class="butlogin" value="" /></td>
            <td>&nbsp;</td>
            <td width="78"><input type="button"  class="butzc" onClick="UserRegistration()"></td>
          </tr>
        </table></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td height="20" colspan="6" align="center">
			<%
			String msg = (String)request.getAttribute("msg");
			if (!TextUtils.isEmpty(msg)) {
				%>
				<%= msg %>
				<%
			}
			%>
		</td>
      </tr>
    </table></td>
  </tr>
</table>
 </form>
</body>
</html>
