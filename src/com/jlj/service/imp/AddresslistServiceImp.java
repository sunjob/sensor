package com.jlj.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.IAddresslistDao;
import com.jlj.dao.IProjectDao;
import com.jlj.model.Addresslist;
import com.jlj.model.Project;
import com.jlj.model.User;
import com.jlj.service.IAddresslistService;
import com.jlj.service.IProjectService;
@Component("addresslistservice")
public class AddresslistServiceImp implements IAddresslistService{
	private IAddresslistDao addresslistDao;
	private IProjectService projectService;
	
	public IAddresslistDao getAddresslistDao() {
		return addresslistDao;
	}
	@Resource
	public void setAddresslistDao(IAddresslistDao addresslistDao) {
		this.addresslistDao = addresslistDao;
	}
	//添加对象
	public void add(Addresslist addresslist) throws Exception {
		addresslistDao.save(addresslist);
	}
	//删除对象
	public void delete(Addresslist addresslist) {
		addresslistDao.delete(addresslist);
	}
	//删除某个id的对象
	public void deleteById(int id) {
		addresslistDao.deleteById(id);
	}
	//修改对象
	public void update(Addresslist addresslist) {
		addresslistDao.update(addresslist);
	}
	//获取所有对象
	public List<Addresslist> getAddresslists() {
		return addresslistDao.getAddresslists();
	}
	//加载一个id的对象
	public Addresslist loadById(int id) {
		return addresslistDao.loadById(id);
	}
	//后台管理-页数获取
	public int getPageCount(int totalCount,int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	public int getTotalCount(int con, String convalue,User user) {
		
		String queryString = "select count(*) from Addresslist mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			if(con==1){
				queryString += "and mo.username like ? "; 
			}else if(con==2){
				queryString += "and mo.phone like ? "; 
			}else if(con==3){
				queryString += "and mo.project.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.project.id desc ";
			break;
		case 1://高级管理员
			queryString += "and mo.project.id="+user.getProject().getId();
			break;
		case 2://普通管理员
			queryString += "and mo.project.id="+user.getProject().getId();
			break;
		default:
			break;
		}
		return addresslistDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	public List<Addresslist> queryList(int con, String convalue,User user, int page, int size) {
		
		String queryString = "from Addresslist mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			if(con==1){
				queryString += "and mo.username like ? "; 
			}else if(con==2){
				queryString += "and mo.phone like ? "; 
			}else if(con==3){
				queryString += "and mo.project.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.project.id desc ";
			break;
		case 1://高级管理员
			queryString += "and mo.project.id="+user.getProject().getId();
			break;
		case 2://普通管理员
			queryString += "and mo.project.id="+user.getProject().getId();
			break;
		default:
			break;
		}
		return addresslistDao.pageList(queryString,p,page,size);
	}
	public List<Addresslist> queryByUserList(User user) {
		String queryString = "from Addresslist mo where 1=1 ";
		Object[] p = null;
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.project.id desc ";
			break;
		case 1://高级管理员
			queryString += "and mo.project.id="+user.getProject().getId();
			break;
		case 2://普通管理员
			queryString += "and mo.project.id="+user.getProject().getId();
			break;
		default:
			break;
		}
		return addresslistDao.getObjectsByCondition(queryString, p);
	}
	public IProjectService getProjectService() {
		return projectService;
	}
	@Resource
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
	
	
}
