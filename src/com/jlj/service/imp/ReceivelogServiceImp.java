package com.jlj.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.IReceivelogDao;
import com.jlj.model.Receivelog;
import com.jlj.service.IReceivelogService;
@Component("receivelogservice")
public class ReceivelogServiceImp implements IReceivelogService  {
	private IReceivelogDao receivelogDao;
	public IReceivelogDao getReceivelogDao() {
		return receivelogDao;
	}
	@Resource
	public void setReceivelogDao(IReceivelogDao receivelogDao) {
		this.receivelogDao = receivelogDao;
	}
	//添加对象
	
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#add(com.jlj.model.Receivelog)
	 */
	public void add(Receivelog receivelog) throws Exception {
		receivelogDao.save(receivelog);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#delete(com.jlj.model.Receivelog)
	 */
	public void delete(Receivelog receivelog) {
		receivelogDao.delete(receivelog);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#deleteById(int)
	 */
	public void deleteById(int id) {
		receivelogDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#update(com.jlj.model.Receivelog)
	 */
	public void update(Receivelog receivelog) {
		receivelogDao.update(receivelog);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#getReceivelogs()
	 */
	public List<Receivelog> getReceivelogs() {
		return receivelogDao.getReceivelogs();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#loadById(int)
	 */
	public Receivelog loadById(int id) {
		return receivelogDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int con, String convalue,int size) {
		int totalCount=this.getTotalCount(con, convalue);
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue) {
		String queryString = "select count(*) from Receivelog mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//日志信息
			if(con==1){
				queryString += "and mo.loginfo like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		return receivelogDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#queryList(int, java.lang.String, int, int)
	 */
	public List<Receivelog> queryList(int con, String convalue, int page, int size) {
		String queryString = "from Receivelog mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//日志信息
			if(con==1){
				queryString += "and mo.loginfo like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.project.name asc,mo.id desc ";
		return receivelogDao.pageList(queryString,p,page,size);
	}

	//获取总页数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#getPageCount(int, int)
	 */
	public int getPageCount(int totalCount, int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//获取某项目的线路记录条数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#getProjectTotalCount(int, java.lang.String, int)
	 */
	public int getProjectTotalCount(int con, String convalue, int projectid) {
		String queryString = "select count(*) from Receivelog mo where mo.project.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//日志信息
			if(con==1){
				queryString += "and mo.loginfo like ? "; 
			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		return receivelogDao.getUniqueResult(queryString,p);
	}


	
	//获取某项目的线路记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#queryProjectList(int, java.lang.String, int, int, int)
	 */
	public List<Receivelog> queryProjectList(int con, String convalue, int projectid,
			int page, int size) {
		String queryString = "from Receivelog mo where mo.project.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//日志信息
			if(con==1){
				queryString += "and mo.loginfo like ? "; 
			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		queryString += " order by mo.project.name asc,mo.id desc ";
		return receivelogDao.pageList(queryString,p,page,size);
	}
	
	
	//获得该项目的所有记录一并显示
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IReceivelogService#getReceivelogsByPid(int)
	 */
	public List<Receivelog> getReceivelogsByPid(int projectid) {
		String queryString="from Receivelog mo where mo.project.id=:projectid ";
		String[] paramNames=new String[]{"projectid"};
		Object[] values=new Object[]{projectid};
		return receivelogDao.queryList(queryString,paramNames,values);
	}
}
