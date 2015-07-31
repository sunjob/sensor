package com.jlj.service.imp;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.ISensorDao;
import com.jlj.model.Sensor;
import com.jlj.model.User;
import com.jlj.service.ISensorService;
@Component("sensorservice")
public class SensorServiceImp implements ISensorService {
	private ISensorDao sensorDao;
	public ISensorDao getSensorDao() {
		return sensorDao;
	}
	@Resource
	public void setSensorDao(ISensorDao sensorDao) {
		this.sensorDao = sensorDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensorService#add(com.jlj.model.Sensor)
	 */
	public void add(Sensor sensor) throws Exception {
		sensorDao.save(sensor);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensorService#delete(com.jlj.model.Sensor)
	 */
	public void delete(Sensor sensor) {
		sensorDao.delete(sensor);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensorService#deleteById(int)
	 */
	public void deleteById(int id) {
		sensorDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensorService#update(com.jlj.model.Sensor)
	 */
	public void update(Sensor sensor) {
		sensorDao.update(sensor);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensorService#getSensors()
	 */
	public List<Sensor> getSensors() {
		return sensorDao.getSensors();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensorService#loadById(int)
	 */
	public Sensor loadById(int id) {
		return sensorDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensorService#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int con, String convalue,int size) {
		int totalCount=this.getTotalCount(con, convalue);
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensorService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue) {
		String queryString = "select count(*) from Sensor mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		return sensorDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensorService#queryList(int, java.lang.String, int, int)
	 */
	public List<Sensor> queryList(int con, String convalue, int page, int size) {
		String queryString = "from Sensor mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name,mo.name asc ";
		return sensorDao.pageList(queryString,p,page,size);
	}
	public int getPageCount(int totalCount, int size) {
		// TODO Auto-generated method stub
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	public int getTotalCount(int con, String convalue, User user) {
		String queryString = "select count(*) from Sensor mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			if(con==1){
				queryString += "and mo.gateway.line.project.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==2){
				queryString += "and mo.gateway.line.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==3){
				queryString += "and mo.gateway.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==4){
				queryString += "and mo.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==5){
				int type = 0;
				if("其他".contains(convalue))
				{
					queryString += "and mo.sensortype ="+type; 
				}
				if("温度".contains(convalue))
				{
					type = 1;
					queryString += "and mo.sensortype ="+type; 
				}
				if("压力".contains(convalue))
				{
					type = 2;
					queryString += "and mo.sensortype ="+type; 
				}
				if("流量".contains(convalue))
				{
					type = 3;
					queryString += "and mo.sensortype ="+type; 
				}
				if("电池电压".contains(convalue))
				{
					type = 4;
					queryString += "and mo.sensortype ="+type; 
				}
				if("表面温度".contains(convalue))
				{
					type = 5;
					queryString += "and mo.sensortype ="+type; 
				}
				p = new Object[]{};
			}
			if(con==6){
				queryString += "and mo.devicetype like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name,mo.name asc ";
			break;
		case 1://高级管理员
			queryString += "and mo.gateway.line.project.id="+user.getProject().getId();
			queryString += " order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name,mo.name asc ";
			break;
		case 2://普通管理员
			queryString += "and mo.gateway.line.id in("+user.getLinetext()+")";  
			queryString += " order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name,mo.name asc ";
			break;
		default:
			break;
		}
		return sensorDao.getUniqueResult(queryString,p);
	}
	public List<Sensor> queryList(int con, String convalue, User user,
			int page, int size) {
		// TODO Auto-generated method stub
		
		String queryString = "from Sensor mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			if(con==1){
				queryString += "and mo.gateway.line.project.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==2){
				queryString += "and mo.gateway.line.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==3){
				queryString += "and mo.gateway.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==4){
				queryString += "and mo.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==5){
				int type = 0;
				if("其他".contains(convalue))
				{
					queryString += "and mo.sensortype ="+type; 
				}
				if("温度".contains(convalue))
				{
					type = 1;
					queryString += "and mo.sensortype ="+type; 
				}
				if("压力".contains(convalue))
				{
					type = 2;
					queryString += "and mo.sensortype ="+type; 
				}
				if("流量".contains(convalue))
				{
					type = 3;
					queryString += "and mo.sensortype ="+type; 
				}
				if("电池电压".contains(convalue))
				{
					type = 4;
					queryString += "and mo.sensortype ="+type; 
				}
				if("表面温度".contains(convalue))
				{
					type = 5;
					queryString += "and mo.sensortype ="+type; 
				}
				p = new Object[]{};
			}
			if(con==6){
				queryString += "and mo.devicetype like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name,mo.name asc ";
			break;
		case 1://高级管理员
			queryString += " and mo.gateway.line.project.id ="+user.getProject().getId();
			queryString += " order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name,mo.name asc ";
			break;
		case 2://普通管理员
			queryString += " and mo.gateway.line.id in("+user.getLinetext()+")";  
			queryString += " order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name,mo.name asc ";
			break;
		default:
			break;
		}
		return sensorDao.pageList(queryString,p,page,size);
	}
	//根据网关id和传感器编号查询传感器对象
	public Sensor getSensorByGatewayidAndSensoraddress(int gatewayid, int sensoraddress) {
		String queryString = "from Sensor mo where mo.sensoraddress=:sensoraddress and mo.gateway.id=:gatewayid";
		String[] paramNames = new String[] { "sensoraddress","gatewayid" };
		Object[] values = new Object[] { sensoraddress, gatewayid};
		return sensorDao.queryByNamedParam(queryString, paramNames, values);
	}
	public List<Sensor> getSensorsByLineid(int lineid) {
		String queryString = "from Sensor mo where mo.gateway.line.id=:lineid";
		String[] paramNames = new String[] { "lineid" };
		Object[] values = new Object[] { lineid};
		return sensorDao.queryList(queryString, paramNames, values);
	}
	public void updateSensorTempAndVoltage(float temperature,
			float battVoltage,Date nowdate, Integer sensorid) {
		String queryString = "update Sensor mo set mo.nowtemp=:temperature,mo.nowvoltage=:battVoltage,mo.lasttime=:nowdate where mo.id=:sensorid";
		String[] paramNames = new String[] {"temperature","battVoltage","nowdate", "sensorid" };
		Object[] values = new Object[] { temperature,battVoltage,nowdate,sensorid};
		sensorDao.updateByHql(queryString, paramNames, values);
		
	}
	//修改该传感器是否可以报警
	public void updateSensorIscanalarm(int iscanalarm, Integer sensorid) {
		String queryString = "update Sensor mo set mo.iscanalarm=:iscanalarm where mo.id=:sensorid";
		String[] paramNames = new String[] {"iscanalarm", "sensorid" };
		Object[] values = new Object[] { iscanalarm,sensorid};
		sensorDao.updateByHql(queryString, paramNames, values);
	}
	//修改该传感器的实时状态
	public void updateStatusBySensorid(int status, Integer sensorid) {
		String queryString = "update Sensor mo set mo.status=:status where mo.id=:sensorid";
		String[] paramNames = new String[] {"status", "sensorid" };
		Object[] values = new Object[] { status,sensorid};
		sensorDao.updateByHql(queryString, paramNames, values);
	}
	public void updateSensorIscanalarm2(int iscanalarm2, Integer sensorid) {
		String queryString = "update Sensor mo set mo.iscanalarm2=:iscanalarm2 where mo.id=:sensorid";
		String[] paramNames = new String[] {"iscanalarm2", "sensorid" };
		Object[] values = new Object[] { iscanalarm2,sensorid};
		sensorDao.updateByHql(queryString, paramNames, values);
	}
	//获取该项目所管辖线路的传感器
	public List<Sensor> getSensorsByLinetext(int projectid, String linetext) {
		String queryString = "from Sensor mo where mo.gateway.line.project.id=:projectid and mo.gateway.line.id in("+linetext+") order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name asc,mo.name asc";
		String[] paramNames = new String[] { "projectid" };
		Object[] values = new Object[] { projectid };
		return sensorDao.queryList(queryString, paramNames, values);
	}
	//获取该项目的传感器
	public List<Sensor> getSensorsByProjectid(int projectid) {
		String queryString = "from Sensor mo where mo.gateway.line.project.id=:projectid  order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name asc,mo.name asc";
		String[] paramNames = new String[] { "projectid" };
		Object[] values = new Object[] { projectid };
		return sensorDao.queryList(queryString, paramNames, values);
	}
	
	
}
