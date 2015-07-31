<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=2MBNhz6LGRasS9Sd2G8wQbwS"></script>
	<title>地图展示</title>
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	
	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(119.87058,31.409671), 16);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.setCurrentCity("宜兴");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

	//工具
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	map.addControl(top_left_control);
	map.addControl(top_left_navigation);

	//点
	var marker1 = new BMap.Marker(new BMap.Point(119.87058,31.409671)); // 创建点
	var marker2 = new BMap.Marker(new BMap.Point(119.871586,31.409687)); // 创建点
	var marker3 = new BMap.Marker(new BMap.Point(119.874964,31.409425)); // 创建点
	var marker4 = new BMap.Marker(new BMap.Point(119.877515,31.409332)); // 创建点
	var marker5 = new BMap.Marker(new BMap.Point(119.880713,31.409071)); // 创建点
	map.addOverlay(marker1);            //增加点
	map.addOverlay(marker2);            //增加点
	map.addOverlay(marker3);            //增加点
	map.addOverlay(marker4);            //增加点
	map.addOverlay(marker5);            //增加点
	
	marker1.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
	//信息框弹出事件
	var sContent = "<h4>节点编号1</h4>"+
	"经度：119.87058；纬度：31.409671<br/>"+
	"设备编号：SN001 温度：45度<br/>"+
	"设备编号：SN002 压力：2000<br/>"+
	"设备编号：SN003 流量：10000<br/>"+
	"<input type=button value=查看街景图片 onclick=javascript:window.location.href='streetpic.html' /><br/>";
	var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
	marker1.addEventListener("click", function(){          
	   this.openInfoWindow(infoWindow);
	   //图片加载完毕重绘infowindow
	   //document.getElementById('imgDemo').onload = function (){
		//   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
	   //}
	});
	//从markers图片中获取
	var point1 = new BMap.Point(119.884091,31.408362);
	var index = 12;
	var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {    
		// 指定定位位置。   
		// 当标注显示在地图上时，其所指向的地理位置距离图标左上    
		// 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
		// 图标中央下端的尖角位置。    
	   	offset: new BMap.Size(10, 25),    
		// 设置图片偏移。   
		// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
		// 需要指定大图的偏移位置，此做法与css sprites技术类似。    
		imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
	});      
	// 创建标注对象并添加到地图   
	var marker6 = new BMap.Marker(point1, {icon: myIcon});    
	map.addOverlay(marker6);   

	//灰色点
	var icons = "img/huise1.png"; //这个是你要显示坐标的图片的相对路径
	var marker7 = new BMap.Marker(new BMap.Point(119.876904,31.405881)); //lng为经度,lat为纬度
	var icon = new BMap.Icon(icons, new BMap.Size(23, 25)); //显示图标大小
	marker7.setIcon(icon);//设置标签的图标为自定义图标
	map.addOverlay(marker7);//将标签添加到地图中去

	//线
	var polyline = new BMap.Polyline([
		new BMap.Point(119.87058,31.409671),
		new BMap.Point(119.871586,31.409687),
		new BMap.Point(119.874964,31.409425),
		new BMap.Point(119.877515,31.409332),
		new BMap.Point(119.880713,31.409071),
		new BMap.Point(119.884091,31.408362)
	], {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});   //创建折线
	map.addOverlay(polyline);   //增加折线

	
</script>