package com.jlj.service;

import java.util.List;

import com.jlj.model.Project;

public interface IProjectService {

	//添加对象
	public abstract void add(Project project) throws Exception;

	//删除对象
	public abstract void delete(Project project);

	//删除某个id的对象
	public abstract void deleteById(int id);

	//修改对象
	public abstract void update(Project project);

	//获取所有对象
	public abstract List<Project> getProjects();

	//加载一个id的对象
	public abstract Project loadById(int id);

	//后台管理-页数获取
	public abstract int getPageCount(int totalCount, int size);

	//后台管理-获取总记录数
	public abstract int getTotalCount(int con, String convalue);

	//后台管理-获取符合条件的记录
	public abstract List<Project> queryList(int con, String convalue, int page,
			int size);

	public abstract Project getById(int projectid);

	public abstract List<Project> getNotestProjects();

	public abstract Project getProjectByName(String convalue);

}