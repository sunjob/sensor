<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统管理</title>
<link rel="stylesheet" type="text/css" href="skin/css/base.css">
<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="js/pageKit.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	
	
	function changeConvalue(what){
				  $("#convalue").empty();//清空  
				  $.ajax({ 
					type:'get', 
					url:'getconvaluelist', 
					dataType: 'json', 
					data:'what='+what,
						success:function(data){ 
							var _tr = "";
							$.each(data,function(i,list){ 
								_tr += "["+list.id+","+list.cvalue+"]";
								$("#convalue").append("<option id='"+list.id+"' value='"+list.cvalue+"'>"+list.cvalue+"</option>");
							}) 
							console.log(_tr);
						} 
						
					})     	 
				}
				
	function loadwhat(what){
		$("#convalue").empty();//清空  
				  $.ajax({ 
					type:'get', 
					url:'getconvaluelist', 
					dataType: 'json', 
					data:'what='+what,
						success:function(data){ 
							var _tr = "";
							$.each(data,function(i,list){ 
								_tr += "["+list.id+","+list.cvalue+"]";
								if(list.cvalue=='<s:property value="convalue"/>'){
									$("#convalue").append("<option id='"+list.id+"' value='"+list.cvalue+"'  selected='selected'>"+list.cvalue+"</option>");
								}else{
									$("#convalue").append("<option id='"+list.id+"' value='"+list.cvalue+"'>"+list.cvalue+"</option>");
								}
								
							}) 
							console.log(_tr);
						} 
						
					}) 
		//document.getElementById('convalue').value='<s:property value="convalue"/>';
	}
</script>
</head>
<body leftmargin="8" topmargin="8" onload="loadwhat(document.getElementById('con').value);">
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：显示查询>>报表查询>>详细查询</div>

  
<!--  内容列表   -->
<form name="form2" action="sensordataAction!reportdetail" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="9" align="center">&nbsp;<b>报表详细查询</b>&nbsp;
	
	</td>
</tr>
<tr bgcolor="#E7E7E7">
  <td height="33" colspan="9" bgcolor="#FFFFE5"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="90%" align="left" style="padding-right: 50px;">
        					<s:hidden name="limits"></s:hidden>
							<s:hidden name="projectid"></s:hidden>
							<s:hidden name="linetext"></s:hidden>
							<s:hidden name="upuserid"></s:hidden>
        	开始时间：
        	<input type="text" name="starttime" value="<s:property value="starttime"/>" id="startdate" class="Wdate" style="width:160px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,maxDate:'#F{$dp.$D(\'enddate\')}'})"/>
        	结束时间：
          	<input type="text" name="endtime" value="<s:property value="endtime"/>" id="enddate" class="Wdate" style="width:160px;"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',readOnly:true,minDate:'#F{$dp.$D(\'startdate\')}',startDate:'#F{$dp.$D(\'startdate\',{d:+1})}'})"/>
        <br>
        
        
     	  <s:select list="#{0:'选择属性',1:'线路名称',2:'网关名称',3:'传感器编号'}" name="con" listKey="key" listValue="value" cssStyle="width:120px" onchange="changeConvalue(this.value)"></s:select>:
			<select id="convalue" name="convalue" style="width:180px;">
				<option value="未选择">未选择</option>
			</select>
			设备类别：
			<s:select list="#{0:'其他',1:'温度/电池电压',2:'压力',3:'流量',5:'表面温度'}" name="stype" cssStyle="width:150px;"></s:select>
			
		
             <input type="submit" class="coolbg np" value="查 询" />
    
        
             <input type="reset" class="coolbg np" value="重 置" />
             
             <input type="button" class="coolbg np" value="导出数据"  onClick="javascript:jumpReportExportPage('sensordataAction!reportdetailexport',document.getElementById('page').value,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="stype"/>,<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>,<s:property value="totalCount" />);"/>
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
	<td width="20%" align="center">时间</td>
  	<td width="10%" align="center">类型</td>
  	<td width="10%" align="center">数值</td>
  	<td width="10%" align="center">电池电压</td>
</tr>

	
   <s:if test="%{sensordatas.size()==0}">
					<td colspan="9" align="center">
						暂无该信息
					</td>
				</s:if>

<s:iterator value="sensordatas" var="sensordata" status="index">
	<tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
		<td><s:property value="#index.count"/></td>
		<td><s:property value="sensor.gateway.line.project.name"/></td>
		<td><s:property value="sensor.gateway.line.name"/></td>
		<td><s:property value="sensor.gateway.name"/></td>
		<td><s:property value="sensor.name"/></td>
	  	<td><s:date name="sdatetime" format="yyyy-MM-dd HH:mm:ss" /></td>
	    <td>
	    	<s:if test="stype==1">
	    		温度/电池电压
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
	    <td><s:property value="vdata"/> v</td>
	</tr>
</s:iterator>
	
<tr align="right" bgcolor="#EEF4EA">
  
  <td height="34" colspan="9" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpReportPage('sensordataAction!reportdetail',1,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="stype"/>,<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" target="rightFrame">首页</a>&nbsp;&nbsp;  
  <a href="javascript:jumpReportPage('sensordataAction!reportdetail',<s:property value="page-1"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="stype"/>,<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpReportPage('sensordataAction!reportdetail',<s:property value="page+1"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="stype"/>,<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpReportPage('sensordataAction!reportdetail',<s:property value="pageCount"/>,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="stype"/>,<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpReportPage('sensordataAction!reportdetail',document.getElementById('page').value,'<s:property value="starttime"/>','<s:property value="endtime"/>',<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="stype"/>,<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page" />" size="2" style="ime-mode=disabled;width:50px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount" />页</td>
  
</tr>
</table>

</form>

</body>
</html>