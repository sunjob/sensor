package com.jlj.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.ISensordataDao;
import com.jlj.model.Sensordata;
import com.jlj.service.ISensordataService;
@Component("sensordataservice")
public class SensordataServiceImp implements ISensordataService{
	private ISensordataDao sensordataDao;
	public ISensordataDao getSensordataDao() {
		return sensordataDao;
	}
	@Resource
	public void setSensordataDao(ISensordataDao sensordataDao) {
		this.sensordataDao = sensordataDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensordataService#add(com.jlj.model.Sensordata)
	 */
	public void add(Sensordata sensordata) throws Exception {
		sensordataDao.save(sensordata);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensordataService#delete(com.jlj.model.Sensordata)
	 */
	public void delete(Sensordata sensordata) {
		sensordataDao.delete(sensordata);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensordataService#deleteById(int)
	 */
	public void deleteById(int id) {
		sensordataDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensordataService#update(com.jlj.model.Sensordata)
	 */
	public void update(Sensordata sensordata) {
		sensordataDao.update(sensordata);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensordataService#getSensordatas()
	 */
	public List<Sensordata> getSensordatas() {
		return sensordataDao.getSensordatas();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensordataService#loadById(int)
	 */
	public Sensordata loadById(int id) {
		return sensordataDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensordataService#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int con, String convalue,int size) {
		int totalCount=this.getTotalCount(con, convalue);
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensordataService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue) {
		String queryString = "select count(*) from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.orderid asc ";
		return sensordataDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISensordataService#queryList(int, java.lang.String, int, int)
	 */
	public List<Sensordata> queryList(int con, String convalue, int page, int size) {
		String queryString = "from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.orderid asc ";
		return sensordataDao.pageList(queryString,p,page,size);
	}
	public int getPageCount(int totalCount, int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//查询系统管理员的总记录数----------------------------------
	public int getTotalCount(String starttime, String endtime,
			String gatewayname, int stype, String sensorname) {
		String queryString = "select count(*) from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(gatewayname!=null&&!gatewayname.equals("")){
			queryString += " and mo.sensor.gateway.name like '%"+gatewayname+"%'";
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(sensorname!=null&&!sensorname.equals("")){
			queryString += " and mo.sensor.name like '%"+sensorname+"%'";
		}
		queryString += " order by mo.id asc ";
		//System.out.println("getTotalCount sql========="+queryString);
		return sensordataDao.getUniqueResult(queryString,p);
	}
	//查询系统管理员的记录
	public List<Sensordata> queryList(String starttime, String endtime,
			String gatewayname, int stype, String sensorname, int page, int size) {
		String queryString = "from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(gatewayname!=null&&!gatewayname.equals("")){
			queryString += " and mo.sensor.gateway.name like '%"+gatewayname+"%'";
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(sensorname!=null&&!sensorname.equals("")){
			queryString += " and mo.sensor.name like '%"+sensorname+"%'";
		}
		queryString += " order by mo.sensor.gateway.line.project.name asc,mo.sensor.gateway.line.name asc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id asc ";
		//System.out.println("queryList sql========="+queryString);
		return sensordataDao.pageList(queryString,p,page,size);
	}
	
	
	//查询超级管理员的该项目的报表总记录数----------------------------------
	public int getProjectTotalCount(String starttime, String endtime,
			String gatewayname, int stype, String sensorname, int projectid) {
		String queryString = "select count(*) from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(gatewayname!=null&&!gatewayname.equals("")){
			queryString += " and mo.sensor.gateway.name like '%"+gatewayname+"%'";
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(sensorname!=null&&!sensorname.equals("")){
			queryString += " and mo.sensor.name like '%"+sensorname+"%'";
		}
		if(projectid!=0){
			queryString += " and mo.sensor.gateway.line.project.id ="+projectid;
		}
		queryString += " order by mo.id asc ";
		//System.out.println("getProjectTotalCount sql========="+queryString);
		return sensordataDao.getUniqueResult(queryString,p);
	}
	//查询超级管理员的该项目的报表记录
	public List<Sensordata> queryProjectList(String starttime, String endtime,
			String gatewayname, int stype, String sensorname, int projectid,
			int page, int size) {
		String queryString = "from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(gatewayname!=null&&!gatewayname.equals("")){
			queryString += " and mo.sensor.gateway.name like '%"+gatewayname+"%'";
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(sensorname!=null&&!sensorname.equals("")){
			queryString += " and mo.sensor.name like '%"+sensorname+"%'";
		}
		if(projectid!=0){
			queryString += " and mo.sensor.gateway.line.project.id ="+projectid;
		}
		queryString += " order by mo.sensor.gateway.line.name asc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id asc ";
		//System.out.println("queryProjectList sql========="+queryString);
		return sensordataDao.pageList(queryString,p,page,size);
	}
	//查询普通管理员的报表总记录数----------------------------------
	public int getManageTotalCount(String starttime, String endtime,
			String gatewayname, int stype, String sensorname, String linetext) {
		String queryString = "select count(*) from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(gatewayname!=null&&!gatewayname.equals("")){
			queryString += " and mo.sensor.gateway.name like '%"+gatewayname+"%'";
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(sensorname!=null&&!sensorname.equals("")){
			queryString += " and mo.sensor.name like '%"+sensorname+"%'";
		}
		if(linetext!=null&&!linetext.equals("")){
			queryString += " and mo.sensor.gateway.line.id in("+linetext+")";
		}
		queryString += " order by mo.id asc ";
		//System.out.println("getManageTotalCount sql========="+queryString);
		return sensordataDao.getUniqueResult(queryString,p);
	}
	//查询普通管理员的报表记录
	public List<Sensordata> queryManageList(String starttime, String endtime,
			String gatewayname, int stype, String sensorname, String linetext,
			int page, int size) {
		String queryString = "from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(gatewayname!=null&&!gatewayname.equals("")){
			queryString += " and mo.sensor.gateway.name like '%"+gatewayname+"%'";
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(sensorname!=null&&!sensorname.equals("")){
			queryString += " and mo.sensor.name like '%"+sensorname+"%'";
		}
		if(linetext!=null&&!linetext.equals("")){
			queryString += " and mo.sensor.gateway.line.id in("+linetext+")";
		}
		queryString += " order by mo.sensor.gateway.line.name asc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id asc ";
		//System.out.println("queryManageList sql========="+queryString);
		return sensordataDao.pageList(queryString,p,page,size);
	}
	//查询该传感器的某段时间内的所有数据列表
	public List<Sensordata> querySensoridAllList(String starttime,
			String endtime, int sensortype, int sensorid) {
		String queryString = "from Sensordata mo where mo.stype=:sensortype and mo.sensor.id=:sensorid ";
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		queryString += " order by mo.id asc ";
		String[] paramNames = new String[] {"sensortype", "sensorid" };
		Object[] values = new Object[] { sensortype,sensorid};
		return sensordataDao.queryList(queryString, paramNames, values);
	}
	public Sensordata getSensordataBySensoridAndStype(int stype, int sensorid) {
		String queryString = "from Sensordata mo where mo.stype=:stype and mo.sensor.id=:sensorid order by mo.id desc";
		String[] paramNames = new String[] { "stype","sensorid" };
		Object[] values = new Object[] { stype, sensorid};
		return sensordataDao.queryByNamedParam(queryString, paramNames, values);
	}
	public List<Sensordata> getTempsByDates(String startdate, String enddate) {
		String queryString = "from Sensordata mo where mo.sdatetime>='"+startdate+"' and mo.sdatetime<='"+enddate+"'";
		return sensordataDao.getObjectsByCondition(queryString, null);
	}
	/**
	 * 移动端和网页端新方法
	 */
	//查询系统管理员的总记录数----------------------------------
	public int getTotalCount(String starttime, String endtime,
			int con, String convalue, int stype) {
		String queryString = "select count(*) from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1线路名称、2网关编号、3传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? ";
			}else if(con==3){
				queryString += " and mo.sensor.name like ? ";
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
//		System.out.println("getTotalCount sql========="+queryString);
		return sensordataDao.getUniqueResult(queryString,p);
	}
	//查询系统管理员的记录
	public List<Sensordata> queryList(String starttime, String endtime,
			int con, String convalue, int stype, int page, int size) {
		String queryString = "from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1线路名称、2网关编号、3传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? ";
			}else if(con==3){
				queryString += " and mo.sensor.name like ? ";
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		queryString += " order by mo.sensor.gateway.line.project.name asc,mo.sensor.gateway.line.name asc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id asc ";
//		System.out.println("queryList sql========="+queryString);
		return sensordataDao.pageList(queryString,p,page,size);
	}
	
	
	//查询超级管理员的该项目的报表总记录数----------------------------------
	public int getProjectTotalCount(String starttime, String endtime,
			int con, String convalue, int stype,  int projectid) {
		String queryString = "select count(*) from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1线路名称、2网关编号、3传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? ";
			}else if(con==3){
				queryString += " and mo.sensor.name like ? ";
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(projectid!=0){
			queryString += " and mo.sensor.gateway.line.project.id ="+projectid;
		}
		//System.out.println("getProjectTotalCount sql========="+queryString);
		return sensordataDao.getUniqueResult(queryString,p);
	}
	//查询超级管理员的该项目的报表记录
	public List<Sensordata> queryProjectList(String starttime, String endtime,
			int con, String convalue, int stype,  int projectid,
			int page, int size) {
		String queryString = "from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1线路名称、2网关编号、3传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? ";
			}else if(con==3){
				queryString += " and mo.sensor.name like ? ";
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(projectid!=0){
			queryString += " and mo.sensor.gateway.line.project.id ="+projectid;
		}
		queryString += " order by mo.sensor.gateway.line.name asc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id asc ";
		//System.out.println("queryProjectList sql========="+queryString);
		return sensordataDao.pageList(queryString,p,page,size);
	}
	//查询普通管理员的报表总记录数----------------------------------
	public int getManageTotalCount(String starttime, String endtime,
			int con, String convalue, int stype,  String linetext) {
		String queryString = "select count(*) from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1线路名称、2网关编号、3传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? ";
			}else if(con==3){
				queryString += " and mo.sensor.name like ? ";
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(linetext!=null&&!linetext.equals("")){
			queryString += " and mo.sensor.gateway.line.id in("+linetext+")";
		}
		//System.out.println("getManageTotalCount sql========="+queryString);
		return sensordataDao.getUniqueResult(queryString,p);
	}
	//查询普通管理员的报表记录
	public List<Sensordata> queryManageList(String starttime, String endtime,
			int con, String convalue, int stype,  String linetext,
			int page, int size) {
		String queryString = "from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1线路名称、2网关编号、3传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? ";
			}else if(con==3){
				queryString += " and mo.sensor.name like ? ";
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(linetext!=null&&!linetext.equals("")){
			queryString += " and mo.sensor.gateway.line.id in("+linetext+")";
		}
		queryString += " order by mo.sensor.gateway.line.name asc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id asc ";
		//System.out.println("queryManageList sql========="+queryString);
		return sensordataDao.pageList(queryString,p,page,size);
	}
	//查询数据库的总容量
	public float getMysqlDatabaseSize(){
//		String sql="select round(sum((data_length+index_length)/1024/1024),2) as data from information_schema.tables where TABLE_SCHEMA='sensor'";
		String sql="select round(sum((data_length+index_length)/1024/1024),2) as data from information_schema.tables ";
		return sensordataDao.executeBySql(sql);
	}
	public Sensordata getOldestSensordata(int page, int size) {
		String queryString = "from Sensordata mo order by mo.id asc";
		Object[] p = null;
		return (sensordataDao.pageList(queryString,p,page,size)).get(0);
	}
	public void deleteByDate(String startdate, String enddate) {
		String queryString = "delete from Sensordata mo where mo.sdatetime>='"+startdate+"' and mo.sdatetime<='"+enddate+"'";
		sensordataDao.updateByHql(queryString, null, null);
	}
	//查询导出的所有数据
	public List<Sensordata> queryExportList(String starttime, String endtime,
			int con, String convalue, int stype) {
		String queryString = "from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1线路名称、2网关编号、3传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? ";
			}else if(con==3){
				queryString += " and mo.sensor.name like ? ";
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		queryString += " order by mo.sensor.gateway.line.project.name asc,mo.sensor.gateway.line.name asc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id asc ";
		return sensordataDao.getObjectsByCondition(queryString, p);
	}
	//查询导出的所属项目的数据
	public List<Sensordata> queryExportProjectList(String starttime,
			String endtime, int con, String convalue, int stype, int projectid) {
		String queryString = "from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1线路名称、2网关编号、3传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? ";
			}else if(con==3){
				queryString += " and mo.sensor.name like ? ";
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(projectid!=0){
			queryString += " and mo.sensor.gateway.line.project.id ="+projectid;
		}
		queryString += " order by mo.sensor.gateway.line.name asc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id asc ";
		return sensordataDao.getObjectsByCondition(queryString, p);
	}
	
	//查出导出的该用户管辖范围内的数据
	public List<Sensordata> queryExportManageList(String starttime,
			String endtime, int con, String convalue, int stype, String linetext) {
		String queryString = "from Sensordata mo where 1=1 ";
		Object[] p = null;
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.sdatetime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.sdatetime<='"+endtime+"'";
		}
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1线路名称、2网关编号、3传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? ";
			}else if(con==3){
				queryString += " and mo.sensor.name like ? ";
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(stype!=0){
			queryString += " and mo.stype ="+stype;
		}
		if(linetext!=null&&!linetext.equals("")){
			queryString += " and mo.sensor.gateway.line.id in("+linetext+")";
		}
		queryString += " order by mo.sensor.gateway.line.name asc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id asc ";
		return sensordataDao.getObjectsByCondition(queryString, p);
	}
}
