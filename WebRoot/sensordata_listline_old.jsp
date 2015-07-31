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
	if(i>50){
		alert('请选择时间段查询');
	}
	var sensordatalist = new Array(i);
	var sensordataTimelist = new Array(i);
	//alert(sensordatalist.length);
	<s:iterator value="sensordatas" var="sensordata" status="index">
		sensordataTimelist[<s:property value="#index.count-1" />] = '<s:date name="sdatetime" format="yyyy-MM-dd HH:mm:ss"/>';
		sensordatalist[<s:property value="#index.count-1" />] = <s:property value="sdata" />;
	</s:iterator>
		
		
$(function () {
    $('#container').highcharts({
        chart: {
            zoomType: 'x',
            spacingRight: 20
        },
        title: {
            text: '<s:property value="sensor.gateway.line.name" />-<s:property value="sensor.gateway.name" />-<s:property value="sensor.name" />-温度折线图'
        },
        /*
        subtitle: {
            text: document.ontouchstart === undefined ?
                '点击或拖拽' :
                '捏起缩小'
        },
        */
        xAxis: {
            type: 'datetime',
            //minZoom:  24*3600000,
            //maxZoom:  24*3600000, // fourteen days
            //tickInterval : 1 * 3600 * 1000,
            //categories:['2015-06-16 12:12:20','2015-06-16 12:12:30','2015-06-16 12:12:40','2015-06-16 12:12:50','2015-06-16 12:13:00','2015-06-16 12:13:10','2015-06-16 12:13:20','2015-06-16 12:13:30'],
			//categories:['2015-06-16 12:12:20','2015-06-16 12:12:30','2015-06-16 12:12:40','2015-06-16 12:12:50','2015-06-16 12:13:00','2015-06-16 12:13:10','2015-06-16 12:13:20','2015-06-16 12:13:30'],
            categories:sensordataTimelist,
            //自定义x刻度上显示的时间格式，根据间隔大小，以下面预设的小时/分钟/日的格式来显示
            /*
            dateTimeLabelFormats:
				{
					second: '%H:%M:%S',
					minute: '%H:%M',
					hour: '%H:%M',
					day: '%e日/%b',
					week: '%e. %b',
					month: '%b %y',
					year: '%Y'
				},
            */
            /*
            dateTimeLabelFormats:{
                millisecond: '%H:%M:%S',
                second: "%H:%M:%S",
                minute: "%Y-%m-%e %H:%M",
                hour:"%Y-%m-%e %H:%M",
                day:"%Y-%m-%e",
                month:"%Y-%m",
                year: '%Y'
            },
            */
            dateTimeLabelFormats:{
                millisecond: '%H:%M:%S',
                second: "%H:%M:%S",
                minute: "%H:%M:%S",
                hour:"%H:%M:%S",
                day:"%Y-%m-%e",
                month:"%Y-%m",
                year: '%Y'
            },
            title: {
                text: null
            }

        },
        yAxis: {
            title: {
                text: '温度值'
            }
        },
        tooltip: {
            shared: true
        },
        legend: {
            enabled: false
        },
        /*
        plotOptions: {
            area: {
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                lineWidth: 1,
                marker: {
                    enabled: false
                },
                shadow: false,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },
        */
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
            name: '温度值',
            //pointInterval: 1*3600 * 1000,
            //pointStart: Date.UTC(2015, 5, 16),
            //data: [
            //    46, 84, 44, 51, 18, 64, 58,32
            //]
            data: sensordatalist
        }]
    });
});				
		</script>
	</head>
	<body>
<script src="js/highcharts.js"></script>
<script src="js/modules/exporting.js"></script>
<div style="margin-left: 50px;">
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
									<input name="starttime" id="startdate" value="<s:property value="starttime"/>" style="width: 150px;" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,maxDate:'#F{$dp.$D(\'enddate\')}'})" />
									至
									<input name="endtime" id="enddate" value="<s:property value="endtime"/>" style="width: 150px;" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:'#F{$dp.$D(\'startdate\')}',startDate:'#F{$dp.$D(\'startdate\',{d:+1})}'})" />
									</td>
								</tr>
							
								<tr>
									<td width="85">
										&nbsp;
									</td>
									<td height="50">
										<input name="input2" type="submit" class="scbtn" value="查询" />

										<input name="input3" type="reset" class="scbtn" value="重置" />
									</td>
								</tr>
							</table>
						</form>
						<hr>
</div>
<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

	</body>
</html>
