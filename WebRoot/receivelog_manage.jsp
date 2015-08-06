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
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置>>应答日志&nbsp; <a href="javascript:history.back();" target="rightFrame">返回</a> </div>

  
<!--  内容列表   -->
<form name="form2" action="receivelogAction!list" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="3" align="center">&nbsp;<b>应答日志</b>&nbsp;
	</td>
</tr>

<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="10%" align="center">序号</td>
  	<td width="50%" align="center">事件</td>
  	<td width="30%" align="center">应答时间</td>
</tr>



	<s:if test="%{receivelogs.size()==0}">
				<td colspan="3" align="center">
					暂无该信息
				</td>
	</s:if>
<s:iterator value="receivelogs" var="receivelog" status="index">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
		<td><s:property value="#index.count"/></td>
		<td><s:property value="loginfo"/></td>
		<td><s:date name="nowtime" format="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
  
</s:iterator>

<tr align="right" bgcolor="#EEF4EA">
  
  <td height="34" colspan="3" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpPublicPage('receivelogAction!list',1,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">首页</a>&nbsp;&nbsp; 
  <a href="javascript:jumpPublicPage('receivelogAction!list',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpPublicPage('receivelogAction!list',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpPublicPage('receivelogAction!list',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpPublicPage('receivelogAction!list',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>');" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page"/>" size="2" style="ime-mode=disabled;width:50px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount"/>页</td>
  
</tr>
</table>

</form>

</body>
</html>