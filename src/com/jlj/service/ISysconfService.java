package com.jlj.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jlj.model.Sysconf;
import com.jlj.model.SysconfInfo;

public interface ISysconfService {

	//添加对象
	public abstract void add(Sysconf sysconf) throws Exception;

	//删除对象
	public abstract void delete(Sysconf sysconf);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Sysconf sysconf);

	//获取所有对象
	public abstract List<Sysconf> getSysconfs();

	//加载一个id的对象
	public abstract Sysconf loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int con, String convalue, int size);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue);

	//后台管理-获取符合条件的记录
	public abstract List<Sysconf> queryList(int con, String convalue, int page,
			int size);

	//webservice部分
	@GET
	@Path("/sysconflist")
	@Produces( { MediaType.APPLICATION_JSON })
	public abstract List<SysconfInfo> getSysconfsJson();

	@GET
	@Path("/upconfstatus/{status}/{sysid}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void updateStatus(@PathParam("status") int status,@PathParam("sysid") int sysid);

	/**
	 * 修改配置信息
	 */
	@GET
	@Path("/upconfstatus/{date}/{status}/{sysid}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int updateConfStatus(@PathParam("date") String date,@PathParam("status") int status,@PathParam("sysid") int sysid);
}