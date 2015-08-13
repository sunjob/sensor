package com.jlj.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
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

import com.jlj.model.Line;
import com.jlj.model.Project;
import com.jlj.model.Sysconf;
import com.jlj.model.User;
import com.jlj.service.ILineService;
import com.jlj.service.IProjectService;
import com.jlj.service.ISysconfService;
import com.jlj.service.IUserService;
import com.jlj.util.LogInterceptor;
import com.jlj.vo.GatewayVO;
import com.jlj.vo.UserVO;
import com.opensymphony.xwork2.ActionSupport;

@Component("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport implements RequestAware,
		SessionAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private ILineService lineService;
	private IUserService userService;
	private ISysconfService sysconfService;
	Map<String, Object> request;
	Map<String, Object> session;
	private javax.servlet.http.HttpServletResponse response;
	private javax.servlet.http.HttpServletRequest req;
	// 单个对象
	private int id;
	private User user;
	// 分页显示
	private String[] arg = new String[2];
	
	private int page;
	private final int size = 10;
	private int pageCount;
	private int totalCount;
	private int status;// 按状态
	private int pid;// 按用户id
	// 条件
	private int con;
	private String convalue;
	//登陆-用户名、密码、验证码参数
	private String username;
	private String password;
	private String validate;
	
	/*
	 * 其他for jsp显示
	 */
	private String projectName;
	/*
	 * service层对象
	 */
	private IProjectService projectService;
	
	/*
	 * 单个对象
	 */
	private Project project;
	private UserVO uservo;
	
	/*
	 * list对象
	 */
	private List<Project> projects;
	private List<Line> lines;
	private List<Line> userlines;
	private List<User> users;
	private List<UserVO> uservos;
	
	/*
	 * 日志处理类
	 */
	private LogInterceptor logInterceptor;
	
	//用户权限为了显示线路所用
	private int ulimit;
	/**
	 * 用户登陆
	 */
	public String login(){
		//sysconf
		Sysconf sysconf = sysconfService.loadById(1);
		if(sysconf==null){
			String loginfail="系统配置错误，请联系管理员";
			request.put("loginFail", loginfail);
			return "adminLogin";
		}
		if(sysconf.getStatus()!=null&&sysconf.getStatus()!=1){
			String loginfail="系统配置过期，请联系管理员";
			request.put("loginFail", loginfail);
			return "adminLogin";
		}
		Date today = new Date();
		if(today.after(sysconf.getDeadline())){
			sysconfService.updateStatus(0,1);
			String loginfail="系统配置过期，请联系管理员";
			request.put("loginFail", loginfail);
			return "adminLogin";
		}
		
		if(username==null||username.equals("")||password==null||password.equals("")){
			String loginfail="用户名或密码不能为空";
			request.put("loginFail", loginfail);
			return "adminLogin";
		}
		User user=userService.userlogin(username,password);
		String code=(String) session.get("code");
		if(user==null){
			String loginfail="用户名或密码输入有误";
			request.put("loginFail", loginfail);
			return "adminLogin";
		}else if(validate==null||!validate.equalsIgnoreCase(code)){
			String loginfail="验证码输入有误";
			request.put("loginFail", loginfail);
			return "adminLogin";
		}else{
			session.put("user", user);
			String ip = getIpAddr(req);
			logInterceptor.addLog("登陆操作", user.getUsername()+"登陆,IP地址为："+ip, user.getProject().getId());
			return "loginSucc";
		}
	}
	/*
	 * 获取IP地址
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	 }
	/**
	 * 用户注销
	 */
	public String logout()
	{
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		logInterceptor.addLog("注销操作", usero.getUsername()+"注销", usero.getProject().getId());
		session.clear();
		return "adminLogin";
	}
	
	private int projectid;
	/**
	 * 用户管理
	 */
	public String list() throws Exception {
		//判断会话是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}

		if (convalue != null && !convalue.equals("")) {
			convalue = URLDecoder.decode(convalue, "utf-8");
		}
		if (page < 1) {
			page = 1;
		}
		// 总记录数
		totalCount = userService.getTotalCount( con, convalue, usero);
		// 总页数
		pageCount = userService.getPageCount(totalCount, size);
		if (page > pageCount && pageCount != 0) {
			page = pageCount;
		}
		// 所有当前页记录对象
		users = userService.queryList(con, convalue, usero, page, size);
		setUserVOsForJSP();
		return "list";
	}

	/**
	 * 运输作用 用户管理显示
	 */
	private void setUserVOsForJSP() {
		// TODO Auto-generated method stub
		if(users!=null&&users.size()>0)
		{
			uservos = new ArrayList<UserVO>();
			for(User usero:users)
			{
				uservo = new UserVO();
				uservo.setId(usero.getId());
				uservo.setUsername(usero.getUsername());
				if(usero.getProject()!=null)
				{
					uservo.setProjectName(usero.getProject().getName());
				}
				uservo.setLimits(usero.getLimits());
				if(usero.getUpuserid()!=null)
				{
					User userup = userService.getUserById(usero.getUpuserid());
					if(userup!=null)
					{
						uservo.setUpuserName(userup.getUsername());
					}
				}
				uservos.add(uservo);
			}
		}
	}

	/** 
	 * 跳转到添加页面
	 * 
	 * @return
	 */
	public String goToAdd() {
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
			/*
			 * 前台选择项目
			 */
			if(projectid==0)
			{
				projectid = usero.getProject().getId();
			}
			lines = lineService.getNotestLinesByPid(projectid);
			projects = projectService.getProjects();
			break;
		case 1:
			projectid = usero.getProject().getId();
			lines = lineService.getNotestLinesByPid(projectid);
			project = projectService.getById(projectid);
			break;
		default:
			break;
		}
		return "add";
	}

	/**
	 * 添加
	 * 
	 * @return
	 * @throws Exception
	 */

	public String add() throws Exception {
		//判断会话是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		
		//根据权限设置用户所属项目
		if (usero.getLimits()!=0) {
			user.setProject(usero.getProject());
		}
		
		
		user.setCreatedate(new Date());//注册日期
		user.setUpuserid(usero.getId());
		
		
		/*
		 * 重新组装user的linetext
		 */
		String linetext = "";
		//根据用户权限，是否显示测试线路
		if (user.getLimits()<=1) {
			Line linetest = lineService.getLineByProjectidAndOrderid(user.getProject().getId(),1);
			
			if(linetest!=null)
			{
				 linetext = linetest.getId()+"";
				 if(user.getLinetext()!=null){
						linetext +=","+user.getLinetext();
					}
			}
		}else
		{
			linetext = user.getLinetext();
		}
		logInterceptor.addLog("用户信息操作", usero.getUsername()+"新增用户["+user.toString()+"]", user.getProject().getId());
		
		userService.add(user);
		
		
		
		arg[0] = "userAction!list";
		arg[1] = "用户管理";
		return SUCCESS;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String delete() {
		//判断会话是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		user = userService.loadById(id);
		logInterceptor.addLog("用户信息操作", usero.getUsername()+"删除用户["+user.toString()+"]", user.getProject().getId());
		userService.deleteById(id);
		arg[0] = "userAction!list";
		arg[1] = "用户管理";
		return SUCCESS;
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	public String update() throws Exception {
		//判断会话是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		/*
		 * 重新组装user的linetext
		 */
		String linetext = "";
		//根据用户权限，是否显示测试线路
		if (user.getLimits()<=1) {
			Line linetest = lineService.getLineByProjectidAndOrderid(user.getProject().getId(),1);
			
			if(linetest!=null)
			{
				 linetext = linetest.getId()+"";
				 if(user.getLinetext()!=null){
						linetext +=","+user.getLinetext();
					}
			}
		}else
		{
			linetext = user.getLinetext();
		}
		
		user.setLinetext(linetext);
		logInterceptor.addLog("用户信息操作", usero.getUsername()+"修改用户["+user.toString()+"]", user.getProject().getId());
		user.setLimits(ulimit);
		userService.update(user);
		arg[0] = "userAction!list";
		arg[1] = "用户管理";
		return SUCCESS;
	}
	
	/**
	 * 跳转到修改秒页面
	 * 
	 * @return
	 */
	public String loadPassword() throws Exception {
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		password = usero.getPassword();
		return "password";
	}
	/**
	 * 修改密码
	 * 
	 * @return
	 */
	public String updatePassword() throws Exception {
		//判断会话是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		usero.setPassword(password);
		userService.update(usero);
		if(usero.getLimits()<2)
		{
			arg[0] = "userAction!list";
			arg[1] = "用户管理";
		}else
		{
			arg[0] = "index.html";
			arg[1] = "主";
		}
		return SUCCESS;
	}

	/**
	 * 查看信息
	 * 
	 * @return
	 */
	public String view() {
		user=userService.loadById(id);
		return "view";
	}

	/**
	 * 跳转到修改页面
	 * 
	 * @return
	 */
	private int isChanging;//0：表示没有在选择用户权限 1：表示正在切换用户权限
	public String load() throws Exception {
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		//获取项目id作为查询的条件
		user=userService.loadById(id);//当前修改用户的id
		
		if(isChanging==0)
		{
			ulimit = user.getLimits();
		}
		/*
		 * 当前操作用户权限划分
		 */
		switch (usero.getLimits()) {
		case 0:
			if(projectid==0)
			{
				projectid = user.getProject().getId();//系统管理员 获得当前用户的项目ID
			}
			lines = lineService.getNotestLinesByPid(projectid);
			userlines = lineService.getLinesByLinetext(projectid,user.getLinetext());
			projects = projectService.getProjects();
			break;
		case 1:
			if(projectid==0)
			{
				projectid = usero.getProject().getId();//高级管理员 获得当前用户的项目ID
			}
			project = projectService.getById(projectid);
			lines = lineService.getNotestLinesByPid(projectid);
			userlines = lineService.getLinesByLinetext(projectid,user.getLinetext());
			break;
		default:
			break;
		}
		return "load";
	}

	/**
	 * 检查用户名是否存在
	 */
	public String checkUsername()
	{
		user = userService.getUserByUsername(username);
		if(user!=null)
		{
			UserVO userVO = new UserVO();
			userVO.setMessage("该用户名已经存在,请重新输入.");
			JSONObject jsonObj = JSONObject.fromObject(userVO);
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
	// get、set-------------------------------------------
	public ILineService getLineService() {
		return lineService;
	}

	@Resource
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}

	// 获得HttpServletResponse对象
	public void setServletResponse(HttpServletResponse response) {
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String[] getArg() {
		return arg;
	}

	public void setArg(String[] arg) {
		this.arg = arg;
	}

	public IUserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	public ISysconfService getSysconfService() {
		return sysconfService;
	}
	@Resource
	public void setSysconfService(ISysconfService sysconfService) {
		this.sysconfService = sysconfService;
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

	public List<Line> getLines() {
		return lines;
	}

	public void setLines(List<Line> lines) {
		this.lines = lines;
	}

	public UserVO getUservo() {
		return uservo;
	}

	public void setUservo(UserVO uservo) {
		this.uservo = uservo;
	}

	public List<UserVO> getUservos() {
		return uservos;
	}

	public void setUservos(List<UserVO> uservos) {
		this.uservos = uservos;
	}

	public List<Line> getUserlines() {
		return userlines;
	}

	public void setUserlines(List<Line> userlines) {
		this.userlines = userlines;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public LogInterceptor getLogInterceptor() {
		return logInterceptor;
	}
	@Resource
	public void setLogInterceptor(LogInterceptor logInterceptor) {
		this.logInterceptor = logInterceptor;
	}

	public int getUlimit() {
		return ulimit;
	}

	public void setUlimit(int ulimit) {
		this.ulimit = ulimit;
	}
	public javax.servlet.http.HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(javax.servlet.http.HttpServletResponse response) {
		this.response = response;
	}
	public javax.servlet.http.HttpServletRequest getReq() {
		return req;
	}
	public void setReq(javax.servlet.http.HttpServletRequest req) {
		this.req = req;
	}
	public int getIsChanging() {
		return isChanging;
	}
	public void setIsChanging(int isChanging) {
		this.isChanging = isChanging;
	}

	
	
}
