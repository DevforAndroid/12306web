<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>所有用户管理</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function UserAdd(){
		window.navigate("UserInfo_Add.jsp");
	}
	
	function del() {
		alert("请学员参考查询实现！");
	}
</script>

<script>
function selectAllNullorReserve(obj,type){
   if(obj!=null&&obj!=""){
    if(document.getElementsByName(obj)!=undefined&&document.getElementsByName(obj).length>0){	//getElementsByName函数的作用按名字查找对象，返回一个数组。
     var userids = document.getElementsByName(obj);
     if(type=="全选"){
      for(var i=0;i<userids.length;i++){
       if(userids[i].checked == false){
        userids[i].checked = true;
       }
      }
     }else if(type=="全不选"){
      for(var i=0;i<userids.length;i++){
       if(userids[i].checked == true){
        userids[i].checked = false;
       }
      }
     }else if(type=="反选"){
      for(var i=0;i<userids.length;i++){
       if(userids[i].checked == true){
        userids[i].checked = false;
       }else{
        userids[i].checked = true;
       }
      }
     }
    }
   }  
}
</script>

<script type="text/javascript">
  	var req = false;
  	function processRequest(page){
  		if (window.XMLHttpRequest) {
             req = new XMLHttpRequest();
         }else if (window.ActiveXObject) {
             req = new ActiveXObject("Microsoft.XMLHTTP");
         }
  		
         if(req){
        	var url = "<%=request.getContextPath() %>/Admin/admin?action=list";
        	// POST方式
        	req.open("post", url, false);	
        	// POST方式需要自己设置http的请求头
		 	req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        	
        	var realname = document.getElementById("realname").value;
        	var sex = document.getElementById("sex").value;
        	var certType = document.getElementById("certType").value;
        	var cert = document.getElementById("cert").value;
        	var userType = document.getElementById("userType").value;
        	var pageSize = document.getElementById("pageSize").value;
        	
       		req.onreadystatechange = updatePage;
       		req.send("realname=" + realname + "&sex=" + sex + "&certType=" + certType + "&cert=" + cert + "&userType=" + userType + "&pageSize=" + pageSize + "&source=0&page=" + page);
         }
  	}

  	function updatePage(){
  		//alert(req.readyState);
  		if(req.readyState==4){
  			if(req.status==200){
  			    var xmlDoc = req.responseXML.documentElement;
  			    
  			    // 显示页数
  			    var pageCount = parseInt(xmlDoc.getElementsByTagName('pageCount')[0].firstChild.nodeValue);
  			    var p = "";
  			    for (var i = 1; i <= pageCount; i++) {
  			    	p += '<a href="javascript:processRequest(' + i + ');">' + i + '</a> ';
  			    }
  			  	document.getElementById('page').innerHTML = p;
  			    
  			    // 显示内容
  			    var c = "<table>";
  			    var td = document.getElementById('container');
 		        	  
  			    var user = xmlDoc.getElementsByTagName("user");
  			    for (var i = 0; i < user.length; i++) {
  			    	var id = user[i].childNodes[0].firstChild.nodeValue;
  			    	var realname = user[i].childNodes[1].firstChild.nodeValue;
  			    	var sex = user[i].childNodes[2].firstChild.nodeValue;
  			    	var certType = user[i].childNodes[3].firstChild.nodeValue;
  			    	var cert = user[i].childNodes[4].firstChild.nodeValue;
  			    	var userType = user[i].childNodes[5].firstChild.nodeValue;
  			    	
  			    	c += '<tr align=\"center\">';
  			    	c += '<td bordercolor=\"#FFFFFF\" class=\"text_cray1\"><input type=\"checkbox\" name=\"ids\" value="' + id + '"></td>';
  			    	c += '<td width="98" bordercolor="#FFFFFF"  class="text_cray1">' + realname + '</td>';
  			    	c += '<td width="80" bordercolor="#FFFFFF"  class="text_cray1">' + sex + '</td>';
  			    	c += '<td width="132" bordercolor="#FFFFFF"  class="text_cray1">' + certType + '</td>';
  			    	c += '<td width="247" bordercolor="#FFFFFF"  class="text_cray1">' + cert + '</td>';
  			    	c += '<td width="82" bordercolor="#FFFFFF"  class="text_cray1">' + userType + '</td>';
  			    	c += '<td width=\"89\" bordercolor=\"#FFFFFF\"  class=\"text_cray1\"><a href=\"javascript:alert(\'请学员参考查询实现！\')\" class=\"text_red\">编辑</a></td>';
  		          	c += "</tr>";
				}
  			    c += "<table>";
  			    td.innerHTML = c;
  			}
  		}
  	}
</script>
</head>
<body class="write_bg">
 <form name="form1" method="post" action="#">
<input type="hidden" name = "source" value="0" />
<table width="1107" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="30"></td>
  </tr>
</table>
<table width="850" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20" colspan="2" ></td>
  </tr>
  <tr>
    <td width="13" height="30" align="left" valign="top"  ></td>
    <td width="822" align="left" valign="top"  class="text_blod_title">用户管理</td>
  </tr>
  <tr>
    <td height="15" colspan="2" align="center" ><img src="../images/line.jpg" width="850" height="6"></td>
  </tr>
  <tr>
    <td height="15" colspan="2"  ></td>
  </tr>
