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

import com.jlj.model.Command;
import com.jlj.model.Gateway;
import com.jlj.model.Project;
import com.jlj.model.User;
import com.jlj.service.ICommandService;
import com.jlj.service.IGatewayService;
import com.jlj.service.IProjectService;
import com.jlj.util.LogInterceptor;
import com.opensymphony.xwork2.ActionSupport;

@Component("commandAction")
@Scope("prototype")
public class CommandAction extends ActionSupport implements RequestAware,
SessionAware,ServletResponseAware,ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	private ICommandService commandService;
	private IGatewayService gatewayService;
	Map<String,Object> request;
	Map<String,Object> session;
	private javax.servlet.http.HttpServletResponse response;
	private javax.servlet.http.HttpServletRequest req;
	//单个对象
	private int id;
	private Command command;
	//分页显示
	private String[] arg=new String[2];
	private List<Command> commands;
	private int page;
	private final int size=10;
	private int pageCount;
	private int totalCount;
	
	private int projectid;//按项目id
	//条件
	private int con;
	private String convalue;
	
	/*
	 * service层对象
	 */
	private IProjectService projectService;
	/*
	 * 单个对象
	 */
	private Project project;
	
	/*
	 * list对象
	 */
	private List<Project> projects;
	/*
	 * 日志处理类
	 */
	private LogInterceptor logInterceptor;
	
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
		//获取项目id作为“查询命令”的条件
		projectid = user.getProject().getId();
//		System.out.println("2-----------------projectid = "+projectid);
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		if(page<1){
			page=1;
		}
		//总记录数
		totalCount=commandService.getTotalCount(con,convalue,user);
		//总页数
		pageCount=commandService.getPageCount(totalCount,size);
		if(page>pageCount&&pageCount!=0){
			page=pageCount;
		}
		//所有当前页记录对象
		commands=commandService.queryList(con,convalue,user,page,size);
		
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
	 * 跳转到命令添加页面
	 * @return
	 */
	private int gatewayid;
	private Gateway gateway;
	public String goToCommandSend(){
		//获取所有命令作为下拉菜单
		commands = commandService.getCommands();
		//获取网关对象
		gateway = gatewayService.loadById(gatewayid);
		return "sendpage";
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
		if (user.getLimits()!=0) {
			command.setProject(user.getProject());
		}
		logInterceptor.addLog("命令信息操作", user.getUsername()+"新增命令["+command.toString()+"]", command.getProject().getId());
		//保存到数据库
		commandService.add(command);
		arg[0]="commandAction!list";
		arg[1]="命令管理";
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
		command = commandService.loadById(id);
		logInterceptor.addLog("命令信息操作", user.getUsername()+"删除命令["+command.toString()+"]", command.getProject().getId());
		commandService.deleteById(id);
		
		arg[0]="commandAction!list";
		arg[1]="命令管理";
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
		logInterceptor.addLog("命令信息操作", user.getUsername()+"修改命令["+command.toString()+"]", command.getProject().getId());
		//修改数据库
		commandService.update(command);
		
		arg[0]="commandAction!list";
		arg[1]="命令管理";
		return SUCCESS;
	}
	/**
	 * 查看信息
	 * @return
	 */
	public String view(){
		command=commandService.loadById(id);
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
		
		command=commandService.loadById(id);
		return "load";
	}
	
	//get、set-------------------------------------------
	public ICommandService getCommandService() {
		return commandService;
	}
	@Resource
	public void setCommandService(ICommandService commandService) {
		this.commandService = commandService;
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
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public Command getCommand() {
		return command;
	}
	public List<Command> getCommands() {
		return commands;
	}
	public void setCommands(List<Command> commands) {
		this.commands = commands;
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
	public int getGatewayid() {
		return gatewayid;
	}
	public void setGatewayid(int gatewayid) {
		this.gatewayid = gatewayid;
	}
	public Gateway getGateway() {
		return gateway;
	}
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
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
	public LogInterceptor getLogInterceptor() {
		return logInterceptor;
	}
	@Resource
	public void setLogInterceptor(LogInterceptor logInterceptor) {
		this.logInterceptor = logInterceptor;
	}
	
	
	
}
