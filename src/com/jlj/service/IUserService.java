package com.jlj.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jlj.model.User;
import com.jlj.vo.Usermo;

public interface IUserService {

	//添加对象
	public abstract void add(User user) throws Exception;

	//删除对象
	public abstract void delete(User user);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(User user);

	//获取所有对象
	public abstract List<User> getUsers();

	//加载一个id的对象
	public abstract User loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int totalCount, int size);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue, User user);

	//后台管理-获取符合条件的记录
	public abstract List<User> queryList(int con, String convalue,User user, int page,
			int size);

	//用户登录
	public abstract  User userlogin(String username, String password) ;
	
	/**
	 * 用户登录
	 */
	@GET
	@Path("/userlogin/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Usermo> userLogin(@PathParam("username") String username,@PathParam("password") String password);
	
	/**
	 * 用户修改密码
	 */
	@GET
	@Path("/updatepwd/{username}/{oldpwd}/{newpwd}")
	@Produces(MediaType.APPLICATION_JSON)
	public int updatePwd(@PathParam("username") String username,@PathParam("oldpwd") String oldpwd,@PathParam("newpwd") String newpwd);

	public abstract User getUserById(Integer upuserid);

	public abstract User getUserByUsername(String username);

}