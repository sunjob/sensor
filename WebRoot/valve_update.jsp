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
<title>修改外设</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">

<script type="text/javascript" src="js/pageKit.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
function changeProject_updateValve()
{
 	console.log("changeProject_updateValve coming....................");
	var projectid = $("#projects").val();
	var vid = $("#vid").val();
	var url = "valveAction!load?projectid="+projectid+"&id="+vid;
	url = encodeURI(url);
	location.href  = url;
}

function changeLine_updateValve()
{
 	console.log("changeLine_updateValve coming....................");
	var projectid = $("#projects").val();
	var vid = $("#vid").val();
	var lineid = $("#lines").val();
	var url = "valveAction!load?projectid="+projectid+"&lineid="+lineid+"&id="+vid;
	url = encodeURI(url);
	location.href  = url;
}

function checkform(){
			var name = document.getElementById("name");
			var valveaddress = document.getElementById("valveaddress");
			var locatenumber = document.getElementById("locatenumber");
			if(name.value.length==0||name.value==''){
				alert("外设名称不能为空");
				name.value="";
				name.focus();
				return false;
			}
			
			if(valveaddress.value.length==0||valveaddress.value==''){
				alert("外设地址不能为空");
				valveaddress.value="";
				valveaddress.focus();
				return false;
			}
			if(isNaN(valveaddress.value)){
				alert("外设地址输入不正确,请输入数字");
				valveaddress.value="";
				valveaddress.focus();
				return false;
			}else if(valveaddress.value<0||valveaddress.value>255){
				alert("外设地址输入不正确,请输入正确范围（0~255）");
				valveaddress.value="";
				valveaddress.focus();
				return false;
			}
			
			if(locatenumber.value.length==0||locatenumber.value==''){
				alert("开关位置号不能为空");
				locatenumber.value="";
				locatenumber.focus();
				return false;
			}
			if(isNaN(locatenumber.value)){
				alert("开关位置号输入不正确,请输入数字");
				locatenumber.value="";
				locatenumber.focus();
				return false;
			}else if(locatenumber.value<0||locatenumber.value>255){
				alert("开关位置号输入不正确,请输入正确范围（0~255）");
				locatenumber.value="";
				locatenumber.focus();
				return false;
			}
			
			return true;
}
</script>

</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置&gt;&gt;修改外设&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
  
<!--  内容列表   -->
<form name="form2" action="valveAction!update" method="post" onsubmit="return checkform()">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center">
	<strong>修改外设</strong>
	<s:hidden name="valve.id" id="vid"></s:hidden>
	<s:hidden name="valve.controlvalue"></s:hidden>
	<s:hidden name="valve.status"></s:hidden>
	</td>
</tr>

<s:if test="#session.user.limits==0">
<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td height="25" align="right"><strong><font color="#333333">项目选择</font></strong></td>
  <td align="left">
    <s:select list="projects" name="gateway.line.project.id" id="projects" value="projectid" listKey="id" listValue="name" onchange="changeProject_updateValve()"></s:select>
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

    <s:select list="lines" name="gateway.line.id" id="lines" value="lineid" listKey="id" listValue="name" onchange="changeLine_updateValve()"></s:select>
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
      <input type='reset' class="coolbg np" onClick="" value='重置' style="width:80" /></td>
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>


</form>

</body>
</html>