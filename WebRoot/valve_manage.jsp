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
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统设置>>外设管理</div>
<!--  快速转换位置按钮  -->
<s:if test="#session.user.limits<2">
<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#8BC7F1" align="center">
<tr>
 <td height="26">
  <table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td align="center">
  <input type='button' class='coolbg np' onClick="javascript:window.location='valveAction!goToAdd'" value='新增外设' />
 </td>
 </tr>
</table>
</td>
</tr>
</table>
</s:if>
<!--  内容列表   -->
<form name="form2" action="valveAction!list" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="11" align="center">&nbsp;<b>外设管理</b>&nbsp;
	
	</td>
</tr>
<tr bgcolor="#E7E7E7">
  <td height="33" colspan="11" bgcolor="#FFFFE5"><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="15%" align="right" style="padding-right: 50px;">
		         <s:select list="#{0:'选择类型',1:'所属线路',2:'所属网关',3:'外设名称'}" name="con" listKey="key" listValue="value" cssStyle="width:120px"></s:select>
        </td>
        <td width="25%">
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
	<td width="5%" align="center">序号</td>
	<td width="10%" align="center">所属项目</td>
	<td width="10%" align="center">所属线路</td>
	<td width="10%" align="center">所属网关</td>
  	<td width="10%" align="center">外设名称</td>
  	<td width="10%" align="center">外设地址</td>
  	<td width="10%" align="center">开关位置号</td>
  	<td width="10%" align="center">开关状态</td>
  	<td width="10%" align="center">外设数据</td>
  	<s:if test="#session.user.limits<2">
	<td width="5%" align="center">修改</td>
	<td width="5%" align="center">删除</td>
	</s:if>
</tr>



	<s:if test="%{valves.size()==0}">
				<td colspan="11" align="center">
					暂无该信息
				</td>
	</s:if>
<s:iterator value="valves" var="valve" status="status">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
		<td><s:property value="#status.count"/></td>
		<td><s:property value="gateway.line.project.name"/></td>
		<td><s:property value="gateway.line.name"/></td>
		<td><s:property value="gateway.name"/></td>
		<td><s:property value="name"/></td>
		<td><s:property value="valveaddress"/></td>
		<td><s:property value="locatenumber" /></td>
	  	<td>
		  	<s:if test="status==1">
		  		<a href="valveAction!changestatus?valveid=<s:property value="id" />&status=0" onclick="return confirm('你确定发送命令关闭外设吗？')">开启状态</a>
		  	</s:if>
		  	<s:elseif test="status==0">
		  		<a href="valveAction!changestatus?valveid=<s:property value="id" />&status=1" onclick="return confirm('你确定发送命令打开外设吗？')">关闭状态</a>
		  	</s:elseif>
	  	</td>
	   	<td><a href="valveAction!datalistsend?valveid=<s:property value="id" />" onclick="return confirm('你确定发送命令并接收外设数据吗？')">接收数据</a></td>
		<s:if test="#session.user.limits<2">
		<td><a href="valveAction!load?id=<s:property value="id" />"><img src="skin/images/frame/huiwu_3.gif" width="20" height="20" border="0"></a></td>
		<td><a href="valveAction!delete?id=<s:property value="id" />" onclick="return confirm('你确定删除该信息吗？')"><img src="skin/images/frame/huiwu_2.gif" width="20" height="20" border="0"></a></td>
		</s:if>
	</tr>
  
</s:iterator>

<tr align="right" bgcolor="#EEF4EA">
  
  <td height="34" colspan="11" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('valveAction!list',1,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">首页</a>&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('valveAction!list',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('valveAction!list',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('valveAction!list',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpLinePage('valveAction!list',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>');" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page"/>" size="2" style="ime-mode=disabled;width:50px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount"/>页</td>
  
</tr>
</table>

</form>

</body>
</html>