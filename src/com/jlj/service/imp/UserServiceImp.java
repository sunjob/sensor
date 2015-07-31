package com.jlj.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jlj.dao.IUserDao;
import com.jlj.model.User;
import com.jlj.service.IUserService;
import com.jlj.vo.Usermo;
@Path("/userservice")
public class UserServiceImp implements IUserService {
	private IUserDao userDao;
	public IUserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IUserServiceImp#add(com.jlj.model.User)
	 */
	public void add(User user) throws Exception {
		userDao.save(user);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IUserServiceImp#delete(com.jlj.model.User)
	 */
	public void delete(User user) {
		userDao.delete(user);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IUserServiceImp#deleteById(int)
	 */
	public void deleteById(int id) {
		userDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IUserServiceImp#update(com.jlj.model.User)
	 */
	public void update(User user) {
		userDao.update(user);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IUserServiceImp#getUsers()
	 */
	public List<User> getUsers() {
		return userDao.getUsers();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IUserServiceImp#loadById(int)
	 */
	public User loadById(int id) {
		return userDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IUserServiceImp#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int totalCount,int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IUserServiceImp#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue, User user) {
		String queryString = "select count(*) from User mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			if(con==1){
				queryString += "and mo.username like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==2){
				int limit = 0;
				if("系统管理员".contains(convalue))
				{
				}
				if("超级管理员".contains(convalue))
				{
					limit = 1;
				}
				if("普通管理员".contains(convalue))
				{
					limit = 2;
				}
				if("游客".contains(convalue))
				{
					limit = 3;
				}
				queryString += "and mo.limits ="+limit; 
				
				p = new Object[]{};
				
			}
			if(con==3){
				User upuser = this.getUserByUsername(convalue);
				if(upuser!=null)
				{
					queryString += "and mo.upuserid ="+upuser.getId(); 
				}
				p = new Object[]{};
			}
			
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.project.id,mo.id desc ";
			break;
		case 1://高级管理员
			queryString += "and mo.limits<>0 and mo.project.id ="+user.getProject().getId();
			queryString += " order by mo.id desc ";
			break;
		case 2://普通管理员
			queryString += "and mo.limits<>0 and and mo.limits<>1 and mo.upuserid ="+user.getId();  
			queryString += " order by mo.id desc ";
			break;
		default:
			break;
		}
		
		return userDao.getUniqueResult(queryString,p);
	}
	public User getUserByUsername(String username) {
		String queryString="from User mo where mo.username=:username";
		String[] paramNames=new String[]{"username"};
		Object[] values=new Object[]{username};
		return userDao.queryByNamedParam(queryString,paramNames,values);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IUserServiceImp#queryList(int, java.lang.String, int, int)
	 */
	public List<User> queryList(int con, String convalue, User user, int page, int size) {
		String queryString = "from User mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			if(con==1){
				queryString += "and mo.username like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==2){
				int limit = 0;
				if("系统管理员".contains(convalue))
				{
					queryString += "and mo.limits ="+limit; 
				}
				if("超级管理员".contains(convalue))
				{
					limit = 1;
					queryString += "and mo.limits ="+limit; 
				}
				if("普通管理员".contains(convalue))
				{
					limit = 2;
					queryString += "and mo.limits ="+limit; 
				}
				if("游客".contains(convalue))
				{
					limit = 3;
					queryString += "and mo.limits ="+limit; 
				}
				p = new Object[]{};
			}
			if(con==3){
				User upuser = this.getUserByUsername(convalue);
				if(upuser!=null)
				{
					queryString += "and mo.upuserid ="+upuser.getId(); 
				}
				p = new Object[]{};
			}
			
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.project.id,mo.id desc ";
			break;
		case 1://高级管理员
			queryString += "and mo.limits<>0 and mo.project.id ="+user.getProject().getId();
			queryString += " order by mo.id desc ";
			break;
		case 2://普通管理员
			queryString += "and mo.limits<>0 and and mo.limits<>1 and mo.upuserid ="+user.getId();  
			queryString += " order by mo.id desc ";
			break;
		default:
			break;
		}
		return userDao.pageList(queryString,p,page,size);
	}
	//用户登录
	public User userlogin(String username, String password) {
		String queryString="from User mo where mo.username=:username and mo.password=:password";
		String[] paramNames=new String[]{"username","password"};
		Object[] values=new Object[]{username,password};
		return userDao.queryByNamedParam(queryString,paramNames,values);
	} 
	
	//webservice部分
	/**
	 * 用户登录
	 */
	@GET
	@Path("/userlogin/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usermo> userLogin(@PathParam("username") String username,@PathParam("password") String password){
		List<Usermo> usermos = new ArrayList<Usermo>();
		
		String queryString="from User mo where mo.username=:username and mo.password=:password";
		String[] paramNames=new String[]{"username","password"};
		Object[] values=new Object[]{username,password};
		User loginer=userDao.queryByNamedParam(queryString, paramNames, values);
		if(loginer!=null){
			Usermo usermo = new Usermo();
			usermo.setUsername(loginer.getUsername());
			usermo.setLimits(loginer.getLimits());
			usermo.setLinetext(loginer.getLinetext()+" ");
			usermo.setProjectid(loginer.getProject().getId());
			usermo.setProjectname(loginer.getProject().getName());
			usermo.setUpuserid(loginer.getUpuserid());
			usermos.add(usermo);
//			islogin=true;
		}
		return usermos;
		
	}
	/**
	 * 用户修改密码
	 */
	@GET
	@Path("/updatepwd/{username}/{oldpwd}/{newpwd}")
	@Produces(MediaType.APPLICATION_JSON)
	public int updatePwd(@PathParam("username") String username,@PathParam("oldpwd") String oldpwd,@PathParam("newpwd") String newpwd){
		int logined=0;
		String queryString="update User mo set mo.password=? where mo.username=? and mo.password=?";
		Object[] p=new Object[]{newpwd,username,oldpwd};
		logined=userDao.updateUserByhql(queryString,p);
		return logined;
		
	}
	public User getUserById(Integer upuserid) {
		// TODO Auto-generated method stub
		return userDao.getUserById(upuserid);
	}
}
