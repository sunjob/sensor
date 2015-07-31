package com.jlj.service;

import java.util.List;

import com.jlj.model.Receivelog;

public interface IReceivelogService {

	public abstract void add(Receivelog receivelog) throws Exception;

	//删除对象
	public abstract void delete(Receivelog receivelog);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Receivelog receivelog);

	//获取所有对象
	public abstract List<Receivelog> getReceivelogs();

	//加载一个id的对象
	public abstract Receivelog loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int con, String convalue, int size);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue);

	//后台管理-获取符合条件的记录
	public abstract List<Receivelog> queryList(int con, String convalue,
			int page, int size);

	//获取总页数
	public abstract int getPageCount(int totalCount, int size);

	//获取某项目的线路记录条数
	public abstract int getProjectTotalCount(int con, String convalue,
			int projectid);

	//获取某项目的线路记录
	public abstract List<Receivelog> queryProjectList(int con, String convalue,
			int projectid, int page, int size);

	//获得该项目的所有记录一并显示
	public abstract List<Receivelog> getReceivelogsByPid(int projectid);

}