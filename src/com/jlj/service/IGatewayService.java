package com.jlj.service;

import java.util.List;

import com.jlj.model.Gateway;
import com.jlj.model.User;

public interface IGatewayService {

	//添加对象
	public abstract void add(Gateway gateway) throws Exception;

	//删除对象
	public abstract void delete(Gateway gateway);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Gateway gateway);

	//获取所有对象
	public abstract List<Gateway> getGateways();

	//加载一个id的对象
	public abstract Gateway loadById(int id);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue);

	//后台管理-获取符合条件的记录
	public abstract List<Gateway> queryList(int con, String convalue, int page,
			int size);

	public abstract int getPageCount(int totalCount, int size);

	public abstract int getTotalCount(int con, String convalue, User usero);

	public abstract List<Gateway> queryList(int con, String convalue,
			User usero, int page, int size);


	public abstract Gateway getGatewayById(int id);

	public abstract Gateway getGatewayByChannel(int channel);
	public abstract Gateway getGatewayByMacaddress(String macaddress);

	public abstract Gateway getGatewayByGateaddress(int gateaddress);

	public abstract List<Gateway> getGatewaysByLineid(int lineid);

	public abstract void updateNewGatewayIpById(String sessionIP, Integer id);

	public abstract List<Gateway> getGatewaysByProjectId(Integer id);

	public abstract List<Gateway> getGatewaysByLinetext(int projectid,
			String linetext);

	public abstract Gateway getGatewayByIp(String sessionIP1);

	public abstract void updateChannelById(int channel, int gatewayid);

	public abstract Gateway getGatewayByGateaddressAndLineid(int gateaddress,
			int lineid);

}