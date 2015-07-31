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
			<script type="text/javascript">
				function checkform(){
					var linename = document.getElementById("linename");
					if(linename.value.length==0){
						alert("线路名称不能为空");
						linename.focus();
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
				<form action="lineAction!monitorshowformobile" method="post" onsubmit="return checkform();">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="60%" align="right" style="padding-right:20px;">
							<s:hidden name="limits"></s:hidden>
							<s:hidden name="projectid"></s:hidden>
							<s:hidden name="linetext"></s:hidden>
							<s:hidden name="upuserid"></s:hidden>
							<s:hidden name="con" value="1"></s:hidden>
							<s:textfield name="convalue" cssStyle="height: 26px;width: 150px;line-height:25px;" id="linename" placeholder="线路名称"/>
						</td>
						<td width="40%" align="left" style="padding-left:10px;">
							<input type="submit" value="查询"  style="height: 32px;width: 100px;"/>
						</td>
					</tr>
				</table>
				</form>
			</div>
			<div style="width: 100%;" id="temp">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<th>
							序号
						</th>
						<th>
							所属项目
						</th>
						<th>
							线路名称
						</th>
						<th>
							查看地图
						</th>
					</tr>
					<s:if test="%{lines.size()==0}">
						<td colspan="4" align="center">
							暂无该信息
						</td>
					</s:if>
					<s:iterator value="lines" var="line" status="status">
					<tr>
						<td>
							<s:property value="#status.count" />
						</td>
						<td>
							<s:property value="project.name" />
						</td>
						<td>
							<s:property value="name" />
						</td>
						<td>
							<a href="sensorAction!sensorrealtime?lineid=<s:property value="id" />">查看</a>

						</td>
					</tr>
					</s:iterator>
					
				</table>
			</div>
			
			<div style="width: 100%;" id="pageshow">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>
							<input type="button" value="首页" style="height: 32px;width: 60px;" onClick="javascript:jumpYoukeLinePage('lineAction!monitorshowformobile',1,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							&nbsp;
							<input type="button" value="上页"  style="height: 32px;width: 60px;"  onClick="javascript:jumpYoukeLinePage('lineAction!monitorshowformobile',<s:property value="page-1" />,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							&nbsp;
							<input type="submit" value="下页"  style="height: 32px;width: 60px;" onClick="javascript:jumpYoukeLinePage('lineAction!monitorshowformobile',<s:property value="page+1" />,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							&nbsp;
							<input type="submit" value="尾页"  style="height: 32px;width: 60px;" onClick="javascript:jumpYoukeLinePage('lineAction!monitorshowformobile',<s:property value="pageCount" />,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"/>
							
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
	function jumpYoukeLinePage(url,page,con,convalue,limits,projectid,linetext,upuserid){
	
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&con='+con+'&convalue='+convalue+'&limits='+limits+'&projectid='+projectid+'&linetext='+linetext+'&upuserid='+upuserid;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}

</script>