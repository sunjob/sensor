package com.jlj.service;

import java.util.List;

import com.jlj.model.Command;
import com.jlj.model.User;

public interface ICommandService {

	//添加对象
	public abstract void add(Command command) throws Exception;

	//删除对象
	public abstract void delete(Command command);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Command command);

	//获取所有对象
	public abstract List<Command> getCommands();

	//加载一个id的对象
	public abstract Command loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int totalCount, int size);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue,User user);

	//后台管理-获取符合条件的记录
	public abstract List<Command> queryList(int con, String convalue,User user, int page,
			int size);

}