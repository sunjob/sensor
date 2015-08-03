package mina;
import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jlj.model.Alarm;
import com.jlj.model.Alarmrecord;
import com.jlj.model.Gateway;
import com.jlj.model.Line;
import com.jlj.model.Project;
import com.jlj.model.Receivelog;
import com.jlj.model.Sensor;
import com.jlj.model.Sensordata;
import com.jlj.model.Valve;
import com.jlj.model.Valvedata;
import com.jlj.service.IAlarmService;
import com.jlj.service.IAlarmrecordService;
import com.jlj.service.IGatewayService;
import com.jlj.service.ILineService;
import com.jlj.service.IProjectService;
import com.jlj.service.IReceivelogService;
import com.jlj.service.ISensorService;
import com.jlj.service.ISensordataService;
import com.jlj.service.IValveService;
import com.jlj.service.IValvedataService;
import com.jlj.sms.Send;
import com.jlj.util.DateTimeKit;
import com.jlj.util.StringToHex;


  
public class TimeServerHandler  implements IoHandler {
	final static int[] cmd_diaoyue = new int[]{0xFF ,0xFF ,0xFF ,0xFF ,0x01 ,0xF0 ,0x90 ,0x00 ,0x00 ,0x08 ,0x01 ,0x89};
	final static String cmd_diaoyue1 = "FF FF FF FF 01 F0 90 00 00 08 01 89";
	final static String cmd_test = "74 65 73 74 73 65 6E 64";
	final static ApplicationContext ac=new ClassPathXmlApplicationContext("beans.xml");
	final static IProjectService projectService = (IProjectService)ac.getBean("projectservice");
	
	final static ILineService lineService = (ILineService)ac.getBean("lineservice");
	final static IGatewayService gatewayService = (IGatewayService)ac.getBean("gatewayservice");
	final static ISensorService sensorService=(ISensorService) ac.getBean("sensorservice");
	final static ISensordataService sensordataService=(ISensordataService)ac.getBean("sensordataservice");
	final static IAlarmService alarmService=(IAlarmService)ac.getBean("alarmservice");
	final static IAlarmrecordService alarmrecordService=(IAlarmrecordService)ac.getBean("alarmrecordservice");
	final static IReceivelogService receivelogService=(IReceivelogService)ac.getBean("receivelogservice");
	final static IValvedataService valvedataService=(IValvedataService)ac.getBean("valvedataservice");
	final static IValveService valveService=(IValveService)ac.getBean("valveservice");
	
	final static StringToHex strToHex = new StringToHex();
	
	public static List<IoSession> sessions = new ArrayList<IoSession>();
	
	private static final Runnable SendCmdThread = null;
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {
		// TODO Auto-generated method stub
		arg1.printStackTrace();  
		
	}

