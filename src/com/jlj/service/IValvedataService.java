package com.jlj.service;

import java.util.List;

import com.jlj.model.Valvedata;

public interface IValvedataService {

	public abstract void add(Valvedata valvedata) throws Exception;

	//删除对象
	public abstract void delete(Valvedata valvedata);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Valvedata valvedata);

	//获取所有对象
	public abstract List<Valvedata> getValvedatas();

	//加载一个id的对象
	public abstract Valvedata loadById(int id);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue);

	//后台管理-获取符合条件的记录
	public abstract List<Valvedata> queryList(int con, String convalue,
			int page, int size);

	//获取总页数
	public abstract int getPageCount(int totalCount, int size);

	public abstract int getValveTotalCount(int con, String convalue, int valveid);

	public abstract List<Valvedata> queryValveList(int con, String convalue,
			int valveid, int page, int size);

}