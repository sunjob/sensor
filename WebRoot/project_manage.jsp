<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>项目管理</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">

<script type="text/javascript" src="js/pageKit.js"></script></head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理>>项目信息管理</div>
<!--  快速转换位置按钮  -->
<table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#8BC7F1" align="center">
<tr>
 <td height="26">
  <table width="98%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td align="center">
  <input type='button' class='coolbg np' onClick="javascript:window.location='projectAction!goToAdd'" value='新增项目' />
 </td>
 </tr>
</table>
</td>
</tr>
</table>
  
<!--  内容列表   -->
<form name="form2" action="projectAction!list" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="8" align="center">&nbsp;<b>项目信息管理</b>&nbsp;
	</td>
</tr>
<tr bgcolor="#E7E7E7">
  <td height="33" colspan="8" bgcolor="#FFFFE5"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="15%" align="right" style="padding-right: 50px;">
          
	<select name="con" id="con" style="width:120px">
    <option value="0" selected="selected">选择类型</option>
    <option value="1">项目名称</option>
    <option value="2">施工单位</option>
    <option value="3">管道厂商</option>
    <option value="4">项目业主</option>


</select>
          
        	
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
	<td width="15%" align="center">项目名称</td>
	<td width="15%" align="center">施工单位</td>
	<td width="15%" align="center">管道厂商</td>
	<td width="15%" align="center">项目业主</td>
	<td width="20%" align="center">项目创建时间</td>
	<td width="5%" align="center">修改</td>
	<td width="5%" align="center">删除</td>
	</tr>
	
<s:if test="%{projects.size()==0}">
				<td colspan="8" align="center">
					暂无该信息
				</td>
	</s:if>
<s:iterator value="projects" var="project" status="status">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
		<td><s:property value="#status.count"/></td>
	  	<td><s:property value="name" /></td>
	  	<td><s:property value="sgdw" /></td>
	  	<td><s:property value="gdcs" /></td>
	  	<td><s:property value="owner" /></td>
	  	
	  	<td><s:property value="createtime" /></td>
		<td><a href="projectAction!load?id=<s:property value="id" />"><img src="skin/images/frame/huiwu_3.gif" width="20" height="20" border="0"></a></td>
		<td>
		<s:if test="id==1">
			<img src="skin/images/nodelete.gif" width="20" height="20" border="0">
		</s:if>
		<s:else>
			<a href="projectAction!delete?id=<s:property value="id" />" onclick="return confirm('你确定删除该信息吗（该项目的所有信息将被删除）？')"><img src="skin/images/frame/huiwu_2.gif" width="20" height="20" border="0"></a>
		</s:else>
		</td>
	</tr>
</s:iterator>
	

<tr align="right" bgcolor="#EEF4EA">
  
  <td height="34" colspan="8" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('projectAction!list',1,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">首页</a>&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('projectAction!list',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('projectAction!list',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('projectAction!list',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpLinePage('projectAction!list',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>');" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page"/>" size="2" style="ime-mode=disabled;width:25px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount"/>页</td>
  
</tr>
</table>

</form>

</body>
</html>
