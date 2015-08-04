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
<title>传感器修改</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/pageKit.js"></script>
<script type="text/javascript">
		var oldDate = <s:property  value="sensor.intervaltime"/>;
		function checkform(){
			var intervaltime = document.getElementById("intervaltime");
			if(intervaltime.value.length==0||intervaltime.value==''){
				alert("采样间隔不能为空");
				intervaltime.value="";
				intervaltime.focus();
				return false;
			}
			return true;
		}
</script>
<script type="text/javascript" src="js/jsp_util.js"></script>


</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置&gt;&gt;修改传感器采样间隔&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
  
  
  <div id="updateform">
<!--  内容列表   -->
<form name="form2" action="sensorAction!sendinterval" method="post" onsubmit="return checkform();">
<font style="font: normal bold 12px;"></font>
<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center">
	<strong>传感器信息</strong></td>
	<s:hidden name="sensorid" value="%{sensor.id}"></s:hidden>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">采样间隔</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield cssClass="ck" id="intervaltime" name="intervaltime" value="%{sensor.intervaltime}" cssStyle="width:40%"></s:textfield>
	  </label></td>
</tr>

</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
      <input type='submit' class="coolbg np" onClick="" value='发送命令' style="width:80" />&nbsp;&nbsp;
      <input type='reset' class="coolbg np" onClick="" value='取消' style="width:80" /></td>
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>

</form>
</div>


</body>
</html>