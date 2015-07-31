package com.jlj.service;

import java.util.List;

import com.jlj.model.Alarmrecord;

public interface IAlarmrecordService {

	//添加对象
	public abstract void add(Alarmrecord alarmrecord) throws Exception;

	//删除对象
	public abstract void delete(Alarmrecord alarmrecord);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Alarmrecord alarmrecord);

	//获取所有对象
	public abstract List<Alarmrecord> getAlarmrecords();

	//加载一个id的对象
	public abstract Alarmrecord loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int totalCount, int size);

	//后台管理-获取总记录数
	public abstract int getTotalCount(String starttime,String endtime,int con, String convalue, int projectid);

	//后台管理-获取符合条件的记录
	public abstract List<Alarmrecord> queryList(String starttime,String endtime,int con, String convalue,
			int projectid, int page, int size);
	/**
	 * 系统管理员-获得总记录
	 * @param starttime
	 * @param endtime
	 * @param con
	 * @param convalue
	 * @return
	 */
	public abstract int getTotalCount(String starttime, String endtime,
			int con, String convalue);

	/**
	 * 系统管理员-查询所有记录
	 * @param starttime
	 * @param endtime
	 * @param con
	 * @param convalue
	 * @param page
	 * @param size
	 * @return
	 */
	public abstract List<Alarmrecord> queryList(String starttime,
			String endtime, int con, String convalue, int page, int size);
	/**
	 * 普通管理员-查询记录总数
	 * @param starttime
	 * @param endtime
	 * @param con
	 * @param convalue
	 * @param linetext
	 * @return
	 */
	public abstract int getTotalCount(String starttime, String endtime,
			int con, String convalue, String linetext);

	/**
	 * 普通管理员-查询所有记录
	 * @param starttime
	 * @param endtime
	 * @param con
	 * @param convalue
	 * @param linetext
	 * @param page
	 * @param size
	 * @return
	 */
	public abstract List<Alarmrecord> queryList(String starttime,
			String endtime, int con, String convalue, String linetext,
			int page, int size);

	public abstract List<Alarmrecord> queryExportList(String starttime,
			String endtime, int con, String convalue);

	public abstract List<Alarmrecord> queryExportList(String starttime,
			String endtime, int con, String convalue, int projectid);

	public abstract List<Alarmrecord> queryExportList(String starttime,
			String endtime, int con, String convalue, String linetext);

}