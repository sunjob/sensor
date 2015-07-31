package com.jlj.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.IAlarmDao;
import com.jlj.model.Alarm;
import com.jlj.model.Line;
import com.jlj.service.IAlarmService;
@Component("alarmservice")
public class AlarmServiceImp implements IAlarmService{
	private IAlarmDao alarmDao;
	public IAlarmDao getAlarmDao() {
		return alarmDao;
	}
	@Resource
	public void setAlarmDao(IAlarmDao alarmDao) {
		this.alarmDao = alarmDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmService#add(com.jlj.model.Alarm)
	 */
	public void add(Alarm alarm) throws Exception {
		alarmDao.save(alarm);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmService#delete(com.jlj.model.Alarm)
	 */
	public void delete(Alarm alarm) {
		alarmDao.delete(alarm);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmService#deleteById(int)
	 */
	public void deleteById(int id) {
		alarmDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmService#update(com.jlj.model.Alarm)
	 */
	public void update(Alarm alarm) {
		alarmDao.update(alarm);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmService#getAlarms()
	 */
	public List<Alarm> getAlarms() {
		return alarmDao.getAlarms();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmService#loadById(int)
	 */
	public Alarm loadById(int id) {
		return alarmDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmService#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int totalCount,int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue,int projectid) {
		String queryString = "select count(*) from Alarm mo where mo.project.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//手机号
			if(con==1){
				queryString += "and mo.phones like ? "; 
			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		queryString += " order by mo.id desc ";
		return alarmDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IAlarmService#queryList(int, java.lang.String, int, int)
	 */
	public List<Alarm> queryList(int con, String convalue,int projectid, int page, int size) {
		String queryString = "from Alarm mo where mo.project.id=?  ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//手机号
			if(con==1){
				queryString += "and mo.phones like ? "; 
			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		queryString += " order by mo.id desc ";
		return alarmDao.pageList(queryString,p,page,size);
	}
	public List<Alarm>  getProjectAlarmSet(Integer id) {
		String queryString = "from Alarm mo where mo.project.id= "+id;
		Object[] p = null;
		List<Alarm> alarmSets =  alarmDao.getObjectsByCondition(queryString, p);
		return alarmSets;
	}
	public Alarm loadByProjectId(Integer id) {
		String queryString = "from Alarm mo where mo.project.id= "+id;
		Object[] p = null;
		List<Alarm> alarmSets =  alarmDao.getObjectsByCondition(queryString, p);
		if(alarmSets!=null&&alarmSets.size()>0)
		{
			return alarmSets.get(0);
		}
		return null;
	}
	public Alarm getAlarmByProjectid(Integer projectid) {
		String queryString = "from Alarm mo where mo.project.id=:projectid ";
		String[] paramNames = new String[] { "projectid" };
		Object[] values = new Object[] { projectid};
		return alarmDao.queryByNamedParam(queryString, paramNames, values);
	}
	
	
}
