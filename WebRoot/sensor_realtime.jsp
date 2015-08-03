<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<!-- <meta http-equiv="refresh" content="60"> -->
	
	<style type="text/css">
	body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=2MBNhz6LGRasS9Sd2G8wQbwS"></script>
	<script language="JavaScript" src="js/jquery.js"></script>
	<title>地图展示</title>
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	function loadurl(url){
		
		var point = map.getCenter();
		//alert(url+"&mapsize="+map.getZoom()+"&lng="+point.lng+"&lat="+point.lat+"&lineid="+<s:property value="#parameters.lineid" />);
		window.location.href=url+"&mapsize="+map.getZoom()+"&lng="+point.lng+"&lat="+point.lat+"&lineid="+<s:property value="#parameters.lineid" />;
	}

	//获取后台的数据列表---------------------------------网关
	var n = <s:property value="gateways.size()" />;
	var gatewaylist = new Array(n);
	<s:iterator value="gateways" var="gateway" status="index">
		gatewaylist[<s:property value="#index.count-1" />] = new Array(6);
		//gatewaylist[0]=new Array(6);//网关名称gatewaylist[0][0]、经度gatewaylist[0][1]、纬度gatewaylist[0][2]、状态gatewaylist[0][3]、街景gatewaylist[0][4]、编号gatewaylist[0][5]
		gatewaylist[<s:property value="#index.count-1" />][0] = '<s:property value="name" />';
		var index22 = <s:property value="#index.count-1" />;
		<s:if test="lng==null||lng==''">
			gatewaylist[<s:property value="#index.count-1" />][1] = 119.87058+index22/10000;
		</s:if>
		<s:else>
			gatewaylist[<s:property value="#index.count-1" />][1] = <s:property value="lng" />;
		</s:else>
		<s:if test="lat==null||lat==''">
			gatewaylist[<s:property value="#index.count-1" />][2] = 31.409671+index22/10000;
		</s:if>
		<s:else>
			gatewaylist[<s:property value="#index.count-1" />][2] = <s:property value="lat" />;
		</s:else>
		
		gatewaylist[<s:property value="#index.count-1" />][3] = <s:property value="status" />;
		gatewaylist[<s:property value="#index.count-1" />][4] = '<s:property value="streetpic" />';
		gatewaylist[<s:property value="#index.count-1" />][5] = <s:property value="id" />;
	</s:iterator>
	//获取后台的数据列表---------------------------------传感器
	var i=<s:property value="sensors.size()" />;
	var sensorlist = new Array(i);
	//alert(sensorlist.length);
	<s:iterator value="sensors" var="sensor" status="index">
		sensorlist[<s:property value="#index.count-1" />] = new Array(9);
		//sensorlist[0]=new Array(9);//传感器名称sensorlist[0][0]、经度sensorlist[0][1]、纬度sensorlist[0][2]、当前温度sensorlist[0][3]、当前电压sensorlist[0][4]、
		//状态sensorlist[0][5]、街景sensorlist[0][6]、编号sensorlist[0][7]、最新时间sensorlist[0][8]
		sensorlist[<s:property value="#index.count-1" />][0] = '<s:property value="name" />';
		var index2 = <s:property value="#index.count-1" />;
		<s:if test="lng==null||lng==''">
			sensorlist[<s:property value="#index.count-1" />][1] = 119.87058+index2/10000;
		</s:if>
		<s:else>
			sensorlist[<s:property value="#index.count-1" />][1] = <s:property value="lng" />;
		</s:else>
		<s:if test="lat==null||lat==''">
			sensorlist[<s:property value="#index.count-1" />][2] = 31.409671+index2/10000;
		</s:if>
		<s:else>
			sensorlist[<s:property value="#index.count-1" />][2] = <s:property value="lat" />;
		</s:else>
		
		<s:if test="nowtemp==null||nowtemp==''">
			sensorlist[<s:property value="#index.count-1" />][3] = 0;
		</s:if>
		<s:else>
			sensorlist[<s:property value="#index.count-1" />][3] = <s:property value="nowtemp" />;
		</s:else>
		
		<s:if test="nowvoltage==null||nowvoltage==''">
			sensorlist[<s:property value="#index.count-1" />][4] = 0;
		</s:if>
		<s:else>
			sensorlist[<s:property value="#index.count-1" />][4] = <s:property value="nowvoltage" />;
		</s:else>
		
		sensorlist[<s:property value="#index.count-1" />][5] = <s:property value="status" />;
		sensorlist[<s:property value="#index.count-1" />][6] = '<s:property value="streetpic" />';
		sensorlist[<s:property value="#index.count-1" />][7] = <s:property value="id" />;
		sensorlist[<s:property value="#index.count-1" />][8] = '<s:date name="lasttime" format="yyyy-MM-dd HH:mm:ss"/>';
	</s:iterator>
	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	//map.centerAndZoom(new BMap.Point(119.87058,31.409671), 16);  // 初始化地图,设置中心点坐标和地图级别
	if(gatewaylist.length==0){
		map.centerAndZoom(new BMap.Point(119.87058,31.409671), 16);  // 初始化地图,设置中心点坐标和地图级别
	}else{
		map.centerAndZoom(new BMap.Point(gatewaylist[0][1],gatewaylist[0][2]), 16);
	}
	<s:if test="#parameters.mapsize!=null&&#parameters.lng!=null&&#parameters.lat!=null">
		map.centerAndZoom(new BMap.Point(<s:property value="#parameters.lng" />,<s:property value="#parameters.lat" />), <s:property value="#parameters.mapsize" />);
	</s:if>
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.setCurrentCity("宜兴");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

	//工具
	var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
	var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
	map.addControl(top_left_control);
	map.addControl(top_left_navigation);
	
	
	
	if(i==0){
		//暂无传感器
		var point1 = new BMap.Point(119.87058,31.409671);// 创建点
		var marker1 = new BMap.Marker(point1); // 创建点标记
		map.addOverlay(marker1);            //增加点
		//信息框弹出事件
		var sContent = "<h4>该线路暂无传感器</h4>"+
		"中心经度: 119.87058<br/>中心纬度: 31.409671<br/>";
		var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
		marker1.addEventListener("click", function(){          
		   this.openInfoWindow(infoWindow);
		   //图片加载完毕重绘infowindow
		   //document.getElementById('imgDemo').onload = function (){
			//   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
		   //}
		});
	}
	
	//点-添加网关
	for(var m=0;m<n;m++){
		var point2 = new BMap.Point(gatewaylist[m][1],gatewaylist[m][2]);// 创建点
		var marker2 = new BMap.Marker(point2); // 创建点标记
		
		if(gatewaylist[m][3]==0){
			//断开红色---------------------------------------
			var myIcon2 = new BMap.Icon("img/gatewayclose.png", new BMap.Size(40, 64), {    
				// 指定定位位置。   
				// 当标注显示在地图上时，其所指向的地理位置距离图标左上    
				// 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
				// 图标中央下端的尖角位置。    
			   	anchor: new BMap.Size(20, 64),    
				// 设置图片偏移。   
				// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
				// 需要指定大图的偏移位置，此做法与css sprites技术类似。    
				//imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
			});      
			// 创建标注对象并添加到地图   
			marker2 = new BMap.Marker(point2, {icon: myIcon2});   
		}else if(gatewaylist[m][3]==1){
			//正常绿色---------------------------------------
			//从markers图片中获取
			
			//var index = 12;
			var myIcon2 = new BMap.Icon("img/gateway.png", new BMap.Size(40, 64), {    
				// 指定定位位置。   
				// 当标注显示在地图上时，其所指向的地理位置距离图标左上    
				// 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
				// 图标中央下端的尖角位置。    
			   	anchor: new BMap.Size(20, 64),    
				// 设置图片偏移。   
				// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
				// 需要指定大图的偏移位置，此做法与css sprites技术类似。    
				//imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
			});      
			// 创建标注对象并添加到地图   
			marker2 = new BMap.Marker(point2, {icon: myIcon2});    
		}
		map.addOverlay(marker2);            //增加点
		marker2.setTitle("g"+gatewaylist[m][5]);	//设置标签
		
		//信息框弹出事件
		var sContent2 = "<div style='margin:0px;padding:0px;'><font style='font: normal bold 10px;'>网关名称："+gatewaylist[m][0]+"</font><br/>"+
		"经度: "+gatewaylist[m][1]+"<br/>"+
		"纬度: "+gatewaylist[m][2]+"<br/>"+
		"<input type=button value=街景图片 style=height:22px;width:60px;font-size:10px; onclick=loadurl('gatewayAction!showStreetpic?id="+gatewaylist[m][5]+"') /></div>";
		addClickHandler(sContent2,marker2);
	
	}
	var pointarray = new Array(i);//线数组
	//点-添加传感器
	for(var j=0;j<i;j++){
		var point1 = new BMap.Point(sensorlist[j][1],sensorlist[j][2]);// 创建点
		pointarray[j]=point1;//添加进线的数组中
		var marker1 = new BMap.Marker(point1); // 创建点标记
		
		if(sensorlist[j][5]==0){
			//断开灰色---------------------------------------
			var myIcon = new BMap.Icon("img/huise1.png", new BMap.Size(23, 25), {    
				// 指定定位位置。   
				// 当标注显示在地图上时，其所指向的地理位置距离图标左上    
				// 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
				// 图标中央下端的尖角位置。    
			   	anchor: new BMap.Size(10, 25),    
				// 设置图片偏移。   
				// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
				// 需要指定大图的偏移位置，此做法与css sprites技术类似。    
				//imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
			});      
			// 创建标注对象并添加到地图   
			marker1 = new BMap.Marker(point1, {icon: myIcon});    
			map.addOverlay(marker1);            //增加点
		}else if(sensorlist[j][5]==1){
			//正常绿色---------------------------------------
			//从markers图片中获取
			
			//var index = 12;
			var myIcon = new BMap.Icon("img/green.png", new BMap.Size(23, 25), {    
				// 指定定位位置。   
				// 当标注显示在地图上时，其所指向的地理位置距离图标左上    
				// 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
				// 图标中央下端的尖角位置。    
			   	anchor: new BMap.Size(10, 25),    
				// 设置图片偏移。   
				// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
				// 需要指定大图的偏移位置，此做法与css sprites技术类似。    
				//imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
			});      
			// 创建标注对象并添加到地图   
			marker1 = new BMap.Marker(point1, {icon: myIcon});    
			map.addOverlay(marker1);            //增加点
		}else if(sensorlist[j][5]==2){
			//温度过高，红色并跳跃---------------------------------------
			map.addOverlay(marker1);            //增加点
			marker1.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
			
		}
		
		//var label1 = new BMap.Label(""+sensorlist[j][7],{offset:new BMap.Size(20,-10)});
		//marker1.setLabel(label1);	//设置标签
		marker1.setTitle(sensorlist[j][7]);	//设置标签
		
		
		//信息框弹出事件
		var sContent = "<div style='margin:0px;padding:0px;'><font style='font: normal bold 10px;'>传感器编号："+sensorlist[j][0]+"</font><br/>"+
		"<font size=1>最新时间: "+sensorlist[j][8]+"<br/>"+
		"经度: "+sensorlist[j][1]+"<br/>"+
		"纬度: "+sensorlist[j][2]+"<br/>"+
		"温度: "+sensorlist[j][3]+" 度<br/>"+
		"电池电压: "+sensorlist[j][4]+" v<br/></font>"+
		"<input type=button value=街景图片 style=height:22px;width:60px;font-size:10px; onclick=loadurl('sensorAction!showStreetpic?id="+sensorlist[j][7]+"') />&nbsp;<input type=button value=查看曲线  style=height:22px;width:60px;font-size:10px;  onclick=loadurl('sensordataAction!listline?sensorid="+sensorlist[j][7]+"') /></div>";
		addClickHandler(sContent,marker1);
		/*
		var infoWindow = new BMap.InfoWindow(markercontentArray[j]);  // 创建信息窗口对象
		marker1.addEventListener("click", function(){          
		   this.openInfoWindow(infoWindow);
		   //图片加载完毕重绘infowindow
		   //document.getElementById('imgDemo').onload = function (){
			//   infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
		   //}
		});
		*/
	}
	
	//线
	//var pointarray = new Array(i);
	
	var polyline = new BMap.Polyline(pointarray, {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5});   //创建折线
	map.addOverlay(polyline);   //增加折线
	
	var opts = {
				width : 120,     // 信息窗口宽度
				height: 160,     // 信息窗口高度
				//title : "信息窗口" , // 信息窗口标题
				enableMessage:true//设置允许信息窗发送短息
			   };
	//多点弹出信息框
	function addClickHandler(content,marker){
		marker.addEventListener("click",function(e){
			openInfo(content,e)}
		);
	}
	function openInfo(content,e){
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
	
	//重画地图
	function reDrawMap(sensorarray){
		//清除所有覆盖物的点
		for(var i=0;i<sensorarray.length;i++){
			deletePoint(sensorarray[i][0]);
		}
		//重新画这些点
		
		//点-添加传感器
		var y = sensorarray.length;
		for(var x=0;x<y;x++){
			var pointnew = new BMap.Point(sensorarray[x][2],sensorarray[x][3]);// 创建点
			var markernew = new BMap.Marker(pointnew); // 创建点标记
			
			if(sensorarray[x][6]==0){
				//断开灰色---------------------------------------
				var myIcon = new BMap.Icon("img/huise1.png", new BMap.Size(23, 25), {    
					// 指定定位位置。   
					// 当标注显示在地图上时，其所指向的地理位置距离图标左上    
					// 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
					// 图标中央下端的尖角位置。    
				   	anchor: new BMap.Size(10, 25),    
					// 设置图片偏移。   
					// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
					// 需要指定大图的偏移位置，此做法与css sprites技术类似。    
					//imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
				});      
				// 创建标注对象并添加到地图   
				markernew = new BMap.Marker(pointnew, {icon: myIcon}); 
				map.addOverlay(markernew); 
			}else if(sensorarray[x][6]==1){
				//正常绿色---------------------------------------
				//从markers图片中获取
				
				//var index = 12;
				var myIcon = new BMap.Icon("img/green.png", new BMap.Size(23, 25), {    
					// 指定定位位置。   
					// 当标注显示在地图上时，其所指向的地理位置距离图标左上    
					// 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
					// 图标中央下端的尖角位置。    
				   	anchor: new BMap.Size(10, 25),    
					// 设置图片偏移。   
					// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
					// 需要指定大图的偏移位置，此做法与css sprites技术类似。    
					//imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
				});      
				// 创建标注对象并添加到地图   
				markernew = new BMap.Marker(pointnew, {icon: myIcon}); 
				map.addOverlay(markernew);   
			}else if(sensorarray[x][6]==2){
				//温度过高，红色并跳跃---------------------------------------
				map.addOverlay(markernew);
				markernew.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				//console.log('i m in');
			}
			            //增加点
			markernew.setTitle(sensorarray[x][0]);	//设置标签
			
			
			//信息框弹出事件
			var sContent = "<div style='margin:0px;padding:0px;'><font style='font: normal bold 10px;'>传感器编号："+sensorarray[x][1]+"</font><br/>"+
			"<font size=1>最新时间: "+sensorarray[x][8]+"<br/>"+
			"经度: "+sensorarray[x][2]+"<br/>"+
			"纬度: "+sensorarray[x][3]+"<br/>"+
			"温度: "+sensorarray[x][4]+" 度<br/>"+
			"电池电压: "+sensorarray[x][5]+" v<br/></font>"+
			"<input type=button value=街景图片 style=height:22px;width:60px;font-size:10px; onclick=loadurl('sensorAction!showStreetpic?id="+sensorarray[x][0]+"') />&nbsp;<input type=button value=查看曲线  style=height:22px;width:60px;font-size:10px;  onclick=loadurl('sensordataAction!listline?sensorid="+sensorarray[x][0]+"') /></div>";
			addClickHandler(sContent,markernew);
			
		}
				
		
	}
	function deletePoint(sensorid){
		//console.log("sensorid="+sensorid);
		var allOverlay = map.getOverlays();
		for (var i = 0; i < allOverlay.length; i++){
				//console.log(allOverlay[i].getTitle());
					try{
						if(allOverlay[i].getTitle() == sensorid){
							map.removeOverlay(allOverlay[i]);
						}
					}catch(e){
						//console.log('undefined function');
						continue;
					}
					
				
			
			
		}
	}
	
				$(function(){
					setInterval("ShowGatemapStatus()",5000);
					setInterval("ShowSensorStatus()",5000);
					
				});
				function ShowSensorStatus(){
				  
				  $.ajax({ 
					type:'get', 
					url:'sensorStatus', 
					dataType: 'json', 
					data:'lineid='+<s:property value="lineid" />,
						success:function(data){ 
							//var _tr = "";
							var sensorarray = new Array();
							$.each(data,function(i,list){ 
								//_tr += "["+list.sensorid+","+list.sensorname+","+list.lng+","+list.lat+","+list.nowtemp+","+list.nowvoltage+","+list.status+","+list.streetpic+","+list.lasttime+"]";
								var sensorobj = new Array(9);
								
								sensorobj[0] = list.sensorid;
								sensorobj[1] = list.sensorname;
								sensorobj[2] = list.lng;
								if(sensorobj[2]==''){
									sensorobj[2] = 119.87058+i/10000;
								}
								sensorobj[3] = list.lat;
								if(sensorobj[3]==''){
									sensorobj[3] = 31.409671+i/10000;
								}
								sensorobj[4] = list.nowtemp;
								sensorobj[5] = list.nowvoltage;
								sensorobj[6] = list.status;
								sensorobj[7] = list.streetpic;
								sensorobj[8] = list.lasttime;
								sensorarray.push(sensorobj);
							}) 
							//$(".menuson").html(_tr);
							
							//console.log(sensorarray.length);
							//console.log(_tr);
							reDrawMap(sensorarray);
						} 
						
					})     	 
				}
				
				function ShowGatemapStatus(){
				  
				  $.ajax({ 
					type:'get', 
					url:'gatemapStatus', 
					dataType: 'json', 
					data:'lineid='+<s:property value="lineid" />,
						success:function(data){ 
							//var _tr = "";
							var gatewayarray = new Array();
							$.each(data,function(i,list){ 
								//_tr += "["+list.gatewayname+","+list.lng+","+list.lat+","+list.status+","+list.streetpic+","+list.gatewayid+"]";
								var gatewayobj = new Array(6);
								
								gatewayobj[0] = list.gatewayname;
								gatewayobj[1] = list.lng;
								if(gatewayobj[1]==''){
									gatewayobj[1] = 119.87058+i/10000;
								}
								gatewayobj[2] = list.lat;
								if(gatewayobj[2]==''){
									gatewayobj[2] = 31.409671+i/10000;
								}
								gatewayobj[3] = list.status;
								gatewayobj[4] = list.streetpic;
								gatewayobj[5] = list.gatewayid;
								gatewayarray.push(gatewayobj);
							}) 
							//$(".menuson").html(_tr);
							
							//console.log(gatewayarray.length);
							//console.log(_tr);
							reDrawGateway(gatewayarray);
						} 
						
					})     	 
				}
	
	//重画地图
	function reDrawGateway(gatewayarray){
		//清除所有覆盖物的点
		for(var i=0;i<gatewayarray.length;i++){
			deletePoint("g"+gatewayarray[i][5]);
		}
		//重新画这些点
		
		//点-添加网关
		var y = gatewayarray.length;
		for(var x=0;x<y;x++){
			var pointnew2 = new BMap.Point(gatewayarray[x][1],gatewayarray[x][2]);// 创建点
			var markernew2 = new BMap.Marker(pointnew2); // 创建点标记
			
			if(gatewayarray[x][3]==0){
				//断开红色---------------------------------------
				var myIcon2 = new BMap.Icon("img/gatewayclose.png", new BMap.Size(40, 64), {    
					// 指定定位位置。   
					// 当标注显示在地图上时，其所指向的地理位置距离图标左上    
					// 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
					// 图标中央下端的尖角位置。    
				   	anchor: new BMap.Size(20, 64),    
					// 设置图片偏移。   
					// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
					// 需要指定大图的偏移位置，此做法与css sprites技术类似。    
					//imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
				});      
				// 创建标注对象并添加到地图   
				markernew2 = new BMap.Marker(pointnew2, {icon: myIcon2});   
			}else if(gatewayarray[x][3]==1){
				//正常绿色---------------------------------------
				//从markers图片中获取
				
				//var index = 12;
				var myIcon2 = new BMap.Icon("img/gateway.png", new BMap.Size(40, 64), {    
					// 指定定位位置。   
					// 当标注显示在地图上时，其所指向的地理位置距离图标左上    
					// 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
					// 图标中央下端的尖角位置。    
				   	anchor: new BMap.Size(20, 64),    
					// 设置图片偏移。   
					// 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
					// 需要指定大图的偏移位置，此做法与css sprites技术类似。    
					//imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
				});      
				// 创建标注对象并添加到地图   
				markernew2 = new BMap.Marker(pointnew2, {icon: myIcon2});    
			}
			map.addOverlay(markernew2);            //增加点
			markernew2.setTitle("g"+gatewayarray[x][5]);	//设置标签
			
			//信息框弹出事件
			var sContentnew2 = "<div style='margin:0px;padding:0px;'><font style='font: normal bold 10px;'>网关名称："+gatewayarray[x][0]+"</font><br/>"+
			"经度: "+gatewayarray[x][1]+"<br/>"+
			"纬度: "+gatewayarray[x][2]+"<br/>"+
			"<input type=button value=街景图片 style=height:22px;width:60px;font-size:10px; onclick=loadurl('gatewayAction!showStreetpic?id="+gatewayarray[x][5]+"') /></div>";
			addClickHandler(sContentnew2,markernew2);
			
		}
				
		
	}
	
</script>