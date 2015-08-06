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
<meta http-equiv="refresh" content="5">
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">

<script type="text/javascript" src="js/pageKit.js"></script></head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置>>外设管理>>外设数据&nbsp;<a href="valveAction!list" target="rightFrame">返回</a></div>

  
<!--  内容列表   -->
<form name="form2" action="valveAction!datalist" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="5" align="center">&nbsp;<b>外设数据</b>&nbsp;
	<s:hidden name="valveid"></s:hidden>
	</td>
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="10%" align="center">序号</td>
  	<td width="20%" align="center">外设编号</td>
  	<td width="20%" align="center">数据类型</td>
  	<td width="20%" align="center">数据</td>
  	<td width="20%" align="center">时间</td>
</tr>



	<s:if test="%{valvedatas.size()==0}">
				<td colspan="5" align="center">
					暂无该信息
				</td>
	</s:if>
<s:iterator value="valvedatas" var="valvedata" status="status">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
		<td><s:property value="#status.count"/></td>
		<td><s:property value="valve.name"/></td>
		<td><s:property value="vtype"/></td>
		<td><s:property value="vdata"/></td>
		<td><s:date name="vtime" format="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
  
</s:iterator>

<tr align="right" bgcolor="#EEF4EA">
  
  <td height="34" colspan="5" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpValvedataPage('valveAction!datalist',1,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="valveid"/>);" target="rightFrame">首页</a>&nbsp;&nbsp; 
  <a href="javascript:jumpValvedataPage('valveAction!datalist',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="valveid"/>);" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpValvedataPage('valveAction!datalist',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="valveid"/>);" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpValvedataPage('valveAction!datalist',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="valveid"/>);" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpValvedataPage('valveAction!datalist',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="valveid"/>);" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page"/>" size="2" style="ime-mode=disabled;width:50px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount"/>页</td>
  
</tr>
</table>

</form>

</body>
</html>