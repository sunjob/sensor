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

		<script type="text/javascript" src="js/pageKit.js"></script>
	</head>
	<body leftmargin="8" topmargin="8">
		<div class="linedwon">
			<img src="skin/images/frame/jiantou.gif" width="20" height="20"
				border="0">
			当前位置：显示查询>>监控显示
		</div>


		<!--  内容列表   -->
		<form name="form2" action="lineAction!monitorshow" method="post">

			<table width="98%" border="0" cellpadding="2" cellspacing="1"
				bgcolor="#D1DDAA" align="center" style="margin-top: 8px">
				<tr bgcolor="#E7E7E7">
					<td height="33" colspan="4" align="center">
						&nbsp;
						<b>监控显示</b>&nbsp;

					</td>
				</tr>
				<tr bgcolor="#E7E7E7">
					<td height="33" colspan="4" bgcolor="#FFFFE5">
						<table width="60%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="15%" align="right" style="padding-right: 20px;">
									<s:hidden name="limits"></s:hidden>
									<s:hidden name="projectid"></s:hidden>
									<s:hidden name="linetext"></s:hidden>
									<s:hidden name="upuserid"></s:hidden>
									<!-- 
									<s:select list="#{0:'选择类型',1:'线路名称'}" name="con" listKey="key"
										listValue="value" cssStyle="width:120px"></s:select>
										 -->
										 线路名称：
										 <s:hidden name="con" value="1"></s:hidden>
								</td>
								<td width="25%">
									<s:textfield name="convalue" cssStyle="width:80%"></s:textfield>

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
					<td width="5%" align="center">
						序号
					</td>
					<td width="30%" align="center">
						所属项目
					</td>
					<td width="35%" align="center">
						线路名称
					</td>
					<td width="10%" align="center">
						查看地图
					</td>
				</tr>

				<s:if test="%{lines.size()==0}">
					<td colspan="4" align="center">
						暂无该信息
					</td>
				</s:if>
				<s:iterator value="lines" var="line" status="status">
					<tr align='center' bgcolor="#FFFFFF"
						onMouseMove="javascript:this.bgColor='#FCFDEE';"
						onMouseOut="javascript:this.bgColor='#FFFFFF';" height="22">
						<td>
							<s:property value="#status.count" />
						</td>
						<td>
							<s:property value="project.name" />
						</td>
						<td>
							<a href="sensordataAction!listlinelastdata?lineid=<s:property value="id" />" onmouseover="this.style.cursor='hand'">
								<s:property value="name" />
							</a>
						</td>
						<td>

							<input type="button" value="查看" onmouseover="this.style.cursor='hand'" 
								onclick="window.location.href='sensorAction!sensorrealtime?lineid=<s:property value="id" />'" />


						</td>

					</tr>
				</s:iterator>





				<tr align="right" bgcolor="#EEF4EA">

					<td height="34" colspan="4" align="center">
						记录数：
						<s:property value="totalCount" />
						&nbsp;&nbsp;&nbsp;
						<a
							href="javascript:jumpYoukeLinePage('lineAction!monitorshow',1,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"
							target="main">首页</a>&nbsp;&nbsp;
						<a
							href="javascript:jumpYoukeLinePage('lineAction!monitorshow',<s:property value="page-1"/>,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"
							target="main">上一页</a>&nbsp;&nbsp;&nbsp;
						<a
							href="javascript:jumpYoukeLinePage('lineAction!monitorshow',<s:property value="page+1"/>,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"
							target="main">下一页</a>&nbsp;&nbsp;&nbsp;
						<a
							href="javascript:jumpYoukeLinePage('lineAction!monitorshow',<s:property value="pageCount"/>,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"
							target="main">尾页</a>&nbsp;&nbsp;&nbsp;
						<input type='button' class="coolbg np"
							onClick="javascript:jumpYoukeLinePage('lineAction!monitorshow',document.getElementById('page').value,<s:property value="con"/>,'<s:property value="convalue"/>',<s:property value="limits"/>,<s:property value="projectid"/>,'<s:property value="linetext"/>',<s:property value="upuserid"/>);"
							value='转到' />
						&nbsp; 当前页：
						<input onpaste="return false" onKeyPress="checkPage();" id="page"
							type="text" name="page" value="1" size="2"
							style="width: 25px; height: 20px; line-height: 18px; BORDER-RIGHT: #cccccc 1px solid; BORDER-TOP: #cccccc 1px solid; FONT-SIZE: 13px; BORDER-LEFT: #cccccc 1px solid; COLOR: #000000; BORDER-BOTTOM: #cccccc 1px solid; FONT-FAMILY: 宋体; BACKGROUND-COLOR: #ffffff;" />
						/共1页
					</td>

				</tr>
			</table>

		</form>

	</body>
</html>
