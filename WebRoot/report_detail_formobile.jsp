<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta name="viewport"
			content="width=device-width, minimum-scale=1, maximum-scale=1" />
			<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
			<script type="text/javascript">
				function checkform(){
					var selectvalue = document.getElementById("selectvalue");
					var attrivalue = document.getElementById("attrivalue");
					var startdate = document.getElementById("startdate");
					var enddate = document.getElementById("enddate");
					if(selectvalue.value==0){
						alert("属性未选择");
						selectvalue.focus();
						return false;
					}
					if(attrivalue.value.length==0){
						alert("属性值不能为空");
						attrivalue.focus();
						return false;
					}
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
	border: 1px solid #F00;
}

#temp table td {
	border: 1px solid #F00;
	text-align: center;
}
#temp table tr {
	height: 45px;
}
#temp table th {
	border: 1px solid #F00;
	text-align: center;
	background-color: #E7E7E7;
	
	
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
				<form action="sensordataAction!reportdetailformobile" method="post" onsubmit="return checkform();">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-bottom: 10px;">
					<tr height="40px">
						<td width="40%">
							<s:hidden name="limits"></s:hidden>
							<s:hidden name="projectid"></s:hidden>
							<s:hidden name="linetext"></s:hidden>
							<s:hidden name="upuserid"></s:hidden>
						
							<s:select list="#{0:'选择属性',1:'线路名称',2:'网关名称',3:'传感器编号'}" name="con" id="selectvalue" listKey="key" listValue="value" cssStyle="height: 30px;width:80%"></s:select>:
						</td>
						<td width="40%" >	
							<s:textfield name="convalue" cssStyle="height: 26px;width:80%;line-height:25px;" id="attrivalue" placeholder="属性值"></s:textfield>
						</td>
						<td width="20%">
						<!-- 
							<input type="submit" value="查询"  style="height: 32px;width: 60px;"/>
							 -->
						</td>
					</tr>
					<tr>
						<td width="40%">
						
							<input type="text"  name="starttime" value="<s:property value="starttime"/>" style="height: 30px;width: 80%;" id="startdate" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,maxDate:'#F{$dp.$D(\'enddate\')}'})"/>
						</td>
						<td width="40%" >	
							<input type="text"  name="endtime" value="<s:property value="endtime"/>" style="height: 30px;width: 80%;" id="enddate" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'#F{$dp.$D(\'startdate\')}',startDate:'#F{$dp.$D(\'startdate\',{d:+1})}'})"/>
						</td>
						<td width="20%">
							<input type="submit" value="查询"  style="height: 32px;width: 60px;"/>
						</td>
					</tr>
				</table>
				</form>
			</div>
			<div style="width: 100%;" id="temp">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th>
							所属项目
						</th>
						<th>
							所属线路
						</th>
						<th>
							所属网关
						</th>
						<th>
							传感器编号
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
					 <s:if test="%{sensordatas.size()==0}">
						<td colspan="7" align="center">
							暂无该信息
						</td>
					</s:if>
					
					<s:iterator value="sensordatas" var="sensordata" status="index">
					<tr>
						<td><s:property value="sensor.gateway.line.project.name"/></td>
						<td><s:property value="sensor.gateway.line.name"/></td>
						<td><s:property value="sensor.gateway.name"/></td>
						<td><s:property value="sensor.name"/></td>
					  	<td><s:date name="sdatetime" format="yyyy-MM-dd HH:mm:ss" /></td>
					    <td>
					    	<s:if test="stype==1">
					    		温度
					    	</s:if>
					    	<s:elseif test="stype==2">
					    		压力
					    	</s:elseif>
							<s:elseif test="stype==3">
					    		流量
					    	</s:elseif>
					    	<s:elseif test="stype==5">
					    		表面温度
					    	</s:elseif>
					    	<s:else>
					    		其他
					    	</s:else>
					    </td>
					    <td>
					    	<s:property value="sdata"/>
					    	<s:if test="stype==1">
					    		度
					    	</s:if>
					    </td>
					</tr>
					</s:iterator>
				</table>
			</div>
			
			<div style="width: 100%;" id="pageshow">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<input type="button" value="首页" style="height: 32px;width: 60px;" onClick="javascript:jumpReportPage('sensordataAction!reportdetailformobile',1,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="stype"/>,<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							&nbsp;
							<input type="button" value="上页"  style="height: 32px;width: 60px;" onClick="javascript:jumpReportPage('sensordataAction!reportdetailformobile',<s:property value="page-1"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="stype"/>,<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							&nbsp;
							<input type="submit" value="下页"  style="height: 32px;width: 60px;" onClick="javascript:jumpReportPage('sensordataAction!reportdetailformobile',<s:property value="page+1"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="stype"/>,<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							&nbsp;
							<input type="submit" value="尾页"  style="height: 32px;width: 60px;" onClick="javascript:jumpReportPage('sensordataAction!reportdetailformobile',<s:property value="pageCount"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="stype"/>,<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							
						</td>
					</tr>
					<tr>
						<td>
							当前页<s:property value="page" />/共<s:property value="pageCount" />页
						</td>
					</tr>
				</table>
				
			</div>
			
		</div>
	</body>
</html>
<script type="text/javascript">
//报表详细页分页查询
function jumpReportPage(url,page,starttime,endtime,con,convalue,stype,limits,projectid,linetext,upuserid){
	
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&starttime='+starttime+'&endtime='+endtime+'&con='+con+'&convalue='+convalue+'&stype='+stype+'&limits='+limits+'&projectid='+projectid+'&linetext='+linetext+'&upuserid='+upuserid;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}
</script>
