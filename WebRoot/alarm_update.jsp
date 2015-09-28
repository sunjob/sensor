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
<script type="text/javascript" src="js/jsp_util.js"></script>
<script type="text/javascript">
function checkNums(){
	var temptime = document.getElementById("temptime");
	var temp = document.getElementById("temp");
	var normaltemp = document.getElementById("normaltemp");
	var tempdown = document.getElementById("tempdown");
	var normaltempdown = document.getElementById("normaltempdown");
	var voltage = document.getElementById("voltage");
	var normalvoltage = document.getElementById("normalvoltage");
	var pressure = document.getElementById("pressure");
	var normalpressure = document.getElementById("normalpressure");
	var flow = document.getElementById("flow");
	var normalflow = document.getElementById("normalflow");
	if(temptime.value.length==0||temptime.value<1||temptime.value>30){
		alert("时间间隔不能为空，范围（1~30天）");
		temptime.value="";
		temptime.focus();
		return false;
	}
	if(temp.value.length!=0||normaltemp.value.length!=0||tempdown.value.length!=0||normaltempdown.value.length!=0){
		if(temp.value.length==0||normaltemp.value.length==0||tempdown.value.length==0||normaltempdown.value.length==0){
			alert("报警上限温度、报警上限正常温度、报警下限温度、报警下限正常相关联，请填写完整");
			temp.value="";
			normaltemp.value="";
			tempdown.value="";
			normaltempdown.value="";
			temp.focus();
			return false;
		}
		if(temp.value<normaltemp.value||normaltemp.value<normaltempdown.value||normaltempdown.value<tempdown.value){
			alert("报警温度上限值>正常温度上限值>正常温度下限值>报警温度下限值,填写有误");
			temp.value="";
			normaltemp.value="";
			tempdown.value="";
			normaltempdown.value="";
			temp.focus();
			return false;
		}
	}
	if(voltage.value.length!=0||normalvoltage.value.length!=0){
		if(voltage.value.length==0||normalvoltage.value.length==0){
			alert("电池电压下限值与正常值关联，请填写完整");
			voltage.value="";
			normalvoltage.value="";
			voltage.focus();
			return false;
		}
		if(voltage.value>normalvoltage.value){
			alert("电池电压下限值不能高于电池电压正常值");
			voltage.value="";
			normalvoltage.value="";
			voltage.focus();
			return false;
		}
		
	}
	
	if(pressure.value.length!=0||normalpressure.value.length!=0){
		if(pressure.value.length==0||normalpressure.value.length==0){
			alert("压力上限值与正常值关联，请填写完整");
			pressure.value="";
			normalpressure.value="";
			pressure.focus();
			return false;
		}
		if(pressure.value<normalpressure.value){
			alert("压力上限值不能低于压力正常值");
			pressure.value="";
			normalpressure.value="";
			pressure.focus();
			return false;
		}
		
	}
	
	
	if(flow.value.length!=0||normalflow.value.length!=0){
		if(flow.value.length==0||normalflow.value.length==0){
			alert("流量上限值与正常值关联，请填写完整");
			flow.value="";
			normalflow.value="";
			flow.focus();
			return false;
		}
		if(flow.value<normalflow.value){
			alert("流量上限值不能低于流量正常值");
			flow.value="";
			normalflow.value="";
			flow.focus();
			return false;
		}
		
	}
	return true;
}
</script>
</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置&gt;&gt;<a href='javascript:history.back();' target='rightFrame'>报警设置</a>&nbsp;</div>
  
<div id="updateform">
<!--  内容列表   -->
<form id="alarmAction" name="form2" action="alarmAction!update" method="post" enctype="multipart/form-data" onsubmit="return checkNums();">

<table width="50%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center"><strong>报警设置</strong>
    <!--
<input type="hidden" name="bigtype.publicaccount" value="" id="userAction_bigtype_publicaccount"/>
  -->
		<s:hidden name="alarm.id"></s:hidden>
		<s:hidden name="alarm.project.id"></s:hidden>
		<s:hidden name="alarm.smstemplate"></s:hidden>
		<s:hidden name="alarm.bmtemp"></s:hidden>
		<s:hidden name="alarm.normalbmtemp"></s:hidden>
		<s:hidden name="alarm.bmtemptime"></s:hidden>
		
		<s:hidden name="alarm.voltagetime"></s:hidden>
		<s:hidden name="alarm.pressuretime"></s:hidden>
		<s:hidden name="alarm.flowtime"></s:hidden>
	</td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">*报警间隔时间</font></strong></td>
  <td width="75%" align="left">
    <label>
	<s:textfield name="alarm.temptime" cssStyle="width:80%" id="temptime"></s:textfield>
