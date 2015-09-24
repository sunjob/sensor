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
<script>
	var oldDate = <s:property  value="gateway.channel"/>;
	var gateaddress = <s:property  value="gateway.gateaddress"/>;
	var channel = <s:property  value="gateway.channel"/>;
</script>

<script type="text/javascript" src="js/jsp_util.js"></script>
<script type="text/javascript">
function checkform(){
	var lng = document.getElementById("lng");
	var lat = document.getElementById("lat");
	var gatewayname = document.getElementById("gatewayname");
	if(gatewayname.value.length==0||gatewayname.value==''){
		alert("网关名称不能为空");
		gatewayname.value="";
		gatewayname.focus();
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
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理&gt;&gt;修改网关&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
<!--  内容列表   -->
<form name="form2" action="gatewayAction!update" method="post"  enctype="multipart/form-data"  onsubmit="return checkform();">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center"><strong>修改网关</strong>
    <s:hidden id="gid" name="gateway.id"/>
	<s:hidden name="gateway.ip"/>
	<s:hidden name="gateway.macaddress"/>
	<s:hidden name="gateway.channel"/>
	<s:hidden name="gateway.orderid"/>
	<s:hidden name="gateway.status"></s:hidden>
	<s:hidden name="gateway.streetpic"></s:hidden>
	<s:hidden id="isChannelUpdated" name="isChannelUpdated" />
	</td>
</tr>
<s:if test="#session.user.limits==0">
<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td height="25" align="right"><strong><font color="#333333">项目选择</font></strong></td>
  <td align="left">
    <s:select list="projects" name="gateway.line.project.id" id="projects" value="projectid" listKey="id" listValue="name" onchange="changeProject_updateGateway()"></s:select>
</td>
</tr>
</s:if>
<s:else>
	<s:hidden name="gateway.line.project.id"/>
</s:else>

<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td height="25" align="right"><strong><font color="#333333">线路选择</font></strong></td>
  <td align="left">

    <s:select list="lines" name="gateway.line.id" listKey="id" listValue="name" id="lineid"></s:select>
</td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">*网关名称</font></strong></td>
  <td width="75%" align="left">
    <label>
    
<s:textfield name="gateway.name" cssStyle="width:80%" id="gatewayname"></s:textfield>
   
    </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">网关地址</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield cssClass="ck"  id="gateaddress" name="gateway.gateaddress" cssStyle="width:80%" onblur="checkGateaddress()"></s:textfield>
	  </label>
	  </td>
</tr>
<!-- 
<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">排序编号</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield cssClass="ck" id="orderid" name="gateway.orderid" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">无线数据通道</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield cssClass="ck" id="channel" name="gateway.channel" cssStyle="width:80%" onblur="checkChannel()"></s:textfield>
	  </label></td>
</tr>
 -->
<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">手机卡号</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield name="gateway.phonenumber" cssClass="ck" id="phonenumber" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">街景图片</font></strong></td>
	<td width="75%" align="left">
	  <label>
		 <s:file name="picture"  cssStyle="width:80%" accept="image/jpeg,image/png,image/jpg"  onchange="change();" id="myfile"></s:file>
	  </label>
		<br/>
		<img alt="暂无图片" src="<%=basePath%>streetpic${gateway.streetpic }" id="myimage" width="300px" height="256px"/>
				<SCRIPT type="text/javascript">
							function change() {
							    var pic = document.getElementById("myimage"),
							        file = document.getElementById("myfile");
							    var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
							    console.log(ext);
							     // gif在IE浏览器暂时无法显示
							     if(ext!='png'&&ext!='jpg'&&ext!='jpeg'){
							         alert("图片的格式必须为png或者jpg或者jpeg格式！"); 
							         return;
							     }
							     var isIE = navigator.userAgent.match(/MSIE/)!= null,
							         isIE6 = navigator.userAgent.match(/MSIE 6.0/)!= null;
							 
							     if(isIE) {
							        file.select();
							        var reallocalpath = document.selection.createRange().text;
							 
							        // IE6浏览器设置img的src为本地路径可以直接显示图片
							         if (isIE6) {
							            pic.src = reallocalpath;
							         }else {
							            // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
							             pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\"" + reallocalpath + "\")";
							             // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
							             pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
							         }
							     }else {
							        html5Reader(file);
							     }
							     pic.alt = '图片';
							}
							 
							 function html5Reader(file){
							     var file = file.files[0];
							     var reader = new FileReader();
							     reader.readAsDataURL(file);
							     reader.onload = function(e){
							         var pic = document.getElementById("myimage");
							         pic.src=this.result;
							     }
							 }
				</SCRIPT>
	  </td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*经度</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield name="gateway.lng" id="lng" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*纬度</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield name="gateway.lat" id="lat" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
    <s:token></s:token>
      <input type='submit' class="coolbg np" onClick="return checkGatewasy();" value='保存' style="width:80" />&nbsp;&nbsp;
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