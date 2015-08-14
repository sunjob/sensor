<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1" />
		<title>Highcharts Example</title>

		<script type="text/javascript" src="http://cdn.hcharts.cn/jquery/jquery-1.8.2.min.js"></script>
		<style type="text/css">
${demo.css}
		</style>
		<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		//获取后台的数据列表
	var i=<s:property value="sensordatas.size()" />;
	if(i==0){
		alert('暂无数据');
	}
	
	var sensordatalist = new Array(i);
	if(i>1000){
		alert('数据超限，日期请选择3天以内！');
	}else{
		<s:iterator value="sensordatas" var="sensordata" status="index">
			sensordatalist[<s:property value="#index.count-1" />] = new Array(2);
			var date1 = '<s:date name="sdatetime" format="yyyy-MM-dd HH:mm:ss"/>';
			var year = date1.substring(0,4);
			var month = date1.substring(5,7)-1;
			var day = date1.substring(8,10);
			var hour = date1.substring(11,13);
			var minutes = date1.substring(14,16);
			var seconds =  date1.substring(17,19);
			sensordatalist[<s:property value="#index.count-1" />][0] = Date.UTC(year,month,day,hour,minutes,seconds);
			<s:if test="stype==1&&othertype==1">
				sensordatalist[<s:property value="#index.count-1" />][1] = <s:property value="vdata" />;
			</s:if>
			<s:else>
				sensordatalist[<s:property value="#index.count-1" />][1] = <s:property value="sdata" />;
			</s:else>
			
		</s:iterator>
	}
$(function () {
    $('#container').highcharts({
        chart: {
            zoomType: 'x',
            spacingRight: 20,
            animation: Highcharts.svg, // don't animate in old IE  
            
            events: {                                                           
                    load: function() {                                              
                                                                                    
                        // set up the updating of the chart each second             
                        var series = this.series[0];      
                        var sensorid = <s:property value="sensor.id" />; 
                        var starttime = document.getElementById("startdate").value;                         
                        setInterval(function() { 
                        	$.ajax({   
					            url:'getnewtemp',//这里是你的action或者servlert的路径地址   
					            type:'get', //数据发送方式
					            data: { "sensorid":sensorid,"starttimestr":starttime},   
					            async:false,
					            dataType:'json',
					            error: function(msg)
					            { //失败   
					            	console.log('get失败');   
					            },   
					            success: function(msg)
					            { //成功
					            	console.log('get成功');
									 if(msg!=null)
									 {
									 	//alert(msg.msg);
									 	if(msg.msg=='success'){
									 		console.log(msg.time+" is "+msg.data);
									 		var newtime=msg.time;
									 		var year = newtime.substring(0,4);
											var month = newtime.substring(5,7)-1;
											var day = newtime.substring(8,10);
											var hour = newtime.substring(11,13);
											var minutes = newtime.substring(14,16);
											var seconds =  newtime.substring(17,19);
									 		var x = Date.UTC(year,month,day,hour,minutes,seconds), // current time         
			                                y = msg.data;                                  
			                            	series.addPoint([x, y], true, true); 
									 	}
									 	
									 }
								}
							});                                   
                                               
                        }, 10000);                                                   
                    }                                                               
                } 
                
        },
        title: {
            text: '<s:property value="sensor.gateway.line.name" />-<s:property value="sensor.gateway.name" />-<s:property value="sensor.name" />-温度曲线图'
        },
        xAxis: {
            type: 'datetime',
            minRange:6*3600000,//6小时
            minTickInterval:3600000,//1小时
            dateTimeLabelFormats:{
                millisecond: '%H:%M:%S',
                second: "%H:%M:%S",
                minute: "%H:%M",
                hour:"%H:%M",
                day:"%Y-%m-%e",
                month:"%Y-%m",
                year: '%Y'
            },
            title: {
                text: null
            }

        },
        yAxis: {
        	minRange: 10,
            title: {
                text: '数值'
            }
        },
         
        tooltip: {
            shared: true
        },
        legend: {
            enabled: false
        },
        credits: {
                text: '',
                href: 'http://#'
            },
		tooltip: {
            shared: true, //是否共享提示，也就是X一样的所有点都显示出来
            useHTML: true, //是否使用HTML编辑提示信息
            headerFormat: '<small>{point.key}</small><table>',
            pointFormat: '<tr><td style="color: {series.color}">{series.name}: </td>' +
            '<td style="text-align: right"><b>{point.y}</b></td></tr>',
            footerFormat: '</table>',
            valueDecimals: 1, //数据值保留小数位数
			dateTimeLabelFormats:{
                millisecond: '%Y-%m-%e %H:%M:%S',
                second: "%Y-%m-%e %H:%M:%S",
                minute: "%Y-%m-%e %H:%M",
				hour:"%Y-%m-%e %H:%M",
				day:"%Y-%m-%e",
				month:"%Y-%m",
                year: '%Y'
			}
        },
		
        series: [{
            type: 'line',
            name: '数值',
            marker: {
                    enabled: true, /*数据点是否显示*/
                    radius: 3  /*数据点大小px*/
            },
            data: (function() { 
            	var data = []; 
                    for (var i = 0; i <sensordatalist.length; i++) {
					
                        data.push({                                                 
                            x: sensordatalist[i][0],                                     
                            y: sensordatalist[i][1]                                        
                        });                                                         
                    }                                                               
                    return data;                                                    
                })() 
        }]
    });
});				
		</script>
	</head>
	<body>
