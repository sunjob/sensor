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
<title>修改项目信息</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">

<script type="text/javascript" src="js/pageKit.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jsp_util.js"></script>
<script type="text/javascript">
function checkform(){
	var lng = document.getElementById("lng");
	var lat = document.getElementById("lat");
	var projectname = document.getElementById("projectname");
	if(projectname.value.length==0||projectname.value==''){
		alert("项目名不能为空");
		projectname.value="";
		projectname.focus();
		return false;
	}
	if(lng.value.length==0||lng.value==''){
		alert("经度不能为空");
		lng.value="";
		lng.focus();
		return false;
	}
	if(isNaN(lng.value)){
		alert("经度输入不正确,请输入正确数字");
		lng.value="";
		lng.focus();
		return false;
	}
	if(lat.value.length==0||lat.value==''){
		alert("纬度不能为空");
		lat.value="";
		lat.focus();
		return false;
	}
	if(isNaN(lat.value)){
		alert("纬度输入不正确,请输入正确数字");
		lat.value="";
		lat.focus();
		return false;
	}
	return true;
}

</script>
</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置&gt;&gt;修改项目信息&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
  
<!--  内容列表   -->
<form name="form2" action="projectAction!update" method="post"  onsubmit="return checkform();">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center">
	<strong>修改项目信息</strong></td>
	  <s:hidden name="project.id"/>
	    <s:hidden name="project.createtime"/>
	    <s:hidden name="project.orderid"></s:hidden>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">项目编号</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield name="project.number"  cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>
<!-- 
<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">项目排序序号</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield cssClass="ck" id="orderid"  name="project.orderid" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>
 -->
<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*项目名称</font></strong></td>
	<td width="75%" align="left">
	  <label>
	  <s:textfield name="project.name" cssStyle="width:80%" id="projectname"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">施工时间</font></strong></td>
	<td width="75%" align="left">
	  <label>
	 	    <input type="date" name="project.startdate" value="<s:date name='project.startdate' format='yyyy-MM-dd'/>"/>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">完工时间</font></strong></td>
	<td width="75%" align="left">
	  	      <input type="date" name="project.enddate" value="<s:date name='project.enddate' format='yyyy-MM-dd'/>"/>
	  </td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">施工单位</font></strong></td>
	<td width="75%" align="left">
	  <label>
	  <s:textfield name="project.sgdw" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">管道厂商</font></strong></td>
	<td width="75%" align="left">
	  <label>
	  <s:textfield name="project.gdcs" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">项目业主</font></strong></td>
	<td width="75%" align="left">
	  <label>
	  <s:textfield name="project.owner" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*中心经度</font></strong></td>
	<td width="75%" align="left">
	  <label>
	  <s:textfield id="lng" name="project.lng" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*中心纬度</font></strong></td>
	<td width="75%" align="left">
	  <label>
	  <s:textfield id="lat" name="project.lat" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
    <s:token></s:token>
      <input type='submit' class="coolbg np" onClick="return checkProject();" value='保存' style="width:80" />&nbsp;&nbsp;
      <input type='reset' class="coolbg np" onClick="" value='重置' style="width:80" />&nbsp;&nbsp;
      <input type='button' class="coolbg np" onClick="javascript:history.back();" value='返回' style="width:80" />&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>


</form>

</body>
</html>