
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
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jsp_util.js"></script>
<script type="text/javascript">
function checkform(){
	var linename = document.getElementById("linename");
	if(linename.value.length==0||linename.value==''){
		alert("线路名不能为空");
		linename.value="";
		linename.focus();
		return false;
	}
	
	return true;
}

</script>
</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理&gt;&gt;<a href='javascript:history.back();' target='main'>线路管理</a>&gt;&gt;新增线路&nbsp;<a href="javascript:history.back();" style=" color:red;">[返回]</a></div>
  
<!--  内容列表   -->
<form name="form2" action="lineAction!add" method="post"  enctype="multipart/form-data"  onsubmit="return checkform();">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="2" align="center"><strong>新增线路</strong>
		<s:hidden name="line.orderid" value="0"></s:hidden>
		
	</td>
</tr>

<s:if test="#session.user.limits==0">
<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
  <td height="25" align="right"><strong><font color="#333333">所属项目</font></strong></td>
  <td align="left">

 <s:select list="projects"  listValue="name " listKey="id"  name="line.project.id" 
             headerKey="0l"  id="projects" value="projectid"
           ></s:select>  
</tr>
</s:if>



<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">*线路名称</font></strong></td>
	<td width="75%" align="left">
	  <label>
     
		<s:textfield name="line.name" cssStyle="width:80%" id="linename"></s:textfield>
    
	  </label></td>
</tr>
<!-- 
<tr align="center" bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25" >
	<td width="25%" height="25" align="right"><strong><font color="#333333">排序编号</font></strong></td>
	<td width="75%" align="left">
	  <label>
     
		<s:textfield id="orderid" cssClass="ck"  name="line.orderid" cssStyle="width:80%"></s:textfield>
    
	  </label></td>
</tr>
 -->



</table>

<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr>
    <td height="29" align="center" valign="bottom">
    <s:token></s:token>
      <input type='submit' class="coolbg np" onClick="return checkLine();" value='保存' style="width:80" />&nbsp;&nbsp;
      <input type='reset' class="coolbg np" onClick="" value='重置' style="width:80" />&nbsp;&nbsp;
      <input type='button' class="coolbg np" onClick="javascript:history.back();" value='返回' style="width:80" />&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td height="18" align="center">&nbsp;</td>
  </tr>
</table>


</form>

</body>
</html>