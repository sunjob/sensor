package com.jlj.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.IProjectDao;
import com.jlj.model.Project;
import com.jlj.service.IProjectService;
@Component("projectservice")
public class ProjectServiceImp implements IProjectService{
	private IProjectDao projectDao;
	public IProjectDao getProjectDao() {
		return projectDao;
	}
	@Resource
	public void setProjectDao(IProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IProjectService#add(com.jlj.model.Project)
	 */
	public void add(Project project) throws Exception {
		projectDao.save(project);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IProjectService#delete(com.jlj.model.Project)
	 */
	public void delete(Project project) {
		projectDao.delete(project);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IProjectService#deleteById(int)
	 */
	public void deleteById(int id) {
		projectDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IProjectService#update(com.jlj.model.Project)
	 */
	public void update(Project project) {
		projectDao.update(project);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IProjectService#getProjects()
	 */
	public List<Project> getProjects() {
		return projectDao.getProjects();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IProjectService#loadById(int)
	 */
	public Project loadById(int id) {
		return projectDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IProjectService#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int totalCount,int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IProjectService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue) {
		String queryString = "select count(*) from Project mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//项目名称、施工单位、管道厂商、项目业主
			if(con==1){
				queryString += "and mo.name like ? "; 
			}else if(con==2){
				queryString += "and mo.sgdw like ? "; 
			}else if(con==3){
				queryString += "and mo.gdcs like ? "; 
			}else if(con==4){
				queryString += "and mo.owner like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		
		return projectDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IProjectService#queryList(int, java.lang.String, int, int)
	 */
	public List<Project> queryList(int con, String convalue, int page, int size) {
		String queryString = "from Project mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//项目名称、施工单位、管道厂商、项目业主
			if(con==1){
				queryString += "and mo.name like ? "; 
			}else if(con==2){
				queryString += "and mo.sgdw like ? "; 
			}else if(con==3){
				queryString += "and mo.gdcs like ? "; 
			}else if(con==4){
				queryString += "and mo.owner like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.name asc ";
		return projectDao.pageList(queryString,p,page,size);
	}
	public Project getById(int projectid) {
		// TODO Auto-generated method stub
		return projectDao.getById(projectid);
	}
	public List<Project> getNotestProjects() {
		// TODO Auto-generated method stub
		String queryString = "from Project mo where mo.orderid<>1 ";
		Object[] p = null;
		return projectDao.getObjectsByCondition(queryString, p);
	}
	public Project getProjectByName(String name) {
		String queryString="from Project mo where mo.name=:name";
		String[] paramNames=new String[]{"name"};
		Object[] values=new Object[]{name};
		return projectDao.queryByNamedParam(queryString,paramNames,values);
	}
	
	
}
