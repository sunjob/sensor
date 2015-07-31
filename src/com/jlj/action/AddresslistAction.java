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

import com.jlj.model.Addresslist;
import com.jlj.model.Project;
import com.jlj.model.User;
import com.jlj.service.IAddresslistService;
import com.jlj.service.IProjectService;
import com.jlj.util.LogInterceptor;
import com.opensymphony.xwork2.ActionSupport;

@Component("addresslistAction")
@Scope("prototype")
public class AddresslistAction extends ActionSupport implements RequestAware,
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
	private IAddresslistService addresslistService;
	
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
	private Project project;
	private Addresslist addresslist;
	
	/*
	 * list对象
	 */
	private List<Project> projects;
	private List<Addresslist> addresslists;
	/*
	 * 日志处理类
	 */
	private LogInterceptor logInterceptor;
	
	/**
	 * 通讯录管理
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		//判断会话是否失效
		User user=(User)session.get("user");
		if(user==null){
			return "opsessiongo";
		}
		//获取项目id作为查询的条件
		projectid = user.getProject().getId();
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		if(page<1){
			page=1;
		}
		//总记录数
		totalCount= addresslistService.getTotalCount(con,convalue,user);
		//总页数
		pageCount=addresslistService.getPageCount(totalCount,size);
		if(page>pageCount&&pageCount!=0){
			page=pageCount;
		}
		//所有当前页记录对象
		addresslists=addresslistService.queryList(con,convalue,user,page,size);
		
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
		if (user.getLimits()!=0) {
			addresslist.setProject(user.getProject());
		}
		//保存到数据库
		addresslistService.add(addresslist);
		logInterceptor.addLog("通讯录信息操作", user.getUsername()+"新增通讯录["+addresslist.toString()+"]", addresslist.getProject().getId());
		arg[0]="addresslistAction!list";
		arg[1]="通讯录管理";
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
		addresslist  = addresslistService.loadById(id);
		logInterceptor.addLog("通讯录信息操作", user.getUsername()+"删除通讯录["+addresslist.toString()+"]", addresslist.getProject().getId());
		addresslistService.deleteById(id);
		
		arg[0]="addresslistAction!list";
		arg[1]="通讯录管理";
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
		addresslistService.update(addresslist);
		logInterceptor.addLog("通讯录信息操作", user.getUsername()+"修改通讯录["+addresslist.toString()+"]", addresslist.getProject().getId());
		arg[0]="addresslistAction!list";
		arg[1]="通讯录管理";
		return SUCCESS;
	}
	/**
	 * 查看信息
	 * @return
	 */
	public String view(){
		addresslist=addresslistService.loadById(id);
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
		addresslist=addresslistService.loadById(id);
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
	public IAddresslistService getAddresslistService() {
		return addresslistService;
	}
	@Resource
	public void setAddresslistService(IAddresslistService addresslistService) {
		this.addresslistService = addresslistService;
	}
	public Addresslist getAddresslist() {
		return addresslist;
	}
	public void setAddresslist(Addresslist addresslist) {
		this.addresslist = addresslist;
	}
	public List<Addresslist> getAddresslists() {
		return addresslists;
	}
	public void setAddresslists(List<Addresslist> addresslists) {
		this.addresslists = addresslists;
	}
	public LogInterceptor getLogInterceptor() {
		return logInterceptor;
	}
	@Resource
	public void setLogInterceptor(LogInterceptor logInterceptor) {
		this.logInterceptor = logInterceptor;
	}
	
	
	
}
