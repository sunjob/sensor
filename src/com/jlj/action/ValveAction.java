package com.jlj.action;

import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mina.CRC_16;
import mina.DataConvertor;
import mina.TimeServerHandler;

import org.apache.mina.core.session.IoSession;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jlj.model.Gateway;
import com.jlj.model.Project;
import com.jlj.model.User;
import com.jlj.model.Valve;
import com.jlj.model.Valvedata;
import com.jlj.service.IGatewayService;
import com.jlj.service.IProjectService;
import com.jlj.service.IUserService;
import com.jlj.service.IValveService;
import com.jlj.service.IValvedataService;
import com.jlj.util.LogInterceptor;
import com.opensymphony.xwork2.ActionSupport;

@Component("valveAction")
@Scope("prototype")
public class ValveAction extends ActionSupport implements RequestAware,
SessionAware,ServletResponseAware,ServletRequestAware {
	
	private static final long serialVersionUID = 1L;

	Map<String,Object> request;
	Map<String,Object> session;
	private javax.servlet.http.HttpServletResponse response;
	private javax.servlet.http.HttpServletRequest req;
	
	private int id;
	
	/*
	 * service层对象
	 */
	private IProjectService projectService;
	private IGatewayService gatewayService;
	private IValveService valveService;
	private IValvedataService valvedataService;
	private IUserService userService;
	
	//分页显示
	private String[] arg=new String[2];
	private int page;
	private final int size=10;
	private int pageCount;
	private int totalCount;
	private int projectid;//按项目id
	private String linetext;
	
	private int firstone;//是否是第一次进入
	//条件
	private int con;
	private String convalue;
	
	/*
	 * 单个对象
	 */
	private Project project;
	private Valve valve;
	
	/*
	 * list对象
	 */
	private List<Project> projects;
	private List<Valve> valves;
	
	/*
	 * 日志处理类
	 */
	private LogInterceptor logInterceptor;
	
	/**
	 * 外设管理
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		//判断该用户的权限
		int limits = user.getLimits();
		if(limits==0){
			//系统管理员，分页显示所有的线路========================
			if(convalue!=null&&!convalue.equals("")){
				convalue=URLDecoder.decode(convalue, "utf-8");
			}
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=valveService.getTotalCount(con,convalue);
			//总页数
			pageCount=valveService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			valves=valveService.queryList(con,convalue,page,size);
			
		}else if(limits==1){
			//超级管理员，查看该项目的所有线路===============================
			projectid = user.getProject().getId();
			if(convalue!=null&&!convalue.equals("")){
				convalue=URLDecoder.decode(convalue, "utf-8");
			}
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=valveService.getProjectTotalCount(con,convalue,projectid);
			//总页数
			pageCount=valveService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			valves=valveService.queryProjectList(con,convalue,projectid,page,size);
		}else if(limits==2){
			//普通管理员，查看该管理员所管理的线路===============================
			linetext = user.getLinetext();
			if(convalue!=null&&!convalue.equals("")){
				convalue=URLDecoder.decode(convalue, "utf-8");
			}
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=valveService.getManageTotalCount(con,convalue,linetext);
			//总页数
			pageCount=valveService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			valves=valveService.queryManageList(con,convalue,linetext,page,size);
		}else if(limits==3){
			//普通用户，查看他的上级管理员所管理的线路===============================
			User upuser = userService.loadById(user.getUpuserid());
			if(upuser!=null){
				linetext = upuser.getLinetext();
				if(convalue!=null&&!convalue.equals("")){
					convalue=URLDecoder.decode(convalue, "utf-8");
				}
				if(page<1){
					page=1;
				}
				//总记录数
				totalCount=valveService.getManageTotalCount(con,convalue,linetext);
				//总页数
				pageCount=valveService.getPageCount(totalCount,size);
				if(page>pageCount&&pageCount!=0){
					page=pageCount;
				}
				//所有当前页记录对象
				valves=valveService.queryManageList(con,convalue,linetext,page,size);
			}else{
				System.out.println("-------------无上级用户---------");
				return NONE;
			}
		}
		
		return "list";
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	private List<Gateway> gateways;//界面上获取的网关集合
	public String goToAdd(){
		
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		/*
		 * 当前操作用户权限划分
		 * 这里保留了需要显示项目名称的功能
		 */
//		switch (usero.getLimits()) {
//		case 0:
//			projects = projectService.getProjects();
//			break;
//		case 1:
//			if(projectid==0)
//			{
//				projectid = usero.getProject().getId();
//			}
//			project = projectService.getById(projectid);
//			break;
//		default:
//			break;
//		}
		gateways = gatewayService.getGatewaysByProjectId(usero.getProject().getId());
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
		Gateway gateway1 = gatewayService.loadById(valve.getGateway().getId());
		logInterceptor.addLog("外设信息操作", user.getUsername()+"新增外设["+valve.toString()+"]", gateway1.getLine().getProject().getId());
		valveService.add(valve);
		arg[0]="valveAction!list";
		arg[1]="外设管理";
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		valve = valveService.loadById(id);
		
		logInterceptor.addLog("外设信息操作", user.getUsername()+"删除外设["+valve.toString()+"]", valve.getGateway().getLine().getProject().getId());
		valveService.deleteById(id);
		
		arg[0]="valveAction!list";
		arg[1]="外设管理";
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
		Gateway gateway1 = gatewayService.loadById(valve.getGateway().getId());
		logInterceptor.addLog("外设信息操作", user.getUsername()+"修改外设["+valve.toString()+"]",  gateway1.getLine().getProject().getId());
		//修改数据库
		valveService.update(valve);
		arg[0]="valveAction!list";
		arg[1]="外设管理";
		return SUCCESS;
	}
	/**
	 * 查看信息
	 * @return
	 */
	public String view(){
		valve=valveService.loadById(id);
		return "view";
	}
	/**
	 * 跳转到修改页面
	 * @return
	 */
	public String load() throws Exception{
		
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		/*
		 * 当前操作用户权限划分
		 * 这里保留了需要显示项目名称的功能
		 */
//		switch (usero.getLimits()) {
//		case 0:
//			projects = projectService.getProjects();
//			break;
//		case 1:
//			if(projectid==0)
//			{
//				projectid = usero.getProject().getId();
//			}
//			project = projectService.getById(projectid);
//			break;
//		default:
//			break;
//		}
		gateways = gatewayService.getGatewaysByProjectId(usero.getProject().getId());
		valve=valveService.loadById(id);
		return "load";
	}
	
	/**
	 * 发送命令，并改变数据库状态
	 * @return
	 */
	private int status;
	public String changestatus(){
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		IoSession currentSession=null;
		valve = valveService.loadById(valveid);
		Gateway gateway1 = gatewayService.getGatewayById(valve.getGateway().getId());
		if(valve!=null){
			if(valve.getGateway()!=null){
				String uid = valve.getGateway().getMacaddress();
				currentSession = this.getCurrrenIoSession(uid);
			}
		}
		if(status==valve.getStatus()){
			request.put("errorInfo", "发送命令失败，已经处于该状态，请刷新界面！");
			return "operror";
		}
		if(currentSession!=null){
			int gateaddress = valve.getGateway().getGateaddress();//设备地址
			int valveaddress = valve.getValveaddress();//外设地址0~255
			int locatenumber = valve.getLocatenumber();//开关位置号0~255
			int controlvalue = status;//控制值：0关闭，非0(1)打开
			//外设控制协议-用于控制外设开关
			//（设备地址0xXX:gateaddress  功能代码0x45  数据长度0x03、外设地址、开关位置号、控制值  CRC16:2个字节）
			//0xXX 0x45    0x03 0x01 0x01 0xFF    0xXX 0xXX
			/*
			SL代码
			*/
			
			byte[] send_byte = new byte[6];
			send_byte[0] = (byte)(gateaddress&0xff);
			send_byte[1] = (byte)(0x45);
			send_byte[2] = (byte)(0x03);
			send_byte[3] = (byte)(valveaddress&0xff);
			send_byte[4] = (byte)(locatenumber&0xff);
			send_byte[5] = (byte)(controlvalue&0xff);
			
			byte[] sbuf1 = CRC_16.getSendBuf(DataConvertor.toHexString(send_byte));
	        currentSession.write(sbuf1); 
	        logInterceptor.addLog("外设操作", usero.getUsername()+(status==1?"开启":"关闭")+"外设", gateway1.getLine().getProject().getId());
			request.put("errorInfo", "命令已发送！点这里查看-<a href='receivelogAction!list?projectid="+usero.getProject().getId()+"' target='rightFrame'>应答数据</a>-");
			return "operror";
		}else{
			request.put("errorInfo", "发送命令失败，请检查当前网关是否断开！");
			return "operror";
		}
	}
	
	/**
	 * 外设数据协议发送命令，并展示所有接收数据
	 * @return
	 */
	public String datalistsend(){
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		//发送接收数据的命令------------------------------------------------------------
		IoSession currentSession=null;
		valve = valveService.loadById(valveid);
		Gateway gateway1 = gatewayService.getGatewayById(valve.getGateway().getId());
		if(valve!=null){
			if(valve.getGateway()!=null){
				String uid = valve.getGateway().getMacaddress();
				currentSession = this.getCurrrenIoSession(uid);
			}
		}
		if(currentSession!=null){
			int gateaddress = valve.getGateway().getGateaddress();//设备地址
			int valveaddress = valve.getValveaddress();//外设地址0~255
			int vtype = 1;//数值类型:温度
			//外设数据协议-用于告知网关发送数据
			//（设备地址0xXX:gateaddress  功能代码0x46  数据长度0x02、外设地址、数值类型  CRC16:2个字节）
			//0xXX 0x46    0x02 0x01 0x01    0xXX 0xXX
			/*
			SL代码
			*/
			
			byte[] send_byte = new byte[5];
			send_byte[0] = (byte)(gateaddress&0xff);
			send_byte[1] = (byte)(0x46);
			send_byte[2] = (byte)(0x02);
			send_byte[3] = (byte)(valveaddress&0xff);
			send_byte[4] = (byte)(vtype&0xff);
			
			byte[] sbuf1 = CRC_16.getSendBuf(DataConvertor.toHexString(send_byte));
			for (int i = 0; i < sbuf1.length; i++) {
				System.out.println("valve action sbuf1["+i+"] is "+sbuf1[i]);
			}
	        currentSession.write(sbuf1); 
	        
	        
			logInterceptor.addLog("外设操作", usero.getUsername()+"发送接收外设数据命令", gateway1.getLine().getProject().getId());
	        request.put("errorInfo", "命令已发送！点这里查看-<a href='valveAction!datalist?valveid="+valveid+"' target='rightFrame'>接收数据历史记录</a>-");
			return "operror";
			
		}else{
			request.put("errorInfo", "发送命令失败，请检查当前网关是否断开！");
			return "operror";
		}
		
	}
	
	
	
	
	private int valveid;
	private List<Valvedata> valvedatas;
	public String datalist(){
		
		//------------------------------------------------------------
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		if(page<1){
			page=1;
		}
		//总记录数
		totalCount=valvedataService.getValveTotalCount(con,convalue,valveid);
		//总页数
		pageCount=valvedataService.getPageCount(totalCount,size);
		if(page>pageCount&&pageCount!=0){
			page=pageCount;
		}
		//所有当前页记录对象
		valvedatas=valvedataService.queryValveList(con,convalue,valveid,page,size);
		return "datalist";
	}
	
	
	public IoSession getCurrrenIoSession(String uid)
	{
		System.out.println("-------------------TimeServerHandler.sessions.size="+TimeServerHandler.sessions.size());
		for(IoSession iosession : TimeServerHandler.sessions)
		{
			//获取IOsession中的IP
			String sessionIP = ((InetSocketAddress)iosession.getRemoteAddress()).getAddress().getHostAddress();
			System.out.println("[IoSession]sessionIP="+sessionIP);
			if(iosession.getAttribute("uid")!=null){
				String sessionuid = (String) iosession.getAttribute("uid");
				if(sessionuid!=null&&sessionuid.equals(uid))
				{
					System.out.println("[CurrrenIoSession]sessionIP="+sessionIP);
					return iosession;
				}
			}
			
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		System.out.println(Float.intBitsToFloat(Integer.parseInt("4001A4A9",16)));
//	}
	//get、set-------------------------------------------
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
	public IProjectService getProjectService() {
		return projectService;
	}
	@Resource
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public IValveService getValveService() {
		return valveService;
	}
	@Resource
	public void setValveService(IValveService valveService) {
		this.valveService = valveService;
	}
	public Valve getValve() {
		return valve;
	}
	public void setValve(Valve valve) {
		this.valve = valve;
	}
	public List<Valve> getValves() {
		return valves;
	}
	public void setValves(List<Valve> valves) {
		this.valves = valves;
	}
	public LogInterceptor getLogInterceptor() {
		return logInterceptor;
	}
	@Resource
	public void setLogInterceptor(LogInterceptor logInterceptor) {
		this.logInterceptor = logInterceptor;
	}
	public IUserService getUserService() {
		return userService;
	}
	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IGatewayService getGatewayService() {
		return gatewayService;
	}

	@Resource
	public void setGatewayService(IGatewayService gatewayService) {
		this.gatewayService = gatewayService;
	}

	public String getLinetext() {
		return linetext;
	}

	public void setLinetext(String linetext) {
		this.linetext = linetext;
	}

	public List<Gateway> getGateways() {
		return gateways;
	}

	public void setGateways(List<Gateway> gateways) {
		this.gateways = gateways;
	}

	public IValvedataService getValvedataService() {
		return valvedataService;
	}

	@Resource
	public void setValvedataService(IValvedataService valvedataService) {
		this.valvedataService = valvedataService;
	}

	public int getValveid() {
		return valveid;
	}

	public void setValveid(int valveid) {
		this.valveid = valveid;
	}

	public List<Valvedata> getValvedatas() {
		return valvedatas;
	}

	public void setValvedatas(List<Valvedata> valvedatas) {
		this.valvedatas = valvedatas;
	}

	public int getFirstone() {
		return firstone;
	}

	public void setFirstone(int firstone) {
		this.firstone = firstone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
