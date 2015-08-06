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
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">
<script type="text/javascript">
function checkform(){
	var cmdcode = document.getElementById("cmdcode");
	var cmd = document.getElementById("cmd");
	if(cmdcode.value.length==0||cmdcode.value==''){
		alert("命令代码不能为空");
		cmdcode.value="";
		cmdcode.focus();
		return false;
	}
	if(cmd.value.length==0||cmd.value==''){
		alert("数据不能为空");
		cmd.value="";
		cmd.focus();
		return false;
	}
	return true;
}

</script>
</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理&gt;&gt;命令发送&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
  
<!--  内容列表   -->
<form name="form2" action="gatewayAction!send" method="post" onsubmit="return checkform();">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center"><strong>命令发送</strong>
	<s:hidden name="gatewayid" value="%{gateway.id}"></s:hidden>
	</td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="10%" height="25" align="right"><strong><font color="#333333">命令选择</font></strong></td>
	<td width="90%" align="left">
	  <label>
		<s:select list="commands" listKey="command" listValue="name" onclick="document.getElementById('cmd').value=this.value"></s:select>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="10%" height="25" align="right"><strong><font color="#333333">数据</font></strong></td>
	<td width="90%" align="left">
	  <label>
		<input type="text" name="cmd" id="cmd" style="width:90%" readonly="readonly" onclick="alert('不能修改数据');"/>
	  </label></td>
</tr>






</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
    <s:token></s:token>
      <input type='submit' class="coolbg np" value='发送' style="width:80" />&nbsp;&nbsp;
      <!-- 
      <input type='button' onclick="javascrpt:window.location.href='gatewayAction!cleanreturnvalue?gatewayid=<s:property value="gateway.id"/>'" class="coolbg np" value='清空返回值' style="width:120" />
      -->
    </td>
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>


</form>

</body>
</html>