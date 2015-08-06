package com.jlj.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jlj.model.Gateway;
import com.jlj.model.Line;
import com.jlj.model.Sensor;
import com.jlj.model.Sensordata;
import com.jlj.model.User;
import com.jlj.service.IGatewayService;
import com.jlj.service.ILineService;
import com.jlj.service.ISensorService;
import com.jlj.service.ISensordataService;
import com.jlj.service.IUserService;
import com.jlj.util.DateTimeKit;
import com.jlj.util.ImportExcel;
import com.jlj.util.LogInterceptor;
import com.jlj.vo.ConvalueVO;
import com.jlj.vo.GatemapStatus;
import com.jlj.vo.SensordataVO;
import com.opensymphony.xwork2.ActionSupport;

@Component("sensordataAction")
@Scope("prototype")
public class SensordataAction extends ActionSupport implements RequestAware,
SessionAware,ServletResponseAware,ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	private ILineService lineService;
	private IGatewayService gatewayService;
	private ISensorService sensorService;
	private ISensordataService sensordataService;
	private IUserService userService;
	Map<String,Object> request;
	Map<String,Object> session;
	private javax.servlet.http.HttpServletResponse response;
	private javax.servlet.http.HttpServletRequest req;
	//单个对象
	private int id;
	private Sensor sensor;
	private Sensordata sensordata;
	//分页显示
	private String[] arg=new String[2];
	private List<Sensordata> sensordatas;
	private int page;
	private final int size=10;
	private int pageCount;
	private int totalCount;
	//条件
	private int con;
	private String convalue;
	//当前项目
	private int projectid;
	
	//传感器id
	private int sensorid;
	private int days;//翻页天数
	
	
	/*
	 * 日志处理类
	 */
	private LogInterceptor logInterceptor;
	
	public String dataoutput(){
		String startdate = req.getParameter("startdate");
		String enddate = req.getParameter("enddate");
		List<Sensordata> sensordatas = sensordataService.getTempsByDates(startdate,enddate);
		System.out.println("sensordatas数量"+sensordatas.size());
		String filename = "output\\"+DateTimeKit.getDateRandom()+"_temps.xls";
		String savePath = ServletActionContext.getServletContext().getRealPath("/")+filename;
//		System.out.println("[--------------------savePath="+savePath);
		boolean isexport = ImportExcel.exportExcel(savePath,sensordatas);
		if(isexport){
			request.put("errorInfo", "导出数据成功,下载点<a href='"+filename+"'>-这里-</a>");
			return "operror";
		}else{
			request.put("errorInfo", "导出数据失败！");
			return "operror";
		}
	}
	
	
	/**
	 * 传感器数据折线展示
	 */
	private int mapsize;
	private String lng;
	private String lat;
	public String listline() throws Exception{
		//判断会话是否失效//游客模式能查看的不限制
//		User user=(User)session.get("user");
//		if(user==null){
//			return "opsessiongo";
//		}
		if(starttime!=null&&!starttime.equals("")){
			starttime=URLDecoder.decode(starttime, "utf-8");
		}
		if(endtime!=null&&!endtime.equals("")){
			endtime=URLDecoder.decode(endtime, "utf-8");
		}
//		if(page<1){
//			page=1;
//		}
//		//总记录数
//		totalCount=sensordataService.getSensoridTotalCount(starttime,endtime,sensorid);
//		//总页数
//		pageCount=sensordataService.getPageCount(totalCount,size);
//		if(page>pageCount&&pageCount!=0){
//			page=pageCount;
//		}
//		//所有当前页记录对象
//		sensordatas=sensordataService.querySensoridList(starttime,endtime,sensorid,page,size);
		//展示传感器的信息
		sensor = sensorService.loadById(sensorid);
		if(stype==0){
			stype=1;//温度/电池电压
		}
		//默认显示最后一天
		if((starttime==null||starttime.trim().equals(""))&&(endtime==null||endtime.trim().equals(""))){
			//查出最后一次数据的日期并默认翻一天
			sensordata = sensordataService.getSensordataBySensoridAndStype(stype,sensorid);
			if(sensordata!=null){
				endtime = DateTimeKit.getDateString(sensordata.getSdatetime());
				days=1;
				endtime = DateTimeKit.dateBeforethis(endtime, -days);
				starttime = DateTimeKit.dateBeforethis(endtime, days);
			}
			
		}else{
			//计算出翻页天数
			days = DateTimeKit.daysBetween(starttime,endtime);//前一个日期，后一个日期
			//如果是向上翻页，starttime作为后一个日期，beforetime作为前一个日期；
			//如果是向下翻页，endtime作为前一个日期，aftertime作为前一个日期；
			if(page==-1){
				endtime = starttime;
				starttime = DateTimeKit.dateBeforethis(endtime, days);
			}else if(page==1){
				starttime = endtime;
				endtime = DateTimeKit.dateBeforethis(starttime, -days);
			}
			
		}
		sensordatas=sensordataService.querySensoridAllList(starttime,endtime,stype,sensorid);
		return "listline";
	}
	
	/**
	 * 获取最新数据
	 * @return
	 */
	private static int lastdata;
	public String getnewtemp()
	{			
		//1-查询出该sensorid和stype的最新温度值
		stype=1;
		sensordata = sensordataService.getSensordataBySensoridAndStype(stype,sensorid);
		Date newtimeDate = sensordata.getSdatetime();
//		Date nowDate = new Date();
//		String newtime = DateTimeKit.getDateMinuteString(newtimeDate);
//		String nowtime = DateTimeKit.getDateMinuteString(nowDate);
		//System.out.println(stype+","+sensorid);
		int tablelastdata = sensordata.getId();
		//发现是新数据，做相应的发送
//		if(sensordata!=null&&newtime!=null&&!newtime.equals(nowtime)&&nowDate.before(newtimeDate)){
		if(sensordata!=null&&tablelastdata!=lastdata){
				//System.out.println("sensordata!=null");
				
				
				SensordataVO dataVO = new SensordataVO();
				String msg = "success";
				dataVO.setMsg(msg);
				dataVO.setData(sensordata.getSdata());
				dataVO.setTime(DateTimeKit.getDateTimeString(newtimeDate));
				JSONObject jsonObj = JSONObject.fromObject(dataVO);
				PrintWriter out;
				try {
					response.setContentType("text/html;charset=UTF-8");
					out = response.getWriter();
					System.out.println(jsonObj.toString());
					out.print(jsonObj.toString());
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				lastdata = tablelastdata;//更新计数器
				System.out.println("lastdata is --------------"+lastdata);
		}
		return null;
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	public String goToAdd(){
		return "add";
	}
	/**
	 * 添加
	 * @return
	 * @throws Exception
	 */
	
	public String add() throws Exception{
		sensordataService.add(sensordata);
		
		arg[0]="sensordataAction!list";
		arg[1]="传感器数据管理";
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception 
	 */
	public String delete() throws Exception{
		sensordataService.deleteById(id);
		return this.databak();
	}
	/**
	 * 修改
	 * @return
	 */
	public String update() throws Exception{
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		sensordataService.update(sensordata);
		logInterceptor.addLog("报警设置操作", user.getUsername()+"修改报警设置["+sensordata.toString()+"]", sensordata.getSensor().getGateway().getLine().getProject().getId());
		arg[0]="sensordataAction!list";
		arg[1]="传感器数据管理";
		return SUCCESS;
	}
	/**
	 * 查看信息
	 * @return
	 */
	public String view(){
		sensordata=sensordataService.loadById(id);
		return "view";
	}
	/**
	 * 跳转到修改页面
	 * @return
	 */
	public String load() throws Exception{
		sensordata=sensordataService.loadById(id);
		return "load";
	}
	/**
	 * 报表-详细查询
	 * @return
	 * @throws Exception 
	 */
	private String linetext;//线路集合
	private String starttime;//开始时间
	private String endtime;//结束时间
	private String gatewayname;//网关别名
	private int stype;//数据类型
	private String sensorname;//设备编号
	private int limits;//权限
	private int upuserid;//上级用户id
	public String reportdetail() throws Exception{
		//判断会话是否失效//游客模式能查看的不限制
//		User user=(User)session.get("user");
//		if(user==null){
//			return "opsessiongo";
//		}
		//网页中文乱码解码
		if(starttime!=null&&!starttime.equals("")){
			starttime=URLDecoder.decode(starttime, "utf-8");
		}
		if(endtime!=null&&!endtime.equals("")){
			endtime=URLDecoder.decode(endtime, "utf-8");
		}
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
//		if(gatewayname!=null&&!gatewayname.equals("")){
//			gatewayname=URLDecoder.decode(gatewayname, "utf-8");
//		}
//		if(sensorname!=null&&!sensorname.equals("")){
//			sensorname=URLDecoder.decode(sensorname, "utf-8");
//		}
		
		//判断该用户的权限
//		int limits = user.getLimits();
		if(limits==0){
			//系统管理员，分页显示所有的线路========================
			
			if(page<1){
				page=1;
			}
			//总记录数
//			totalCount=sensordataService.getTotalCount(starttime,endtime,gatewayname,stype,sensorname);
			totalCount=sensordataService.getTotalCount(starttime,endtime,con,convalue,stype);
			//总页数
			pageCount=sensordataService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
//			sensordatas=sensordataService.queryList(starttime,endtime,gatewayname,stype,sensorname,page,size);
			sensordatas=sensordataService.queryList(starttime,endtime,con,convalue,stype,page,size);
			
		}else if(limits==1){
			//超级管理员，查看该项目的所有线路===============================
//			projectid = user.getProject().getId();
			if(page<1){
				page=1;
			}
			//总记录数
//			totalCount=sensordataService.getProjectTotalCount(starttime,endtime,gatewayname,stype,sensorname,projectid);
			totalCount=sensordataService.getProjectTotalCount(starttime,endtime,con,convalue,stype,projectid);
			//总页数
			pageCount=sensordataService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
//			sensordatas=sensordataService.queryProjectList(starttime,endtime,gatewayname,stype,sensorname,projectid,page,size);
			sensordatas=sensordataService.queryProjectList(starttime,endtime,con,convalue,stype,projectid,page,size);
		}else if(limits==2){
			//普通管理员，查看该管理员所管理的线路===============================
//			linetext = user.getLinetext();
			if(page<1){
				page=1;
			}
			//总记录数
//			totalCount=sensordataService.getManageTotalCount(starttime,endtime,gatewayname,stype,sensorname,linetext);
			totalCount=sensordataService.getManageTotalCount(starttime,endtime,con,convalue,stype,linetext);
			//总页数
			pageCount=sensordataService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
//			sensordatas=sensordataService.queryManageList(starttime,endtime,gatewayname,stype,sensorname,linetext,page,size);
			sensordatas=sensordataService.queryManageList(starttime,endtime,con,convalue,stype,linetext,page,size);
		}else if(limits==3){
			//普通用户，查看他的上级管理员所管理的线路===============================
//			User upuser = userService.loadById(user.getUpuserid());
			User upuser = userService.loadById(upuserid);
			if(upuser!=null){
				linetext = upuser.getLinetext();
				if(page<1){
					page=1;
				}
				//总记录数
//				totalCount=sensordataService.getManageTotalCount(starttime,endtime,gatewayname,stype,sensorname,linetext);
				totalCount=sensordataService.getManageTotalCount(starttime,endtime,con,convalue,stype,linetext);
				//总页数
				pageCount=sensordataService.getPageCount(totalCount,size);
				if(page>pageCount&&pageCount!=0){
					page=pageCount;
				}
				//所有当前页记录对象
//				sensordatas=sensordataService.queryManageList(starttime,endtime,gatewayname,stype,sensorname,linetext,page,size);
				sensordatas=sensordataService.queryManageList(starttime,endtime,con,convalue,stype,linetext,page,size);
			}else{
				System.out.println("-------------无上级用户---------");
				return NONE;
			}
		}
		return "reportdetail";
	}
	
	/**
	 * 移动端报表查询
	 * @return
	 * @throws Exception
	 */
	public String reportdetailformobile() throws Exception{
		if(starttime!=null&&!starttime.equals("")){
			starttime=URLDecoder.decode(starttime, "utf-8");
		}
		if(endtime!=null&&!endtime.equals("")){
			endtime=URLDecoder.decode(endtime, "utf-8");
		}
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		
		//判断该用户的权限
//		int limits = user.getLimits();
		if(limits==0){
			//系统管理员，分页显示所有的线路========================
			
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=sensordataService.getTotalCount(starttime,endtime,con,convalue,stype);
			//总页数
			pageCount=sensordataService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			sensordatas=sensordataService.queryList(starttime,endtime,con,convalue,stype,page,size);
			
		}else if(limits==1){
			//超级管理员，查看该项目的所有线路===============================
//			projectid = user.getProject().getId();
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=sensordataService.getProjectTotalCount(starttime,endtime,con,convalue,stype,projectid);
			//总页数
			pageCount=sensordataService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			sensordatas=sensordataService.queryProjectList(starttime,endtime,con,convalue,stype,projectid,page,size);
		}else if(limits==2){
			//普通管理员，查看该管理员所管理的线路===============================
//			linetext = user.getLinetext();
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=sensordataService.getManageTotalCount(starttime,endtime,con,convalue,stype,linetext);
			//总页数
			pageCount=sensordataService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			sensordatas=sensordataService.queryManageList(starttime,endtime,con,convalue,stype,linetext,page,size);
		}else if(limits==3){
			//普通用户，查看他的上级管理员所管理的线路===============================
//			User upuser = userService.loadById(user.getUpuserid());
			User upuser = userService.loadById(upuserid);
			if(upuser!=null){
				linetext = upuser.getLinetext();
				if(page<1){
					page=1;
				}
				//总记录数
				totalCount=sensordataService.getManageTotalCount(starttime,endtime,con,convalue,stype,linetext);
				//总页数
				pageCount=sensordataService.getPageCount(totalCount,size);
				if(page>pageCount&&pageCount!=0){
					page=pageCount;
				}
				//所有当前页记录对象
				sensordatas=sensordataService.queryManageList(starttime,endtime,con,convalue,stype,linetext,page,size);
			}else{
				System.out.println("-------------无上级用户---------");
				return NONE;
			}
		}
		return "reportdetailformobile";
	}
	
	/**
	 * 数据备份
	 * @return
	 * @throws Exception
	 */
	public String databak() throws Exception{
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		if(starttime!=null&&!starttime.equals("")){
			starttime=URLDecoder.decode(starttime, "utf-8");
		}
		if(endtime!=null&&!endtime.equals("")){
			endtime=URLDecoder.decode(endtime, "utf-8");
		}
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		
		//判断该用户的权限
		int limits = user.getLimits();
		if(limits==0){
			//系统管理员，分页显示所有的线路========================
			
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=sensordataService.getTotalCount(starttime,endtime,con,convalue,stype);
			//总页数
			pageCount=sensordataService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			sensordatas=sensordataService.queryList(starttime,endtime,con,convalue,stype,page,size);
			
		}else if(limits==1){
			//超级管理员，查看该项目的所有线路===============================
			projectid = user.getProject().getId();
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=sensordataService.getProjectTotalCount(starttime,endtime,con,convalue,stype,projectid);
			//总页数
			pageCount=sensordataService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			sensordatas=sensordataService.queryProjectList(starttime,endtime,con,convalue,stype,projectid,page,size);
		}else if(limits==2){
			//普通管理员，查看该管理员所管理的线路===============================
			linetext = user.getLinetext();
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=sensordataService.getManageTotalCount(starttime,endtime,con,convalue,stype,linetext);
			//总页数
			pageCount=sensordataService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			sensordatas=sensordataService.queryManageList(starttime,endtime,con,convalue,stype,linetext,page,size);
		}else if(limits==3){
			//普通用户，查看他的上级管理员所管理的线路===============================
			User upuser = userService.loadById(user.getUpuserid());
//			User upuser = userService.loadById(upuserid);
			if(upuser!=null){
				linetext = upuser.getLinetext();
				if(page<1){
					page=1;
				}
				//总记录数
				totalCount=sensordataService.getManageTotalCount(starttime,endtime,con,convalue,stype,linetext);
				//总页数
				pageCount=sensordataService.getPageCount(totalCount,size);
				if(page>pageCount&&pageCount!=0){
					page=pageCount;
				}
				//所有当前页记录对象
				sensordatas=sensordataService.queryManageList(starttime,endtime,con,convalue,stype,linetext,page,size);
			}else{
				System.out.println("-------------无上级用户---------");
				return NONE;
			}
		}
		return "databak";
	}
	//数据备份下载并删除最先30天的数据
	public String databakAndDownload(){
		float datasize = sensordataService.getMysqlDatabaseSize();
		//System.out.println("datasize--------"+datasize+"MB");
		if(datasize>20000){
			//超过容量
			//1-导出最先30天的数据；
			sensordata = sensordataService.getOldestSensordata(1,1);
			String startdate = DateTimeKit.getDateTimeString(sensordata.getSdatetime());
			String enddate = "";
			try {
				enddate = DateTimeKit.dateBeforethis(startdate, -30);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			List<Sensordata> sensordatas = sensordataService.getTempsByDates(startdate,enddate);
			//System.out.println("sensordatas数量"+sensordatas.size());
			String filename = "output\\"+DateTimeKit.getDateRandom()+"_temps.xls";
			String savePath = ServletActionContext.getServletContext().getRealPath("/")+filename;
//			System.out.println("[--------------------savePath="+savePath);
			boolean isexport = ImportExcel.exportExcel(savePath,sensordatas);
			if(isexport){
				request.put("errorInfo", "数据库清理并导出数据成功,下载点<a href='"+filename+"'>-这里-</a>");
				//2-删除这些数据
				sensordataService.deleteByDate(startdate,enddate);
				return "operror";
			}else{
				request.put("errorInfo", "导出数据失败！");
				return "operror";
			}
			
		}else{
			request.put("errorInfo", "当前数据库占用容量："+datasize+"MB,不需要清理！");
			return "operror";
		}
	}
	
	/**
	 * 曲线图展示该线路的所有传感器的最新温度
	 * @return
	 */
	private int lineid;
	List<Sensor> sensors;
	public String listlinelastdata(){
		sensors = sensorService.getSensorsByLineid(lineid);
		return "listlinelastdata";
	}
	
	
	public String reportdetailexport() throws Exception{
		//网页中文乱码解码
		if(starttime!=null&&!starttime.equals("")){
			starttime=URLDecoder.decode(starttime, "utf-8");
		}
		if(endtime!=null&&!endtime.equals("")){
			endtime=URLDecoder.decode(endtime, "utf-8");
		}
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		
		//判断该用户的权限
		if(limits==0){
			//系统管理员，分页显示所有的线路========================
			//所有当前页记录对象
			sensordatas=sensordataService.queryExportList(starttime,endtime,con,convalue,stype);
			
		}else if(limits==1){
			//超级管理员，查看该项目的所有线路===============================
			//所有当前页记录对象
			sensordatas=sensordataService.queryExportProjectList(starttime,endtime,con,convalue,stype,projectid);
		}else if(limits==2){
			//普通管理员，查看该管理员所管理的线路===============================
			sensordatas=sensordataService.queryExportManageList(starttime,endtime,con,convalue,stype,linetext);
		}else if(limits==3){
			//普通用户，查看他的上级管理员所管理的线路===============================
			User upuser = userService.loadById(upuserid);
			if(upuser!=null){
				linetext = upuser.getLinetext();
				if(page<1){
					page=1;
				}
				//所有当前页记录对象
				sensordatas=sensordataService.queryExportManageList(starttime,endtime,con,convalue,stype,linetext);
			}else{
				System.out.println("-------------无上级用户---------");
				return NONE;
			}
		}
		if(sensordatas.size()>0){
			//导出数据---------------------------------------------
			String filename = "output\\"+DateTimeKit.getDateRandom()+"_temps.xls";
			String savePath = ServletActionContext.getServletContext().getRealPath("/")+filename;
//			System.out.println("[--------------------savePath="+savePath);
//			System.out.println("[--------------------size="+sensordatas.size());
			boolean isexport = ImportExcel.exportExcel(savePath,sensordatas);
			if(isexport){
				request.put("errorInfo", "导出数据成功,下载点<a href='"+filename+"'>-这里-</a>");
				return "operror";
			}else{
				request.put("errorInfo", "导出数据失败！");
				return "operror";
			}
		}else{
			request.put("errorInfo", "查询失败，未导出数据！");
			return "operror";
		}
		
	}
	/**
	 * AJAX获取convalue列表
	 * @return
	 */
	private int what;//传入的参数：线路1、网关2、传感器3
	public String getconvaluelist(){
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
			return NONE;
		}
		int limits = user.getLimits();
		int projectid = user.getProject().getId();
		String linetext = user.getLinetext();
		List<ConvalueVO> convaluevos = new ArrayList<ConvalueVO>();
		if(what==1){
			//线路
			List<Line> lines=null;
			if(limits==0){
				lines = lineService.getLines();
			}else if(limits==1){
				lines = lineService.getLinesByPid(projectid);
			}else{
				lines = lineService.getLinesByLinetext(projectid, linetext);
			}
			if(lines!=null&&lines.size()>0){
				
				for (int i = 0; i < lines.size(); i++) {
					Line lineone = lines.get(i);
					ConvalueVO convalueVO = new ConvalueVO();
					convalueVO.setId(lineone.getId());
					if(lineone.getOrderid()==1){
						convalueVO.setCvalue(lineone.getProject().getName()+"-"+lineone.getName());
					}else{
						convalueVO.setCvalue(lineone.getName());
					}
					
					convaluevos.add(convalueVO);
				}
				
			}
		}else if(what==2){
			//网关
			List<Gateway> gateways = null;
			if(limits==0){
				gateways = gatewayService.getGateways();
			}else if(limits==1){
				gateways = gatewayService.getGatewaysByProjectId(projectid);
			}else{
				gateways = gatewayService.getGatewaysByLinetext(projectid,linetext);
			}
			if(gateways!=null&&gateways.size()>0){
				
				for (int i = 0; i < gateways.size(); i++) {
					Gateway gatewayone = gateways.get(i);
					ConvalueVO convalueVO = new ConvalueVO();
					convalueVO.setId(gatewayone.getId());
					convalueVO.setCvalue(gatewayone.getName());
					convaluevos.add(convalueVO);
				}
				
			}
		}else if(what==3){
			//传感器
			List<Sensor> sensors = null;
			if(limits==0){
				sensors = sensorService.getSensors();
			}else if(limits==1){
				sensors = sensorService.getSensorsByProjectid(projectid);
			}else{
				sensors = sensorService.getSensorsByLinetext(projectid,linetext);
			}
			if(sensors!=null&&sensors.size()>0){
				
				for (int i = 0; i < sensors.size(); i++) {
					Sensor sensorone = sensors.get(i);
					ConvalueVO convalueVO = new ConvalueVO();
					convalueVO.setId(sensorone.getId());
					convalueVO.setCvalue(sensorone.getName());
					convaluevos.add(convalueVO);
				}
				
			}
		}else if(what==0){
			//未选择
			ConvalueVO convalueVO = new ConvalueVO();
			convalueVO.setId(0);
			convalueVO.setCvalue("未选择");
			convaluevos.add(convalueVO);
		}
		// 将list转化成JSON对象
		JSONArray jsonArray = JSONArray.fromObject(convaluevos);
		//System.out.println(jsonArray.toString());
		PrintWriter out;
		try {
			response.setCharacterEncoding("UTF-8"); 
			out = response.getWriter();
			out.print(jsonArray);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	//get、set-------------------------------------------
	public ISensordataService getSensordataService() {
		return sensordataService;
	}
	@Resource
	public void setSensordataService(ISensordataService sensordataService) {
		this.sensordataService = sensordataService;
	}
	// 获得HttpServletResponse对象
    public void setServletResponse(HttpServletResponse response)
    {
        this.response = response;
    }    
    public void setServletRequest(HttpServletRequest req) {
		this.req = req;
	}
    public Map<String, Object> getRequest() {
		return request;
	}
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public void setSensordata(Sensordata sensordata) {
		this.sensordata = sensordata;
	}
	
	public Sensordata getSensordata() {
		return sensordata;
	}
	public List<Sensordata> getSensordatas() {
		return sensordatas;
	}
	public void setSensordatas(List<Sensordata> sensordatas) {
		this.sensordatas = sensordatas;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getCon() {
		return con;
	}
	public void setCon(int con) {
		this.con = con;
	}
	public String getConvalue() {
		return convalue;
	}
	public void setConvalue(String convalue) {
		this.convalue = convalue;
	}
	public String[] getArg() {
		return arg;
	}
	public void setArg(String[] arg) {
		this.arg = arg;
	}


	public int getProjectid() {
		return projectid;
	}

	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}

	public IUserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public String getLinetext() {
		return linetext;
	}
	public void setLinetext(String linetext) {
		this.linetext = linetext;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getGatewayname() {
		return gatewayname;
	}
	public void setGatewayname(String gatewayname) {
		this.gatewayname = gatewayname;
	}
	public int getStype() {
		return stype;
	}
	public void setStype(int stype) {
		this.stype = stype;
	}
	public String getSensorname() {
		return sensorname;
	}
	public void setSensorname(String sensorname) {
		this.sensorname = sensorname;
	}
	public int getSensorid() {
		return sensorid;
	}
	public void setSensorid(int sensorid) {
		this.sensorid = sensorid;
	}
	public ISensorService getSensorService() {
		return sensorService;
	}
	@Resource
	public void setSensorService(ISensorService sensorService) {
		this.sensorService = sensorService;
	}
	public Sensor getSensor() {
		return sensor;
	}
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public int getLimits() {
		return limits;
	}

	public void setLimits(int limits) {
		this.limits = limits;
	}

	public int getUpuserid() {
		return upuserid;
	}

	public void setUpuserid(int upuserid) {
		this.upuserid = upuserid;
	}
	public LogInterceptor getLogInterceptor() {
		return logInterceptor;
	}
	@Resource
	public void setLogInterceptor(LogInterceptor logInterceptor) {
		this.logInterceptor = logInterceptor;
	}


	public int getLineid() {
		return lineid;
	}

	public void setLineid(int lineid) {
		this.lineid = lineid;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	public int getMapsize() {
		return mapsize;
	}
	public void setMapsize(int mapsize) {
		this.mapsize = mapsize;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public ILineService getLineService() {
		return lineService;
	}
	@Resource
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	public IGatewayService getGatewayService() {
		return gatewayService;
	}
	@Resource
	public void setGatewayService(IGatewayService gatewayService) {
		this.gatewayService = gatewayService;
	}
	public int getWhat() {
		return what;
	}
	public void setWhat(int what) {
		this.what = what;
	}
	
	
}
