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
			var lng = document.getElementById("lng");
			var lat = document.getElementById("lat");
			var sensorname = document.getElementById("sensorname");
			var orderid = document.getElementById("orderid");
			if(sensorname.value.length==0||sensorname.value==''){
				alert("传感器编号不能为空");
				sensorname.value="";
				sensorname.focus();
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
			
			if(orderid.value.length==0||orderid.value==''){
				alert("地图排序编号不能为空");
				orderid.value="";
				orderid.focus();
				return false;
			}
			if(isNaN(orderid.value)){
				alert("地图排序编号输入不正确,请输入正确数字");
				orderid.value="";
				orderid.focus();
				return false;
			}
			return true;
		}
</script>
<script type="text/javascript" src="js/jsp_util.js"></script>


</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置&gt;&gt;修改传感器&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
  
  
  <div id="updateform">
<!--  内容列表   -->
<form name="form2" action="sensorAction!update" method="post"  enctype="multipart/form-data" onsubmit="return checkform();">
<font style="font: normal bold 12px;"></font>
<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center">
	<strong>传感器信息</strong></td>
	<s:hidden name="sensor.id"></s:hidden>
	<s:hidden name="sensor.gateway.id"></s:hidden>
	<s:hidden name="sensor.sensoraddress"></s:hidden>
	<s:hidden name="sensor.streetpic"></s:hidden>
	<s:if test="sensor.nowtemp==0">
		<s:hidden name="sensor.nowtemp" value=""></s:hidden>
	</s:if>
	<s:else>
		<s:hidden name="sensor.nowtemp"></s:hidden>
	</s:else>
	
	<s:if test="sensor.nowpressure==0">
		<s:hidden name="sensor.nowpressure" value=""></s:hidden>
	</s:if>
	<s:else>
		<s:hidden name="sensor.nowpressure"></s:hidden>
	</s:else>
	
	<s:if test="sensor.nowvoltage==0">
		<s:hidden name="sensor.nowvoltage" value=""></s:hidden>
	</s:if>
	<s:else>
		<s:hidden name="sensor.nowvoltage"></s:hidden>
	</s:else>
	
	<s:if test="sensor.nowflow==0">
		<s:hidden name="sensor.nowflow" value=""></s:hidden>
	</s:if>
	<s:else>
		<s:hidden name="sensor.nowflow"></s:hidden>
	</s:else>
	
	<s:if test="sensor.nowbmtemp==0">
		<s:hidden name="sensor.nowbmtemp" value=""></s:hidden>
	</s:if>
	<s:else>
		<s:hidden name="sensor.nowbmtemp"></s:hidden>
	</s:else>
	
	<s:hidden name="sensor.status"></s:hidden>
	<s:hidden name="sensor.intervaltime"></s:hidden>
	<s:hidden name="sensor.iscanalarm"></s:hidden>
	<s:hidden name="sensor.iscanalarm2"></s:hidden>
	<s:hidden id="isDataUpdated" name="isDataUpdated"></s:hidden>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">所属网关</font></strong></td>
	<td width="75%" align="left">
	  <label>
	  	<s:property value="sensor.gateway.name" />
	  </label></td>
</tr>
<!-- 
<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">排序编号</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield id="orderid" cssClass="ck"   name="sensor.orderid" style="width:80%"></s:textfield>
	  </label></td>
</tr>
 -->
<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*传感器编号</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield  name="sensor.name" style="width:80%" id="sensorname"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">类型</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:select list="#{0:'其他',1:'温度',2:'压力',3:'流量',4:'电池电压',5:'表面温度'}" name="sensor.sensortype"   listKey="key" listValue="value" />
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">设备型号</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield  name="sensor.devicetype" style="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*经度</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield id="lng"  name="sensor.lng" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*纬度</font></strong></td>
	<td width="75%" align="left">
	  <label>
				<s:textfield id="lat"  name="sensor.lat" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*地图排序编号</font></strong></td>
	<td width="75%" align="left">
	  <label>
				<s:textfield id="orderid"  name="sensor.orderid" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">传感器地址</font></strong></td>
	<td width="75%" align="left">
	  <label>
			<s:property value="sensor.sensoraddress" />
	  </label></td>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td width="25%" height="25" align="right"><strong><font color="#333333">短信接收手机</font></strong></td>
  <td width="75%" align="left">
    <label>
      <s:textarea rows="5" cols="50" name="sensor.alarmphones" id="phones" readonly="true"></s:textarea>
      <input type="button" onclick="hiddenUpdateform()" value="重新选择"/>
    </label>
  </td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">街景图片</font></strong></td>
	<td width="75%" align="left">
	  <label>
		 <s:file name="picture" cssStyle="width:80%" accept="image/jpeg,image/png,image/jpg" onchange="change();" id="myfile"></s:file>
	  </label>
		<br/>
			
