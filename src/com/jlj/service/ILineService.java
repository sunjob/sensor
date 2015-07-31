package com.jlj.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jlj.model.Line;
import com.jlj.model.LineInfo;
import com.jlj.model.User;

public interface ILineService {

	//添加对象
	public abstract void add(Line line) throws Exception;

	//删除对象
	public abstract void delete(Line line);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Line line);

	//获取所有对象
	public abstract List<Line> getLines();

	//加载一个id的对象
	public abstract Line loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int con, String convalue, int size);

	//后台管理-获取总记录数
	/**
	 * con-选择第1项
	 * convalue-输入线路名称
	 */
	public abstract int getTotalCount(int con, String convalue);

	//后台管理-获取符合条件的记录
	/**
	 * con-选择第1项
	 * convalue-输入线路名称
	 * page-第几页
	 * size-每页显示条数
	 */
	public abstract List<Line> queryList(int con, String convalue, int page,
			int size);

	public abstract int getPageCount(int con, String convalue, int size,
			 User user);

	public abstract List<Line> queryList(int con, String convalue, int page,
			int size,  User user);

	public abstract int getTotalCount(int con, String convalue, 
			User user);

	public abstract List<Line> getLinesByPid(int projectid);

	//后台管理-页数获取
	public abstract int getPageCount(int totalCount, int size);

	public abstract int getProjectTotalCount(int con, String convalue,
			int projectid);

	public abstract List<Line> queryProjectList(int con, String convalue,
			int projectid, int page, int size);

	public abstract int getManageTotalCount(int con, String convalue,
			String linetext);

	public abstract List<Line> queryManageList(int con, String convalue,
			String linetext, int page, int size);

	public abstract List<Line> getLinesByLinetext(int projectid, String linetext);

	public abstract List<Line> getNotestLinesByPid(int projectid);

	public abstract Line getProjectTestLine(Integer id);
	
	public abstract Line getLineByProjectidAndOrderid(int projectid, int orderid);
	
}