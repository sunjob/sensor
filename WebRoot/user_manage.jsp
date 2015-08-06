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
<title>用户管理</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">

<script type="text/javascript" src="js/pageKit.js"></script></head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理>>用户管理</div>
<!--  快速转换位置按钮  -->
<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#8BC7F1" align="center">
<tr>
 <td height="26">
  <table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td align="center">
  <input type='button' class='coolbg np' onClick="javascript:window.location='userAction!goToAdd'" value='新增用户' />
 </td>
 </tr>
</table>
</td>
</tr>
</table>
  
<!--  内容列表   -->
<form name="form2" action="userAction!list" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="7" align="center">&nbsp;<b>用户管理</b>&nbsp;
    <!--
<s:hidden name="publicaccount" value="%{#session.pubclient.publicaccount}"></s:hidden>
  -->
	
	</td>
</tr>
<tr bgcolor="#E7E7E7">
  <td height="33" colspan="7" bgcolor="#FFFFE5"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="15%" align="right" style="padding-right: 50px;">
          <!--
          <select name="con" style="width:120px">
              <option value="0">选择类型</option>
              <option value="1">用户名称</option>
          </select>
        -->
		<s:select list="#{0:'选择类型',1:'用户名称',2:'权限',3:'上级用户'}" name="con" listKey="key" listValue="value" cssStyle="width:120px"></s:select>
          
        	
        </td>
        <td width="25%">
        <!--
          <input type="text" name="convalue" style="width:80%"/>
              --> 
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
		<td width="15%" align="center">所属项目</td>
	<td width="20%" align="center">用户名称</td>
  	<td width="15%" align="center">权限</td>
  
  	<td width="15%" align="center">上级用户</td>
	<td width="5%" align="center">修改</td>
	<td width="5%" align="center">删除</td>
	</tr>

	<s:if test="%{uservos.size()==0}">
				<td colspan="5" align="center">
					暂无该信息
				</td>
			</s:if>
   
<s:iterator value="uservos" var="user" status="status">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
		<td><s:property value="#status.count" /></td>
			<td><s:property value="projectName" /></td>
	  	<td><a href="userAction!view?id=<s:property value="id" />"><s:property value="username" /></a></td>
	    <td>
	    <s:if test="limits==0">
		 系统管理员
		</s:if>
		<s:if test="limits==1">
		 超级管理员
		</s:if>
		<s:elseif test="limits==2">
		 普通管理员
		</s:elseif>
		<s:elseif test="limits==3">
		 普通用户
		</s:elseif>
		</td>
		<td><s:property value="upuserName"/></td>
			<td><a href="userAction!load?id=<s:property value="id"/>"><img src="skin/images/frame/huiwu_3.gif" width="20" height="20" border="0"></a></td>
			<td>
				<s:if test="#session.user.id!=id">
				<a href="userAction!delete?id=<s:property value="id" />" onclick="return confirm('你确定删除该信息吗？')"><img src="skin/images/frame/huiwu_2.gif" width="20" height="20" border="0"></a>
				</s:if>
				<s:else>
					<img src="skin/images/nodelete.gif" width="20" height="20" border="0">
				</s:else>
			</td>
		</tr>
</s:iterator>

<tr align="right" bgcolor="#EEF4EA">
  
  <td height="34" colspan="7" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpPublicPage('userAction!list',1,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">首页</a>&nbsp;&nbsp;  
  <a href="javascript:jumpPublicPage('userAction!list',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpPublicPage('userAction!list',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpPublicPage('userAction!list',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpPublicPage('userAction!list',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>');" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="1" size="2" style="ime-mode=disabled;width:50px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount"/>页</td>
  
</tr>
</table>

</form>

</body>
</html>