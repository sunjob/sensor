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

import org.apache.mina.core.session.IoSession;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jlj.model.Addresslist;
import com.jlj.model.Gateway;
import com.jlj.model.Line;
import com.jlj.model.Project;
import com.jlj.model.Sensor;
import com.jlj.model.User;
import com.jlj.service.IAddresslistService;
import com.jlj.service.IGatewayService;
import com.jlj.service.ILineService;
import com.jlj.service.IProjectService;
import com.jlj.service.ISensorService;
import com.jlj.util.DateTimeKit;
import com.jlj.util.LogInterceptor;
import com.jlj.vo.GatewayStatus;
import com.jlj.vo.SensorStatus;
import com.opensymphony.xwork2.ActionSupport;

@Component("sensorAction")
@Scope("prototype")
public class SensorAction extends ActionSupport implements RequestAware,
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
	private ISensorService sensorService;
	private IProjectService projectService;
	private IGatewayService gatewayService;
	private ILineService lineService;
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
	private int isDataUpdated;
	/*
	 * json 提交字段
	 */
	private int gateaddress;
	private int channel;
	/*
	 * 单个对象
	 */
	private Project project;
	private Gateway gateway;
	private Sensor sensor;
	
	/*
	 * list对象
	 */
	private List<Project> projects;
	private List<Line> lines;
	private List<Line> userlines;
	private List<Sensor> sensors;
	private List<Addresslist> addresslists;
	
	/*
	 * 日志处理类
	 */
	private LogInterceptor logInterceptor;
	
	
	/**
	 * 传感器管理
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		//判断会话是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		if(convalue!=null&&!convalue.equals("")){
			convalue=URLDecoder.decode(convalue, "utf-8");
		}
		if(page<1){
			page=1;
		}
		//总记录数
		totalCount= sensorService.getTotalCount(con,convalue,usero);
		//总页数
		pageCount=sensorService.getPageCount(totalCount,size);
		if(page>pageCount&&pageCount!=0){
			page=pageCount;
		}
		//所有当前页记录对象
		sensors=sensorService.queryList(con,convalue,usero,page,size);
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
		sensorService.add(sensor);
		arg[0]="sensorAction!list";
		arg[1]="传感器管理";
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
		sensor = sensorService.loadById(id);
		logInterceptor.addLog("传感器信息操作", user.getUsername()+"删除传感器["+sensor.toString()+"]", sensor.getGateway().getLine().getProject().getId());
		sensorService.deleteById(id);
		arg[0]="sensorAction!list";
		arg[1]="传感器管理";
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
//		System.out.println(isDataUpdated);
//		int gateaddress = 0;
//		int sensoraddress = 0;
//		int intervaltime = 0;
//		String gatewayIP ="";
//		
//		gateway = gatewayService.getGatewayById(sensor.getGateway().getId());
//		if(gateway.getGateaddress()!=null)
//		{
//			 gateaddress = gateway.getGateaddress();//网关地址（即协议文件中的设备地址:0xXX）
//		}
//		if(sensor.getSensoraddress()!=null)
//		{
//			 sensoraddress = sensor.getSensoraddress();//传感器地址：1~255
//		}
//		if(sensor.getIntervaltime()!=null)
//		{
//			 intervaltime = sensor.getIntervaltime();//2个字节的采样间隔,范围5~1440
//		}
//		
//		if(gateway!=null)
//		{
//			gatewayIP = gateway.getIp();//最新的网关表中的ip地址
//		}
//		//1-判断采样间隔是否被改变，若已修改发送节点配置命令(即传感器配置命令)-for LQ-----------------
//		if(isDataUpdated==1){
//			//true：已确认数据被修改,发送命令-for SL-----------------
//			//（设备地址0xXX:gateaddress  功能代码0x44  数据长度0x03,传感器地址0xXX:sensoraddress范围1~255,2个字节的采样间隔范围如0x00,0x05：intervaltime范围5~1440  CRC16:2个字节）
//			//0xXX 0x44 0x03,0xXX,0x00,0x05 0xXX 0xXX
//			/*
//			SL代码
//			
//			*/
//			byte send_byte[] = new byte[6];
//			send_byte[0] = (byte) gateaddress;
//			send_byte[1] = 0x44;
//			send_byte[2] = 0x03;
//			send_byte[3] = (byte) sensoraddress;
//			send_byte[4] = (byte) (intervaltime&0xff);
//			send_byte[5] = (byte) (intervaltime>>8);
//			
//			byte send_byte_final[] = CRC_16.getSendBuf(DataConvertor.toHexString(send_byte));
//			
//			for (int i = 0; i < send_byte_final.length; i++) {
//				System.out.println("sensor action send_byte_final["+i+"] is "+send_byte_final[i]);
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
			File photofile=new File(ServletActionContext.getServletContext().getRealPath("/")+sensor.getStreetpic());
			photofile.delete();
			sensor.setStreetpic("/"+imageName);
		}
		
		//修改数据库
		
		logInterceptor.addLog("传感器信息操作", user.getUsername()+"修改传感器["+sensor.toString()+"]", gateway.getLine().getProject().getId());
		sensorService.update(sensor);
		arg[0]="sensorAction!list";
		arg[1]="传感器管理";
		return SUCCESS;
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
	
