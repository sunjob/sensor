package com.jlj.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
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
import com.jlj.model.User;
import com.jlj.service.ILineService;
import com.jlj.service.IProjectService;
import com.jlj.service.IUserService;
import com.jlj.util.LogInterceptor;
import com.jlj.vo.AjaxMessage;
import com.jlj.vo.UserVO;
import com.opensymphony.xwork2.ActionSupport;

@Component("lineAction")
@Scope("prototype")
public class LineAction extends ActionSupport implements RequestAware,
SessionAware,ServletResponseAware,ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	private ILineService lineService;
	private IUserService userService;
	Map<String,Object> request;
	Map<String,Object> session;
	private javax.servlet.http.HttpServletResponse response;
	private javax.servlet.http.HttpServletRequest req;
	//单个对象
	private int id;
	private Line line;
	//分页显示
	private String[] arg=new String[2];
	private List<Line> lines;
	private int page;
	private final int size=10;
	private int pageCount;
	private int totalCount;
	//条件
	private int con;
	private String convalue;
	//当前项目
	private int projectid;
	
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
	
	public String menulist(){
		lines = lineService.getLines();
		request.put("lines", lines);
		return NONE;
	}
	
	
	/**
	 * 线路管理
	 */
	public String list() throws Exception{
		//判断会话是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		//获取项目id作为查询的条件
		projectid = usero.getProject().getId();
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		if(page<1){
			page=1;
		}
		//总记录数
		totalCount=lineService.getTotalCount(con,convalue,usero);
		//总页数
		pageCount=lineService.getPageCount(totalCount, size);
		if(page>pageCount&&pageCount!=0){
			page=pageCount;
		}
		//所有当前页记录对象
		lines=lineService.queryList(con,convalue,page,size,usero);
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
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		
		if (user.getLimits()!=0) {
			line.setProject(user.getProject());
		}
		lineService.add(line);
		
		logInterceptor.addLog("线路信息操作", user.getUsername()+"新增线路["+line.toString()+"]", line.getProject().getId());
		arg[0]="lineAction!list";
		arg[1]="线路管理";
		return SUCCESS;
	}
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		line=lineService.loadById(id);
		logInterceptor.addLog("线路信息操作", user.getUsername()+"删除线路["+line.toString()+"]", line.getProject().getId());
		lineService.delete(line);
		arg[0]="lineAction!list";
		arg[1]="线路管理";
		return SUCCESS;
	}
	/**
	 * 修改
	 * @return
	 */
	public String update() throws Exception{
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		lineService.update(line);
		logInterceptor.addLog("线路信息操作", user.getUsername()+"修改线路["+line.toString()+"]", line.getProject().getId());
		arg[0]="lineAction!list";
		arg[1]="线路管理";
		return SUCCESS;
	}
	/**
	 * 查看信息
	 * @return
	 */
	public String view(){
		line=lineService.loadById(id);
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
			//获取项目id作为查询的条件
			if(projectid==0)
			{
				projectid = usero.getProject().getId();
			}
			project = projectService.getById(projectid);
			break;
		default:
			break;
		}
		line=lineService.loadById(id);
		return "load";
	}
	/**
	 * 实时监控显示
	 * @return
	 * @throws Exception 
	 */
	private int limits;
	private String linetext;
	private int upuserid;
	public String monitorshow() throws Exception{
		//判断会话是否失效
//		User user=(User)session.get("user");
//		if(user==null){
//			return "opsessiongo";
//		}
		//判断该用户的权限
//		int limits = user.getLimits();
		if(limits==0){
			//系统管理员，分页显示所有的线路========================
			if(convalue!=null&&!convalue.equals("")){
				convalue=URLDecoder.decode(convalue, "utf-8");
			}
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=lineService.getTotalCount(con,convalue);
			//总页数
			pageCount=lineService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			lines=lineService.queryList(con,convalue,page,size);
			
		}else if(limits==1){
			//超级管理员，查看该项目的所有线路===============================
//			projectid = user.getProject().getId();
			if(convalue!=null&&!convalue.equals("")){
				convalue=URLDecoder.decode(convalue, "utf-8");
			}
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=lineService.getProjectTotalCount(con,convalue,projectid);
			//总页数
			pageCount=lineService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			lines=lineService.queryProjectList(con,convalue,projectid,page,size);
		}else if(limits==2){
			//普通管理员，查看该管理员所管理的线路===============================
//			linetext = user.getLinetext();
			if(convalue!=null&&!convalue.equals("")){
				convalue=URLDecoder.decode(convalue, "utf-8");
			}
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=lineService.getManageTotalCount(con,convalue,linetext);
			//总页数
			pageCount=lineService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			lines=lineService.queryManageList(con,convalue,linetext,page,size);
		}else if(limits==3){
			//普通用户，查看他的上级管理员所管理的线路===============================
//			User upuser = userService.loadById(user.getUpuserid());
			User upuser = userService.loadById(upuserid);
			if(upuser!=null){
				linetext = upuser.getLinetext();
				if(convalue!=null&&!convalue.equals("")){
					convalue=URLDecoder.decode(convalue, "utf-8");
				}
				if(page<1){
					page=1;
				}
				//总记录数
				totalCount=lineService.getManageTotalCount(con,convalue,linetext);
				//总页数
				pageCount=lineService.getPageCount(totalCount,size);
				if(page>pageCount&&pageCount!=0){
					page=pageCount;
				}
				//所有当前页记录对象
				lines=lineService.queryManageList(con,convalue,linetext,page,size);
			}else{
				System.out.println("-------------无上级用户---------");
				return NONE;
			}
		}
		return "monitorshow";
	}
	
	public String monitorshowformobile() throws Exception{
		//判断会话是否失效
//		User user=(User)session.get("user");
//		if(user==null){
//			return "opsessiongo";
//		}
		//判断该用户的权限
//		int limits = user.getLimits();
		if(limits==0){
			//系统管理员，分页显示所有的线路========================
			if(convalue!=null&&!convalue.equals("")){
				convalue=URLDecoder.decode(convalue, "utf-8");
			}
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=lineService.getTotalCount(con,convalue);
			//总页数
			pageCount=lineService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			lines=lineService.queryList(con,convalue,page,size);
			
		}else if(limits==1){
			//超级管理员，查看该项目的所有线路===============================
//			projectid = user.getProject().getId();
			if(convalue!=null&&!convalue.equals("")){
				convalue=URLDecoder.decode(convalue, "utf-8");
			}
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=lineService.getProjectTotalCount(con,convalue,projectid);
			//总页数
			pageCount=lineService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			lines=lineService.queryProjectList(con,convalue,projectid,page,size);
		}else if(limits==2){
			//普通管理员，查看该管理员所管理的线路===============================
//			linetext = user.getLinetext();
			if(convalue!=null&&!convalue.equals("")){
				convalue=URLDecoder.decode(convalue, "utf-8");
			}
			if(page<1){
				page=1;
			}
			//总记录数
			totalCount=lineService.getManageTotalCount(con,convalue,linetext);
			//总页数
			pageCount=lineService.getPageCount(totalCount,size);
			if(page>pageCount&&pageCount!=0){
				page=pageCount;
			}
			//所有当前页记录对象
			lines=lineService.queryManageList(con,convalue,linetext,page,size);
		}else if(limits==3){
			//普通用户，查看他的上级管理员所管理的线路===============================
//			User upuser = userService.loadById(user.getUpuserid());
			User upuser = userService.loadById(upuserid);
			if(upuser!=null){
				linetext = upuser.getLinetext();
				if(convalue!=null&&!convalue.equals("")){
					convalue=URLDecoder.decode(convalue, "utf-8");
				}
				if(page<1){
					page=1;
				}
				//总记录数
				totalCount=lineService.getManageTotalCount(con,convalue,linetext);
				//总页数
				pageCount=lineService.getPageCount(totalCount,size);
				if(page>pageCount&&pageCount!=0){
					page=pageCount;
				}
				//所有当前页记录对象
				lines=lineService.queryManageList(con,convalue,linetext,page,size);
			}else{
				System.out.println("-------------无上级用户---------");
				return NONE;
			}
		}
		return "monitorshowformobile";
	}
	
	private String linename;
	public String checkLinename()
	{
		line = lineService.getLineByNameAndProject(linename,projectid);
		if(line!=null)
		{
			AjaxMessage ajaxmsg = new AjaxMessage();
			ajaxmsg.setMessage("该线路名称已经存在,请重新输入.");
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
	public ILineService getLineService() {
		return lineService;
	}
	@Resource
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
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
	
	public void setLine(Line line) {
		this.line = line;
	}
	
	public Line getLine() {
		return line;
	}
	public List<Line> getLines() {
		return lines;
	}
	public void setLines(List<Line> lines) {
		this.lines = lines;
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


	public String getLinetext() {
		return linetext;
	}


	public void setLinetext(String linetext) {
		this.linetext = linetext;
	}


	public IUserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
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


	public String getLinename() {
		return linename;
	}


	public void setLinename(String linename) {
		this.linename = linename;
	}
	
	
}
