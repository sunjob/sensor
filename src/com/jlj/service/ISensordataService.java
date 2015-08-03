package com.jlj.service;

import java.util.List;

import com.jlj.model.Sensordata;

public interface ISensordataService {

	//添加对象
	public abstract void add(Sensordata sensordata) throws Exception;

	//删除对象
	public abstract void delete(Sensordata sensordata);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Sensordata sensordata);

	//获取所有对象
	public abstract List<Sensordata> getSensordatas();

	//加载一个id的对象
	public abstract Sensordata loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int con, String convalue, int size);

	//后台管理-页数获取
	public abstract int getPageCount(int totalCount, int size);
	
	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue);

	//后台管理-获取符合条件的记录
	public abstract List<Sensordata> queryList(int con, String convalue,
			int page, int size);

	public abstract int getTotalCount(String starttime, String endtime,
			String gatewayname, int stype, String sensorname);

	public abstract List<Sensordata> queryList(String starttime,
			String endtime, String gatewayname, int stype, String sensorname,
			int page, int size);

	public abstract int getProjectTotalCount(String starttime, String endtime,
			String gatewayname, int stype, String sensorname, int projectid);

	public abstract List<Sensordata> queryProjectList(String starttime,
			String endtime, String gatewayname, int stype, String sensorname,
			int projectid, int page, int size);

	public abstract int getManageTotalCount(String starttime, String endtime,
			String gatewayname, int stype, String sensorname, String linetext);

	public abstract List<Sensordata> queryManageList(String starttime,
			String endtime, String gatewayname, int stype, String sensorname,
			String linetext, int page, int size);

	public abstract List<Sensordata> querySensoridAllList(String starttime,
			String endtime, int sensortype,int sensorid);

	public abstract Sensordata getSensordataBySensoridAndStype(int stype,
			int sensorid);

	public abstract List<Sensordata> getTempsByDates(String startdate,
			String enddate);

	public abstract int getTotalCount(String starttime, String endtime,
			int con, String convalue, int stype);

	public abstract List<Sensordata> queryList(String starttime,
			String endtime, int con, String convalue, int stype, int page,
			int size);

	public abstract int getProjectTotalCount(String starttime, String endtime,
			int con, String convalue, int stype, int projectid);

	public abstract List<Sensordata> queryProjectList(String starttime,
			String endtime, int con, String convalue, int stype, int projectid,
			int page, int size);

	public abstract int getManageTotalCount(String starttime, String endtime,
			int con, String convalue, int stype, String linetext);

	public abstract List<Sensordata> queryManageList(String starttime,
			String endtime, int con, String convalue, int stype,
			String linetext, int page, int size);

	//查询数据库的总容量
	public abstract float getMysqlDatabaseSize();

	public abstract Sensordata getOldestSensordata(int page, int size);

	public abstract void deleteByDate(String startdate, String enddate);

	public abstract List<Sensordata> queryExportList(String starttime,
			String endtime, int con, String convalue, int stype);

	public abstract List<Sensordata> queryExportProjectList(String starttime,
			String endtime, int con, String convalue, int stype, int projectid);

	public abstract List<Sensordata> queryExportManageList(String starttime,
			String endtime, int con, String convalue, int stype, String linetext);

	public abstract Sensordata getOldestSensordataBySensorId(int sensorid, int page,
			int size);
}