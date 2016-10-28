<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.user.po.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户信息修改</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">

</head>

<body class="write_bg">
<%
User user = (User)session.getAttribute("user");
%>

<form name="form1" method="post" action="<%= request.getContextPath() %>/User/user?action=user" enctype="multipart/form-data">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30"><br>
            <br></td>
  </tr>
</table>
<table width="835" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20" colspan="2" align="center" ></td>
  </tr>
  <tr>
    <td width="64" height="11" class="text_blod_title"></td>
    <td width="786" height="30" align="left" class="text_blod_title">修改个人信息</td>
  </tr>
  <tr>
    <td height="15" colspan="2" ><img src="../images/line1.jpg" width="835" height="6"></td>
  </tr>
  <tr>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="64"></td>
        <td width="772" height="25" align="left" class="text_cray">注：标有 <span class="text_red">*</span> 处，均为必填项</td>
      </tr>
      <tr>
        <td height="20" colspan="2"></td>
        </tr>
    </table>
        <table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="15" colspan="4" align="left" class="text_title">详细信息</td>
          </tr>
          <tr>
            <td height="10" colspan="4" ></td>
          </tr>
          <tr>
            <td width="20" align="center" class="text_red1"></td>
            <td width="100" height="40" align="left" class="text_cray1">登录名：</td>
            <td width="350" align="left" class="text_cray1">
            	<input name="textfield22" type="text" disabled="disabled" class="text_cray" id="textfield22" value="<%= user.getUsername() %>" />
            </td>
            <td width="230" colspan="-1" rowspan="7" align="center" background="../images/bg_point_write.gif" class="text_cray1">
            	<img src="<%= request.getContextPath() %>/images/photo/${ user.imagePath == null ? 'photo_mr.jpg': user.imagePath  }"  width="120">              
            	<table width="90%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="15" ></td>
                </tr>
                <tr>
                  <td height="7" align="center" class="text_cray">上传照片</td>
                </tr>
                <tr>
                  <td height="8" ></td>
                </tr>
                <tr>
                  <td align="center"><input name="uploadFile" type="file" class="text_cray" size="20" /></td>
                </tr>
              </table>
            </td>
          </tr>
          <tr>
            <td width="20" ></td>
            <td width="100" height="40" align="left" class="text_cray1">真实姓名：</td>
            <td align="left" class="text_cray1"><input name="textfield2" type="text" disabled="disabled" class="text_cray" id="textfield2" value="<%= user.getRealname() %>"  /></td>
          </tr>
          <tr>
            <td width="20" ></td>
            <td width="100" height="30" align="left" class="text_cray1">性 别：</td>
            <td align="left" class="text_cray1">
              <input type="radio" name="userDTO.sex_code" value="1"  disabled="disabled" <%= "1".equals(user.getSex())? "checked": "" %> />
          <span class="text_cray">
              <label>男</label>
              <input type="radio" name="userDTO.sex_code" value="2"  disabled="disabled" <%= "2".equals(user.getSex())? "checked": "" %> />
              <label>女</label>
              </span>
              <label></label>
<span><label></label>
</span> 
</td>
 </tr>
          <tr>
            <td width="20" align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">省份：</td>
            <td align="left" class="text_cray1"><input name="textfield2" type="text" disabled="disabled" class="text_cray" id="textfield2" value="<%= user.getCity().getProvince().getProvince() %>" />            </td>
          </tr>
          <tr>
            <td width="20" align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">城市：</td>
            <td align="left" class="text_cray1"><input name="textfield2" type="text" disabled="disabled" class="text_cray" id="textfield2" value="<%= user.getCity().getCity() %>" />            </td>
          </tr>
          <tr>
            <td width="20" align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">证件类型：</td>
            <td align="left" class="text_cray1"><input name="textfield2" type="text" disabled="disabled" class="text_cray" id="textfield2" value="<%= user.getCertType().getContent() %>" />            </td>
          </tr>
          <tr>
            <td width="20" align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">证件号码：</td>
            <td align="left" class="text_cray1">
            	<input name="textfield6" type="text" disabled="disabled" class="text_cray" id="textfield6"  value="<%= user.getCert() %>" />
            </td>
          </tr>
          <tr>
            <td width="20" align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">出生日期：</td>
            <td colspan="2" align="left" class="text_cray1">
            	<input name="textfield7" type="text" disabled="disabled" class="text_cray" id="textfield7" value="<%= new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday()) %>" />
            </td>
          </tr>
          <tr>
            <td width="20" height="35" ></td>
            <td width="100" height="40" align="left" class="text_cray1">旅客类型：</td>
            <td height="35" colspan="2" align="left" class="text_cray1"><select class="text_cray" name="userType">
                <option value="1" <%= user.getUserType().getId()== 1 ? "selected" : "" %>>成人</option>
                <option value="2" <%= user.getUserType().getId()== 2 ? "selected" : "" %>>儿童</option>
                <option value="3" <%= user.getUserType().getId()== 3 ? "selected" : "" %>>学生</option>
                <option value="4" <%= user.getUserType().getId()== 4 ? "selected" : "" %>>残疾军人、伤残人民警察</option>
            </select>            </td>
          </tr>
          <tr>
            <td height="10" colspan="4" align="center"></td>
          </tr>
          <tr>
            <td width="20" ></td>
            <td width="100" height="80" align="left" class="text_cray1">备注：</td>
            <td height="80" colspan="2" align="left" class="text_cray1">
            	<textarea name="content" rows="8" class="text_cray" style="width:100%"><%= user.getContent() %>
            </textarea></td>
          </tr>
        </table><br>
        <table width="100%" border="0" cellspacing="0">
          <tr>
            <td></td>
          </tr>
        </table>
        <table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td width="164"></td>
            <td width="99" height="30" align="center">
            	<input name="button" type="submit" class="buttj" id="button" value="">
            </td>
            <td width="98" ></td>
            <td width="97" align="center">
            	<input name="button2" type="reset" class="butcz" id="button2" value="">
            </td>
            <td width="92" ></td>
          </tr>
        </table>
  </table>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td height="20"></td>
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
