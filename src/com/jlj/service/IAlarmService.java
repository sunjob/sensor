package com.jlj.service;

import java.util.List;

import com.jlj.model.Alarm;

public interface IAlarmService {

	//添加对象
	public abstract void add(Alarm alarm) throws Exception;

	//删除对象
	public abstract void delete(Alarm alarm);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Alarm alarm);

	//获取所有对象
	public abstract List<Alarm> getAlarms();

	//加载一个id的对象
	public abstract Alarm loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int totalCount, int size);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue, int projectid);

	//后台管理-获取符合条件的记录
	public abstract List<Alarm> queryList(int con, String convalue, int projectid , int page,
			int size);

	public abstract List<Alarm>  getProjectAlarmSet(Integer id);

	public abstract Alarm loadByProjectId(Integer id);

	public abstract Alarm getAlarmByProjectid(Integer id);

}