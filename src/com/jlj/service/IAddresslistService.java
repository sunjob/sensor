package com.jlj.service;

import java.util.List;

import com.jlj.model.Addresslist;
import com.jlj.model.User;

public interface IAddresslistService {

	//添加对象
	public abstract void add(Addresslist addresslist) throws Exception;

	//删除对象
	public abstract void delete(Addresslist addresslist);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Addresslist addresslist);

	//获取所有对象
	public abstract List<Addresslist> getAddresslists();

	//加载一个id的对象
	public abstract Addresslist loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int totalCount, int size);

	//后台管理-获取总记录数
	/**
	 * con-选择第1项
	 * convalue-输入线路名称
	 */
	public abstract int getTotalCount(int con, String convalue, User user);

	//后台管理-获取符合条件的记录
	/**
	 * con-选择第1项
	 * convalue-输入线路名称
	 * page-第几页
	 * size-每页显示条数
	 */
	public abstract List<Addresslist> queryList(int con, String convalue, User user , int page,
			int size);

	public abstract List<Addresslist> queryByUserList(User user);

	
}