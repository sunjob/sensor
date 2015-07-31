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
<title>菜单</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>


<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>
<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav2 li a").click(function(){
		$(".nav2 li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})	
})	
</script>


<!--[if IE 6]>
	<script src="js/IE6PNG.js"></script>
	<script type="text/javascript">
		IE6PNG.fix('.png');
	</script>
<![endif]-->
</head>

<body style="background: #DEEFFF;">
<dl class="leftmenu">
        
    <s:if test="#session.user.limits<3">
    <dd>
    <div class="title">
        <span><img src="images/leftico02.png" /></span>系统设置
    </div>
    	<ul class="menuson">
	    	<s:if test="#session.user.limits<2">
	            <li><cite></cite><a href="userAction!list" target="rightFrame">用户管理</a></li>
	        	<li><cite></cite><a href="addresslistAction!list" target="rightFrame">通讯录管理</a></li>
	        </s:if>
	    	<s:if test="#session.user.limits==0">
	    		<li><cite></cite><a href="projectAction!list" target="rightFrame">项目管理</a></li>
	    	</s:if>
	    	<s:elseif test="#session.user.limits==1">
	    	 	<li><cite></cite><a href="projectAction!viewMyProject" target="rightFrame">我的项目</a></li>
	    	</s:elseif>
    	
	    	<s:if test="#session.user.limits<2">
	            <li><cite></cite><a href="lineAction!list" target="rightFrame">线路管理</a></li>
            </s:if>
	            <li><cite></cite><a href="gatewayAction!list" target="rightFrame">网关管理</a></li>
	            <li><cite></cite><a href="sensorAction!list" target="rightFrame">传感器管理</a></li>
	            <li><cite></cite><a href="valveAction!list" target="rightFrame">外设管理</a></li>
	        <s:if test="#session.user.limits==0">
	            <li><cite></cite><a href="commandAction!list" target="rightFrame">命令管理</a></li>
	        </s:if>
            <s:if test="#session.user.limits<2">
	            <li><cite></cite><a href="alarmAction!load" target="rightFrame">报警短信设置</a></li>
            </s:if>
            	<li><cite></cite><a href="operationAction!list" target="rightFrame">操作日志</a></li>
            <s:if test="#session.user.limits<2">
            	<li><cite></cite><a href="sensordataAction!databak" target="rightFrame">数据管理</a></li>
            </s:if>
        </ul>    
    </dd>
    </s:if>
     
    <dd>
    <div class="title">
        <span><img src="images/leftico03.png" /></span>显示查询
    </div>
        <ul class="menuson">
            <li><cite></cite><a href="lineAction!monitorshow?limits=${user.limits }&projectid=${user.project.id }&linetext=${user.linetext }&upuserid=${user.upuserid }" target="rightFrame">监控显示</a></li>
            <li><cite></cite><a href="sensordataAction!reportdetail?limits=${user.limits }&projectid=${user.project.id }&linetext=${user.linetext }&upuserid=${user.upuserid }" target="rightFrame">报表查询</a></li>
            <li><cite></cite><a href="alarmrecordAction!list?limits=${user.limits }&projectid=${user.project.id }&linetext=${user.linetext }&upuserid=${user.upuserid }" target="rightFrame">报警记录</a></li>
        </ul>     
    </dd>

     
    </dl>

</body>
</html>
