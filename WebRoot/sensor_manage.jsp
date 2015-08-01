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

<script type="text/javascript" src="js/pageKit.js"></script></head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理>>传感器管理</div>

  
<!--  内容列表   -->
<form name="form2" action="sensorAction!list" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="14" align="center">&nbsp;<b>传感器管理</b>&nbsp;
    <!--
<input type="hidden" name="publicaccount" value="" id="publicaccount"/>
  -->
	
	</td>
</tr>
<tr bgcolor="#E7E7E7">
  <td height="33" colspan="14" bgcolor="#FFFFE5"><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="15%" align="right" style="padding-right: 50px;">
          <!--
          <select name="con" style="width:120px">
              <option value="0">选择类型</option>
              <option value="1">用户名称</option>
          </select>
        -->
      <s:select list="#{0:'选择类型',1:'所属项目',2:'所属线路',3:'网关名称',4:'传感器编号',5:'类型',6:'设备型号'}" name="con" listKey="key" listValue="value" cssStyle="width:120px"></s:select>
          
        	
        </td>
        <td width="25%">
        <!--
          <input type="text" name="convalue" style="width:80%"/>
              --> 
          <input type="text" name="convalue" value="" id="convalue" style="width:80%"/>
			
		</td>
        <td width="5%">
             <input type="submit" class="coolbg np" onClick="" value='查 询' />
    
        </td>
        <td width="5%">
             <input type="reset" class="coolbg np" onClick="" value='重 置' />
        </td>
      </tr>
    </table>
    </td>
</tr>
<tr align="center" bgcolor="#FAFAF1" height="22">
<td width="8%" align="center">序号</td>
	<td width="12%" align="center">所属项目</td>
	<td width="9%" align="center">所属线路</td>
	<td width="8%" align="center">网关名称</td>
	<td width="7%" align="center">传感器编号</td>
  	<td width="7%" align="center">类型</td>
  	<td width="7%" align="center">设备型号</td>
  	<td width="8%" align="center">经度</td>
  	<td width="8%" align="center">纬度</td>
  	<td width="7%" align="center">位置描述</td>
  	<td width="8%" align="center">街景图片</td>
	<td width="5%" align="center">修改</td>
	<td width="5%" align="center">采样间隔</td>
	<td width="5%" align="center">删除</td>
</tr>
<s:if test="%{sensors.size()==0}">
				<td colspan="14" align="center">
					暂无该信息
				</td>
			</s:if>
	<s:iterator value="sensors" var="sensor" status="status">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
		<td><s:property value="#status.count" /></td>
		<td><s:property value="gateway.line.project.name" /></td>
		<td><s:property value="gateway.line.name" /></td>
		<td><s:property value="gateway.name" /></td>
	   <td><a href="sensorAction!view?id=<s:property value="id"/>"><s:property value="name"/></a></td>
	   <td>
	   	<s:if test="sensortype==0">
	   			其他
	   		</s:if>
	   		<s:if test="sensortype==1">
	   			温度
	   		</s:if>
	   		<s:if test="sensortype==2">
	   			压力
	   		</s:if>
	   		<s:if test="sensortype==3">
	   			流量
	   		</s:if>
	   		<s:if test="sensortype==4">
	   			电池电压
	   		</s:if>
	   		<s:if test="sensortype==5">
	   			表面温度
	   		</s:if>
	   		
	   </td>
	   	<td><s:property value="devicetype" /></td>
		<td><s:property value="lng" /></td>
		<td><s:property value="lat" /></td>
		<td><s:property value="location" /></td>
		 <td><input type="button" value="查看" onclick="window.location.href='sensorAction!showStreetpic?id=<s:property value="id" />'"/></td>
		<td>
		<s:if test="lng==null||lng==''||lat==null||lat==''">
			<a href="sensorAction!load?id=<s:property value="id" />"><img src="skin/images/frame/redpen.gif" width="20" height="20" border="0"></a>
		</s:if>
		<s:else>
			<a href="sensorAction!load?id=<s:property value="id" />"><img src="skin/images/frame/huiwu_3.gif" width="20" height="20" border="0"></a>
		</s:else>
		</td>
		<td>
			<a href="sensorAction!loadsendinterval?id=<s:property value="id" />" >配置</a>
		</td>
		<td>
			<a href="sensorAction!delete?id=<s:property value="id" />" onclick="return confirm('你确定删除该信息吗？')"><img src="skin/images/frame/huiwu_2.gif" width="20" height="20" border="0"></a>
		</td>
	</tr>
</s:iterator>

<tr align="right" bgcolor="#EEF4EA">
  
  <td height="34" colspan="14" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('sensorAction!list',1,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">首页</a>&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('sensorAction!list',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('sensorAction!list',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('sensorAction!list',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpLinePage('sensorAction!list',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>');" value='转到' />
&nbsp;
当前页：

<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page"/>" size="2" style="ime-mode=disabled;width:25px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount"/>页</td>
  
</tr>
</table>

</form>

</body>
</html>