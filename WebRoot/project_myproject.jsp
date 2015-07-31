<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>我的项目信息</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">

<script type="text/javascript" src="js/pageKit.js"></script></head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置>>我的项目信息&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
  
<!--  内容列表   -->

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center">
	<strong>查看我的项目信息</strong></td>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="40" >
	<td width="25%" height="25" align="right" style="padding-right: 50px;"><strong><font color="#333333">项目编号</font></strong></td>
	<td width="75%" align="left" style="padding-left: 50px;">
	  <label>
		 <s:property value="project.number"  />
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="40" >
	<td width="25%" height="25" align="right" style="padding-right: 50px;"><strong><font color="#333333">项目名称</font></strong></td>
	<td width="75%" align="left" style="padding-left: 50px;">
	  <label>
		  <s:property value="project.name"  />
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="40" >
	<td width="25%" height="25" align="right" style="padding-right: 50px;"><strong><font color="#333333">施工时间</font></strong></td>
	<td width="75%" align="left" style="padding-left: 50px;">
	  <label>
		   <s:property value="project.startdate"  />
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="40" >
	<td width="25%" height="25" align="right" style="padding-right: 50px;"><strong><font color="#333333">完工时间</font></strong></td>
	<td width="75%" align="left" style="padding-left: 50px;">
	  <label>
		 <s:property value="project.enddate"  />
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="40" >
	<td width="25%" height="25" align="right" style="padding-right: 50px;"><strong><font color="#333333">施工单位</font></strong></td>
	<td width="75%" align="left" style="padding-left: 50px;">
	  <label>
		 <s:property value="project.sgdw"  />
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="40" >
	<td width="25%" height="25" align="right" style="padding-right: 50px;"><strong><font color="#333333">管道厂商</font></strong></td>
	<td width="75%" align="left" style="padding-left: 50px;">
	  <label>
	  <s:property value="project.gdcs"  />
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="40" >
	<td width="25%" height="25" align="right" style="padding-right: 50px;"><strong><font color="#333333">项目业主</font></strong></td>
	<td width="75%" align="left" style="padding-left: 50px;">
	  <label>
		   <s:property value="project.owner"  />
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="40" >
	<td width="25%" height="25" align="right" style="padding-right: 50px;"><strong><font color="#333333">中心精度</font></strong></td>
	<td width="75%" align="left" style="padding-left: 50px;">
	  <label>
		  <s:property value="project.lng"  />
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="40" >
	<td width="25%" height="25" align="right" style="padding-right: 50px;"><strong><font color="#333333">中心纬度</font></strong></td>
	<td width="75%" align="left" style="padding-left: 50px;">
	  <label>
		   <s:property value="project.lat"  />
	  </label></td>
</tr>

<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="40" >
  <td height="25" align="right" style="padding-right: 50px;"><strong><font color="#333333">系统创建时间</font></strong></td>
  <td align="left" style="padding-left: 50px;">
    <s:property value="project.createtime"  />
  </td>
</tr>



</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
      <input type='submit' class="coolbg np" onClick="javascript:window.location.href='projectAction!loadMyProject?id=<s:property value="project.id"/>';" value='修改' style="width:80" /></td>
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>



</body>
</html>