package com.jlj.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.ILineDao;
import com.jlj.model.Line;
import com.jlj.model.User;
import com.jlj.service.ILineService;
@Component("lineservice")
public class LineServiceImp implements ILineService {
	private ILineDao lineDao;
	public ILineDao getLineDao() {
		return lineDao;
	}
	@Resource
	public void setLineDao(ILineDao lineDao) {
		this.lineDao = lineDao;
	}
	//添加对象
	
	public void add(Line line) throws Exception {
		lineDao.save(line);
	}
	//删除对象
	public void delete(Line line) {
		lineDao.delete(line);
	}
	//删除某个id的对象
	public void deleteById(int id) {
		lineDao.deleteById(id);
	}
	//修改对象
	public void update(Line line) {
		lineDao.update(line);
	}
	//获取所有对象
	public List<Line> getLines() {
		return lineDao.getLines();
	}
	//加载一个id的对象
	public Line loadById(int id) {
		return lineDao.loadById(id);
	}
	//后台管理-页数获取
	public int getPageCount(int con, String convalue,int size) {
		int totalCount=this.getTotalCount(con, convalue);
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	public int getTotalCount(int con, String convalue) {
		String queryString = "select count(*) from Line mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		return lineDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	public List<Line> queryList(int con, String convalue, int page, int size) {
		String queryString = "from Line mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.project.name asc,mo.name asc ";
		return lineDao.pageList(queryString,p,page,size);
	}

	//获取总页数
	public int getPageCount(int totalCount, int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//获取某项目的线路记录条数
	public int getProjectTotalCount(int con, String convalue, int projectid) {
		String queryString = "select count(*) from Line mo where mo.project.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		return lineDao.getUniqueResult(queryString,p);
	}


	public List<Line> getLinesByPid(int projectid) {
		String queryString="from Line mo where mo.project.id=:projectid ";
		String[] paramNames=new String[]{"projectid"};
		Object[] values=new Object[]{projectid};
		return lineDao.queryList(queryString,paramNames,values);
	}
	//获取某项目的线路记录
	public List<Line> queryProjectList(int con, String convalue, int projectid,
			int page, int size) {
		String queryString = "from Line mo where mo.project.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		queryString += " order by mo.project.name asc,mo.name asc ";
		return lineDao.pageList(queryString,p,page,size);
	}
	//查询某管理员所管理的线路的总记录数
	public int getManageTotalCount(int con, String convalue, String linetext) {
		String queryString = "select count(*) from Line mo where mo.id in("+linetext+") ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		return lineDao.getUniqueResult(queryString,p);
	}
	//查询某管理员所管理的线路的记录
	public List<Line> queryManageList(int con, String convalue,
			String linetext, int page, int size) {
		String queryString = "from Line mo where mo.id in("+linetext+") ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.project.name asc,mo.name asc ";
		return lineDao.pageList(queryString,p,page,size);
	}
	public List<Line> getLinesByLinetext(int projectid, String linetext) {
		// TODO Auto-generated method stub
		String queryString = "from Line mo where mo.project.id="+projectid;
		
		queryString += " and mo.id in("+linetext+")  order by mo.project.id asc,mo.orderid asc";  
		
		return lineDao.queryList(queryString);
	}
	
	/**
	 * line管理
	 * 根据当前用户的权限及项目来查询当前的line list
	 * lq
	 */
	public int getPageCount(int con, String convalue, int size, 
			User user) {
		int totalCount=this.getTotalCount(con, convalue,user);
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	public int getTotalCount(int con, String convalue,  User user) {
		String queryString = "select count(*) from Line mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.project.name asc,mo.name asc ";
			break;
		case 1://高级管理员
			queryString += "and mo.project.id="+user.getProject().getId();
			queryString += " order by mo.project.name asc,mo.name asc ";
			break;
		case 2://普通管理员
			queryString += "and mo.project.id="+user.getProject().getId();
			queryString += "and mo.id in("+user.getLinetext()+")";  
			queryString += " order by mo.project.name asc,mo.name asc ";
			break;
		default:
			break;
		}
		return lineDao.getUniqueResult(queryString,p);
	}
	
	public List<Line> queryList(int con, String convalue, int page, int size,
			User user) {
		String queryString = "from Line mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.project.name asc,mo.name asc ";
			break;
		case 1://高级管理员
			queryString += "and mo.project.id="+user.getProject().getId();
			queryString += " order by mo.project.name asc,mo.name asc ";
			break;
		case 2://普通管理员
			queryString += "and mo.project.id="+user.getProject().getId();
			queryString += "and mo.id in("+user.getLinetext()+")";  
			queryString += " order by mo.project.name asc,mo.name asc ";
			break;
		default:
			break;
		}
		return lineDao.pageList(queryString,p,page,size);
	}
	public List<Line> getNotestLinesByPid(int projectid) {
		String queryString = "from Line mo where mo.orderid<>1 and mo.project.id= "+projectid;
		Object[] p = null;
		return lineDao.getObjectsByCondition(queryString, p);
	}
	public Line getProjectTestLine(Integer id) {
		String queryString = "from Line mo where mo.orderid=1 and mo.project.id= "+id;
		Object[] p = null;
		 List<Line> linetests =  lineDao.getObjectsByCondition(queryString, p);
		 if(linetests!=null&&linetests.size()>0)
		 {
			 return linetests.get(0);
		 }
		return null;
	}
	//根据项目id和排序id查询某测试线路
	public Line getLineByProjectidAndOrderid(int projectid, int orderid) {
		String queryString = "from Line mo where mo.project.id=:projectid and mo.orderid=:orderid";
		String[] paramNames = new String[] { "projectid","orderid" };
		Object[] values = new Object[] { projectid,orderid };
		return lineDao.queryByNamedParam(queryString, paramNames, values);
	}
	public Line getLineByNameAndProject(String linename, int projectid) {
		String queryString = "from Line mo where mo.project.id=:projectid and mo.name=:linename";
		String[] paramNames = new String[] { "projectid","linename" };
		Object[] values = new Object[] { projectid,linename };
		return lineDao.queryByNamedParam(queryString, paramNames, values);
	}
	
}
