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
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理>>命令管理</div>
<!--  快速转换位置按钮  -->
<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#8BC7F1" align="center">
<tr>
 <td height="26">
  <table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td align="center">
  <input type='button' class='coolbg np' onClick="javascript:window.location='commandAction!goToAdd'" value='新增命令' />
 </td>
 </tr>
</table>
</td>
</tr>
</table>
  
<!--  内容列表   -->
<form name="form2" action="commandAction!list" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="6" align="center">&nbsp;<b>命令管理</b>&nbsp;
	
	</td>
</tr>
<tr bgcolor="#E7E7E7">
  <td height="33" colspan="6" bgcolor="#FFFFE5"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="15%" align="right" style="padding-right: 50px;">
          
	<s:select list="#{1:'命令名称'}" name="con" listKey="key" listValue="value" cssStyle="width:120px"></s:select>
          
        	
        </td>
        <td width="25%">
          <s:textfield name="convalue" cssStyle="width:80%"></s:textfield>
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
	<td width="5%" align="center">序号</td>
	<td width="20%" align="center">所属项目名称</td>
	<td width="25%" align="center">命令名称</td>
	<td width="20%" align="center">命令值</td>
	<td width="5%" align="center">修改</td>
	<td width="5%" align="center">删除</td>
	</tr>
	<s:if test="%{commands.size()==0}">
				<td colspan="6" align="center">
					暂无该信息
				</td>
	</s:if>
<s:iterator value="commands" var="command" status="status">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
		<td><s:property value="#status.count"/></td>
		<td><s:property value="project.name" /></td>
	  	<td><s:property value="name" /></td>
	  	<td><s:property value="command" /></td>
		<td><a href="commandAction!load?id=<s:property value="id" />"><img src="skin/images/frame/huiwu_3.gif" width="20" height="20" border="0"></a></td>
		<td><a href="commandAction!delete?id=<s:property value="id" />" onclick="return confirm('你确定删除该信息吗？')"><img src="skin/images/frame/huiwu_2.gif" width="20" height="20" border="0"></a></td>
	</tr>
  
</s:iterator>

<tr align="right" bgcolor="#EEF4EA">
  
  <td height="34" colspan="6" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('commandAction!list',1,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">首页</a>&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('commandAction!list',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('commandAction!list',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('commandAction!list',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpLinePage('commandAction!list',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>');" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page"/>" size="2" style="ime-mode=disabled;width:25px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount"/>页</td>
  
</tr>
</table>

</form>

</body>
</html>