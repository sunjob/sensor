<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/pageKit.js"></script></head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理>>操作日志</div>

  
<!--  内容列表   -->
<form name="form2" action="operationAction!list" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="6" align="center">&nbsp;<b>操作日志</b>&nbsp;
    <!--
<input type="hidden" name="publicaccount" value="" id="publicaccount"/>
  -->
	
	</td>
</tr>
<tr bgcolor="#E7E7E7">
  <td height="33" colspan="6" bgcolor="#FFFFE5"><table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="60%" align="right" style="padding-right: 50px;">
        	<input type="text" name="startdate" value="<s:property value='startdate'/>" id="startdate" class="Wdate" style="width:160px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd-HH:mm:ss',readOnly:true,maxDate:'#F{$dp.$D(\'enddate\')}'})"/>至
          	<input type="text" name="enddate" value="<s:property value='enddate'/>"  id="enddate" class="Wdate" style="width:160px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd-HH:mm:ss',readOnly:true,minDate:'#F{$dp.$D(\'startdate\')}',startDate:'#F{$dp.$D(\'startdate\',{d:+1})}'})"/>
        </td>
        <td width="10%" align="right" style="padding-right: 10px;">
          
		<s:select list="#{0:'选择类型',1:'操作类型'}" name="con" listKey="key" listValue="value" cssStyle="width:120px"></s:select>
          
        	
        </td>
        <td width="20%">
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
	<td width="5%" align="center">编号</td>
	<td width="15%" align="center">所属项目名称</td>
	<td width="15%" align="center">操作时间</td>
  	<td width="20%" align="center">操作类别</td>
  	<td width="40%" align="center">操作内容</td>
  	<td width="10%" align="center">删除</td>
</tr>
	<s:if test="%{operations.size()==0}">
				<td colspan="6" align="center">
					暂无该信息
				</td>
			</s:if>
   <s:iterator value="operations" var="operation" status="status">
	
  <tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
    <td><s:property value="#status.count"/></td>
    <td><s:property value="project.name" /></td>
    <td><s:date name="optime" format="yyyy-MM-dd-HH:mm:ss"/></td>
    <td><s:property value="optype" /></td>
    <td><s:property value="content" /></td>
    <td>
    	<s:if test="#session.user.limits==0">
			<a href="operationAction!delete?id=<s:property value="id" />" onclick="return confirm('你确定删除该信息吗？')"><img src="skin/images/frame/huiwu_2.gif" width="20" height="20" border="0"></a>
		</s:if>
		<s:else>
				<img src="skin/images/nodelete.gif" width="20" height="20" border="0">
		</s:else>
		</td>
  </tr>
</s:iterator>

<tr align="right" bgcolor="#EEF4EA">
  
 <td height="34" colspan="6" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpDatePage('operationAction!list',1,<s:property value="con"/>,'<s:property value="convalue"/>','<s:property value="startdate"/>','<s:property value="enddate"/>');" target="rightFrame">首页</a>&nbsp;&nbsp; 
  <a href="javascript:jumpDatePage('operationAction!list',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>','<s:property value="startdate"/>','<s:property value="enddate"/>');" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpDatePage('operationAction!list',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>','<s:property value="startdate"/>','<s:property value="enddate"/>');" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpDatePage('operationAction!list',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>','<s:property value="startdate"/>','<s:property value="enddate"/>');" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpDatePage('operationAction!list',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>','<s:property value="startdate"/>','<s:property value="enddate"/>');" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page"/>" size="2" style="ime-mode=disabled;width:50px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount"/>页</td>
  
</table>

</form>

</body>
</html>