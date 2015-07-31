<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/pageKit.js"></script></head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：显示查询>>报警记录</div>

  
<!--  内容列表   -->
<form name="form2" action="alarmrecordAction!list" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="9" align="center">&nbsp;<b>报警记录</b>&nbsp;
	
	</td>
</tr>
<tr bgcolor="#E7E7E7">
  <td height="33" colspan="9" bgcolor="#FFFFE5"><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="23%" align="right">
        					<s:hidden name="limits"></s:hidden>
							<s:hidden name="projectid"></s:hidden>
							<s:hidden name="linetext"></s:hidden>
							<s:hidden name="upuserid"></s:hidden>
        	<input type="text" name="starttime" value="<s:property value='starttime'/>" id="startdate" class="Wdate" style="width:160px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,maxDate:'#F{$dp.$D(\'enddate\')}'})"/>&nbsp;至&nbsp;
        </td>
        <td width="22%">
          	<input type="text" name="endtime" value="<s:property value='endtime'/>" id="enddate" class="Wdate" style="width:160px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true,minDate:'#F{$dp.$D(\'startdate\')}',startDate:'#F{$dp.$D(\'startdate\',{d:+1})}'})"/>
        </td>
        
        <td width="5%">
             <input type="submit" class="coolbg np" value='查 询' />
    
        </td>
        <td width="5%">
             <input type="reset" class="coolbg np" value='重 置' />
        </td>
        <td width="5%">
             <input type="button" class="coolbg np" value="导出数据"  onClick="javascript:jumpAlarmRecordExportPage('alarmrecordAction!listExport',document.getElementById('page').value,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
        </td>
      </tr>
    </table>
    </td>
</tr>
<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="5%" align="center">序号</td>
  	<td width="10%" align="center">项目名称</td>
  	<td width="10%" align="center">线路名称</td>
  	<td width="10%" align="center">网关名称</td>
	<td width="10%" align="center">传感器编号</td>
	<td width="14%" align="center">报警时间</td>
  	<td width="10%" align="center">报警类型</td>
    <td width="5%" align="center">数值</td>
  	<td width="25%" align="center">发送手机</td>
</tr>

<s:if test="%{alarmrecords.size()==0}">
				<td colspan="9" align="center">
					暂无该信息
				</td>
	</s:if>	
   
<s:iterator value="alarmrecords" var="alarmrecord" status="index">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22"  style="background:<s:if test="sensor.status==2">red</s:if>">
		<td><s:property value="#index.count" /></td>
    	<td><s:property value="sensor.gateway.line.project.name" /></td>
    	<td><s:property value="sensor.gateway.line.name" /></td>
    	<td><s:property value="sensor.gateway.name" /></td>
		<td><s:property value="sensor.name" /></td>
	  	<td><s:date name="alarmtime" format="yyyy-MM-dd HH:mm:ss"/></td>
	    <td>
	    	<s:if test="alarmtype==1">温度/电压</s:if>
	    	<s:elseif test="alarmtype==2">压力</s:elseif>
	    	<s:elseif test="alarmtype==3">流量</s:elseif>
	    	<s:elseif test="alarmtype==5">表面温度</s:elseif>
	    	<s:else>
	    	其他
	    	</s:else>
	    </td>
      	<td><s:property value="alarmdata" /></td>
	    <td><s:property value="phones" /></td>
	    
	</tr>
</s:iterator>
	
<tr align="right" bgcolor="#EEF4EA">
  
  <td height="34" colspan="9" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpAlarmRecordPage('alarmrecordAction!list',1,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" target="rightFrame">首页</a>&nbsp;&nbsp;  
  <a href="javascript:jumpAlarmRecordPage('alarmrecordAction!list',<s:property value="page-1"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpAlarmRecordPage('alarmrecordAction!list',<s:property value="page+1"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpAlarmRecordPage('alarmrecordAction!list',<s:property value="pageCount"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpAlarmRecordPage('alarmrecordAction!list',document.getElementById('page').value,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="1" size="2" style="ime-mode=disabled;width:25px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共1页</td>
  
</tr>
</table>

</form>

</body>
</html>