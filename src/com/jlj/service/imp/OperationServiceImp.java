package com.jlj.service.imp;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.IOperationDao;
import com.jlj.model.Operation;
import com.jlj.model.User;
import com.jlj.service.IOperationService;
@Component("operationservice")
public class OperationServiceImp implements IOperationService {
	private IOperationDao operationDao;
	public IOperationDao getOperationDao() {
		return operationDao;
	}
	@Resource
	public void setOperationDao(IOperationDao operationDao) {
		this.operationDao = operationDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IOperationService#add(com.jlj.model.Operation)
	 */
	public void add(Operation operation) throws Exception {
		operationDao.save(operation);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IOperationService#delete(com.jlj.model.Operation)
	 */
	public void delete(Operation operation) {
		operationDao.delete(operation);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IOperationService#deleteById(int)
	 */
	public void deleteById(int id) {
		operationDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IOperationService#update(com.jlj.model.Operation)
	 */
	public void update(Operation operation) {
		operationDao.update(operation);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IOperationService#getOperations()
	 */
	public List<Operation> getOperations() {
		return operationDao.getOperations();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IOperationService#loadById(int)
	 */
	public Operation loadById(int id) {
		return operationDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IOperationService#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int totalCount,int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IOperationService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue,User user, String startdate, String enddate) {
		
		
		String queryString = "select count(*) from Operation mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//命令名称
			if(con==1){
				queryString += "and mo.optype like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(startdate!=null&&!startdate.equals("")){
			queryString += " and mo.optime>='"+startdate+"'";
		}
		if(enddate!=null&&!enddate.equals("")){
			queryString += " and mo.optime<='"+enddate+"'";
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.project.id desc ";
			break;
		case 1://高级管理员
			queryString += "and mo.project.id="+user.getProject().getId()+ " order by mo.id desc ";
			break;
		case 2://普通管理员
			queryString += "and mo.project.id="+user.getProject().getId()+ " order by mo.id desc ";
			break;
		default:
			break;
		}
		
		return operationDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IOperationService#queryList(int, java.lang.String, int, int)
	 */
	public List<Operation> queryList(int con, String convalue, User user, int page, int size, String startdate, String enddate) {
		
		String queryString = "from Operation mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.optype like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		if(startdate!=null&&!startdate.equals("")){
			queryString += " and mo.optime>='"+startdate+"'";
		}
		if(enddate!=null&&!enddate.equals("")){
			queryString += " and mo.optime<='"+enddate+"'";
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.project.id,mo.id desc ";
			break;
		case 1://高级管理员
			queryString += "and mo.project.id="+user.getProject().getId()+ " order by mo.id desc ";
			break;
		case 2://普通管理员
			queryString += "and mo.project.id="+user.getProject().getId()+ " order by mo.id desc ";
			break;
		default:
			break;
		}
		return operationDao.pageList(queryString,p,page,size);
	}
	
	
}
