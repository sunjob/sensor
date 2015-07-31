package com.jlj.action;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jlj.model.Addresslist;
import com.jlj.model.Alarm;
import com.jlj.model.User;
import com.jlj.service.IAddresslistService;
import com.jlj.service.IAlarmService;
import com.jlj.service.IGatewayService;
import com.opensymphony.xwork2.ActionSupport;

@Component("alarmAction")
@Scope("prototype")
public class AlarmAction extends ActionSupport implements RequestAware,
SessionAware,ServletResponseAware,ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	private IAlarmService alarmService;
	private IGatewayService gatewayService;
	private IAddresslistService addresslistService;
	Map<String,Object> request;
	Map<String,Object> session;
	private javax.servlet.http.HttpServletResponse response;
	private javax.servlet.http.HttpServletRequest req;
	//单个对象
	private int id;
	private Alarm alarm;
	//分页显示
	private String[] arg=new String[2];
	private List<Alarm> alarms;
	private List<Addresslist> addresslists;
	private int page;
	private final int size=10;
	private int pageCount;
	private int totalCount;
	
	private int projectid;//按项目id
	//条件
	private int con;
	private String convalue;
	private String starttime;
	private String endtime;
	
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
/*		if(convalue!=null&&!convalue.equals("")){
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
		//总记录数
		totalCount=alarmService.getTotalCount(starttime,endtime,con,convalue,projectid);
		//总页数
		pageCount=alarmService.getPageCount(totalCount,size);
		if(page>pageCount&&pageCount!=0){
			page=pageCount;
		}
		//所有当前页记录对象
		alarms=alarmService.queryList(starttime,endtime,con,convalue,projectid,page,size);*/
		
		return "list";
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
//		alarm.setProject(user.getProject());
		//保存到数据库
		alarmService.add(alarm);
		
		arg[0]="alarmAction!list";
		arg[1]="报警管理";
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		alarmService.deleteById(id);
		
		arg[0]="alarmAction!list";
		arg[1]="报警管理";
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
//		alarm.setProject(user.getProject());
		//修改数据库
		
		
		alarmService.update(alarm);
		
		arg[0]="alarmAction!load";
		arg[1]="报警设置";
		return SUCCESS;
	}
	/**
	 * 查看信息
	 * @return
	 */
	public String view(){
		alarm=alarmService.loadById(id);
		return "view";
	}
	/**
	 * 跳转到修改页面
	 * @return
	 */
	public String load() throws Exception{
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		alarm=alarmService.loadByProjectId(user.getProject().getId());
		addresslists = addresslistService.queryByUserList(user);
		if(alarm!=null)
		{
			return "load";
		}
		return NONE;
		
	}
	
	//get、set-------------------------------------------
	public IAlarmService getAlarmService() {
		return alarmService;
	}
	@Resource
	public void setAlarmService(IAlarmService alarmService) {
		this.alarmService = alarmService;
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
	
	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}
	
	public Alarm getAlarm() {
		return alarm;
	}
	public List<Alarm> getAlarms() {
		return alarms;
	}
	public void setAlarms(List<Alarm> alarms) {
		this.alarms = alarms;
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


	public IAddresslistService getAddresslistService() {
		return addresslistService;
	}

	@Resource
	public void setAddresslistService(IAddresslistService addresslistService) {
		this.addresslistService = addresslistService;
	}


	public List<Addresslist> getAddresslists() {
		return addresslists;
	}


	public void setAddresslists(List<Addresslist> addresslists) {
		this.addresslists = addresslists;
	}
	
	
	
}