天
    </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">报警温度上限</font></strong></td>
	<td width="75%" align="left">
	  <label>
	      
		<s:textfield name="temp" value="%{alarm.temp}" cssStyle="width:80%" id="temp"></s:textfield>℃
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">正常温度上限</font></strong></td>
	<td width="75%" align="left">
	  <label>
	      
		<s:textfield name="normaltemp" value="%{alarm.normaltemp}" cssStyle="width:80%" id="normaltemp"></s:textfield>℃
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">正常温度下限</font></strong></td>
	<td width="75%" align="left">
	  <label>
	      
		<s:textfield name="normaltempdown" value="%{alarm.normaltempdown}" cssStyle="width:80%" id="normaltempdown"></s:textfield>℃
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">报警温度下限</font></strong></td>
	<td width="75%" align="left">
	  <label>
	      
		<s:textfield name="tempdown" value="%{alarm.tempdown}" cssStyle="width:80%"  id="tempdown"></s:textfield>℃
	  </label></td>
</tr>





<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">报警电压</font></strong></td>
  <td width="75%" align="left">
    <label>
        
  	<s:textfield name="voltage" value="%{alarm.voltage}" cssStyle="width:80%" id="voltage"></s:textfield>
  V
    </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">正常电压</font></strong></td>
  <td width="75%" align="left">
    <label>
        
  	<s:textfield name="normalvoltage" value="%{alarm.normalvoltage}" cssStyle="width:80%" id="normalvoltage"></s:textfield>
  V
    </label></td>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">报警压力</font></strong></td>
  <td width="75%" align="left">
    <label>
        
  	<s:textfield name="pressure" value="%{alarm.pressure}" cssStyle="width:80%" id="pressure"></s:textfield>
  KPa
    </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">正常压力</font></strong></td>
  <td width="75%" align="left">
    <label>
        
  	<s:textfield name="normalpressure" value="%{alarm.normalpressure}" cssStyle="width:80%" id="normalpressure"></s:textfield>
  KPa
    </label></td>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">报警流量</font></strong></td>
  <td width="75%" align="left">
    <label>
        
  	<s:textfield name="flow" value="%{alarm.flow}" cssStyle="width:80%" id="flow"></s:textfield>
  m³/H
    </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">正常流量</font></strong></td>
  <td width="75%" align="left">
    <label>
        
  	<s:textfield name="normalflow" value="%{alarm.normalflow}" cssStyle="width:80%"  id="normalflow"></s:textfield>
  m³/H
    </label></td>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">短信接收手机</font></strong></td>
  <td width="75%" align="left">
    <label>
      <s:textarea rows="5" cols="50" name="alarm.phones" id="phones" readonly="true"></s:textarea>
      <input type="button" onclick="hiddenUpdateform()" value="重新选择"/>
    </label>
  </td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">报警短信模板</font></strong></td>
  <td width="75%" align="left">
    <label>
      <s:property value="alarm.smstemplate"/>
    </label>
  </td>
</tr>


</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
    <s:token></s:token>
      <input type='submit' class="coolbg np" onclick="" value='保存' style="width:80" />&nbsp;&nbsp;
      <input type='reset' class="coolbg np" onClick="" value='重置' style="width:80" />&nbsp;&nbsp;
    </td>
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>


</form>

</div>

<div id="addresslist" style="display:none">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="6" align="center">&nbsp;<b>通讯录列表</b>&nbsp;
	</td>
</tr>
<tr align="center" bgcolor="#FAFAF1" height="22">
		<td width="10%" align="center"></td>
		<td width="5%" align="center">序号</td>
		<td width="20%" align="center">接收人</td>
 		 <td width="30%" align="center">手机号码</td>
	</tr>
<s:iterator value="addresslists" var="addresslist" status="status">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
		<td><input class="checkbox" type="checkbox" id ="${id}" name="check"></td>
		<td><s:property value="#status.count" /></td>
	  	<td><s:property value="username" /></td>
	    <td><input type="text" id="phone_${id}" value="<s:property value="phone" />" readonly></td>
		</tr>
</s:iterator>



</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
    <s:token></s:token>
      <input type='button' class="coolbg np" onclick="showUpdateform()" value='保存' style="width:80" />&nbsp;&nbsp;
      <input type='button' class="coolbg np" onclick="backUpdateform()" value='返回' style="width:80" />&nbsp;&nbsp;
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>
</div>

</body>
</html>