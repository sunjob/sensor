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
		<title>最新传感器数据折线图</title>

		<script type="text/javascript" src="http://cdn.hcharts.cn/jquery/jquery-1.8.2.min.js"></script>
		<style type="text/css">
${demo.css}
		</style>
		<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript">
		//获取后台的数据列表
	var i=<s:property value="sensors.size()" />;
	if(i==0){
		alert('暂无数据');
	}
	var sensordatalist = new Array(i);
	var sensordataTimelist = new Array(i);
	//alert(sensordatalist.length);
	var linename='';
	<s:iterator value="sensors" var="sensor" status="index">
		if(linename==''){
			linename = '<s:property value="gateway.line.name" />';
		}
		<s:if test="name==null||name==''">
			sensordataTimelist[<s:property value="#index.count-1" />] = '未知名称-传感器地址:'+<s:property value="sensoraddress" />;
		</s:if>
		<s:else>
			sensordataTimelist[<s:property value="#index.count-1" />] = '<s:property value="name" />';
		</s:else>
		sensordatalist[<s:property value="#index.count-1" />] = <s:property value="nowtemp" />;
	</s:iterator>
		
		
$(function () {
    $('#container').highcharts({
        chart: {
            zoomType: 'x',
            spacingRight: 20
        },
        title: {
            text: linename+'-最新温度折线图'
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

<div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>

	</body>
</html>
