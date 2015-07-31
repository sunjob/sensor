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

import com.jlj.model.Project;
import com.jlj.model.Receivelog;
import com.jlj.model.User;
import com.jlj.service.IProjectService;
import com.jlj.service.IReceivelogService;
import com.jlj.service.IUserService;
import com.jlj.util.LogInterceptor;
import com.opensymphony.xwork2.ActionSupport;

@Component("receivelogAction")
@Scope("prototype")
public class ReceivelogAction extends ActionSupport implements RequestAware,
SessionAware,ServletResponseAware,ServletRequestAware {
	
	private static final long serialVersionUID = 1L;
	
	Map<String,Object> request;
	Map<String,Object> session;
	private javax.servlet.http.HttpServletResponse response;
	private javax.servlet.http.HttpServletRequest req;
	
	//分页显示
	private String[] arg=new String[2];
	private List<Receivelog> receivelogs;
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
	private IReceivelogService receivelogService;
	private IUserService userService;
	private IProjectService projectService;
	/*
	 * 单个对象
	 */
	private int id;
	private Receivelog receivelog;
	private Project project;
	
	
	/**
	 * 应答日志
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
		totalCount=receivelogService.getProjectTotalCount(con,convalue,projectid);
		//总页数
		pageCount=receivelogService.getPageCount(totalCount, size);
		if(page>pageCount&&pageCount!=0){
			page=pageCount;
		}
		//所有当前页记录对象
		receivelogs=receivelogService.queryProjectList(con,convalue,projectid,page,size);
		return "list";
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
		receivelog=receivelogService.loadById(id);
		receivelogService.delete(receivelog);
		arg[0]="receivelogAction!list";
		arg[1]="应答日志";
		return SUCCESS;
	}
	
	//get、set-------------------------------------------
	public IReceivelogService getReceivelogService() {
		return receivelogService;
	}
	@Resource
	public void setReceivelogService(IReceivelogService receivelogService) {
		this.receivelogService = receivelogService;
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
	
	public void setReceivelog(Receivelog receivelog) {
		this.receivelog = receivelog;
	}
	
	public Receivelog getReceivelog() {
		return receivelog;
	}
	public List<Receivelog> getReceivelogs() {
		return receivelogs;
	}
	public void setReceivelogs(List<Receivelog> receivelogs) {
		this.receivelogs = receivelogs;
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

	
	
	
}
