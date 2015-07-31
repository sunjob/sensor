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
<script type="text/javascript" src="js/jquery.js"></script>

<script type="text/javascript">
function checkform(){
	var channel = document.getElementById("channel");
	if(channel.value.length==0||channel.value==''){
		alert("无线数据通道不能为空");
		channel.value="";
		channel.focus();
		return false;
	}
	
	return true;
}

</script>
</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理&gt;&gt;<a href='javascript:history.back();' target='main'>网关管理</a>&gt;&gt;配置无线数据通道&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
<!--  内容列表   -->
<form name="form2" action="gatewayAction!sendchannel" method="post" onsubmit="return checkform();">

<table width="30%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center"><strong>配置无线数据通道</strong>
	<s:hidden name="gatewayid" value="%{gateway.id}"/>
	</td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*无线数据通道</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield cssClass="ck" id="channel" name="channel" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
      <input type='submit' class="coolbg np" value='发送命令' style="width:80" />&nbsp;&nbsp;
      <input type='button' class="coolbg np" onClick="javascript:history.back();" value='返回' style="width:80" />&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>


</form>

</body>
</html>