package com.jlj.service;

import java.util.Date;
import java.util.List;

import com.jlj.model.Sensor;
import com.jlj.model.User;

public interface ISensorService {

	//添加对象
	public abstract void add(Sensor sensor) throws Exception;

	//删除对象
	public abstract void delete(Sensor sensor);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Sensor sensor);

	//获取所有对象
	public abstract List<Sensor> getSensors();

	//加载一个id的对象
	public abstract Sensor loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int con, String convalue, int size);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue);

	//后台管理-获取符合条件的记录
	public abstract List<Sensor> queryList(int con, String convalue, int page,
			int size);

	public abstract Sensor getSensorByGatewayidAndSensoraddress(int gatewayid,
			int sensoraddress);
	
	public abstract int getTotalCount(int con, String convalue, User usero);

	public abstract int getPageCount(int totalCount, int size);

	public abstract List<Sensor> queryList(int con, String convalue,
			User usero, int page, int size);


	public abstract List<Sensor> getSensorsByLineid(int lineid);

	public abstract void updateSensorTempAndVoltage(float temperature,
			float battVoltage,Date nowdate, Integer id);

	public abstract void updateSensorIscanalarm(int iscanalarm, Integer id);

	public abstract void updateStatusBySensorid(int i, Integer id);

	public abstract void updateSensorIscanalarm2(int iscanalarm2, Integer id);

	public abstract List<Sensor> getSensorsByProjectid(int projectid);

	public abstract List<Sensor> getSensorsByLinetext(int projectid,
			String linetext);

}