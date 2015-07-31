<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>能源物联网管理系统</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>
<!--[if IE 6]>
	<script src="js/IE6PNG.js"></script>
	<script type="text/javascript">
		IE6PNG.fix('.png');
	</script>
<![endif]-->
<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})	
	
	
	$("#time").html(new Date().toLocaleString());
	
	
	function realtime()
	{
		$("#time").html(new Date().toLocaleString());
	}
	setInterval(realtime,1000);  
})	

function logout(){
	if(confirm("你确定退出管理系统吗？")){
		parent.location.href = "userAction!logout";
	}
	
}
</script>

</head>

<body style="background: url(img/topbg.jpg); background-color:#DEEFFF; background-repeat: repeat-x; background-position: 500px 0px;" >
<div class="topleft">能源物联网管理系统<font size="10">&nbsp;v1.0</font></div>
<div class="topright">    
    <ul>
      <li><span> ${user.username} 	<s:if test="#session.user.limits==0">系统管理员</s:if>
	    	<s:elseif test="#session.user.limits==1">超级管理员</s:elseif>
	    	<s:elseif test="#session.user.limits==2">普通管理员</s:elseif>
	    	<s:else>
	    	游客
	    	</s:else> </span></li>
	    	<li><span><div id="time"></div> </span></li>
      <li><span><a href="userAction!loadPassword" target="rightFrame">修改密码</a></span></li>
      <li><span><a href="javascript:void(0)" onClick="logout()">注销</a></span></li>
    </ul>
   
    </div>
    <!--
<div class="topleftmain" >    
    <ul class="nav">
    <li style="width: 150px; background: none; margin-left:50px;"><a href="smanag.html" target="rightFrame"><span>[ 用户管理 ]</span></a></li>

    <li><a href="left.html" target="leftFrame" class="selected">
    <h2>设 置</h2></a></li>
    <li><a href="left-2.html"  target="leftFrame">
    <h2>查 看</h2></a></li>
    <li><a href="left-3.html"  target="leftFrame">
    <h2>控 制</h2>
      </a></li>
    <li><a href="left-4.html" target="leftFrame">
    <h2>编 辑</h2></a></li>
  </ul>
</div>
-->
</body>
</html>
