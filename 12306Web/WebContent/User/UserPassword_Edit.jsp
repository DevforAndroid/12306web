<%@page import="com.util.TextUtils"%>
<%@page import="com.user.po.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户密码修改</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>

<body class="write_bg">
<%
User user = (User)session.getAttribute("user");
%>

<form name="form1" method="post" action="<%= request.getContextPath() %>/User/user?action=password">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30"></td>
  </tr>
</table>
<table width="835" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20" colspan="2"></td>
  </tr>
  <tr>
    <td width="64" height="12" ></td>
    <td width="744" height="30" align="left" class="text_blod_title">密码修改</td>
  </tr>
  <tr>
    <td height="15" colspan="2"><img src="../images/line1.jpg" width="835" height="6"></td>
    </tr>
  <tr>
    <td colspan="2"  ><table width="700" border="0" align="center" cellspacing="0">
      <tr>
        <td width="20"></td>
        <td width="100" height="40" class="text_cray1">用户名：</td>
        <td align="left" class="text_cray1">
        	<input name="textfield4" type="text" disabled="disabled" class="text_cray" id="textfield4" value="<%= user.getUsername() %>" size="30" readonly="readonly"/>
        </td>
      </tr>
      <tr>
        <td width="20" align="center" class="text_red">*</td>
        <td width="100" height="40" class="text_cray1">原密码：</td>
        <td align="left" class="text_cray1">
        	<input name="passwordOld" type="password" class="text_cray" id="passwordOld" size="30" />
        </td>
      </tr>
      <tr>
        <td width="20" align="center"  class="text_red">*</td>
        <td width="100" height="40" class="text_cray1">新密码：</td>
        <td align="left" class="text_cray1">
        	<input name="password" type="password" class="text_cray" id="password" size="30" />
        </td>
      </tr>
      <tr>
        <td width="20" align="center" class="text_red">*</td>
        <td width="100" height="40" class="text_cray1">确认新密码：</td>
        <td align="left" class="text_cray1">
        	<input name="password2" type="password" class="text_cray" id="password2" size="30" />
        </td>
      </tr>
    </table>
      <br></td>
  </tr>
</table>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="124" height="30"></td>
    <td width="78" align="left"><input name="button" type="submit" class="buttj" id="button"value=""></td>
    <td width="39" align="center"></td>
    <td align="left"><input name="button2" type="reset" class="butqx" id="button2"value=""></td>
    </tr>
</table>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td height="275" valign="top">
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
</table>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td height="2" background="../images/bottom_point.gif"></td>
  </tr>
  <tr>
    <td height="25" align="center" background="../images/bottom_ny_bg.gif" class="text_cray">copyright@12306 购票网</td>
  </tr>
</table>
</form>
</body>
</html>
