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
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
		function test(){
		var tid = $("#linetower").val();
	       $.ajax({   
	            url:'getTid',//这里是你的action或者servlert的路径地址   
	            type:'post', //数据发送方式   
	            dataType:'json',
	            data: { "tid":tid},
	            error: function(msg)
	            { //失败   
	            	console.log('post失败');   
	            },   
	            success: function(msg)
	            { //成功
	            	return true;
		        }
    	    });  
 		}
	function getTower()
	{
		var linetowers = [];
	    var id = ${initlineid};
		$.ajax({   
	            url:'getLineTowers',//这里是你的action或者servlert的路径地址   
	            type:'post', //数据发送方式   
	            dataType:'json',
	            data: { "lineid":id},
	            error: function(msg)
	            { //失败   
	            	console.log('post失败');   
	            },   
	            success: function(msg)
	            { //成功
	            	$("#linetower option").remove();
	            	linetowers = msg;
	            	
	            	for(var i=0;i<linetowers.length;i++)
	            	{
	            		 $("#linetower").append("<option value=" + linetowers[i].id + ">" + linetowers[i].name + "</option>");
	            	}

		        }
    	    });  
    	    
	}	
		
	function changeTower()
	{
		var linetowers = [];
	    var id = $("#lineid").val();
		$.ajax({   
	            url:'getLineTowers',//这里是你的action或者servlert的路径地址   
	            type:'post', //数据发送方式   
	            dataType:'json',
	            data: { "lineid":id},
	            error: function(msg)
	            { //失败   
	            	console.log('post失败');   
	            },   
	            success: function(msg)
	            { //成功
	            	$("#linetower option").remove();
	            	linetowers = msg;
	            	
	            	for(var i=0;i<linetowers.length;i++)
	            	{
	            		 $("#linetower").append("<option value=" + linetowers[i].id + ">" + linetowers[i].name + "</option>");
	            	}

		        }
    	    });  
	}
	
	
</script>
	</head>
	<body leftmargin="8" topmargin="8" onload="getTower()">
		<div class="linedwon">
			<img src="skin/images/frame/jiantou.gif" width="20" height="20"
				border="0">
			当前位置：系统管理&gt;&gt;
			<a href='javascript:history.back();' target='main'>测温点管理</a>&gt;&gt;新增测温点&nbsp;
			<a href="javascript:history.back();" style="color: red;">[返回]</a>
		</div>

		<!--  内容列表   -->
		<form name="form2" action="temppointAction!add" method="post"
			enctype="multipart/form-data" >

			<table width="50%" border="0" cellpadding="2" cellspacing="1"
				bgcolor="#D1DDAA" align="center" style="margin-top: 8px">
				<tr bgcolor="#E7E7E7">
					<td height="33" colspan="2" align="center">
						<strong>新增测温点</strong>
					</td>
				</tr>


				<tr align="center" bgcolor="#FFFFFF"
					onMouseMove="javascript:this.bgColor='#FCFDEE';"
					onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25">
					<td width="25%" height="25" align="right">
						<strong><font color="#333333">测温点名称</font>
						</strong>
					</td>
					<td width="75%" align="left">
						<label>

							<s:textfield name="temppoint.name" cssStyle="width:80%"></s:textfield>


						</label>
					</td>
				</tr>

				<tr align='center' bgcolor="#FFFFFF"
					onMouseMove="javascript:this.bgColor='#FCFDEE';"
					onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25">
					<td height="25" align="right">
						<strong><font color="#333333">线路</font>
						</strong>
					</td>
					<td align="left">


						<s:select list="lines" name="temppoint.tower.line.id" id="lineid"
							listKey="id" listValue="name"  onchange="changeTower()"></s:select>
						 

					</td>
				</tr>

				<tr align="center" bgcolor="#FFFFFF"
					onMouseMove="javascript:this.bgColor='#FCFDEE';"
					onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25">
					<td width="25%" height="25" align="right">
						<strong><font color="#333333">杆塔</font>
						</strong>
					</td>
					<td width="75%" align="left">
						<label>
							<select name="linetower" id="linetower">
							</select>	
						</label>
					</td>
				</tr>

				<tr align="center" bgcolor="#FFFFFF"
					onMouseMove="javascript:this.bgColor='#FCFDEE';"
					onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25">
					<td width="25%" height="25" align="right">
						<strong><font color="#333333">相位</font>
						</strong>
					</td>
					<td width="75%" align="left">
						<label>
							<!--
<s:textfield name="bigtype.name" cssStyle="width:80%"></s:textfield>
    -->
    <s:select list="#{1:'A1相',2:'A2相',3:'B1相',4:'B2相',5:'C1相',6:'C2相',0:'未知相位'}" name="temppoint.phasetype" listKey="key" listValue="value"></s:select>
							
						</label>
					</td>
				</tr>

				<tr align="center" bgcolor="#FFFFFF"
					onMouseMove="javascript:this.bgColor='#FCFDEE';"
					onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25">
					<td width="25%" height="25" align="right">
						<strong><font color="#333333">侧位</font>
						</strong>
					</td>
					<td width="75%" align="left">
						<label>
    <s:select list="#{1:'电源侧',2:'负荷侧',0:'未知侧位'}" name="temppoint.sidetype" listKey="key" listValue="value"></s:select>
							
						</label>
					</td>
				</tr>

	<tr align="center" bgcolor="#FFFFFF"
					onMouseMove="javascript:this.bgColor='#FCFDEE';"
					onMouseOut="javascript:this.bgColor='#FFFFFF';" height="25">
					<td width="25%" height="25" align="right">
						<strong><font color="#333333">排序编号</font>
						</strong>
					</td>
					<td width="75%" align="left">
						<label>
							<s:textfield name="temppoint.orderid" cssStyle="width:80%" value="0"></s:textfield>
						</label>
					</td>
				</tr>

				
			</table>

			<table width="98%" border="0" cellspacing="0" cellpadding="0"
				align="center">
				<tr>
					<td height="29" align="center" valign="bottom">
						<s:token></s:token>
						<input type='submit' class="coolbg np" onClick="" value='保存'
							style="width: 80" />
						&nbsp;&nbsp;
						<input type='reset' class="coolbg np" onClick="" value='重置'
							style="width: 80" />
						&nbsp;&nbsp;
						<input type='button' class="coolbg np"
							onClick="javascript:history.back();" value='返回' style="width: 80" />
						&nbsp;&nbsp;
					</td>
				</tr>
				<tr>
					<td height="18" align="center">
						&nbsp;
					</td>
				</tr>
			</table>


		</form>

	</body>
</html>