package com.jlj.service;

import java.util.Date;
import java.util.List;

import com.jlj.model.Operation;
import com.jlj.model.User;

public interface IOperationService {

	//添加对象
	public abstract void add(Operation operation) throws Exception;

	//删除对象
	public abstract void delete(Operation operation);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Operation operation);

	//获取所有对象
	public abstract List<Operation> getOperations();

	//加载一个id的对象
	public abstract Operation loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int totalCount, int size);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue, User user, String startdate, String enddate);

	//后台管理-获取符合条件的记录
	public abstract List<Operation> queryList(int con, String convalue, User user,
			int page, int size, String startdate, String enddate);

}