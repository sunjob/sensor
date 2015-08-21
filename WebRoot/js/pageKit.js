//pageKit.js
//登陆页面，验证码
function changeImage()
{
			var v=document.getElementById("im");
			var anum = Math.random()*100;
			v.innerHTML="<img src=toolkitAction!validateCode?id="+anum+" width=80 height=32  />";
}

//分页显示-根据条件、条件值、listnum
function jumpLinePage(url,page,con,convalue,listnum){
	
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&con='+con+'&convalue='+convalue+'&listnum='+listnum;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}
//查询某外设的分页展示列表
function jumpValvedataPage(url,page,con,convalue,valveid){
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&con='+con+'&convalue='+convalue+'&valveid='+valveid;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}
//查询游客线路展示列表
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

//报表导出数据
function jumpReportExportPage(url,page,starttime,endtime,con,convalue,stype,limits,projectid,linetext,upuserid,totalCount){
	//alert(totalCount);
	if(totalCount>20000){
		alert("数据量超出范围，请缩小查询范围导出，1~20000条数据");
		return;
	}
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

//报警记录分页查询
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

//报警记录导出数据
function jumpAlarmRecordExportPage(url,page,starttime,endtime,con,convalue,limits,projectid,linetext,upuserid){
	
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


//分页显示-根据条件、条件值、状态、pkid来查询
function jumpPage(url,page,con,convalue,status,pid){
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&con='+con+'&convalue='+convalue+'&status='+status+'&pkid='+pid;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}


//分页显示-根据条件、条件值、开始日期和结束日期来查询
function jumpDatePage(url,page,con,convalue,startdate,enddate){
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&con='+con+'&convalue='+convalue+'&startdate='+startdate+'&enddate='+enddate;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}

//根据开始时间、结束时间、项目编号分页显示
function jumpTimePage(url,page,starttime,endtime,projectid){
console.log("jumpTimePage");
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&starttime='+starttime+'&endtime='+endtime+'&projectid='+projectid;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}

//根据条件、调节值以及状态查询
function jumpPage(url,page,con,convalue,status){
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&con='+con+'&convalue='+convalue+'&status='+status;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}
//根据条件、条件值来查询-接收应答日志
function jumpPublicPage(url,page,con,convalue){
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&con='+con+'&convalue='+convalue;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}

//分页显示
function jumpTime2Page(url,page,con,convalue,start,end,status,pid){
	var page=page;
	if(isNaN(page)){
		var page2=document.getElementById(page).value;
		page=parseInt(page2);
	}
	
	var url=url+'?page='+page+'&con='+con+'&convalue='+convalue+'&start='+start+'&end='+end+'&status='+status+'&pid='+pid;
	url=encodeURI(url);
	url=encodeURI(url);
	window.location=url;
}




function deleteBatch(url){
				var aa=document.all.chek;
				var loc=url+"!deleteBatch?ids=0";
				if(aa.value>0){
					loc=url+"!delete?id="+aa.value;
				}
				if(aa.length>0){
					for(var i=0;i<aa.length;i++){
						if(aa[i].checked==true){
							var cpid=aa[i].value;
							loc=loc+","+cpid;
						}
					}
				}
				
				window.location=loc;
}

function deleteBatch2(url){
				var aa=document.all.chek;
				var loc=url+"!deleteBatch2?ids=0";
				if(aa.value>0){
					loc=url+"!delete2?id="+aa.value;
				}
				if(aa.length>0){
					for(var i=0;i<aa.length;i++){
						if(aa[i].checked==true){
							var cpid=aa[i].value;
							loc=loc+","+cpid;
						}
					}
				}
				
				window.location=loc;
}

function deleteBatch3(url){
				var aa=document.all.chek;
				var loc=url+"!deleteBatch3?ids=0";
				if(aa.value>0){
					loc=url+"!delete3?id="+aa.value;
				}
				if(aa.length>0){
					for(var i=0;i<aa.length;i++){
						if(aa[i].checked==true){
							var cpid=aa[i].value;
							loc=loc+","+cpid;
						}
					}
				}
				
				window.location=loc;
}
//全选
function selectAll(){
	var aa=document.getElementsByName("chek");
	if(aa.length>0){
		for(var i=0;i<aa.length;i++){
			aa[i].checked=true;
		}
	}

}
//反选
function reverse(){
	var aa=document.getElementsByName("chek");
	if(aa.length>0){
		for(var i=0;i<aa.length;i++){
			if(aa[i].checked==false){
				aa[i].checked=true;
			}else{
				aa[i].checked=false;
			}
		}
	}

}
//禁止使用退格键backspace
function keyDown(){
            // 禁止使用backspace键
            if(window.event.keyCode == 8){
                alert("该文本框为只读");
                event.returnValue=false;
            }
            // 后面还可以禁止其它键，照着上面的方法写就行了
            // 比如：if(event.shiftKey&&event.keyCode == 121) // 屏蔽shift+F10
}
//公文字典多选择删除shoufatype_manage.jsp


function checkNum(obj){
    //定义正则表达式部分
    var num=obj.value;
    if(isNaN(num)){
        alert("密码只能输入数字");
        obj.value="";
        obj.focus();
    }
}



function checkNum2(){
    //定义正则表达式部分
    var num=obj.value;
    if(isNaN(num)){
        alert("密码只能输入数字");
        obj.value="";
        obj.focus();
    }
}

