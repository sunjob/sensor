package com.jlj.service;

import java.util.List;

import com.jlj.model.User;
import com.jlj.model.Valve;

public interface IValveService {

	//添加对象
	public abstract void add(Valve valve) throws Exception;

	//删除对象
	public abstract void delete(Valve valve);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Valve valve);

	//获取所有对象
	public abstract List<Valve> getValves();

	//加载一个id的对象
	public abstract Valve loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int totalCount, int size);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue,User user);

	//后台管理-获取符合条件的记录
	public abstract List<Valve> queryList(int con, String convalue,User user, int page,
			int size);

	public abstract List<Valve> queryList(int con, String convalue, int page,
			int size);

	public abstract int getTotalCount(int con, String convalue);

	public abstract int getProjectTotalCount(int con, String convalue,
			int projectid);

	public abstract List<Valve> queryProjectList(int con, String convalue,
			int projectid, int page, int size);

	public abstract List<Valve> queryManageList(int con, String convalue,
			String linetext, int page, int size);

	public abstract int getManageTotalCount(int con, String convalue,
			String linetext);

	public abstract Valve getValveByGatewayIdAndValveaddress(int gatewayid,
			int valveaddress);

	public abstract void updateStatusByConditionAndGatewayid(int controlvalue,
			int status, int valveaddress, int locatenumber, int gatewayid);

}