<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>能源物联网管理系统</title>
<base target="_self">
<link rel="stylesheet" type="text/css" href="skin/css/base.css" />
<link rel="stylesheet" type="text/css" href="skin/css/main.css" />
</head>
<body leftmargin="8" topmargin='8'>

<div class="main_box">
<div class="main_box_top">
  <div class="main_box_tleft"><img src="skin/images/frame/index_r3_c3.jpg" width="10" height="43"> </div>
  <div class="main_tpic"><img src="skin/images/frame/index_r3_logo.jpg" width="123" height="43"></div>
  <div class="main_box_tright"><img src="skin/images/frame/index_r3_c4.jpg" width="10" height="43"></div>
</div>
<div class="main_box_mid">

  <div class="main_line">
    <div class="main_line_tit">系统基本信息</div>
  </div>
  <div class="main_dotline">
    <div class="main_banner"></div>
    <div class="main_banner"></div>
    <div class="main_xtxx_pic"><img src="skin/images/frame/dot/index_r11_c6.jpg" width="47" height="44"></div>
    <div class="main_xtxx_txt">欢迎您，${user.username }
		
	</div>
    <div class="main_xtxx_pic"><img src="skin/images/frame/dot/index_r11_c14.jpg" alt="" width="46" height="44"></div>
    <div class="main_xtxx_txt">系统版本信息：cgq_v1.0</div>
  </div>
  
</div>

<div class="main_box_end">
  <div class="main_box_end_tleft"><img src="skin/images/frame/index_r7_c3.jpg" width="10" height="22"></div>
  <div class="main_box_end_tright"><img src="skin/images/frame/index_r7_c4.jpg" width="10" height="22"></div>
</div>
</div>

</body>
</html>