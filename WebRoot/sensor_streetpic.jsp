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
<title>传感器街景图片</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/pageKit.js"></script>
<script type="text/javascript" src="js/jsp_util.js"></script>


</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置&gt;&gt;传感器街景图片&nbsp;<a href="sensorAction!sensorrealtime?lineid=<s:property value="#parameters.lineid" />&mapsize=<s:property value="#parameters.mapsize" />&lng=<s:property value="#parameters.lng" />&lat=<s:property value="#parameters.lat" />" style=" color:red;">[返回]</a></div>
  
  
  <div id="updateform">
<!--  内容列表   -->
<font style="font: normal bold 12px;"></font>
<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">街景图片</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<img alt="暂无图片" src="<%=basePath%>streetpic${sensor.streetpic}" id="myimage" width="300px" height="256px" height="auto"/>
	  </label></td>
</tr>
</table>



</div>
</body>
</html>
		
