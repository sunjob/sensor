package com.jlj.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.ICommandDao;
import com.jlj.model.Command;
import com.jlj.model.User;
import com.jlj.service.ICommandService;
@Component("commandservice")
public class CommandServiceImp implements ICommandService {
	private ICommandDao commandDao;
	public ICommandDao getCommandDao() {
		return commandDao;
	}
	@Resource
	public void setCommandDao(ICommandDao commandDao) {
		this.commandDao = commandDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ICommandService#add(com.jlj.model.Command)
	 */
	public void add(Command command) throws Exception {
		commandDao.save(command);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ICommandService#delete(com.jlj.model.Command)
	 */
	public void delete(Command command) {
		commandDao.delete(command);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ICommandService#deleteById(int)
	 */
	public void deleteById(int id) {
		commandDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ICommandService#update(com.jlj.model.Command)
	 */
	public void update(Command command) {
		commandDao.update(command);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ICommandService#getCommands()
	 */
	public List<Command> getCommands() {
		return commandDao.getCommands();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ICommandService#loadById(int)
	 */
	public Command loadById(int id) {
		return commandDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ICommandService#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int totalCount,int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ICommandService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue,User user) {
		
		String queryString = "select count(*) from Command mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//命令名称
			if(con==1){
				queryString += "and mo.name like ? "; 
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
		return commandDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ICommandService#queryList(int, java.lang.String, int, int)
	 */
	public List<Command> queryList(int con, String convalue,User user, int page, int size) {
		
		String queryString = "from Command mo where 1=1 ";
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
		return commandDao.pageList(queryString,p,page,size);
	}
	
}
