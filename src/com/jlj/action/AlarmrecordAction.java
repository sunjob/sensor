package com.jlj.action;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jlj.model.Alarmrecord;
import com.jlj.model.User;
import com.jlj.service.IAlarmrecordService;
import com.jlj.service.IGatewayService;
import com.jlj.service.IUserService;
import com.jlj.util.DateTimeKit;
import com.jlj.util.ExportExcelForAlarmRecord;
import com.opensymphony.xwork2.ActionSupport;

@Component("alarmrecordAction")
@Scope("prototype")
public class AlarmrecordAction extends ActionSupport implements RequestAware,
SessionAware,ServletResponseAware,ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	private IAlarmrecordService alarmrecordService;
	private IGatewayService gatewayService;
	private IUserService userService;
	Map<String,Object> request;
	Map<String,Object> session;
	private javax.servlet.http.HttpServletResponse response;
	private javax.servlet.http.HttpServletRequest req;
	//单个对象
	private int id;
	private Alarmrecord alarmrecord;
	//分页显示
	private String[] arg=new String[2];
	private List<Alarmrecord> alarmrecords;
	private int page;
	private final int size=10;
	private int pageCount;
	private int totalCount;
	
	private int projectid;//按项目id
	//条件
	private int con;
	private String convalue;
	
	private String linetext;//线路集合
	private String starttime;//开始时间
	private String endtime;//结束时间
	private int limits;//权限
	private int upuserid;//上级用户id
	/**
	 * 报警记录管理
	 */
	public String list() throws Exception{
		//判断会话是否失效
//		User user=(User)session.get("user");
//		if(user==null){
//			return "opsessiongo";
//		}
		//获取项目id作为“查询报警记录”的条件
//		projectid = user.getProject().getId();
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		if(page<1){
			page=1;
		}
		if(starttime!=null&&!starttime.equals("")){
			starttime=URLDecoder.decode(starttime, "utf-8");
		}
		if(endtime!=null&&!endtime.equals("")){
			endtime=URLDecoder.decode(endtime, "utf-8");
		}
		
		if(limits==0){
			//总记录数
			totalCount=alarmrecordService.getTotalCount(starttime,endtime,con,convalue);
			//总页数
			pageCount=alarmrecordService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			alarmrecords=alarmrecordService.queryList(starttime,endtime,con,convalue,page,size);
			
		}else if(limits==1){
			//超级管理员，查看该项目的所有线路===============================
			//总记录数
			totalCount=alarmrecordService.getTotalCount(starttime,endtime,con,convalue,projectid);
			//总页数
			pageCount=alarmrecordService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			alarmrecords=alarmrecordService.queryList(starttime,endtime,con,convalue,projectid,page,size);
		}else if(limits==2){
			//普通管理员，查看该管理员所管理的线路===============================
			//总记录数
			totalCount=alarmrecordService.getTotalCount(starttime,endtime,con,convalue,linetext);
			//总页数
			pageCount=alarmrecordService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			alarmrecords=alarmrecordService.queryList(starttime,endtime,con,convalue,linetext,page,size);
		}else if(limits==3){
			//普通用户，查看他的上级管理员所管理的线路===============================
//			User upuser = userService.loadById(upuserid);
//			if(upuser!=null){
//				linetext = upuser.getLinetext();
				//总记录数
				totalCount=alarmrecordService.getTotalCount(starttime,endtime,con,convalue,linetext);
				//总页数
				pageCount=alarmrecordService.getPageCount(totalCount,size);
				if(page>pageCount&&pageCount!=0){
					page=pageCount;
				}
				//所有当前页记录对象
				alarmrecords=alarmrecordService.queryList(starttime,endtime,con,convalue,linetext,page,size);
//			}
//		}else{
//			System.out.println("-------------无上级用户---------");
//			return NONE;
		}
		return "list";
	}
	
	
	/**
	 * 查询报警记录formobile
	 * @return
	 * @throws Exception 
	 */
	public String alarmrecordformobile() throws Exception{
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		if(page<1){
			page=1;
		}
		if(starttime!=null&&!starttime.equals("")){
			starttime=URLDecoder.decode(starttime, "utf-8");
		}
		if(endtime!=null&&!endtime.equals("")){
			endtime=URLDecoder.decode(endtime, "utf-8");
		}
		if(limits==0){
			//总记录数
			totalCount=alarmrecordService.getTotalCount(starttime,endtime,con,convalue);
			//总页数
			pageCount=alarmrecordService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			alarmrecords=alarmrecordService.queryList(starttime,endtime,con,convalue,page,size);
			
		}else if(limits==1){
			//超级管理员，查看该项目的所有线路===============================
			//总记录数
			totalCount=alarmrecordService.getTotalCount(starttime,endtime,con,convalue,projectid);
			//总页数
			pageCount=alarmrecordService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			alarmrecords=alarmrecordService.queryList(starttime,endtime,con,convalue,projectid,page,size);
		}else if(limits==2){
			//普通管理员，查看该管理员所管理的线路===============================
			//总记录数
			totalCount=alarmrecordService.getTotalCount(starttime,endtime,con,convalue,linetext);
			//总页数
			pageCount=alarmrecordService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			alarmrecords=alarmrecordService.queryList(starttime,endtime,con,convalue,linetext,page,size);
		}else if(limits==3){
			//普通用户，查看他的上级管理员所管理的线路===============================
//			User upuser = userService.loadById(upuserid);
//			if(upuser!=null){
//				linetext = upuser.getLinetext();
				//总记录数
				totalCount=alarmrecordService.getTotalCount(starttime,endtime,con,convalue,linetext);
				//总页数
				pageCount=alarmrecordService.getPageCount(totalCount,size);
				if(page>pageCount&&pageCount!=0){
					page=pageCount;
				}
				//所有当前页记录对象
				alarmrecords=alarmrecordService.queryList(starttime,endtime,con,convalue,linetext,page,size);
//			}
//		}else{
//			System.out.println("-------------无上级用户---------");
//			return NONE;
		}
		return "alarmrecordformobile";
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
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		//设置所属项目
//		alarmrecord.setProject(user.getProject());
		//保存到数据库
		alarmrecordService.add(alarmrecord);
		
		arg[0]="alarmrecordAction!list";
		arg[1]="报警记录管理";
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		alarmrecordService.deleteById(id);
		
		arg[0]="alarmrecordAction!list";
		arg[1]="报警记录管理";
		return SUCCESS;
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
		//设置所属项目
//		alarmrecord.setProject(user.getProject());
		//修改数据库
		alarmrecordService.update(alarmrecord);
		
		arg[0]="alarmrecordAction!list";
		arg[1]="报警记录管理";
		return SUCCESS;
	}
	/**
	 * 查看信息
	 * @return
	 */
	public String view(){
		alarmrecord=alarmrecordService.loadById(id);
		return "view";
	}
	/**
	 * 跳转到修改页面
	 * @return
	 */
	public String load() throws Exception{
		alarmrecord=alarmrecordService.loadById(id);
		return "load";
	}
	
	/**
	 * 报警记录管理
	 */
	public String listExport() throws Exception{
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		if(starttime!=null&&!starttime.equals("")){
			starttime=URLDecoder.decode(starttime, "utf-8");
		}
		if(endtime!=null&&!endtime.equals("")){
			endtime=URLDecoder.decode(endtime, "utf-8");
		}
		
		if(limits==0){
			//所有当前页记录对象
			alarmrecords=alarmrecordService.queryExportList(starttime,endtime,con,convalue);
		}else if(limits==1){
			//超级管理员，查看该项目的所有线路===============================
			//所有当前页记录对象
			alarmrecords=alarmrecordService.queryExportList(starttime,endtime,con,convalue,projectid);
		}else if(limits==2){
			//普通管理员，查看该管理员所管理的线路===============================
			//所有当前页记录对象
			alarmrecords=alarmrecordService.queryExportList(starttime,endtime,con,convalue,linetext);
		}else if(limits==3){
			//普通用户，查看他的上级管理员所管理的线路===============================
//			User upuser = userService.loadById(upuserid);
//			if(upuser!=null){
//				linetext = upuser.getLinetext();
				//所有当前页记录对象
				alarmrecords=alarmrecordService.queryExportList(starttime,endtime,con,convalue,linetext);
//			}
//		}else{
//			request.put("errorInfo", "无上级用户");
//			return "operror";
		}
		if(alarmrecords.size()>0){
			//导出数据-------------------------------------
			String filename = "output\\"+DateTimeKit.getDateRandom()+"_temps.xls";
			String savePath = ServletActionContext.getServletContext().getRealPath("/")+filename;
//			System.out.println("[--------------------savePath="+savePath);
			boolean isexport = ExportExcelForAlarmRecord.exportExcel(savePath,alarmrecords);
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
	
	//get、set-------------------------------------------
	public IAlarmrecordService getAlarmrecordService() {
		return alarmrecordService;
	}
	@Resource
	public void setAlarmrecordService(IAlarmrecordService alarmrecordService) {
		this.alarmrecordService = alarmrecordService;
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
	
	public void setAlarmrecord(Alarmrecord alarmrecord) {
		this.alarmrecord = alarmrecord;
	}
	
	public Alarmrecord getAlarmrecord() {
		return alarmrecord;
	}
	public List<Alarmrecord> getAlarmrecords() {
		return alarmrecords;
	}
	public void setAlarmrecords(List<Alarmrecord> alarmrecords) {
		this.alarmrecords = alarmrecords;
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
	public IGatewayService getGatewayService() {
		return gatewayService;
	}
	@Resource
	public void setGatewayService(IGatewayService gatewayService) {
		this.gatewayService = gatewayService;
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
	public String getLinetext() {
		return linetext;
	}
	public void setLinetext(String linetext) {
		this.linetext = linetext;
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
	public IUserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	
}
