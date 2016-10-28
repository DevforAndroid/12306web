<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增用户信息</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>

<body class="write_bg">
<form action="<%=request.getContextPath() %>/Admin/admin?action=add" method="post">
  <table width="100%" border="0" cellspacing="0">
    <tr>
      <td height="30"></td>
    </tr>
  </table>
<table width="835" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20" colspan="2" ></td>
  </tr>
  <tr>
    <td width="64" height="30"  ></td>
    <td width="771" height="30" valign="top"  class="text_blod_title">新增用户信息</td>
  </tr>
  <tr>
    <td height="15" colspan="2" ><img src="../images/line1.jpg" width="835" height="6"></td>
    </tr>
  <tr>
    <td colspan="2" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="64"></td>
        <td width="771" height="25" align="left" class="text_cray">注：标有<span class="text_red"> *</span> 处，均为必填项</td>
      </tr>
      <tr>
        <td></td>
        <td height="15"></td>
      </tr>
    </table>
        <table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="15" colspan="3" align="left" class="text_title">登录信息</td>
          </tr>
          <tr>
            <td height="10" colspan="3"></td>
          </tr>
          <tr>
            <td width="20" align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">登录名：</td>
            <td class="text_cray"><input type="text" name="username" id="username" />
            由字母、数字或“_”组成，长度不少于6位，不多于30位</td>
          </tr>
        </table>
      <table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="20" colspan="3" ></td>
          </tr>
          <tr>
            <td height="15" colspan="3" align="left" class="text_title">权限设置</td>
          </tr>
          <tr>
            <td height="10" colspan="3" align="left" class="text_red1"></td>
          </tr>
          <tr>
            <td width="20" align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">用户权限：</td>
            <td><label>
              <select name="rule" class="text_cray">
                <option value="1">管理员</option>
                <option value="2">普通用户</option>
              </select>
            </label></td>
          </tr>
        </table>
      <table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="20" colspan="6" ></td>
          </tr>
          <tr>
            <td height="15" colspan="6" align="left" class="text_title">详细信息</td>
          </tr>
          <tr>
            <td height="10" colspan="6" ></td>
          </tr>
          <tr>
            <td width="20" align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">真实姓名：</td>
            <td width="14"></td>
            <td colspan="3" align="left"><input name="realname" type="text" class="text_cray" id="realname" /></td>
          </tr>
          <tr>
            <td align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">性 别：</td>
            <td></td>
            <td colspan="3" align="left" class="text_cray1">
              <input name="sex" type="radio" value="1" checked />
              <span class="text_cray">
              <label>男</label>
              <input type="radio" name="sex" value="2" />
                <label>女</label>
                <label></label>
              </span> </td>
          </tr>
          <tr>
            <td align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">省 份：</td>
            <td>&nbsp;</td>
            <td align="left" class="text_cray"><select name="province" class="text_cray" id="province">
                <option value="省份" selected="selected">省份</option>
            </select> 
            </td>
            <td width="48" align="left" class="text_cray">城市：</td>
            <td width="343" align="left" class="text_cray"><select name="city" class="text_cray" id="city">
                <option value="城市" selected="selected">市县</option>
            </select>            </td>
          </tr>
          <tr>
            <td align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">证件类型：</td>
            <td>&nbsp;</td>
            <td colspan="3" align="left"><select class="text_cray" name="certType" id="certType">
                <option value="1">二代身份证</option>
                <option value="2">港澳通行证</option>
                <option value="3">台湾通行证</option>
                <option value="4">护照</option>
            </select>            </td>
          </tr>
          <tr>
            <td align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">证件号码：</td>
            <td></td>
            <td colspan="3" align="left" class="text_cray"><input type="text" name="cert" id="cert" /></td>
          </tr>
          <tr>
            <td align="center" class="text_red">*</td>
            <td width="100" height="40" align="left" class="text_cray1">出生日期：</td>
            <td></td>
            <td width="175" align="left" class="text_cray"><input type="text" name="birthday" id="birthday" /></td>
            <td colspan="2" align="left"></td>
          </tr>
          <tr>
            <td align="center"></td>
            <td width="100" height="40" align="left" class="text_cray1">旅客类型：</td>
            <td></td>
            <td width="175" align="left" class="text_cray"><select class="text_cray" id="userType" name="userType">
              <option value="1" selected="selected">成人</option>
                <option value="2">儿童</option>
                <option value="3">学生</option>
                <option value="4">残疾军人、伤残人民警察</option>
            </select>            </td>
            <td colspan="2" align="left"></td>
          </tr>
          <tr>
            <td height="10" colspan="6"></td>
          </tr>
          <tr>
            <td align="center"></td>
            <td height="15" align="left" class="text_cray1">备注：</td>
            <td></td>
            <td height="15" colspan="3" align="left"><textarea name="content" style="width:100%" rows="8"></textarea></td>
          </tr>
        </table>
</table><br>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td></td>
  </tr>
</table>
<table width="700" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="164"></td>
    <td width="99" height="30" align="center"><input name="button" type="submit" class="buttj" id="button" value=""></td>
    <td width="98" ></td>
    <td width="97" align="center"><input name="button2" type="reset" class="butcz" id="button2" value=""></td>
    <td width="92" ></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <td height="20" colspan="6" align="center">
    		${ msg }
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
<script type="text/JavaScript" src="../js/common.js"></script>
</body>
</html>