//	public IoSession getCurrrenIoSession(String gatewayIP)
//	{
//		for(IoSession iosession : TimeServerHandler.sessions)
//		{
//			//获取IOsession中的IP
//			String sessionIP = ((InetSocketAddress)iosession.getRemoteAddress()).getAddress().getHostAddress();
//			if(sessionIP!=null&&sessionIP.equals(gatewayIP))
//			{
//				return iosession;
//			}
//		}
//		return null;
//	}
	
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
	 * 查看信息
	 * @return
	 */
	public String view(){
		sensor=sensorService.loadById(id);
		return "view";
	}
	
	/**
	 * 查看街景图片
	 * @return
	 */
	public String showStreetpic(){
		sensor=sensorService.loadById(id);
		return "streetpic";
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
		addresslists = addresslistService.queryByUserList(usero);
		sensor=sensorService.loadById(id);//当前修改用户的id
		return "load";
	}
	
	/**
	 * 检查无线数据通道
	 * @return
	 */
	public String checkDataUpdated()
	{
/*			sensor = sensorService.loadById(id);
			if(sensor!=null)
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
			}*/
		return  null;
	}
	
	
	/*
	 * 传感器实时状态-地图展示
	 * @return
	 */
	private List<Gateway> gateways;
	private int lineid;//前端传递来的线路id
	public String sensorrealtime(){
		gateways = gatewayService.getGatewaysByLineid(lineid);
		sensors = sensorService.getSensorsByLineid(lineid);
		return "sensorrealtime";
	}
	
	/**
	 * 地图重画时-状态展示
	 * @return
	 */
	public String sensorStatus(){
		sensors = sensorService.getSensorsByLineid(lineid);
		if(sensors!=null&&sensors.size()>0){
			List<SensorStatus> sensorstatuses = new ArrayList<SensorStatus>();
			for (int i = 0; i < sensors.size(); i++) {
				Sensor sensor = sensors.get(i);
				String lasttimestr="";
				if(sensor.getLasttime()!=null){
					lasttimestr=DateTimeKit.getDateTimeString(sensor.getLasttime());
				}
				
				SensorStatus sensorStatus = new SensorStatus(sensor.getId(),sensor.getName()==null?"":sensor.getName(),sensor.getLng()==null?"":sensor.getLng(),sensor.getLat()==null?"":sensor.getLat(),sensor.getNowtemp()==null?"":sensor.getNowtemp().toString(),sensor.getNowvoltage()==null?"":sensor.getNowvoltage().toString(),sensor.getStatus()==null?0:sensor.getStatus(),sensor.getStreetpic()==null?"":sensor.getStreetpic(),lasttimestr);
				sensorstatuses.add(sensorStatus);
			}
			// 将list转化成JSON对象
			JSONArray jsonArray = JSONArray.fromObject(sensorstatuses);
//			System.out.println(jsonArray.toString());
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
	 * 发送采样间隔命令
	 * @return
	 */
	private int sensorid;
	private int intervaltime;//2个字节的采样间隔,范围5~1440
	public String sendchannel(){
		//判断session会话是否用户是否失效
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		//根据参数查询出该传感器对象和网关对象
		sensor = sensorService.loadById(sensorid);
//		gateway = gatewayService.getGatewayById(sensor.getGateway().getId());
		gateway = sensor.getGateway();
		
		
		//获取当前IoSession
		IoSession currentSession=null;
		if(sensor!=null){
			String uid = gateway.getMacaddress();
			currentSession = this.getCurrrenIoSession(uid);
		}else{
			request.put("errorInfo", "发送命令失败，未查询到该网关！");
			return "operror";
		}
		//如果当前IoSession是否为空
		if(currentSession!=null){
			int gateaddress = 0;//网关地址（即协议文件中的设备地址0xXX）
			int sensoraddress = 0;//传感器地址：1~255
			if(gateway.getGateaddress()==null||gateway.getGateaddress()==0)
			{
				request.put("errorInfo", "发送命令失败，网关地址未配置！");
				return "operror";
			}else{
				gateaddress = gateway.getGateaddress();//网关地址（即协议文件中的设备地址0xXX）
			}
			if(sensor.getSensoraddress()==null||sensor.getSensoraddress()==0)
			{
				request.put("errorInfo", "发送命令失败，网关地址未配置！");
				return "operror";
			}else{
				sensoraddress = sensor.getSensoraddress();
			}
			//true：已确认数据被修改,发送命令-for SL-----------------
			//（设备地址0xXX:gateaddress  功能代码0x44  数据长度0x03,传感器地址0xXX:sensoraddress范围1~255,2个字节的采样间隔范围如0x00,0x05：intervaltime范围5~1440  CRC16:2个字节）
			//0xXX 0x44 0x03,0xXX,0x00,0x05 0xXX 0xXX
			/*
			SL代码
			
			*/
			byte send_byte[] = new byte[6];
			send_byte[0] = (byte) gateaddress;
			send_byte[1] = 0x44;
			send_byte[2] = 0x03;
			send_byte[3] = (byte) sensoraddress;
			send_byte[4] = (byte) (intervaltime&0xff);
			send_byte[5] = (byte) (intervaltime>>8);
			
			byte send_byte_final[] = CRC_16.getSendBuf(DataConvertor.toHexString(send_byte));
			
			for (int i = 0; i < send_byte_final.length; i++) {
				System.out.println("sensor action send_byte_final["+i+"] is "+send_byte_final[i]);
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
	/**
	 * 跳转到发送采样间隔配置命令的界面
	 * @return
	 */
	public String loadsendinterval(){
		User usero=(User)session.get("user");
		if(usero==null){
			return "opsessiongo";
		}
		sensor=sensorService.loadById(id);//当前修改用户的id
		return "loadsendinterval";
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
	public ISensorService getSensorService() {
		return sensorService;
	}
	@Resource
	public void setSensorService(ISensorService sensorService) {
		this.sensorService = sensorService;
	}
	public Sensor getSensor() {
		return sensor;
	}
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	public List<Sensor> getSensors() {
		return sensors;
	}
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	public List<Gateway> getGateways() {
		return gateways;
	}
	public void setGateways(List<Gateway> gateways) {
		this.gateways = gateways;
	}
	public int getLineid() {
		return lineid;
	}
	public void setLineid(int lineid) {
		this.lineid = lineid;
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
	public int getIsDataUpdated() {
		return isDataUpdated;
	}
	public void setIsDataUpdated(int isDataUpdated) {
		this.isDataUpdated = isDataUpdated;
	}
	public LogInterceptor getLogInterceptor() {
		return logInterceptor;
	}
	@Resource
	public void setLogInterceptor(LogInterceptor logInterceptor) {
		this.logInterceptor = logInterceptor;
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
	public int getSensorid() {
		return sensorid;
	}
	public void setSensorid(int sensorid) {
		this.sensorid = sensorid;
	}
	public int getIntervaltime() {
		return intervaltime;
	}
	public void setIntervaltime(int intervaltime) {
		this.intervaltime = intervaltime;
	}
	
}
