package com.jlj.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.IAlarmrecordDao;
import com.jlj.model.Alarmrecord;
import com.jlj.service.IAlarmrecordService;
@Component("alarmrecordservice")
public class AlarmrecordServiceImp implements IAlarmrecordService {
	private IAlarmrecordDao alarmrecordDao;
	public IAlarmrecordDao getAlarmrecordDao() {
		return alarmrecordDao;
	}
	@Resource
	public void setAlarmrecordDao(IAlarmrecordDao alarmrecordDao) {
		this.alarmrecordDao = alarmrecordDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmrecordService#add(com.jlj.model.Alarmrecord)
	 */
	public void add(Alarmrecord alarmrecord) throws Exception {
		alarmrecordDao.save(alarmrecord);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmrecordService#delete(com.jlj.model.Alarmrecord)
	 */
	public void delete(Alarmrecord alarmrecord) {
		alarmrecordDao.delete(alarmrecord);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmrecordService#deleteById(int)
	 */
	public void deleteById(int id) {
		alarmrecordDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmrecordService#update(com.jlj.model.Alarmrecord)
	 */
	public void update(Alarmrecord alarmrecord) {
		alarmrecordDao.update(alarmrecord);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmrecordService#getAlarmrecords()
	 */
	public List<Alarmrecord> getAlarmrecords() {
		return alarmrecordDao.getAlarmrecords();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmrecordService#loadById(int)
	 */
	public Alarmrecord loadById(int id) {
		return alarmrecordDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmrecordService#getPageCount(int, int)
	 */
	public int getPageCount(int totalCount,int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//超级管理员-后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmrecordService#getTotalCount(int, java.lang.String, int)
	 */
	public int getTotalCount(String starttime,String endtime,int con, String convalue,int projectid) {
		String queryString = "select count(*) from Alarmrecord mo where mo.sensor.gateway.line.project.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-线路名称2-网关名称3-传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.sensor.name like ? "; 
			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.alarmtime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.alarmtime<='"+endtime+"'";
		}
		return alarmrecordDao.getUniqueResult(queryString,p);
	}
	//超级管理员-后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmrecordService#queryList(int, java.lang.String, int, int, int)
	 */
	public List<Alarmrecord> queryList(String starttime,String endtime,int con, String convalue,int projectid, int page, int size) {
		String queryString = "from Alarmrecord mo where mo.sensor.gateway.line.project.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-线路名称2-网关名称3-传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.sensor.name like ? "; 
			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.alarmtime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.alarmtime<='"+endtime+"'";
		}
		queryString += " order by mo.sensor.status desc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id desc ";
		return alarmrecordDao.pageList(queryString,p,page,size);
	}
	//系统管理员-获得总记录
	public int getTotalCount(String starttime, String endtime, int con,
			String convalue) {
		String queryString = "select count(*) from Alarmrecord mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-线路名称2-网关名称3-传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.sensor.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.alarmtime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.alarmtime<='"+endtime+"'";
		}
		return alarmrecordDao.getUniqueResult(queryString,p);
	}
	//系统管理员-查询所有记录
	public List<Alarmrecord> queryList(String starttime, String endtime,
			int con, String convalue, int page, int size) {
		String queryString = "from Alarmrecord mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-线路名称2-网关名称3-传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.sensor.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.alarmtime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.alarmtime<='"+endtime+"'";
		}
		queryString += " order by mo.sensor.status desc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id desc ";
		return alarmrecordDao.pageList(queryString,p,page,size);
	}
	//普通管理员-查询记录总数
	public int getTotalCount(String starttime, String endtime, int con,
			String convalue, String linetext) {
		String queryString = "select count(*) from Alarmrecord mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-线路名称2-网关名称3-传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.sensor.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.alarmtime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.alarmtime<='"+endtime+"'";
		}
		if(linetext!=null&&!linetext.equals("")){
			queryString += " and mo.sensor.gateway.line.id in("+linetext+")";
		}
		return alarmrecordDao.getUniqueResult(queryString,p);
	}
	//普通管理员-查询所有记录
	public List<Alarmrecord> queryList(String starttime, String endtime,
			int con, String convalue, String linetext, int page, int size) {
		String queryString = "from Alarmrecord mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-线路名称2-网关名称3-传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.sensor.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.alarmtime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.alarmtime<='"+endtime+"'";
		}
		if(linetext!=null&&!linetext.equals("")){
			queryString += " and mo.sensor.gateway.line.id in("+linetext+")";
		}
		queryString += " order by mo.sensor.status desc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id desc ";
		return alarmrecordDao.pageList(queryString,p,page,size);
	}
	//系统管理员-查询所有记录
	public List<Alarmrecord> queryExportList(String starttime, String endtime,
			int con, String convalue) {
		String queryString = "from Alarmrecord mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-线路名称2-网关名称3-传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.sensor.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.alarmtime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.alarmtime<='"+endtime+"'";
		}
		queryString += " order by mo.sensor.status desc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id desc ";
		return alarmrecordDao.getObjectsByCondition(queryString, p);
	}
	//超级管理员-查询该项目的报警记录
	public List<Alarmrecord> queryExportList(String starttime, String endtime,
			int con, String convalue, int projectid) {
		String queryString = "from Alarmrecord mo where mo.sensor.gateway.line.project.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-线路名称2-网关名称3-传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.sensor.name like ? "; 
			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.alarmtime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.alarmtime<='"+endtime+"'";
		}
		queryString += " order by mo.sensor.status desc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id desc ";
		return alarmrecordDao.getObjectsByCondition(queryString, p);
	}
	//普通管理员-查询所管辖的报警记录
	public List<Alarmrecord> queryExportList(String starttime, String endtime,
			int con, String convalue, String linetext) {
		String queryString = "from Alarmrecord mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-线路名称2-网关名称3-传感器编号
			if(con==1){
				queryString += " and mo.sensor.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.sensor.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.sensor.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(starttime!=null&&!starttime.equals("")){
			queryString += " and mo.alarmtime>='"+starttime+"'";
		}
		if(endtime!=null&&!endtime.equals("")){
			queryString += " and mo.alarmtime<='"+endtime+"'";
		}
		if(linetext!=null&&!linetext.equals("")){
			queryString += " and mo.sensor.gateway.line.id in("+linetext+")";
		}
		queryString += " order by mo.sensor.status desc,mo.sensor.gateway.name asc,mo.sensor.name asc,mo.id desc ";
		return alarmrecordDao.getObjectsByCondition(queryString, p);
	}
	
	
}
