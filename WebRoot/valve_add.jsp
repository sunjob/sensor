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
<title>新增外设</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">

<script type="text/javascript" src="js/pageKit.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
function changeProject_addValve()
{
 	console.log("changeProject_addValve coming....................");
	var projectid = $("#projects").val();
	var url = "valveAction!goToAdd?projectid="+projectid;
	url = encodeURI(url);
	location.href  = url;
}

function changeLine_addValve()
{
 	console.log("changeLine_addValve coming....................");
	var projectid = $("#projects").val();
	var lineid = $("#lines").val();
	var url = "valveAction!goToAdd?projectid="+projectid+"&lineid="+lineid;
	url = encodeURI(url);
	location.href  = url;
}
</script>
</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置&gt;&gt;新增外设&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
  
<!--  内容列表   -->
<form name="form2" action="valveAction!add" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center">
	<strong>新增外设</strong>
	<s:hidden name="valve.status" value="0"></s:hidden>
	</td>
</tr>

<s:if test="#session.user.limits==0">
<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td height="25" align="right"><strong><font color="#333333">项目选择</font></strong></td>
  <td align="left">
    <s:select list="projects" name="gateway.line.project.id" id="projects" value="projectid" listKey="id" listValue="name" onchange="changeProject_addValve()"></s:select>
</td>
</tr>
</s:if>
<s:else>
	<s:hidden name="gateway.line.project.id"/>
	<s:property value="gateway.line.project.name"/>
</s:else>

<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td height="25" align="right"><strong><font color="#333333">线路选择</font></strong></td>
  <td align="left">

    <s:select list="lines" name="gateway.line.id" id="lines" value="lineid" listKey="id" listValue="name" onchange="changeLine_addValve()"></s:select>
</td>
</tr>

<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td height="25" align="right"><strong><font color="#333333">所属网关</font></strong></td>
  <td align="left">

 <s:select list="gateways" listValue="name" listKey="id" name="valve.gateway.id" 
             headerKey="0l"  id="gateways"></s:select>  
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*外设名称</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield  name="valve.name"  id="name"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*外设地址</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield  name="valve.valveaddress"  id="valveaddress" ></s:textfield>
	  </label></td>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*开关位置号</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield  name="valve.locatenumber" id="locatenumber"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">位置描述</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield  name="valve.location"  id="location"></s:textfield>
	  </label></td>
</tr>





</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
		<s:token></s:token>
      <input type='submit' class="coolbg np" onClick="" value='保存' style="width:80" />&nbsp;&nbsp;
      <input type='reset' class="coolbg np" onClick="" value='取消' style="width:80" /></td>
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>


</form>

</body>
</html>