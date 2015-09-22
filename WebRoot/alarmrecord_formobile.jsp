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
		<meta name="viewport"
			content="width=device-width, minimum-scale=1, maximum-scale=1" />
			<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
			<script type="text/javascript">
				function checkform(){
					var startdate = document.getElementById("startdate");
					var enddate = document.getElementById("enddate");
					if(startdate.value.length==0){
						alert("开始时间不能为空");
						startdate.focus();
						return false;
					}
					if(enddate.value.length==0){
						alert("结束时间不能为空");
						enddate.focus();
						return false;
					}
					return true;
				}
			</script>
		<style type="text/css">
#temp table {
	border: 1px solid #FFF;
}

#temp table td {
	border: 1px solid #FFF;
	text-align: center;
}
#temp table tr {
	height: 45px;
}
#temp table th {
	border: 1px solid #FFF;
	text-align: center;
	background-color: #a0a0a0;
	
	
}
#query table {
	text-align: center;
	height: 50px;
}
#pageshow table {
	text-align: center;
	height: 50px;
	margin: 5px;
}
</style>
	</head>
	<body>
		<div style="width: 100%;margin: 0px;padding: 0px;">
			<div style="width: 100%;" id="query">
				<form action="alarmrecordAction!alarmrecordformobile" method="post" onsubmit="return checkform();" id="aform">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-bottom: 10px;">
					<tr height="40px">
						<td width="35%">
							<s:hidden name="limits"></s:hidden>
							<s:hidden name="projectid"></s:hidden>
							<s:hidden name="linetext"></s:hidden>
							<s:hidden name="upuserid"></s:hidden>
						
							<s:select list="#{0:'选择属性',1:'线路',2:'网关',3:'传感器'}" name="con" id="selectvalue" listKey="key" listValue="value" cssStyle="height: 30px;width:80%"></s:select>:
						</td>
						<td width="65%" colspan="2" align="left">	
							<s:textfield name="convalue" cssStyle="height: 26px;width:80%;line-height:25px;" id="attrivalue" placeholder="属性值"></s:textfield>
						</td>
						
					</tr>
					<tr>
						<td width="35%">
							<input type="text"  name="starttime" value="<s:property value='starttime'/>"  style="height: 30px;width: 80%;" id="startdate" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'enddate\')}'})"/>
						</td>
						<td width="35%" align="left">
							<input type="text"  name="endtime" value="<s:property value='endtime'/>"  style="height: 30px;width: 80%;" id="enddate" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startdate\')}',startDate:'#F{$dp.$D(\'startdate\',{d:+1})}'})"/>
						</td>
						<td width="30%">
						<a href="#" onclick="javascript:document.getElementById('aform').submit();"><img src="img/chaxun1.png" width="90" height="35"/></a>
						<!-- 
						<input type="submit" value="查询"  style="height: 32px;width: 60px;"/>
						 -->
							
						</td>
					</tr>
				</table>
				</form>
			</div>
			<div style="width: 100%;" id="temp">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th>
							项目
						</th>
						<th>
							线路
						</th>
						<th>
							网关
						</th>
						<th>
							传感器
						</th>
						<th>
							日期
						</th>
						
						<th>
							类型
						</th>
						<th>
							数值
						</th>
						
					</tr>
					<s:if test="%{alarmrecords.size()==0}">
								<td colspan="8" align="center">
									暂无该信息
								</td>
					</s:if>	
					<s:iterator value="alarmrecords" var="alarmrecord" status="status">
					<tr style="
					background-color:
					<s:if test="sensor.status==2">#FF0000;</s:if>
					<s:elseif test="#status.count%2==0">#d0d8e8;</s:elseif>
					<s:else>#e9edf4;</s:else> 
					">
						<td><s:property value="sensor.gateway.line.project.name" /></td>
						<td><s:property value="sensor.gateway.line.name" /></td>
						<td><s:property value="sensor.gateway.name" /></td>
						<td><s:property value="sensor.name" /></td>
						<td><s:date name="alarmtime" format="yyyy-MM-dd HH:mm:ss"/></td>
						
					    <td>
					    	<s:if test="alarmtype==1">温度</s:if>
					    	<s:elseif test="alarmtype==2">压力</s:elseif>
					    	<s:elseif test="alarmtype==3">流量</s:elseif>
					    	<s:elseif test="alarmtype==4">电池电压</s:elseif>
					    	<s:elseif test="alarmtype==5">表面温度</s:elseif>
					    	<s:else>
					    	其他
					    	</s:else>
					    </td>
				      	<td>
				      	<s:property value="alarmdata" />
				      	<s:if test="alarmtype==1">度</s:if>
				      	</td>
					</tr>
					</s:iterator>
				</table>
			</div>
			
			<div style="width: 100%;" id="pageshow">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					<!-- 
						<td>
							<input type="button" value="首页" style="height: 32px;width: 60px;" onClick="javascript:jumpAlarmRecordPage('alarmrecordAction!alarmrecordformobile',1,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							&nbsp;
							<input type="button" value="上页"  style="height: 32px;width: 60px;" onClick="javascript:jumpAlarmRecordPage('alarmrecordAction!alarmrecordformobile',<s:property value="page-1"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							&nbsp;
							<input type="submit" value="下页"  style="height: 32px;width: 60px;" onClick="javascript:jumpAlarmRecordPage('alarmrecordAction!alarmrecordformobile',<s:property value="page+1"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							&nbsp;
							<input type="submit" value="尾页"  style="height: 32px;width: 60px;" onClick="javascript:jumpAlarmRecordPage('alarmrecordAction!alarmrecordformobile',<s:property value="pageCount"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							
						</td>
					 -->
					 <td style="text-align: center;">
							<a href="#" onClick="javascript:jumpAlarmRecordPage('alarmrecordAction!alarmrecordformobile',1,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"><img src="img/shouye1.png" width="35" height="30" style="vertical-align: middle;"/></a>
							&nbsp;
							<a href="#" onClick="javascript:jumpAlarmRecordPage('alarmrecordAction!alarmrecordformobile',<s:property value="page-1"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"><img src="img/shangyiye1.png" width="35" height="30" style="vertical-align: middle;"/></a>
							&nbsp;
							<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page" />" size="2" style="ime-mode=disabled;width:50px; height:25px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 16px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;vertical-align: baseline;"/>
							<input type='button' class="coolbg np" onClick="javascript:jumpAlarmRecordPage('alarmrecordAction!alarmrecordformobile',document.getElementById('page').value,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" value='GO' width="30" height="30" style="vertical-align: middle;"/>
							&nbsp;
							<a href="#" onClick="javascript:jumpAlarmRecordPage('alarmrecordAction!alarmrecordformobile',<s:property value="page+1"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"><img src="img/xiayiye1.png" width="35" height="30" style="vertical-align: middle;"/></a>
							&nbsp;
							<a href="#" onClick="javascript:jumpAlarmRecordPage('alarmrecordAction!alarmrecordformobile',<s:property value="pageCount"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"><img src="img/weiye1.png" width="35" height="30" style="vertical-align: middle;"/></a>
							
						</td>
					</tr>
					<!-- 
					<tr>
						<td>
							当前页<s:property value="page"/>/共<s:property value="pageCount"/>页
						</td>
					</tr>
					 -->
				</table>
				
			</div>
			
		</div>
	</body>
</html>
<script type="text/javascript">
function jumpAlarmRecordPage(url,page,starttime,endtime,con,convalue,limits,projectid,linetext,upuserid){
	
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&starttime='+starttime+'&endtime='+endtime+'&con='+con+'&convalue='+convalue+'&limits='+limits+'&projectid='+projectid+'&linetext='+linetext+'&upuserid='+upuserid;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}

</script>