<script src="js/highcharts.js"></script>
<script src="js/modules/exporting.js"></script>

<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
<div style="margin-left: 10px;">
<hr>
						<form action="sensordataAction!listline" method="post">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr style="height: 35px;">
									<td colspan="2"><h3>按时间段查询数据</h3>
										<s:hidden name="sensorid"></s:hidden>
									</td>
								</tr>
								<tr style="height: 35px;">
									<td>
									时间：
									</td>
									<td>
									<input name="starttime" id="startdate" value="<s:property value="starttime"/>" style="width: 120px;" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'enddate\')}'})" />
									至
									<input name="endtime" id="enddate" value="<s:property value="endtime"/>" style="width: 120px;" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startdate\')}',startDate:'#F{$dp.$D(\'startdate\',{d:+1})}'})" />
									</td>
								</tr>
							
								<tr style="height: 35px;">
									<td>
									类型：
									</td>
									<td>
										<div id="showstype" style="float: left;">
											<s:select list="#{1:'温度/电池电压',2:'压力',3:'流量',5:'表面温度'}" name="stype" listKey="key" listValue="value" onchange="checkstype(this.value);"></s:select>&nbsp;
										</div>
									<script type="text/javascript">
										function checkstype(svalue){
											
											var otherid=document.getElementById("otherid");
											if(svalue==1){
												otherid.style.display="block";
											}else{
												otherid.style.display="none";
											}
										}
									</script>
										<s:if test="stype==1">
											<div id="otherid" style="float: left;">
												<s:radio list="#{0:'温度',1:'电池电压'}" name="othertype" listKey="key" listValue="value"></s:radio>
											</div>
										</s:if>
										<s:else>
											<div id="otherid" style="float: left;display: none;">
												<s:radio list="#{0:'温度',1:'电池电压'}" name="othertype" listKey="key" listValue="value"></s:radio>
											</div>
										</s:else>
										
									</td>
								</tr>
								
								<tr>
									<td width="85">
										&nbsp;
									</td>
									<td height="50">
										<s:hidden name="lineid" value="%{#parameters.lineid}"></s:hidden>
										<s:hidden name="mapsize" value="%{#parameters.mapsize}"></s:hidden>
										<s:hidden name="lng" value="%{#parameters.lng}"></s:hidden>
										<s:hidden name="lat" value="%{#parameters.lat}"></s:hidden>
										<input name="input2" type="submit" class="scbtn" value="查询" />

										<input name="input3" type="reset" class="scbtn" value="重置" />
											<input name="input3" type="button" class="scbtn" value="返回地图" onclick="window.location.href='sensorAction!sensorrealtime?lineid=<s:property value="#parameters.lineid" />&mapsize=<s:property value="#parameters.mapsize" />&lng=<s:property value="#parameters.lng" />&lat=<s:property value="#parameters.lat" />'"/>
										
									</td>
								</tr>
								<tr>
									<td width="85">
										翻页：
									</td>
									<td height="50">
										<input name="pagebefore" type="button" class="scbtn" value="上页"  onclick="javascript:jumpListlinePage('sensordataAction!listline',<s:property value="sensor.id" />,-1,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="lineid" />,<s:property value="mapsize" />,'<s:property value="lng" />','<s:property value="lat" />');"/>
									
										<input name="pagenext" type="button" class="scbtn" value="下页"  onclick="javascript:jumpListlinePage('sensordataAction!listline',<s:property value="sensor.id" />,1,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="lineid" />,<s:property value="mapsize" />,'<s:property value="lng" />','<s:property value="lat" />');"/>

									</td>
								</tr>
							</table>
						</form>
						<hr>
</div>
	</body>
</html>
<script type="text/javascript">
function jumpListlinePage(url,sensorid,page,starttime,endtime,lineid,mapsize,lng,lat){
	
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?sensorid='+sensorid+'&page='+page+'&starttime='+starttime+'&endtime='+endtime+'&lineid='+lineid+'&mapsize='+mapsize+'&lng='+lng+'&lat='+lat;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}

        function browserRedirect() {
            var sUserAgent = navigator.userAgent.toLowerCase();
            var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
            var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
            var bIsMidp = sUserAgent.match(/midp/i) == "midp";
            var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
            var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
            var bIsAndroid = sUserAgent.match(/android/i) == "android";
            var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
            var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
            //document.writeln("您的浏览设备为：");
            if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {
                //alert("phone");
                var container = document.getElementById("container");
                container.style.height='200px';
            }
        }

        browserRedirect();
</script>