<img alt="暂无图片" src="<%=basePath%>streetpic${sensor.streetpic }" id="myimage" width="300px" height="256px"/>
				<SCRIPT type="text/javascript">
							function change() {
							    var pic = document.getElementById("myimage"),
							        file = document.getElementById("myfile");
							    var ext=file.value.substring(file.value.lastIndexOf(".")+1).toLowerCase();
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

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">位置描述</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield  name="sensor.location" cssStyle="width:80%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">报警温度上限</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield cssClass="ck"  name="sensor.alarmtemp" cssStyle="width:40%"></s:textfield> ℃
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">正常温度上限</font></strong></td>
	<td width="75%" align="left">
	  <label>

		<s:textfield cssClass="ck" id="normaltemp" name="sensor.normaltemp" cssStyle="width:40%"></s:textfield> ℃
	  </label></td>
</tr>



<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">正常温度下限</font></strong></td>
	<td width="75%" align="left">
	  <label>

		<s:textfield cssClass="ck" id="normaltemp" name="sensor.normaltempdown" cssStyle="width:40%"></s:textfield> ℃
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">报警温度下限</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield cssClass="ck"  name="sensor.alarmtempdown" cssStyle="width:40%"></s:textfield> ℃
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">报警电池电压</font></strong></td>
	<td width="75%" align="left">
	  <label>
				<s:textfield cssClass="ck"  name="sensor.alarmvoltage" cssStyle="width:40%"></s:textfield> V
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">正常电池电压</font></strong></td>
	<td width="75%" align="left">
	  <label>
				<s:textfield cssClass="ck" id="normalvoltage"  name="sensor.normalvoltage" style="width:40%"></s:textfield> V
	  </label></td>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">报警压力</font></strong></td>
	<td width="75%" align="left">
	  <label>
				<s:textfield cssClass="ck"  id="alarmpressure"  name="sensor.alarmpressure" style="width:40%"></s:textfield> KPa
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">正常压力</font></strong></td>
	<td width="75%" align="left">
	  <label>
				<s:textfield  cssClass="ck" id="normalpressure"  name="sensor.normalpressure" style="width:40%"></s:textfield> KPa
	  </label></td>
</tr>


<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">报警流量</font></strong></td>
	<td width="75%" align="left">
	  <label>
				<s:textfield cssClass="ck"  name="sensor.alarmflow" cssStyle="width:40%"></s:textfield> m³/H
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">正常流量</font></strong></td>
	<td width="75%" align="left">
	  <label>
				<s:textfield cssClass="ck"   id="normalflow" name="sensor.normalflow" style="width:40%"></s:textfield> m³/H
	  </label></td>
</tr>

<!-- 
<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">报警表面温度</font></strong></td>
	<td width="75%" align="left">
	  <label>
				<s:textfield  cssClass="ck" name="sensor.alarmbmtemp" cssStyle="width:40%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">正常表面温度</font></strong></td>
	<td width="75%" align="left">
	  <label>
				<s:textfield cssClass="ck" id="normalbmtemp"   name="sensor.normalbmtemp" style="width:40%"></s:textfield>
	  </label></td>
</tr>

<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="30" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">采样间隔</font></strong></td>
	<td width="75%" align="left">
	  <label>
		<s:textfield cssClass="ck" id="intervaltime" name="sensor.intervaltime" cssStyle="width:40%" onblur="setDataUpdated()"></s:textfield>
	  </label></td>
</tr>
 -->
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