package com.jlj.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jlj.model.Alarm;
import com.jlj.model.Command;
import com.jlj.model.Gateway;
import com.jlj.model.Line;
import com.jlj.model.Project;
import com.jlj.model.User;
import com.jlj.service.IAlarmService;
import com.jlj.service.ICommandService;
import com.jlj.service.IGatewayService;
import com.jlj.service.ILineService;
import com.jlj.service.IProjectService;
import com.jlj.util.LogInterceptor;
import com.jlj.vo.AjaxMessage;
import com.jlj.vo.UserVO;
import com.opensymphony.xwork2.ActionSupport;

@Component("projectAction")
@Scope("prototype")
public class ProjectAction extends ActionSupport implements RequestAware,
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
	private ICommandService commandService;
	private IGatewayService gatewayService;
	private IProjectService projectService;
	private ILineService lineService;
	private LogInterceptor logInterceptor;
	private IAlarmService alarmService;
	
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
	
	/*
	 * 单个对象
	 */
	private Command command;
	private Project project;
	private Line line;
	private Alarm alarm;
	
	/*
	 * list对象
	 */
	private List<Command> commands;
	private List<Project> projects;
	
	
	/**
	 * 项目管理
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		//获取项目id作为“查询命令”的条件
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		if(page<1){
			page=1;
		}
		//总记录数
		totalCount= projectService.getTotalCount(con,convalue);
		//总页数
		pageCount=projectService.getPageCount(totalCount,size);
		if(page>pageCount&&pageCount!=0){
			page=pageCount;
		}
		//所有当前页记录对象
		projects=projectService.queryList(con,convalue,page,size);
		
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
	 * 跳转到项目添加页面
	 * @return
	 */
	private int gatewayid;
	private Gateway gateway;
	public String goToCommandSend(){
		//获取所有项目作为下拉菜单
		projects = projectService.getProjects();
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
		//保存到数据库
		Date createTime = new Date();
		project.setCreatetime(createTime);
		projectService.add(project);
		
		initTestLine(project);//初始化
		initAlarmSet(project);//初始化报警设置
		arg[0]="projectAction!list";
		arg[1]="项目管理";
		return SUCCESS;
	}
	/*
	 * 新增项目 新增报警设置
	 */
	private void initAlarmSet(Project project) {
		// TODO Auto-generated method stub
		List<Alarm> alarmSets = alarmService.getProjectAlarmSet(project.getId());
		if(alarmSets==null||alarmSets.size()<1)
		{
			Alarm alarm = new Alarm();
			alarm.setTemp(30.00f);
			alarm.setTemptime(5);
			alarm.setVoltage(1.50f);
			alarm.setVoltagetime(5);
			alarm.setSmstemplate("【能源物联网】#日期时间#：数据超限，请尽快查看！#项目#-#线路#-#网关#-#传感器编号#-#温度#");
			alarm.setProject(project);
			try {
				alarmService.add(alarm);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	/*
	 * 新增项目 新增测试线路
	 */
	private void initTestLine(Project project) throws Exception {
		// TODO Auto-generated method stub
		if(lineService.getProjectTestLine(project.getId())==null)
		{
			Line testline = new Line();
			testline.setOrderid(1);
			testline.setName("测试线路");
			testline.setProject(project);
			
			lineService.add(testline);
		}
		
	}
	
	/*
	 * 删除项目 新增测试线路
	 */
	private void deleteTestLine(int pid) throws Exception {
		// TODO Auto-generated method stub
		line = lineService.getProjectTestLine(pid);
		if(line!=null)
		{
			lineService.delete(line);
		}
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception 
	 */
	public String delete() throws Exception{
		deleteTestLine(id);
		projectService.deleteById(id);
		arg[0]="projectAction!list";
		arg[1]="项目管理";
		
		
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
		projectService.update(project);
		
		arg[0]="projectAction!list";
		arg[1]="项目管理";
		return SUCCESS;
	}
	/**
	 * 查看信息
	 * @return
	 */
	public String view(){
		project=projectService.loadById(id);
		return "view";
	}
	/**
	 * 跳转到修改页面
	 * @return
	 */
	public String load() throws Exception{
		project=projectService.loadById(id);
		return "load";
	}
	
	
	
	/**
	 * 查看我的项目信息
	 * @return
	 */
	public String viewMyProject(){
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		//保存到数据库
		project = projectService.loadById(user.getProject().getId());
		return "myproject";
	}
	/**
	 * 跳转到修改我的项目页面
	 * @return
	 */
	public String loadMyProject() throws Exception{
		project=projectService.loadById(id);
		return "loadmyproject";
	}
	
	/**
	 * 修改我的项目页面
	 * @return
	 */
	public String updateMyProject() throws Exception{
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		//修改数据库
		projectService.update(project);
		
		arg[0]="projectAction!viewMyProject?id="+user.getProject().getId();
		arg[1]="我的项目信息";
		return SUCCESS;
	}
	
	
	/**
	 * 检查用户名是否存在
	 */
	private String projectname;
	public String checkProjectname()
	{
		project = projectService.getProjectByName(projectname);
		if(project!=null)
		{
			AjaxMessage ajaxmsg = new AjaxMessage();
			ajaxmsg.setMessage("该项目名称已经存在,请重新输入.");
			JSONObject jsonObj = JSONObject.fromObject(ajaxmsg);
			PrintWriter out;
			try {
				response.setContentType("text/html;charset=UTF-8");
				out = response.getWriter();
				out.print(jsonObj.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return  null;
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
	public ILineService getLineService() {
		return lineService;
	}
	@Resource
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	public LogInterceptor getLogInterceptor() {
		return logInterceptor;
	}
	public void setLogInterceptor(LogInterceptor logInterceptor) {
		this.logInterceptor = logInterceptor;
	}
	public Line getLine() {
		return line;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	public IAlarmService getAlarmService() {
		return alarmService;
	}
	@Resource
	public void setAlarmService(IAlarmService alarmService) {
		this.alarmService = alarmService;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	
	
	
}