	public void inputClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("IP:"+arg0.getRemoteAddress().toString()+" inputClosed"); 
		arg0.close(true);
	}
	protected byte[] m_oData = null;
	
	public void messageReceived(IoSession session, Object msg) throws Exception {
		
		AnalysisData_Sensor(DataConvertor.toByteArray(msg),session);
	
	}
	
	 public static String BytesHexString(byte[] b) {   
         String ret = "";   
         for (int i = 0; i < b.length; i++) {   
           String hex = Integer.toHexString(b[i] & 0xFF);   
           if (hex.length() == 1) {   
             hex = '0' + hex;   
           }   
           ret += hex.toUpperCase();   
         }   
         return ret;   
      }   
	
	public void messageSent(IoSession arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("发送信息:"+arg1.toString()); 
	}

	public void sessionClosed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("IP:"+arg0.getRemoteAddress().toString()+"断开连接"); 
		sessions.remove(arg0);
	}

	public void sessionCreated(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("IP:"+arg0.getRemoteAddress().toString()); 
		sessions.add(arg0);
		
	}

	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		System.out.println( "IDLE " + arg0.getIdleCount( arg1 ));  
		sessions.remove(arg0);
		arg0.close(true);
	}

	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println( "opened " );  

	}
	
	
	public static String encode(String cmd){
		String[] cmds = new String[cmd.length()];
    	char[] temp = cmd.toCharArray();
    	String res;
    	//System.out.println(Integer.toHexString((int)(temp[0])));
    	
    	for(int i = 0;i<cmd.length();i++)
    		cmds[i]= String.valueOf(Integer.toHexString((int)(temp[i])));
    	System.out.println(StringUtils.join(cmds," "));
    	res = StringUtils.join(cmds," ");
		return res;
		
	}
	
	
	public static String decode(String bytes)
	{
	 ByteArrayOutputStream baos=new ByteArrayOutputStream(bytes.length()/2);
	//将每2位16进制整数组装成一个字节
	String hexString="0123456789ABCDEF";
	 for(int i=0;i<bytes.length();i+=2)
	 baos.write((hexString.indexOf(bytes.charAt(i))<<4 |hexString.indexOf(bytes.charAt(i+1))));
	return new String(baos.toByteArray());
	}
	
	public static void AnalysisData_Sensor(byte[] data,IoSession session) throws Exception{
		
		//校验数据
		byte t_data[] = new byte[data.length -2];
		
		for (int i = 0; i < data.length-2; i++) {
			t_data[i] = data[i];
		}
		
		
		
		byte[] sbuf1 = CRC_16.getSendBuf(DataConvertor.toHexString(t_data));
//		System.out.println("[数据校验]--------------------start");
//		for (int i = 0; i < data.length; i++) {
//			System.out.println("the data["+i+"] is "+data[i]+" the sbuf1["+i+"] is "+sbuf1[i]);
//		}
//		System.out.println("[数据校验]--------------------end");
		if((sbuf1[sbuf1.length-1] == data[data.length-1])&& (sbuf1[sbuf1.length-2] == data[data.length-2])){
			System.out.println("[数据校验]====================成功====================");
			if( data[1] == 0x41){
				//心跳包==========================================================
				System.out.println("[心跳包]========================================开始");
				byte[] send_byte = new byte[4];
				send_byte[0] = data[0];
				send_byte[1] = 0x41;
				send_byte[2] = 0x01;
				send_byte[3] = 0x00;
			    
				//硬件ID================================================================
				byte macadd[] = new byte[12];
				for (int i = 0; i < macadd.length; i++) {
					macadd[i] = data[i+3];
				}
				
				String macaddress = DataConvertor.toHexString(macadd);
				//更新session
				if(session.getAttribute("uid")==null)
				{
						//如果是新的session，uid不存在，循环检测sessions，并删除老的session
						for(int i=0;i<sessions.size();i++)
						{
							if(sessions.get(i).getAttribute("uid")!=null){
								String uid= (String)sessions.get(i).getAttribute("uid");
								if(uid.equals(macaddress)){
									//如果发现session中存在该uid，去掉旧的连接
									sessions.remove(sessions.get(i));
									sessions.get(i).close(true);
								}
								
								
							}
						}
						session.setAttribute("uid",macaddress);
				}
				
				
				String sessionIP = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();//当前session的ip地址
				//SL需提供-硬件地址UID
				System.out.println("[心跳包]--------------------macaddress="+macaddress);
				//1、判断该硬件地址UID的该网关是否存在？若存在，应答相应的网关地址；若不存在，新增（未知线路的）网关对象
				//（说明：获取心跳包里的12个字节的硬件地址，如果发现数据库中有这个网关，那不新增；若发现没有这个硬件地址，新增网关）
				//默认如果管理员未修改该网关数据，应答0x00；若该管理员已修改网关地址为1（范围在1~247），应答0x01
				Gateway gateway = gatewayService.getGatewayByMacaddress(macaddress);
				if(gateway!=null){
					//存在网关，查询其gateway的网关地址是否存在；（并更新该网关的最新IP地址）--------------------
					//网关地址不是0（0x00），则应答相应的值，如0x01；此时测试数据会自动发送
					//网关地址是0（0x00），则应答0x00；此时测试数据不会有发送过来
//					System.out.println("[心跳包]--------------------gateway!=null");
					int gateaddress = 0;
					if(gateway.getGateaddress()!=null){
						gateaddress = gateway.getGateaddress();
					}
					if(gateaddress>=1&&gateaddress<=247){
						String hexGateaddress = Integer.toHexString(gateaddress);
						System.out.println("[心跳包]--------------------hexGateaddress="+hexGateaddress);
						byte gateaddrbyte = Integer.valueOf(hexGateaddress, 16).byteValue();  
//						System.out.println("[心跳包]--------------------gateaddrbyte="+gateaddrbyte);
						//应答设备地址为管理员设置好的网关地址
						send_byte[3]=gateaddrbyte;
					}else{
						//网关地址配置不正确，应答设备地址为0x00
						send_byte[3]=0x00;
					}
					
					String gatewayIp=gateway.getIp();
					if(sessionIP!=null&&gatewayIp!=null&&!sessionIP.equals(gatewayIp)){
						//并更新该网关的最新IP地址
						System.out.println("[心跳包]--------------------更新网关ip="+sessionIP);
						gatewayService.updateNewGatewayIpById(sessionIP,gateway.getId());
					}
					
				}else{
					//不存在该网关，新增网关---------------------------------
					gateway = new Gateway();
					Line line = lineService.getLineByProjectidAndOrderid(1,1);
					if(line==null){
//						System.out.println("[心跳包]--------------------line==null");
						line = new Line();
						Project project = projectService.loadById(1);
						if(project==null){
//							System.out.println("[心跳包]--------------------project==null");
							project = new Project();
							project.setId(1);
							project.setName("热力物联网");
							projectService.add(project);
						}
						line.setProject(project);
						line.setId(1);
						line.setOrderid(1);
						line.setName("测试线路");
						lineService.add(line);
					}
					gateway.setLine(line);
					gateway.setMacaddress(macaddress);
					gateway.setGateaddress(0);
					gateway.setStatus(1);//设置网关为正常状态
					gateway.setIp(sessionIP);//记录该网关的最新ip地址
					gatewayService.add(gateway);
					//默认应答设备地址为0x00
					send_byte[3]=0x00;
				}
				
				//发送应答数据包
				byte[] sbuf2 = CRC_16.getSendBuf(DataConvertor.toHexString(send_byte));
				System.out.println("[心跳包]--------------------应答数据"+CRC16M.getBufHexStr(sbuf2));
				session.write(sbuf2);
				System.out.println("[心跳包]========================================应答成功");
			}
			else if(data[1] == 0x42){
				//测试数据==========================================================
				System.out.println("[测试数据]++++++++++++++++++++++++++++++++++++++++");
				byte[] send_byte = new byte[3];
				send_byte[0] = data[0];
				send_byte[1] = data[1];
				send_byte[2] = 0x00;
				//send_byte[3] = 0x01;
				byte[] sbuf2 = CRC_16.getSendBuf(DataConvertor.toHexString(send_byte));
				System.out.println("[测试数据]++++++++++"+CRC16M.getBufHexStr(sbuf2));
				session.write(sbuf2);
				System.out.println("[测试数据]========================================应答成功");
				
				
				//硬件ID================================================================
				byte macadd[] = new byte[12];
				for (int i = 0; i < macadd.length; i++) {
					macadd[i] = data[i+3];
				}
				String macaddress = DataConvertor.toHexString(macadd);

				System.out.println("[测试数据]++++++++++macaddress is "+macaddress);
				//更新session
				if(session.getAttribute("uid")==null)
				{
						//如果是新的session，uid不存在，循环检测sessions，并删除老的session
						for(int i=0;i<sessions.size();i++)
						{
							if(sessions.get(i).getAttribute("uid")!=null){
								String uid= (String)sessions.get(i).getAttribute("uid");
								if(uid.equals(macaddress)){
									//如果发现session中存在该uid，去掉旧的连接
									sessions.get(i).close(true);
								}
								
								
							}
						}
						session.setAttribute("uid",macaddress);
				}
				
				
				float temp2 =(( data[17]&0xff)<<8)+(data[16]&0xff);
				if(temp2 >32767){
					temp2 -= 65536;
				}
				
				float temperature = temp2/10;



				System.out.println("[测试数据]++++++++++temperature is "+temperature);
				
				float BattVoltage = (data[23]<<8)+data[22];
				BattVoltage/= 1000;
				System.out.println("[测试数据]++++++++++BattVoltage is "+BattVoltage);
				
				//无线节点地址0x01-即传感器设备编号
//				String sensoraddressStr = String.valueOf(data[3]);
				String sensoraddressStr = String.valueOf(data[15]);
				int sensoraddress = Integer.parseInt(sensoraddressStr);
				System.out.println("[测试数据]++++++++++sensoraddress is "+sensoraddress);
				
				//1、查询该网关设备地址0x01的网关对象
				//2、判断该网关对象是否有该传感器编号，若没有，插入新传感器；若有，新增该传感器在这个时间的温度/压力/电压/流量/表面温度的数据
				//(说明：网关地址设置好了，比如0x01，那下次来心跳包的时候，我就应答这个，然后不断发测试数据，添加对应网关地址的这个网关的传感器设备以及相应的温度值等)
				int gateaddress=(int)data[0];//data[0]范围在1~247
				if(gateaddress>=1&&gateaddress<=247){
					Gateway gateway = gatewayService.getGatewayByMacaddress(macaddress);
					if(gateway!=null){
//						System.out.println("[测试数据]------------------gateway!=null");
						//根据网关id和传感器地址sensoraddress查询传感器对象是否存在
						Sensor sensor = sensorService.getSensorByGatewayidAndSensoraddress(gateway.getId(),sensoraddress);
						if(sensor!=null){
							//存在该传感器，记录对应时间的温度以及其他的数据数值
							//直接新增温度等数据值
//							System.out.println("[测试数据]------------------sensor!=null");
							Date nowdate = new Date();//今日时间对象
							//记录修改当前温度
							sensorService.updateSensorTempAndVoltage(temperature,BattVoltage,nowdate,sensor.getId());
							
							//插入传感器新数据
							
							Sensordata sensordata = new Sensordata();
							sensordata.setSdatetime(nowdate);
							sensordata.setSensor(sensor);
							sensordata.setStype(1);//1温度2压力3流量4电池电压5表面温度
							sensordata.setSdata(temperature);//温度数据
							sensordata.setVdata(BattVoltage);//电池电压数据
							sensordataService.add(sensordata);
							
						}else{
							Date nowdate = new Date();//今日时间对象
							//新增传感器记录，并记录对应时间的温度以及其他的数据数值
//							System.out.println("[测试数据]------------------sensor==null");
							sensor = new Sensor();
							sensor.setGateway(gateway);
							sensor.setSensoraddress(sensoraddress);
							sensor.setName(gateaddress+"_"+sensoraddress);//默认产生一个传感器名称：根据网关地址+下划线+传感器地址
							//记录现在的温度以及电池电压
							sensor.setNowtemp(temperature);
							sensor.setNowvoltage(BattVoltage);
							sensor.setLasttime(nowdate);//更新最新的报警时间
							sensor.setStatus(1);
							sensor.setSensortype(1);//默认传感器类型：温度传感器
							sensor.setIscanalarm(1);
							//插入报警表的默认报警值
							Project projectobj = null;
							if(gateway.getLine()!=null&&gateway.getLine().getProject()!=null){
								projectobj = gateway.getLine().getProject();
							}
							if(projectobj!=null){
								int projectid = projectobj.getId();
								//根据projectid查询该项目的报警设置
								Alarm alarmobj = alarmService.getAlarmByProjectid(projectid);
								if(alarmobj.getTemp()!=null){
									sensor.setAlarmtemp(alarmobj.getTemp());//报警温度上限
								}
								if(alarmobj.getTempdown()!=null){
									sensor.setAlarmtempdown(alarmobj.getTempdown());//报警温度下限
								}
								if(alarmobj.getVoltage()!=null){
									sensor.setAlarmvoltage(alarmobj.getVoltage());//报警电压
								}
								if(alarmobj.getFlow()!=null){
									sensor.setAlarmflow(alarmobj.getFlow());//报警流量
								}
								if(alarmobj.getPressure()!=null){
									sensor.setAlarmpressure(alarmobj.getPressure());//报警压力
								}
//								if(alarmobj.getBmtemp()!=null){
//									sensor.setAlarmbmtemp(alarmobj.getBmtemp());//报警表面温度
//								}
								if(alarmobj.getPhones()!=null){
									sensor.setAlarmphones(alarmobj.getPhones());//发送手机
								}
								
							}
							
							sensorService.add(sensor);
							//新增温度等数据值
							Sensordata sensordata = new Sensordata();
							sensordata.setSdatetime(nowdate);
							sensordata.setSensor(sensor);
							sensordata.setStype(1);//1温度2压力3流量4电池电压5表面温度
							sensordata.setSdata(temperature);//温度数据
							sensordata.setVdata(BattVoltage);//电池电压数据
							sensordataService.add(sensordata);
							
							
						}
						
						//检查温度数据是否超过设定值，若超出设定值----------------------
						//1-以前报过警（即iscanalarm=0），并且温度值保持在正常值以上温度的时间与最近报警的时间计算时间间隔，超过设置时间间隔天数，则再次报警
						//2-判断温度是否超温，若超过报警温度并且可以报警（iscanalarm=1），则直接报警，并改为不可再次报警（iscanalarm=0）
						//3-若温度有低于正常值，表示可以再次报警（即设置iscanalarm=1）
						//*********************************报警温度上限*************************************
						if(sensor!=null&&sensor.getIscanalarm()!=null){
							int iscanalarm = sensor.getIscanalarm();
							
							Alarm alarm = alarmService.getAlarmByProjectid(gateway.getLine().getProject().getId());//根据项目id查出该报警设置对象
							
							Date nowdate = new Date();//现在的时间-util
							String nowtime = DateTimeKit.getDateTimeString(nowdate);//现在的时间-string
							
							if(iscanalarm==1){
								float alarmtemp = 0f;//获取报警温度
								if(sensor.getAlarmtemp()!=null){
									alarmtemp = sensor.getAlarmtemp();
								}
								if(alarmtemp!=0&&temperature>alarmtemp){
									//2温度超过某个值报警
									System.out.println("[超温报警]++++++++++"+temperature);
									//发送报警短信------------------------start
									String phones="";
									if(sensor.getAlarmphones()!=null&&!sensor.getAlarmphones().equals("")){
										phones = sensor.getAlarmphones();
									}else{
										phones = alarm.getPhones();
									}
									if(!phones.equals("")){
										String smstemplate = alarm.getSmstemplate();
										smstemplate = smstemplate.replace("#日期时间#", nowtime);
										smstemplate = smstemplate.replace("#项目#-#线路#-#网关#-#传感器编号#-#温度#",gateway.getLine().getProject().getName()+"-"+gateway.getLine().getName()+"-"+gateway.getName()+"-"+sensor.getName()+"-"+temperature+"度");
										String result = Send.sendSMS(phones, smstemplate);
										//发送报警短信------------------------end
										
										//记录报警内容，报警短信和报警时间到数据库-------------------------start
										Alarmrecord alarmrecord = new Alarmrecord();
										alarmrecord.setAlarmdata(temperature);
										alarmrecord.setAlarmtime(nowdate);
										alarmrecord.setAlarmtype(1);
										alarmrecord.setPhones(phones);
										result = Send.parseSmsResultXml(result);
										alarmrecord.setSendreturn(result);
										alarmrecord.setSensor(sensor);
										alarmrecordService.add(alarmrecord);
										//记录报警内容，报警短信和报警时间到数据库-------------------------end
									}
									//修改传感器实时状态为高温报警状态（即status=2）-------------------------
									sensorService.updateStatusBySensorid(2,sensor.getId());
									
									//设置为不能报警（iscanalarm=0）-------------------------
									iscanalarm = 0;
									sensorService.updateSensorIscanalarm(iscanalarm,sensor.getId());
								}
							}else if(iscanalarm==0){
								//3若温度有低于正常值，表示可以再次报警（即设置iscanalarm=1）
								float normaltemp = 0f;//获取正常温度
								if(sensor.getNormaltemp()!=null){
									normaltemp = sensor.getNormaltemp();
								}
								if(normaltemp!=0&&temperature<normaltemp){
									//修改可以再次报警iscanalarm=1
									iscanalarm = 1;
									sensorService.updateSensorIscanalarm(iscanalarm,sensor.getId());
									//修改传感器实时状态为正常温度状态（即status=1）-------------------------
									sensorService.updateStatusBySensorid(1,sensor.getId());
								}
								//1温度值保持在正常值以上温度的时间与最近报警的时间计算时间间隔，超过设置时间间隔天数，则再次报警
								String lasttime = DateTimeKit.getDateTimeString(sensor.getLasttime());//数据库中记录的最新报警时间
								int interalday = DateTimeKit.daysBetween(lasttime,nowtime);//相隔天数
								
								//查出该项目设置的报警相隔天数------------------------------
								int temptime=0;//获取报警间隔天数
								if(alarm==null){
									System.out.println("[超温报警]--------------------该报警设置不存在，请联系管理员！");
									temptime = 5;//默认5天
								}else{
									temptime = alarm.getTemptime();
								}
								 
								if(interalday>temptime){
									System.out.println("[超温报警]++++++++++"+temperature);
									//发送报警短信------------------------start
									String phones="";
									if(sensor.getAlarmphones()!=null&&!sensor.getAlarmphones().equals("")){
										phones = sensor.getAlarmphones();
									}else{
										phones = alarm.getPhones();
									}
									if(!phones.equals("")){
										String smstemplate = alarm.getSmstemplate();
										smstemplate = smstemplate.replace("#日期时间#", nowtime);
										smstemplate = smstemplate.replace("#项目#-#线路#-#网关#-#传感器编号#-#温度#",gateway.getLine().getProject().getName()+"-"+gateway.getLine().getName()+"-"+gateway.getName()+"-"+sensor.getName()+"-"+temperature+"度");
										String result = Send.sendSMS(phones, smstemplate);
										//发送报警短信------------------------end
										
										//记录报警内容，报警短信和报警时间到数据库-------------------------start
										Alarmrecord alarmrecord = new Alarmrecord();
										alarmrecord.setAlarmdata(temperature);
										alarmrecord.setAlarmtime(nowdate);
										alarmrecord.setAlarmtype(1);
										alarmrecord.setPhones(phones);
										result = Send.parseSmsResultXml(result);
										alarmrecord.setSendreturn(result);
										alarmrecord.setSensor(sensor);
										alarmrecordService.add(alarmrecord);
										//记录报警内容，报警短信和报警时间到数据库-------------------------end
									}
									
									
								}
								
							}
						}
						
						//*********************************报警温度下限*************************************
						if(sensor!=null&&sensor.getIscanalarm()!=null){
							int iscanalarm = sensor.getIscanalarm();
							
							Alarm alarm = alarmService.getAlarmByProjectid(gateway.getLine().getProject().getId());//根据项目id查出该报警设置对象
							
							Date nowdate = new Date();//现在的时间-util
							String nowtime = DateTimeKit.getDateTimeString(nowdate);//现在的时间-string
							
							if(iscanalarm==1){
								float alarmtempdown = 0f;//获取报警温度下限
								if(sensor.getAlarmtempdown()!=null){
									alarmtempdown = sensor.getAlarmtempdown();
								}
								if(alarmtempdown!=0&&temperature<alarmtempdown){
									//2温度低于某个值报警
									System.out.println("[低温报警]++++++++++"+temperature);
									//发送报警短信------------------------start
									String phones="";
									if(sensor.getAlarmphones()!=null&&!sensor.getAlarmphones().equals("")){
										phones = sensor.getAlarmphones();
									}else{
										phones = alarm.getPhones();
									}
									if(!phones.equals("")){
										String smstemplate = alarm.getSmstemplate();
										smstemplate = smstemplate.replace("#日期时间#", nowtime);
										smstemplate = smstemplate.replace("#项目#-#线路#-#网关#-#传感器编号#-#温度#",gateway.getLine().getProject().getName()+"-"+gateway.getLine().getName()+"-"+gateway.getName()+"-"+sensor.getName()+"-"+temperature+"度");
										String result = Send.sendSMS(phones, smstemplate);
										//发送报警短信------------------------end
										
										//记录报警内容，报警短信和报警时间到数据库-------------------------start
										Alarmrecord alarmrecord = new Alarmrecord();
										alarmrecord.setAlarmdata(temperature);
										alarmrecord.setAlarmtime(nowdate);
										alarmrecord.setAlarmtype(1);
										alarmrecord.setPhones(phones);
										result = Send.parseSmsResultXml(result);
										alarmrecord.setSendreturn(result);
										alarmrecord.setSensor(sensor);
										alarmrecordService.add(alarmrecord);
										//记录报警内容，报警短信和报警时间到数据库-------------------------end
									}
									//修改传感器实时状态为高温报警状态（即status=2）-------------------------
									sensorService.updateStatusBySensorid(2,sensor.getId());
									
									//设置为不能报警（iscanalarm=0）-------------------------
									iscanalarm = 0;
									sensorService.updateSensorIscanalarm(iscanalarm,sensor.getId());
								}
							}else if(iscanalarm==0){
								//3若温度有高于正常值下限，表示可以再次报警（即设置iscanalarm=1）
								float normaltempdown = 0f;//获取正常温度
								if(sensor.getNormaltempdown()!=null){
									normaltempdown = sensor.getNormaltempdown();
								}
								if(normaltempdown!=0&&temperature>normaltempdown){
									//修改可以再次报警iscanalarm=1
									iscanalarm = 1;
									sensorService.updateSensorIscanalarm(iscanalarm,sensor.getId());
									//修改传感器实时状态为正常温度状态（即status=1）-------------------------
									sensorService.updateStatusBySensorid(1,sensor.getId());
								}
								//1温度值保持在正常值以上温度的时间与最近报警的时间计算时间间隔，超过设置时间间隔天数，则再次报警
								String lasttime = DateTimeKit.getDateTimeString(sensor.getLasttime());//数据库中记录的最新报警时间
								int interalday = DateTimeKit.daysBetween(lasttime,nowtime);//相隔天数
								
								//查出该项目设置的报警相隔天数------------------------------
								int temptime=0;//获取报警间隔天数
								if(alarm==null){
									System.out.println("[低温报警]--------------------该报警设置不存在，请联系管理员！");
									temptime = 5;//默认5天
								}else{
									temptime = alarm.getTemptime();
								}
								 
								if(interalday>temptime){
									System.out.println("[低温报警]++++++++++"+temperature);
									//发送报警短信------------------------start
									String phones="";
									if(sensor.getAlarmphones()!=null&&!sensor.getAlarmphones().equals("")){
										phones = sensor.getAlarmphones();
									}else{
										phones = alarm.getPhones();
									}
									if(!phones.equals("")){
										String smstemplate = alarm.getSmstemplate();
										smstemplate = smstemplate.replace("#日期时间#", nowtime);
										smstemplate = smstemplate.replace("#项目#-#线路#-#网关#-#传感器编号#-#温度#",gateway.getLine().getProject().getName()+"-"+gateway.getLine().getName()+"-"+gateway.getName()+"-"+sensor.getName()+"-"+temperature+"度");
										String result = Send.sendSMS(phones, smstemplate);
										//发送报警短信------------------------end
										
										//记录报警内容，报警短信和报警时间到数据库-------------------------start
										Alarmrecord alarmrecord = new Alarmrecord();
										alarmrecord.setAlarmdata(temperature);
										alarmrecord.setAlarmtime(nowdate);
										alarmrecord.setAlarmtype(1);
										alarmrecord.setPhones(phones);
										result = Send.parseSmsResultXml(result);
										alarmrecord.setSendreturn(result);
										alarmrecord.setSensor(sensor);
										alarmrecordService.add(alarmrecord);
										//记录报警内容，报警短信和报警时间到数据库-------------------------end
									}
									
									
								}
								
							}
						}
						
//						float alarmpressure = sensor.getAlarmpressure();
//						float alarmflow = sensor.getAlarmflow();
//						float alarmbmtemp = sensor.getAlarmbmtemp();
						//*********************************报警电压上限*************************************
						if(sensor!=null&&sensor.getIscanalarm2()!=null){
							int iscanalarm2 = sensor.getIscanalarm2();
							
							Alarm alarm = alarmService.getAlarmByProjectid(gateway.getLine().getProject().getId());//根据项目id查出该报警设置对象
							
							Date nowdate = new Date();//现在的时间-util
							String nowtime = DateTimeKit.getDateTimeString(nowdate);//现在的时间-string
							
							if(iscanalarm2==1){
								float alarmvoltagedown = 0f;//获取报警电压下限
								if(sensor.getAlarmvoltage()!=null){
									alarmvoltagedown = sensor.getAlarmvoltage();
								}
								if(alarmvoltagedown!=0&&BattVoltage<alarmvoltagedown){
									//2电压低于某个值报警
									System.out.println("[低电池报警]++++++++++"+BattVoltage);
									//发送报警短信------------------------start
									String phones="";
									if(sensor.getAlarmphones()!=null&&!sensor.getAlarmphones().equals("")){
										phones = sensor.getAlarmphones();
									}else{
										phones = alarm.getPhones();
									}
									if(!phones.equals("")){
										String smstemplate = alarm.getSmstemplate();
										smstemplate = smstemplate.replace("#日期时间#", nowtime);
										smstemplate = smstemplate.replace("#项目#-#线路#-#网关#-#传感器编号#-#温度#",gateway.getLine().getProject().getName()+"-"+gateway.getLine().getName()+"-"+gateway.getName()+"-"+sensor.getName()+"-"+BattVoltage+"v");
										String result = Send.sendSMS(phones, smstemplate);
										//发送报警短信------------------------end
										
										//记录报警内容，报警短信和报警时间到数据库-------------------------start
										Alarmrecord alarmrecord = new Alarmrecord();
										alarmrecord.setAlarmdata(BattVoltage);
										alarmrecord.setAlarmtime(nowdate);
										alarmrecord.setAlarmtype(4);//电池电压
										alarmrecord.setPhones(phones);
										result = Send.parseSmsResultXml(result);
										alarmrecord.setSendreturn(result);
										alarmrecord.setSensor(sensor);
										alarmrecordService.add(alarmrecord);
										//记录报警内容，报警短信和报警时间到数据库-------------------------end
									}
									//修改传感器实时状态为低电池报警状态（即status=2）-------------------------
									sensorService.updateStatusBySensorid(2,sensor.getId());
									
									//设置为不能报警（iscanalarm2=0）-------------------------
									iscanalarm2 = 0;
									sensorService.updateSensorIscanalarm2(iscanalarm2,sensor.getId());
								}
							}else if(iscanalarm2==0){
								//3若电池有高于正常值下限，表示可以再次报警（即设置iscanalarm2=1）
								float normalvoltagedown = 0f;//获取正常电池下限
								if(sensor.getNormalvoltage()!=null){
									normalvoltagedown = sensor.getNormalvoltage();
								}
								if(normalvoltagedown!=0&&BattVoltage>normalvoltagedown){
									//修改可以再次报警iscanalarm2=1
									iscanalarm2 = 1;
									sensorService.updateSensorIscanalarm2(iscanalarm2,sensor.getId());
									//修改传感器实时状态为正常电池状态（即status=1）-------------------------
									sensorService.updateStatusBySensorid(1,sensor.getId());
								}
								//1电池保持在正常值以上电池的时间与最近报警的时间计算时间间隔，超过设置时间间隔天数，则再次报警
								String lasttime = DateTimeKit.getDateTimeString(sensor.getLasttime());//数据库中记录的最新报警时间
								int interalday = DateTimeKit.daysBetween(lasttime,nowtime);//相隔天数
								
								//查出该项目设置的报警相隔天数------------------------------
								int temptime=0;//获取报警间隔天数
								if(alarm==null){
									System.out.println("[低电池报警]--------------------该报警设置不存在，请联系管理员！");
									temptime = 5;//默认5天
								}else{
									temptime = alarm.getTemptime();
								}
								 
								if(interalday>temptime){
									System.out.println("[低电池报警]++++++++++"+BattVoltage);
									//发送报警短信------------------------start
									String phones="";
									if(sensor.getAlarmphones()!=null&&!sensor.getAlarmphones().equals("")){
										phones = sensor.getAlarmphones();
									}else{
										phones = alarm.getPhones();
									}
									if(!phones.equals("")){
										String smstemplate = alarm.getSmstemplate();
										smstemplate = smstemplate.replace("#日期时间#", nowtime);
										smstemplate = smstemplate.replace("#项目#-#线路#-#网关#-#传感器编号#-#温度#",gateway.getLine().getProject().getName()+"-"+gateway.getLine().getName()+"-"+gateway.getName()+"-"+sensor.getName()+"-"+BattVoltage+"v");
										String result = Send.sendSMS(phones, smstemplate);
										//发送报警短信------------------------end
										
										//记录报警内容，报警短信和报警时间到数据库-------------------------start
										Alarmrecord alarmrecord = new Alarmrecord();
										alarmrecord.setAlarmdata(BattVoltage);
										alarmrecord.setAlarmtime(nowdate);
										alarmrecord.setAlarmtype(4);//电池电压
										alarmrecord.setPhones(phones);
										result = Send.parseSmsResultXml(result);
										alarmrecord.setSendreturn(result);
										alarmrecord.setSensor(sensor);
										alarmrecordService.add(alarmrecord);
										//记录报警内容，报警短信和报警时间到数据库-------------------------end
									}
									
									
								}
								
							}
						}
					}
					
				}
				
				
			}else if(data[1] == 0x43){
				
				System.out.println("[信道配置]========================================成功");
				
				int gateaddress = data[0];//网关地址
				Receivelog receivelog= new Receivelog();
				receivelog.setLoginfo("网关[地址："+gateaddress+"]信道配置成功");
				receivelog.setNowtime(new Date());
				String sessionIP1 = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();//当前session的ip地址
				Gateway gateway1= gatewayService.getGatewayByIp(sessionIP1);
				receivelog.setProject(gateway1.getLine().getProject());
				receivelogService.add(receivelog);
				//修改网关的信道----------
				int channel = data[3];//无线数据通道
				gatewayService.updateChannelById(channel,gateway1.getId());
				
				
			}else if(data[1] == 0x44){
				System.out.println("[节点配置]========================================成功");
				
				int gateaddress = data[0];//网关地址
				Receivelog receivelog= new Receivelog();
				receivelog.setLoginfo("网关[地址："+gateaddress+"]节点配置成功");
				receivelog.setNowtime(new Date());
				String sessionIP1 = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();//当前session的ip地址
				Gateway gateway1= gatewayService.getGatewayByIp(sessionIP1);
				receivelog.setProject(gateway1.getLine().getProject());
				receivelogService.add(receivelog);
				//修改网关的下属传感器的采样间隔
				int sensoraddress = data[3];//节点地址
				int intervaltime = ((data[5]&0xff)<<8)+(data[4]&0xff);//采样间隔
				sensorService.updateIntervaltimeByGatewayIdAndSensoraddress(intervaltime,gateway1.getId(),sensoraddress);
				
			}else if(data[1] == 0x45){
				System.out.println("[外设控制]========================================成功");
				
				int gateaddress = data[0];//网关地址
				Receivelog receivelog= new Receivelog();
				receivelog.setLoginfo("网关[地址："+gateaddress+"]外设控制成功");
				receivelog.setNowtime(new Date());
				String sessionIP1 = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();//当前session的ip地址
				Gateway gateway1= gatewayService.getGatewayByIp(sessionIP1);
				receivelog.setProject(gateway1.getLine().getProject());
				receivelogService.add(receivelog);
				//修改网关的下属传感器的状态
				int valveaddress = data[3];//外设地址
				int locatenumber = data[4];//外设控制位置号
				int controlvalue = data[5];//控制值
				int status = 0;//状态：0-关闭状态；1-开启状态
				if(controlvalue==0){
					status = 0;
				}else{
					status = 1;
				}
				valveService.updateStatusByConditionAndGatewayid(controlvalue,status,valveaddress,locatenumber,gateway1.getId());
				
			}else if(data[1]== 0x46){
				//(设备地址   功能代码   数据长度，外设地址，数值类型，4个字节的浮点数    校验码)
				// 0xXX      0x46    0x06,0x01,0x01,0x00,0x00,0x00,0x00    0xXX,0xXX
				/*
				SL代码
				*/
				
				int gateaddress = data[0];
				int valveaddress = data[3];
				int vtype = data[4];
				float vdata = 0x0f;
				
				int temp_data[] = new int[4];
				
				for (int i = 0; i < temp_data.length; i++) {
					temp_data[i] = data[i+5];
				}
				
//				String temp1 = DataConvertor.bytesToHexString(DataConvertor.toByteArray(temp_data[3]));
//				String temp2 = DataConvertor.bytesToHexString(DataConvertor.toByteArray(temp_data[2]));
//				String temp3 = DataConvertor.bytesToHexString(DataConvertor.toByteArray(temp_data[1]));
//				String temp4 = DataConvertor.bytesToHexString(DataConvertor.toByteArray(temp_data[0]));
				
				byte temp1[] = new byte[4];
				for (int i = 0; i < temp1.length; i++) {
					temp1[i] = (byte) (temp_data[3-i]&0xff);
				}
				
				String temp = DataConvertor.bytesToHexString(temp1);
				vdata=Float.intBitsToFloat(Integer.parseInt(temp,16));
				System.out.println("[接收数据]========================================"+gateaddress+","+valveaddress+","+vtype+","+vdata);
				
				String sessionIP1 = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();//当前session的ip地址
				//查询出该网关唯一的外设地址的该外设
				Gateway gateway1= gatewayService.getGatewayByIp(sessionIP1);
				Valve valve = valveService.getValveByGatewayIdAndValveaddress(gateway1.getId(),valveaddress);
				Valvedata valvedata = new Valvedata();
				valvedata.setValve(valve);
				valvedata.setVtime(new Date());
				valvedata.setVdata(vdata);
				valvedata.setVtype(vtype);
				valvedataService.add(valvedata);
				
			}else if(data[1]== 0x47){
				System.out.println("[0x47命令]========================================成功");
				
				int gateaddress = data[0];
				Receivelog receivelog= new Receivelog();
				receivelog.setLoginfo("网关[地址："+gateaddress+"]发送命令成功");
				receivelog.setNowtime(new Date());
				String sessionIP1 = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();//当前session的ip地址
				Gateway gateway1= gatewayService.getGatewayByIp(sessionIP1);
				receivelog.setProject(gateway1.getLine().getProject());
				receivelogService.add(receivelog);
			}
		}
		
		
	}
	

	
	  public static String bytesToHexString(byte[] src){  
	      StringBuilder stringBuilder = new StringBuilder("");  
	      if (src == null || src.length <= 0) {  
	          return null;  
	      }  
	      for (int i = 0; i < src.length; i++) {  
	          int v = src[i] & 0xFF;  
	          String hv = Integer.toHexString(v);  
	          if (hv.length() < 2) {  
	              stringBuilder.append(0);  
	          }  
	          stringBuilder.append(hv);  
	      }  
	      System.out.println("---"+stringBuilder.toString()+"---");
	      
	      return stringBuilder.toString();  
	  }  
	  
	  public static int str2hex(String str){
	  	String temp = str.substring(0, 1);
		    String temp2= str.substring(1, 2);
	  	int l = str2hexfun(temp);
	      int m = str2hexfun(temp2);
	      int sum = (l<<4)+m;
	      
			return sum;
	  	
	  }
	  
	  public static int str2hexfun(String str){
	  	
	  	if(str.equals("a")){
	  		return 10;
	  	}else if(str.equals("b"))
	  		return 11;
	  	else if(str.equals("c"))
	  		return 12;
	  	else if(str.equals("d"))
	  		return 13;
	  	else if(str.equals("e"))
	  		return 14;
	  	else if(str.equals("f"))
	  		return 15;
	  	else{
	  		int i = Integer.valueOf(str).intValue();
	  	return i;
	  	}
	  }
	  public static String str2HexStr(String str)    
	  {      
	    
	      char[] chars = "0123456789ABCDEF".toCharArray();      
	      StringBuilder sb = new StringBuilder("");    
	      byte[] bs = str.getBytes();      
	      int bit;      
	          
	      for (int i = 0; i < bs.length; i++)    
	      {      
	          bit = (bs[i] & 0x0f0) >> 4;      
	          sb.append(chars[bit]);      
	          bit = bs[i] & 0x0f;      
	          sb.append(chars[bit]);    
	          sb.append(' ');    
	      }      
	      return sb.toString().trim();      
	  }    


}
