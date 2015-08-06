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
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理>>测温点管理</div>
<!--  快速转换位置按钮  -->

<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#8BC7F1" align="center">
<tr>
 <td height="26">
  <table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td align="center">
  <s:if test="#session.user.limits<2&&listnum==1">
  <input type='button' class='coolbg np' onClick="javascript:window.location='temppointAction!goToAdd?listnum=<s:property value="listnum" />'" value='新增测温点' />
   </s:if>&nbsp;&nbsp;
  <input type='button' class='coolbg np' onClick="javascript:jumpLinePage('temppointAction!list',<s:property value="page"/>,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="listnum"/>);" value='刷新页面' />
 </td>
 </tr>
</table>
</td>
</tr>
</table>

<!--  内容列表   -->
<form name="form2" action="temppointAction!list" method="post">

<table width="90%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="6" align="center">&nbsp;<b>测温点管理</b>&nbsp;
	<s:hidden name="listnum"></s:hidden>
	
	</td>
</tr>
<tr bgcolor="#E7E7E7">
  <td height="33" colspan="6" bgcolor="#FFFFE5"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="15%" align="right" style="padding-right: 50px;">

			<s:select list="#{0:'选择类型',1:'线路名称',2:'杆塔名称',3:'杆塔别名',4:'测温点'}" name="con" listKey="key" listValue="value" cssStyle="width:120px"></s:select>
         
        	
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
	<td width="55%" align="center">线路-杆塔（别名）-相位-侧位-测温点</td>
	<td width="10%" align="center">当前温度</td>
	<td width="10%" align="center">当前电压</td>
	  <s:if test="#session.user.limits<2">
	<td width="5%" align="center">修改</td>
	<td width="5%" align="center">删除</td>
	</s:if>
	</tr>

	<s:if test="%{temppoints.size()==0}">
				<td colspan="6" align="center">
					暂无该信息
				</td>
			</s:if>
   
<s:iterator value="temppoints" var="temppoint" status="status">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
		<td><s:property value="#status.count"/></td>
	  <td><s:property value="gateway.line.name" />-<s:property value="gateway.name" />（<s:property value="gateway.alias" />）-
	  <s:if test="phasetype==1">
		A1相</s:if>
		<s:elseif test="phasetype==2">
		A2相</s:elseif>
		<s:elseif test="phasetype==3">
		B1相</s:elseif>
		<s:elseif test="phasetype==4">
		B2相</s:elseif>
		<s:elseif test="phasetype==5">
		C1相</s:elseif>
		<s:elseif test="phasetype==6">
		C2相</s:elseif>
		<s:else>
		未知相位
		</s:else>
		-<s:if test="sidetype==1">
		电源侧</s:if>
		<s:elseif test="sidetype==2">
		负荷侧</s:elseif>
		<s:else>
		未知侧位
		</s:else>-
		<s:property value="name" /></td>
		
		<td><a href="temp_line.html?id=<s:property value="id"/>"><s:property value="lasttp"/></a></td>
		<td><a href="v_line.html?id=<s:property value="id"/>"><s:property value="lastv"/></a></td>
		 <s:if test="#session.user.limits<2">
		<td><a href="temppointAction!load?id=<s:property value="id" />&listnum=<s:property value="listnum" />&page=<s:property value="page"/>"><img src="skin/images/frame/huiwu_3.gif" width="20" height="20" border="0"></a></td>
		<td><a href="temppointAction!delete?id=<s:property value="id" />&listnum=<s:property value="listnum" />" onclick="return confirm('你确定删除该信息吗？')"><img src="skin/images/frame/huiwu_2.gif" width="20" height="20" border="0"></a></td>
		</s:if>
	</tr>
</s:iterator>

<tr align="right" bgcolor="#EEF4EA">
  
  <td height="34" colspan="6" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('temppointAction!list',1,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="listnum"/>);" target="rightFrame">首页</a>&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('temppointAction!list',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="listnum"/>);" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('temppointAction!list',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="listnum"/>);" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('temppointAction!list',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="listnum"/>);" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpLinePage('temppointAction!list',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="listnum"/>);" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page"/>" size="2" style="ime-mode=disabled;width:50px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount"/>页</td>
</tr>
</table>

</form>

</body>
</html>