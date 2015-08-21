<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改用户信息</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script>
	var ulimit = <s:property value="ulimit" />;
	console.log("后台传过来的ulimit="+ulimit);
</script>
<script type="text/javascript" src="js/jsp_util.js"></script>
<script type="text/javascript">
	function checkform(){
		var userlines = document.getElementsByName("user.linetext");
        var valueArray = new Array();
        for(var i = 0; i < userlines.length; i++){
        	if(userlines[i].checked)
        	valueArray.push(userlines[i].value);
        } 
        var valueStr = valueArray.toString();
		if(valueStr==""||valueStr.length==0){
			alert("管理线路未选择");
			return false;
		}
		return true;
	}

</script>

</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：修改用户信息&gt;&gt;修改用户&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
  
<!--  内容列表   -->
<s:form name="form2" action="userAction!update" method="post"  enctype="multipart/form-data" onsubmit="return checkform()">

<table width="50%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center"><strong>修改用户</strong>
  	   <s:hidden id="uid" name="user.id"/>
  	   <s:hidden name="user.upuserid"/>
  	   <s:hidden name="user.limits"/>
  	   <s:hidden  id="password" name="user.password"/>
  	   <s:hidden name="user.createdate"/>
  	   <s:hidden id="username" name="user.username"/>
	</td>
</tr>

<s:if test="#session.user.limits==0">
<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td height="25" align="right"><strong><font color="#333333">所属项目</font></strong></td>
  <td align="left">

 <s:select list="projects"  listValue="name " listKey="id"  name="user.project.id" 
             headerKey="0l"  id="projects" value="projectid"
            onchange="changeProject_update()" ></s:select>  
</tr>
</s:if>
<s:else>
	<s:hidden name="user.project.id"/>
</s:else>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">用户名称</font></strong></td>
	<td width="75%" align="left">
	  <label>
	<s:property value="user.username"/>
	  </label></td>
</tr>



<s:if test="ulimit>1">
<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">管理线路</font></strong></td>
  <td width="75%" align="left">
    <label>
	<s:checkboxlist name="user.linetext" list="lines"  listKey="id" listValue="name"  value="userlines.{id}" />
    </label></td>
</tr>
</s:if>

<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td height="25" align="right"><strong><font color="#333333">权限级别</font></strong></td>
  <td align="left">
  
<!-- 权限0,1显示用户管理 -->
<s:if test="session.user.limits==0">
	<s:select list="#{0:'系统管理员',1:'超级管理员',2:'普通管理员',3:'普通用户'}" onchange="changeLimit_update()" id="limit" name="ulimit" listKey="key" listValue="value" value="ulimit"></s:select>
</s:if>
<s:if test="session.user.limits==1">
	<s:select list="#{1:'超级管理员',2:'普通管理员',3:'普通用户'}" onchange="changeLimit_update()" id="limit" name="user.limits" listKey="key" listValue="value" value="ulimit"></s:select>
</s:if>
  	
</td>
</tr>





</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
    <s:token></s:token>
      <input type='submit' class="coolbg np" onClick="return checkUser();" value='保存' style="width:80" />&nbsp;&nbsp;
      <input type='reset' class="coolbg np" onClick="" value='重置' style="width:80" />&nbsp;&nbsp;
      <input type='button' class="coolbg np" onClick="changePassword();" value='重置密码' style="width:80" />&nbsp;&nbsp;
      <input type='button' class="coolbg np" onClick="javascript:history.back();" value='返回' style="width:80" />&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>


</s:form>

</body>
</html>