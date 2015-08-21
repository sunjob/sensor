
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

<script type="text/javascript" src="js/pageKit.js"></script>
<script language="JavaScript" src="js/jquery.js"></script>
<script type="text/javascript">
				$(function(){
					setInterval("ShowGatewayStatus()",5000);
				});
				function ShowGatewayStatus(){
				  
				  $.ajax({ 
					type:'get', 
					url:'gatewayStatus', 
					dataType: 'json', 
					success:function(data){ 
						var _tr = "";
						$.each(data,function(i,list){ 
							_tr += "["+list.gatewayid+","+list.gatewayname+","+list.status+"]";
							var gatewayid = ""+list.gatewayid;
							document.getElementById(gatewayid).innerHTML=list.status==1?'正常':'断开';
						}) 
						//$(".menuson").html(_tr);
						console.log(_tr);
						} 
						
					})     	 
				}


</script>
</head>
<body leftmargin="8" topmargin="8" >
<div class="linedwon"><img src="skin/images/frame/jiantou.gif" width="20" height="20" border="0">当前位置：系统管理>>网关管理</div>

  
<!--  内容列表   -->
<form name="form2" action="gatewayAction!list" method="post">

<table width="98%" border="0" cellpadding="2" cellspacing="1" bgcolor="#D1DDAA" align="center" style="margin-top:8px">
<tr bgcolor="#E7E7E7">
	<td height="33" colspan="13" align="center">&nbsp;<b>网关管理</b>&nbsp;
    <!--
<input type="hidden" name="publicaccount" value="" id="publicaccount"/>
  -->
	
	</td>
</tr>
<tr bgcolor="#E7E7E7">
  <td height="33" colspan="13" bgcolor="#FFFFE5"><table width="60%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td width="15%" align="right" style="padding-right: 50px;">

         <s:select list="#{0:'选择类型',1:'所属线路',2:'网关名称',3:'网关地址',4:'网关硬件UID',5:'无线数据通道',6:'手机号码'}" name="con" listKey="key" listValue="value" cssStyle="width:120px"></s:select>
          
        </td>
        <td width="35%">

          <input type="text" name="convalue" value="" id="convalue" style="width:80%"/>
      
			
		</td>
        <td width="5%">
             <input type="submit" class="coolbg np" onClick="" value='查 询' />
    
        </td>
        <td width="5%">
             <input type="reset" class="coolbg np" onClick="" value='重 置' />
        </td>
      </tr>
    </table>
    </td>
</tr>
<tr align="center" bgcolor="#FAFAF1" height="22">
	<td width="5%" align="center">序号</td>
 	<td width="15%" align="center">所属项目</td>
  	<td width="8%" align="center">所属线路</td>
	<td width="5%" align="center">网关名称</td>
    <td width="8%" align="center">网关地址</td>
	<td width="15%" align="center">网关硬件UID</td>
	<td width="5%" align="center">数据通道</td>
	<td width="8%" align="center">手机卡号</td>
	<td width="5%" align="center">街景图片</td>
	<td width="5%" align="center">状态</td>
	<td width="5%" align="center">发送命令</td>
	<td width="5%" align="center">修改</td>
	<td width="5%" align="center">删除</td>
	</tr>

	
  
	<s:if test="%{gateways.size()==0}">
				<td colspan="13" align="center">
					暂无该信息
				</td>
			</s:if>
  
<s:iterator value="gateways" var="gateway" status="status">
	
  <tr align='center' bgcolor="#FFFFFF" onMouseMove="javascript:this.bgColor='#FCFDEE';" onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22" >
    <td><s:property value="#status.count"/></td>
    <td><s:property value="line.project.name" /></td>
    <td><s:property value="line.name" /></td>
     <td><a href="gatewayAction!view?id=<s:property value="id" />"><s:property value="name" /></a></td>
       <td><s:property value="gateaddress" /></td>
          <td><s:property value="macaddress" /></td>
          <td><a href="gatewayAction!loadchannel?id=<s:property value="id" />" title="配置通道"><s:property value="channel" /></a></td>
            <td><s:property value="phonenumber" /></td>
    <td><input type="button" value="查看" onclick="window.location.href='gatewayAction!showStreetpic?id=<s:property value="id" />'"/></td>
    
    
    <td id="<s:property value="id" />">
    -
    
    </td>
    <td><a href="gatewayAction!goToCommandSend?id=<s:property value="id" />"><img src="skin/images/frame/huiwu_5.gif" width="20" height="20" border="0"></a></td>
    
    <s:if test="gateaddress<247&&gateaddress>0&&gateaddress!=null">
    	<td><a href="gatewayAction!load?id=<s:property value="id" />"><img src="skin/images/frame/huiwu_3.gif" width="20" height="20" border="0"></a></td>
   </s:if>
   <s:else>
   		<td><a href="gatewayAction!load?id=<s:property value="id" />"><img src="skin/images/frame/redpen.gif" width="20" height="20" border="0"></a></td>
   </s:else>
    <td><a href="gatewayAction!delete?id=<s:property value="id" />" onclick="return confirm('网关下所有传感器及测试数据都将被删除，你确定删除该信息吗？')"><img src="skin/images/frame/huiwu_2.gif" width="20" height="20" border="0"></a></td>
  </tr>
</s:iterator>

<tr align="right" bgcolor="#EEF4EA">
  
 <td height="34" colspan="13" align="center">记录数：<s:property value="totalCount" />
  &nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('gatewayAction!list',1,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">首页</a>&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('gatewayAction!list',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">上一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('gatewayAction!list',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">下一页</a>&nbsp;&nbsp;&nbsp; 
  <a href="javascript:jumpLinePage('gatewayAction!list',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>');" target="rightFrame">尾页</a>&nbsp;&nbsp;&nbsp;
  <input type='button' class="coolbg np" onClick="javascript:jumpLinePage('gatewayAction!list',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>');" value='转到' />
&nbsp;
当前页：
<input onpaste="return false" onKeyPress="checkPage();" id="page" type="text" name="page" value="<s:property value="page"/>" size="2" style="ime-mode=disabled;width:50px; height:20px;line-height:18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;"/>
/共<s:property value="pageCount"/>页</td>
  
  
</tr>
</table>

</form>

</body>
</html>