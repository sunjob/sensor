package com.jlj.action;

import java.net.URLDecoder;
import java.util.Date;
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

import com.jlj.model.Operation;
import com.jlj.model.Project;
import com.jlj.model.User;
import com.jlj.service.IGatewayService;
import com.jlj.service.IOperationService;
import com.jlj.service.IProjectService;
import com.opensymphony.xwork2.ActionSupport;

@Component("operationAction")
@Scope("prototype")
public class OperationAction extends ActionSupport implements RequestAware,
SessionAware,ServletResponseAware,ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	private IOperationService operationService;
	private IGatewayService gatewayService;
	Map<String,Object> request;
	Map<String,Object> session;
	private javax.servlet.http.HttpServletResponse response;
	private javax.servlet.http.HttpServletRequest req;
	//单个对象
	private int id;
	
	//分页显示
	private String[] arg=new String[2];
	
	private int page;
	private final int size=10;
	private int pageCount;
	private int totalCount;
	
	private int projectid;//按项目id
	//条件
	private int con;
	private String convalue;
	private String startdate;
	private String enddate;
	
	/*
	 * service层对象
	 */
	private IProjectService projectService;
	/*
	 * 单个对象
	 */
	private Project project;
	private Operation operation;
	/*
	 * list对象
	 */
	private List<Project> projects;
	private List<Operation> operations;
	
	/**
	 * 命令管理
	 */
	public String list() throws Exception{
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
//			System.out.println("1-----------------session失效");
			return "opsessiongo";
		}
//		System.out.println("2-----------------projectid = "+projectid);
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		if(page<1){
			page=1;
		}
		//总记录数
		totalCount=operationService.getTotalCount(con,convalue,user,startdate,enddate);
		//总页数
		pageCount=operationService.getPageCount(totalCount,size);
		if(page>pageCount&&pageCount!=0){
			page=pageCount;
		}
		//所有当前页记录对象
		//操作类型convalue (0、登陆操作 1、用户信息操作 2、命令信息操作 3、通讯录信息操作 4、线路信息操作 5、网关信息操作 6、传感器信息操作 7、阀门控制信息操作 8、报警设置操作)
		operations=operationService.queryList(con,convalue,user,page,size,startdate,enddate);
		
		return "list";
	}
	/**
	 * 跳转到添加页面
	 * @return
	 */
	public String goToAdd(){
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		/*
		 * 当前操作用户权限划分
		 * 这里保留了需要显示项目名称的功能
		 */
		switch (usero.getLimits()) {
		case 0:
			projects = projectService.getProjects();
			break;
		case 1:
			if(projectid==0)
			{
				projectid = usero.getProject().getId();
			}
			project = projectService.getById(projectid);
			break;
		default:
			break;
		}
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
		//保存到数据库
		operationService.add(operation);
		
		arg[0]="operationAction!list";
		arg[1]="日志管理";
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		operationService.deleteById(id);
		
		arg[0]="operationAction!list";
		arg[1]="日志管理";
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
		//修改数据库
		operationService.update(operation);
		
		arg[0]="operationAction!list";
		arg[1]="日志管理";
		return SUCCESS;
	}
	/**
	 * 查看信息
	 * @return
	 */
	public String view(){
		operation=operationService.loadById(id);
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
		switch (usero.getLimits()) {
		case 0:
			projects = projectService.getProjects();
			break;
		case 1:
			if(projectid==0)
			{
				projectid = usero.getProject().getId();
			}
			project = projectService.getById(projectid);
			break;
		default:
			break;
		}
		
		operation=operationService.loadById(id);
		return "load";
	}
	
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
	
	public void setCommand(Operation operation) {
		this.operation = operation;
	}
	
	public Operation getCommand() {
		return operation;
	}
	public List<Operation> getCommands() {
		return operations;
	}
	public void setCommands(List<Operation> operations) {
		this.operations = operations;
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
	public IOperationService getOperationService() {
		return operationService;
	}
	@Resource
	public void setOperationService(IOperationService operationService) {
		this.operationService = operationService;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public Operation getOperation() {
		return operation;
	}
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	public List<Operation> getOperations() {
		return operations;
	}
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	
	
	
}