</table>
<table width="835" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="835" background="../images/wb_01 (3).jpg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="5"></td>
        <td width="4%" height="25" align="left" class="text_cray1">姓名</td>
        <td width="11%" align="left" class="text_cray1"><label>
          <input id="realname" name="realname" type="text" class="text_cray" style="width:80px" value="${sessionScope.listUser.realname }">
        </label></td>
        <td width="6%" align="center" class="text_cray1">性别</td>
        <td width="6%" align="left" class="text_cray1"><label>
          <select id="sex" name="sex" id="sex" class="text_cray">
            <option value="1" ${sessionScope.listUser.sex == '1' ? 'selected' : '' } >男</option>
            <option value="2" ${sessionScope.listUser.sex == '2' ? 'selected' : '' } >女</option>
          </select>
        </label></td>
        <td width="9%" align="center" class="text_cray1">证件类型</td>
        <td width="13%" align="left" class="text_cray1"><label>
          <select class="text_cray" name="certType" id="certType">
            <option value="1" ${sessionScope.listUser.certType.id == '1' ? 'selected' : '' }>二代身份证</option>
            <option value="2" ${sessionScope.listUser.certType.id == '2' ? 'selected' : '' }>港澳通行证 </option>
            <option value="3" ${sessionScope.listUser.certType.id == '3' ? 'selected' : '' }>台湾通行证</option>
            <option value="4" ${sessionScope.listUser.certType.id == '4' ? 'selected' : '' }>护照</option>
          </select>
        </label></td>
        <td width="8%" align="center" class="text_cray1">证件号码</td>
        <td width="13%" align="left" class="text_cray1"><label>
          <input name="cert" id="cert" type="text" class="text_cray" style="width:100px" value="${sessionScope.listUser.cert }">
        </label></td>
        <td width="8%" align="center" class="text_cray1">旅客类型</td>
        <td width="13%" align="left" class="text_blod"><label>
          <select class="text_cray" id="userType" name="userType" style="width:100px">
            <option value="1" ${sessionScope.listUser.userType.id == '1' ? 'selected' : '' }>成人</option>
            <option value="2" ${sessionScope.listUser.userType.id == '2' ? 'selected' : '' }>儿童</option>
            <option value="3" ${sessionScope.listUser.userType.id == '3' ? 'selected' : '' }>学生</option>
            <option value="4" ${sessionScope.listUser.userType.id == '4' ? 'selected' : '' }>残疾军人、伤残人民警察</option>
          </select>
        </label></td>
        <td width="8%" align="center" valign="middle" class="text_craybold"><label>
          <input name="button" type="button" class="butcx" value="" onclick="processRequest(1)" />
        </label></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td height="20" colspan="11" align="center">&nbsp;</td>
      </tr>
    </table>
        <table id="content" width="553" border="1" align="center" cellpadding="0" cellspacing="1" bordercolor="#dadada" bgcolor="#FFFFFF">
          <tr align="center">
            <td width="44" height="25" valign="middle" bordercolor="#FFFFFF" bgcolor="#FFFFFF"   class="text_cray1">选择</td>
            <td width="98" height="25" valign="middle" bordercolor="#FFFFFF" bgcolor="#FFFFFF"  class="text_cray1">姓名</td>
            <td width="80" height="25" valign="middle" bordercolor="#FFFFFF" bgcolor="#FFFFFF"  class="text_cray1">性别</td>
            <td width="132" height="25" valign="middle" bordercolor="#FFFFFF" bgcolor="#FFFFFF"  class="text_cray1">证件类型</td>
            <td width="247" height="25" valign="middle" bordercolor="#FFFFFF" bgcolor="#FFFFFF"  class="text_cray1">证件号码</td>
            <td width="82" height="25" valign="middle" bordercolor="#FFFFFF" bgcolor="#FFFFFF"  class="text_cray1">旅客类型</td>
            <td width="89" height="25" valign="middle" bordercolor="#FFFFFF" bgcolor="#FFFFFF"  class="text_cray1">操作</td>
          </tr>
          <tr align="center">
            <td height="15" colspan="7" bordercolor="#FFFFFF" bgcolor="#FFFFFF"   class="text_cray1"><img src="../images/line1.jpg" width="790" height="6"></td>
          </tr>
          
          <tr align="center">
          	<td id="container" colspan="7" bordercolor="#FFFFFF" bgcolor="#FFFFFF"   class="text_cray1"></td>
          </tr>
        </table>
      <br>
        <table width="773" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr align="center">
            <td width="102" align="left"  class="text_cray1"><a href="#">
              <label></label>
              <label></label>
              <label>
              <input type="checkbox" name="checkbox2" value="11"  onclick="selectAllNullorReserve('ids','反选');" ><span class="text_blue">全选</span></label>
            </a></td>
            <td width="525" align="right"  class="text_cray1"><a href="#">
            </a></td>
            <td width="55" align="right"  class="text_cray1"><a href="#">
              <input name="Submit22" type="button" class="butsc" value="" onclick="del()" />
            </a></td>
            <td width="91" align="right"  class="text_cray1"><label>
              <input name="Submit3" type="button" class="butdc" value="" onclick="window.location.href='<%=request.getContextPath() %>/Admin/admin?action=export'" />
            </label></td>
          </tr>
        </table>
      <br>
        <table width="773" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr align="center" style="width:60%">
            <td width="335" align="center"  class="text_cray">&nbsp;</td>
            <td width="284" align="center"  class="text_cray" id="page">
			</td>
            <td width="154" align="right"  class="text_cray1" style="width:20%"><label class="text_cray"> 每页显示
                <select name="pageSize" id="pageSize">
                    <option value="10" ${sessionScope.pageSize == '10' ? 'selected' : ''} >10</option>
                    <option value="20" ${sessionScope.pageSize == '20' ? 'selected' : ''}>20</option>
                    <option value="30" ${sessionScope.pageSize == '30' ? 'selected' : ''}>30</option>
                  </select>
            条信息</label></td> 
          </tr>
        </table>
      <br></td>
  </tr>
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
