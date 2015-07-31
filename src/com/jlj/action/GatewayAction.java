package com.jlj.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mina.CRC_16;
import mina.DataConvertor;
import mina.TimeServerHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.mina.core.session.IoSession;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jlj.model.Command;
import com.jlj.model.Gateway;
import com.jlj.model.Line;
import com.jlj.model.Project;
import com.jlj.model.User;
import com.jlj.service.ICommandService;
import com.jlj.service.IGatewayService;
import com.jlj.service.ILineService;
import com.jlj.service.IProjectService;
import com.jlj.util.DateTimeKit;
import com.jlj.util.LogInterceptor;
import com.jlj.vo.GatemapStatus;
import com.jlj.vo.GatewayStatus;
import com.jlj.vo.GatewayVO;
import com.opensymphony.xwork2.ActionSupport;

@Component("gatewayAction")
@Scope("prototype")
public class GatewayAction extends ActionSupport implements RequestAware,
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
	private ILineService lineService;
	private ICommandService commandService;
	
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
	private int isChannelUpdated;
	/*
	 * json 提交字段
	 */
	private int gateaddress;
	private int channel;//通道数据（0~255）
	/*
	 * 单个对象
	 */
	private Project project;
	private Gateway gateway;
	
	/*
	 * list对象
	 */
	private List<Project> projects;
	private List<Gateway> gateways;
	private List<Line> lines;
	private List<Line> userlines;
	private List<Command> commands;
	/*
	 * 日志处理类
	 */
	private LogInterceptor logInterceptor;
	/**
	 * 网关管理
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		//判断会话是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		//获取项目id作为查询的条件
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		if(page<1){
			page=1;
		}
		//总记录数
		totalCount= gatewayService.getTotalCount(con,convalue,usero);
		//总页数
		pageCount=gatewayService.getPageCount(totalCount,size);
		if(page>pageCount&&pageCount!=0){
			page=pageCount;
		}
		//所有当前页记录对象
		gateways=gatewayService.queryList(con,convalue,usero,page,size);
		
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
		//保存到数据库
		gatewayService.add(gateway);
		arg[0]="gatewayAction!list";
		arg[1]="网关管理";
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
		gateway =  gatewayService.loadById(id);
		logInterceptor.addLog("网关信息操作", user.getUsername()+"删除网管["+gateway.toString()+"]", gateway.getLine().getProject().getId());
		gatewayService.deleteById(id);
		
		arg[0]="gatewayAction!list";
		arg[1]="网关管理";
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
		
//		int channel = 0;//通道数据（0~255）
//		int gateaddress = 0;//网关地址（即协议文件中的设备地址0xXX）
//		if(gateway.getChannel()!=null)
//		{
//			channel = gateway.getChannel();//通道数据（0~255）
//		}
//		if(gateway.getGateaddress()!=null)
//		{
//			gateaddress = gateway.getGateaddress();//网关地址（即协议文件中的设备地址0xXX）
//		}
//		String gatewayIP = gateway.getIp();//最新的网关表中的ip地址
//		
//		//1-判断无线数据通道channel是否被改变，若已修改发送信道配置命令(网关配置命令)-for LQ-----------------
//		System.out.println(isChannelUpdated);
//		if(isChannelUpdated==1){
//			//true：已确认通道数据（channel）被修改,发送命令-for SL-----------------
//			//（设备地址0xXX:gateaddress  功能代码0x43  数据长度0x01  通道数据0xXX:channel  CRC16:2个字节）
//			//0xXX 0x43 0x01 0xXX 0xXX 0xXX
//			/*
//			SL代码
//			*/
//			
//			byte send_byte[] = new byte[4];
//			send_byte[0] = (byte) gateaddress;
//			send_byte[1] = 0x43;
//			send_byte[2] = 0x01;
//			send_byte[3] = (byte) channel;
//			
//			byte send_byte_final[] = CRC_16.getSendBuf(DataConvertor.toHexString(send_byte));
//			
//			for (int i = 0; i < send_byte_final.length; i++) {
//				System.out.println("gateway action send_byte_final["+i+"] is "+send_byte_final[i]);
//			}
//			
//			//发送命令
//			String uid = gateway.getMacaddress();
//			if(uid!=null&&!uid.equals("")){
//				IoSession currentSession = this.getCurrrenIoSession(uid);
//				if(currentSession!=null){
//					currentSession.write(send_byte_final);
//				}
//			}
//			
//		}
		
		if(picture!=null){
			String imageName=DateTimeKit.getDateRandom()+pictureFileName.substring(pictureFileName.indexOf("."));
			System.out.println(imageName);
			this.upload(imageName);
			File photofile=new File(ServletActionContext.getServletContext().getRealPath("/")+gateway.getStreetpic());
			photofile.delete();
			gateway.setStreetpic("/"+imageName);
		}
		//2-修改数据库
		
		logInterceptor.addLog("网关信息操作", user.getUsername()+"修改网关["+gateway.toString()+"]", gateway.getLine().getProject().getId());
		gatewayService.update(gateway);
		
		arg[0]="gatewayAction!list";
		arg[1]="网关管理";
		return SUCCESS;
	}
	
	public IoSession getCurrrenIoSession(String uid)
	{
//		System.out.println("-------------------TimeServerHandler.sessions.size="+TimeServerHandler.sessions.size());
		for(IoSession iosession : TimeServerHandler.sessions)
		{
			//获取IOsession中的IP
			String sessionIP = ((InetSocketAddress)iosession.getRemoteAddress()).getAddress().getHostAddress();
//			System.out.println("[IoSession]sessionIP="+sessionIP);
			if(iosession.getAttribute("uid")!=null){
				String sessionuid = (String) iosession.getAttribute("uid");
				if(sessionuid!=null&&sessionuid.equals(uid))
				{
//					System.out.println("[CurrrenIoSession]sessionIP="+sessionIP);
					return iosession;
				}
			}
			
		}
		return null;
		
//		for(IoSession iosession : TimeServerHandler.sessions)
//		{
//			//获取IOsession中的IP
//			String sessionIP = ((InetSocketAddress)iosession.getRemoteAddress()).getAddress().getHostAddress();
//			System.out.println("[CurrrenIoSession]sessionIP="+sessionIP+",gatewayIP="+gatewayIP);
//			if(sessionIP!=null&&sessionIP.equals(gatewayIP))
//			{
//				return iosession;
//			}
//		}
//		return null;
	}
	
	/**
	 * 查看信息
	 * @return
	 */
	public String view(){
		gateway=gatewayService.loadById(id);
		return "view";
	}
	
	public String loadchannel(){
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		//获取项目id作为查询的条件
		gateway=gatewayService.getGatewayById(id);//当前修改用户的id
		if(gateway.getGateaddress()==null||gateway.getGateaddress()==0){
			request.put("errorInfo", "请先分配网关地址");
			return "operror";
			
		}else{
			return "loadchannel";
		}
		
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
		//获取项目id作为查询的条件
		gateway=gatewayService.getGatewayById(id);//当前修改用户的id
		/*
		 * 当前操作用户权限划分
		 */
		switch (usero.getLimits()) {
		case 0:
			if(projectid==0)
			{
				projectid =gateway.getLine().getProject().getId();//系统管理员 获得当前网关的项目ID
			}
			lines = lineService.getLinesByPid(projectid);//所有线路
			projects = projectService.getProjects();//首先是获得全部的项目
			break;
		case 1:
			if(projectid==0)
			{
				projectid = usero.getProject().getId();//高级管理员 获得当前用户的项目ID
			}
			project = projectService.getById(projectid);
			lines = lineService.getLinesByPid(projectid);
			break;
		case 2:
			if(projectid==0)
			{
				projectid = usero.getProject().getId();//高级管理员 获得当前用户的项目ID
			}
			project = projectService.getById(projectid);
			lines = lineService.getLinesByLinetext(projectid,usero.getLinetext());
			break;
		default:
			break;
		}
		
		return "load";
	}
	/**
	 * 检查是否能修改通道，检查依据是否存在网关地址
	 */
	public String checkCanUpdateChannel()
	{
		gateway = gatewayService.loadById(id);
		if(gateway!=null)
		{
			Integer gateaddress = gateway.getGateaddress();
			GatewayVO gatewayVO = new GatewayVO();
			if(gateaddress!=null&&gateaddress!=0)
			{
				gatewayVO.setGateaddress(gateaddress);
			}else
			{
				gatewayVO.setGateaddress(0);
			}
			gatewayVO.setChannel(gateway.getChannel());
			JSONObject jsonObj = JSONObject.fromObject(gatewayVO);
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
	
	/**
	 * 检查无线数据通道
	 * @return
	 */
	public String checkChannel()
	{
		  Gateway currentgateway = gatewayService.loadById(id);
		  gateway = gatewayService.getGatewayByChannel(channel);
			
			if(gateway!=null)
			{
				GatewayVO gatewayVO = new GatewayVO();
				String msg = "该无线数据通道已存在,请重新填写";
				gatewayVO.setMsg(msg);
				if(currentgateway.getChannel()!=null)
				{
					gatewayVO.setChannel(currentgateway.getChannel());
				}
				JSONObject jsonObj = JSONObject.fromObject(gatewayVO);
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
	
	/**
	 * 检查网关地址
	 * @return
	 */
	public String checkGateaddress()
	{
			Gateway currentgateway = gatewayService.getGatewayById(id);
			gateway = gatewayService.getGatewayByGateaddress(gateaddress);
			if(gateway!=null)
			{
			
				GatewayVO gatewayVO = new GatewayVO();
				String msg = "该网关地址已存在,请重新填写";
				gatewayVO.setMsg(msg);
				if(currentgateway.getGateaddress()!=null)
				{
					gatewayVO.setGateaddress(currentgateway.getGateaddress());
				}
				JSONObject jsonObj = JSONObject.fromObject(gatewayVO);
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
	
	/**
	 * 实时监控-某线路下的地图展示
	 * @return
	 */
	public String maprealtime(){
		return "maprealtime";
	}
	//上传照片
	private File picture;
	private String pictureContentType;
	private String pictureFileName;
	//文件上传
	public void upload(String imageName) throws Exception{
		File saved=new File(ServletActionContext.getServletContext().getRealPath("streetpic"),imageName);
		InputStream ins=null;
		OutputStream ous=null;
		try {
			saved.getParentFile().mkdirs();
			ins=new FileInputStream(picture);
			ous=new FileOutputStream(saved);
			byte[] b=new byte[1024];
			int len = 0;
			while((len=ins.read(b))!=-1){
				ous.write(b,0,len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(ous!=null)
				ous.close();
			if(ins!=null) 
				ins.close();
		}
	}
	/**
	 * 查看街景图片
	 * @return
	 */
	public String showStreetpic(){
		gateway=gatewayService.loadById(id);
		return "streetpic";
	}
	/**
	 * 判断网关状态：正常或断开
	 * @return
	 */
	public String gatewayStatus(){
		//判断会话是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return NONE;
		}
		//所有当前页记录对象
		gateways=gatewayService.queryList(con,convalue,usero,page,size);
		if(gateways!=null&&gateways.size()>0){
			List<GatewayStatus> gatewaystatuses = new ArrayList<GatewayStatus>();
			for (int i = 0; i < gateways.size(); i++) {
				Gateway gateway = gateways.get(i);
				IoSession theSession = this.getCurrrenIoSession(gateway.getMacaddress());
				int thestatus=0;
				if(theSession!=null){
					thestatus=1;//正常
				}else{
					thestatus=0;//断开
				}
				GatewayStatus gatewayStatus = new GatewayStatus(gateway.getId(),gateway.getName(),thestatus);
				gatewaystatuses.add(gatewayStatus);
			}
			// 将list转化成JSON对象
			JSONArray jsonArray = JSONArray.fromObject(gatewaystatuses);
			//System.out.println(jsonArray.toString());
			PrintWriter out;
			try {
				response.setCharacterEncoding("UTF-8"); 
				out = response.getWriter();
				out.print(jsonArray);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}
	
	
	/**
	 * 地图ajax判断网关状态：正常或断开
	 * @return
	 */
	private int lineid;
	public String gatemapStatus(){
		//查询该线路的网关记录对象
		gateways=gatewayService.getGatewaysByLineid(lineid);
		if(gateways!=null&&gateways.size()>0){
			List<GatemapStatus> gatemapstatuses = new ArrayList<GatemapStatus>();
			for (int i = 0; i < gateways.size(); i++) {
				Gateway gateway = gateways.get(i);
				IoSession theSession = this.getCurrrenIoSession(gateway.getMacaddress());
				int thestatus=0;
				if(theSession!=null){
					thestatus=1;//正常
				}else{
					thestatus=0;//断开
				}
				GatemapStatus gatemapStatus = new GatemapStatus(gateway.getName()==null?"":gateway.getName(),gateway.getLng()==null?"":gateway.getLng(),gateway.getLat()==null?"":gateway.getLat(),thestatus,gateway.getStreetpic()==null?"":gateway.getStreetpic(),gateway.getId());
				gatemapstatuses.add(gatemapStatus);
			}
			// 将list转化成JSON对象
			JSONArray jsonArray = JSONArray.fromObject(gatemapstatuses);
			//System.out.println(jsonArray.toString());
			PrintWriter out;
			try {
				response.setCharacterEncoding("UTF-8"); 
				out = response.getWriter();
				out.print(jsonArray);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return NONE;
	}
	
	/**
	 * 调整到命令发送界面
	 * @return
	 */
	public String goToCommandSend(){
		commands = commandService.getCommands();
		gateway = gatewayService.loadById(id);
		return "gatewaysend";
	}
	
	/**
	 * 发送命令
	 * @return
	 */
	private String cmdcode;
	private String cmd;
	private int gatewayid;
	public String send(){
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		System.out.println("cmd="+cmd);
		gateway = gatewayService.loadById(gatewayid);
		IoSession currentSession=null;
		if(gateway!=null){
			String uid = gateway.getMacaddress();
			currentSession = this.getCurrrenIoSession(uid);
		}
		if(currentSession!=null){
			int gateaddress = gateway.getGateaddress();
			//发送命令协议-用于网关管理-配置命令
			//发送命令的参数，gateaddress(网关地址)，cmd(数据)
			//（设备地址0xXX:gateaddress  功能代码0x47  数据长度0xXX:根据数据参数cmd计算出长度  数据0xXX,0xXX,0xXX...:cmd[长度不确定]  CRC16:2个字节）
			//0xXX 0x47 0xXX 0xXX,0xXX,0xXX... 0xXX 0xXX
			/*
			SL代码
			*/
			String[] cmds = cmd.split(" ");
			byte[] aaa = new byte[cmds.length+3];
			aaa[0] = (byte) gateaddress;
			aaa[1] = 0x47;
			aaa[2] = (byte) (cmds.length&0xff);
	        int i = 3;
	        for (String b : cmds) {
	            if (b.equals("FF")) {
	                aaa[i++] = -1;
	            } else {
	                aaa[i++] = Integer.valueOf(b, 16).byteValue();;
	            }
	        }
	        byte[] sbuf1 = CRC_16.getSendBuf(DataConvertor.toHexString(aaa));
	        for (int j = 0; j < sbuf1.length; j++) {
				System.out.println("gateway action sbuf1["+j+"] is "+sbuf1[j]);
			}
	        currentSession.write(sbuf1); 
			
			request.put("errorInfo", "命令已发送！点这里查看-<a href='receivelogAction!list?projectid="+usero.getProject().getId()+"' target='rightFrame'>应答数据</a>-");
			return "operror";
		}else{
			request.put("errorInfo", "发送命令失败，请检查当前网关是否断开！");
			return "operror";
		}
		
	}
	/**
	 * 发送数据通道命令
	 * @return
	 */
	public String sendchannel(){
		//判断session会话是否用户是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		//根据参数查询出该网关对象
		gateway = gatewayService.loadById(gatewayid);
		//获取当前IoSession
		IoSession currentSession=null;
		if(gateway!=null){
			String uid = gateway.getMacaddress();
			currentSession = this.getCurrrenIoSession(uid);
		}else{
			request.put("errorInfo", "发送命令失败，未查询到该网关！");
			return "operror";
		}
		//如果当前IoSession是否为空
		if(currentSession!=null){
			int gateaddress = 0;//网关地址（即协议文件中的设备地址0xXX）
			if(gateway.getGateaddress()==null||gateway.getGateaddress()==0)
			{
				request.put("errorInfo", "发送命令失败，网关地址未配置！");
				return "operror";
			}else{
				gateaddress = gateway.getGateaddress();//网关地址（即协议文件中的设备地址0xXX）
			}
			
				//true：已确认通道数据（channel）被修改,发送命令-for SL-----------------
				//（设备地址0xXX:gateaddress  功能代码0x43  数据长度0x01  通道数据0xXX:channel  CRC16:2个字节）
				//0xXX 0x43 0x01 0xXX 0xXX 0xXX
				/*
				SL代码
				*/
				
				byte send_byte[] = new byte[4];
				send_byte[0] = (byte) gateaddress;
				send_byte[1] = 0x43;
				send_byte[2] = 0x01;
				send_byte[3] = (byte) channel;
				
				byte send_byte_final[] = CRC_16.getSendBuf(DataConvertor.toHexString(send_byte));
				
				for (int i = 0; i < send_byte_final.length; i++) {
					System.out.println("gateway action send_byte_final["+i+"] is "+send_byte_final[i]);
				}
				
				//发送命令
				currentSession.write(send_byte_final);
				
			request.put("errorInfo", "命令已发送！点这里查看-<a href='receivelogAction!list?projectid="+usero.getProject().getId()+"' target='rightFrame'>应答数据</a>-");
			return "operror";
		}else{
			request.put("errorInfo", "发送命令失败，请检查当前网关是否断开！");
			return "operror";
		}
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
	public IGatewayService getGatewayService() {
		return gatewayService;
	}
	@Resource
	public void setGatewayService(IGatewayService gatewayService) {
		this.gatewayService = gatewayService;
	}
	public Gateway getGateway() {
		return gateway;
	}
	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	public List<Gateway> getGateways() {
		return gateways;
	}
	public void setGateways(List<Gateway> gateways) {
		this.gateways = gateways;
	}
	public ILineService getLineService() {
		return lineService;
	}
	@Resource
	public void setLineService(ILineService lineService) {
		this.lineService = lineService;
	}
	public List<Line> getLines() {
		return lines;
	}
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
	public List<Line> getUserlines() {
		return userlines;
	}
	public void setUserlines(List<Line> userlines) {
		this.userlines = userlines;
	}
	public int getGateaddress() {
		return gateaddress;
	}
	public void setGateaddress(int gateaddress) {
		this.gateaddress = gateaddress;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getIsChannelUpdated() {
		return isChannelUpdated;
	}
	public void setIsChannelUpdated(int isChannelUpdated) {
		this.isChannelUpdated = isChannelUpdated;
	}
	public LogInterceptor getLogInterceptor() {
		return logInterceptor;
	}
	@Resource
	public void setLogInterceptor(LogInterceptor logInterceptor) {
		this.logInterceptor = logInterceptor;
	}
	public File getPicture() {
		return picture;
	}
	public void setPicture(File picture) {
		this.picture = picture;
	}
	public String getPictureContentType() {
		return pictureContentType;
	}
	public void setPictureContentType(String pictureContentType) {
		this.pictureContentType = pictureContentType;
	}
	public String getPictureFileName() {
		return pictureFileName;
	}
	public void setPictureFileName(String pictureFileName) {
		this.pictureFileName = pictureFileName;
	}
	public int getLineid() {
		return lineid;
	}
	public void setLineid(int lineid) {
		this.lineid = lineid;
	}
	public ICommandService getCommandService() {
		return commandService;
	}
	@Resource
	public void setCommandService(ICommandService commandService) {
		this.commandService = commandService;
	}
	public List<Command> getCommands() {
		return commands;
	}
	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}
	public String getCmdcode() {
		return cmdcode;
	}
	public void setCmdcode(String cmdcode) {
		this.cmdcode = cmdcode;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public int getGatewayid() {
		return gatewayid;
	}
	public void setGatewayid(int gatewayid) {
		this.gatewayid = gatewayid;
	}
	
	
}
