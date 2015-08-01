package com.jlj.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.IValveDao;
import com.jlj.model.User;
import com.jlj.model.Valve;
import com.jlj.service.IValveService;
@Component("valveservice")
public class ValveServiceImp implements IValveService {
	private IValveDao valveDao;
	public IValveDao getValveDao() {
		return valveDao;
	}
	@Resource
	public void setValveDao(IValveDao valveDao) {
		this.valveDao = valveDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValveService#add(com.jlj.model.Valve)
	 */
	public void add(Valve valve) throws Exception {
		valveDao.save(valve);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValveService#delete(com.jlj.model.Valve)
	 */
	public void delete(Valve valve) {
		valveDao.delete(valve);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValveService#deleteById(int)
	 */
	public void deleteById(int id) {
		valveDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValveService#update(com.jlj.model.Valve)
	 */
	public void update(Valve valve) {
		valveDao.update(valve);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValveService#getValves()
	 */
	public List<Valve> getValves() {
		return valveDao.getValves();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValveService#loadById(int)
	 */
	public Valve loadById(int id) {
		return valveDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValveService#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int totalCount,int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValveService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue,User user) {

		String queryString = "select count(*) from Valve mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-阀门编号；2-SIM卡号
			if(con==1){
				queryString += "and mo.number like ? "; 
			}else if(con==2){
				queryString += "and mo.simcardnumber like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
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
		return valveDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValveService#queryList(int, java.lang.String, int, int)
	 */
	public List<Valve> queryList(int con, String convalue,User user, int page, int size) {
		String queryString = "from Valve mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//1-阀门编号；2-SIM卡号
			if(con==1){
				queryString += "and mo.number like ? "; 
			}else if(con==2){
				queryString += "and mo.simcardnumber like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
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
		return valveDao.pageList(queryString,p,page,size);
	}
	//系统管理员-查询所有总记录数--------------------
	public int getTotalCount(int con, String convalue) {
		String queryString = "select count(*) from Valve mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//所属线路、网关名称、外设编号、外设地址、开关位置号
			if(con==1){
				queryString += " and mo.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.name like ? "; 
			}
//			else if(con==4){
//				queryString += " and mo.valveaddress like ? "; 
//			}else if(con==5){
//				queryString += " and mo.locatenumber like ? "; 
//			}
			
			p = new Object[]{'%'+convalue+'%'};
		}
		return valveDao.getUniqueResult(queryString,p);
	}
	//系统管理员-查询所有记录
	public List<Valve> queryList(int con, String convalue, int page, int size) {
		String queryString = "from Valve mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//所属线路、网关名称、外设编号、外设地址、开关位置号
			if(con==1){
				queryString += " and mo.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.name like ? "; 
			}
//			else if(con==4){
//				queryString += " and mo.valveaddress like ? "; 
//			}else if(con==5){
//				queryString += " and mo.locatenumber like ? "; 
//			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name asc,mo.name asc ";
		return valveDao.pageList(queryString,p,page,size);
	}
	//超级管理员-查询当前项目的总记录数--------------------
	public int getProjectTotalCount(int con, String convalue, int projectid) {
		String queryString = "select count(*) from Valve mo where mo.gateway.line.project.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//所属线路、网关名称、外设编号、外设地址、开关位置号
			if(con==1){
				queryString += " and mo.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.name like ? "; 
			}
//			else if(con==4){
//				queryString += " and mo.valveaddress like ? "; 
//			}else if(con==5){
//				queryString += " and mo.locatenumber like ? "; 
//			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		return valveDao.getUniqueResult(queryString,p);
	}
	//超级管理员-查询当前项目的记录
	public List<Valve> queryProjectList(int con, String convalue,
			int projectid, int page, int size) {
		String queryString = "from Valve mo where mo.gateway.line.project.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//所属线路、网关名称、外设编号、外设地址、开关位置号
			if(con==1){
				queryString += " and mo.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.name like ? "; 
			}
//			else if(con==4){
//				queryString += " and mo.valveaddress like ? "; 
//			}else if(con==5){
//				queryString += " and mo.locatenumber like ? "; 
//			}
			p = new Object[]{projectid,'%'+convalue+'%'};
		}else{
			p = new Object[]{projectid};
		}
		queryString += " order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name asc,mo.name asc ";
		return valveDao.pageList(queryString,p,page,size);
	}
	//普通管理员-查询该管理员所管辖的记录数--------------------
	public int getManageTotalCount(int con, String convalue, String linetext) {
		String queryString = "select count(*) from Valve mo where mo.gateway.line.id in("+linetext+") ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//所属线路、网关名称、外设编号、外设地址、开关位置号
			if(con==1){
				queryString += " and mo.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.name like ? "; 
			}
//			else if(con==4){
//				queryString += " and mo.valveaddress like ? "; 
//			}else if(con==5){
//				queryString += " and mo.locatenumber like ? "; 
//			}
			p = new Object[]{'%'+convalue+'%'};
		}
		return valveDao.getUniqueResult(queryString,p);
	}
	//普通管理员-查询该管理员所管辖的记录
	public List<Valve> queryManageList(int con, String convalue,
			String linetext, int page, int size) {
		String queryString = "from Valve mo where mo.gateway.line.id in("+linetext+") ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//所属线路、网关名称、外设编号、外设地址、开关位置号
			if(con==1){
				queryString += " and mo.gateway.line.name like ? "; 
			}else if(con==2){
				queryString += " and mo.gateway.name like ? "; 
			}else if(con==3){
				queryString += " and mo.name like ? "; 
			}
//			else if(con==4){
//				queryString += " and mo.valveaddress like ? "; 
//			}else if(con==5){
//				queryString += " and mo.locatenumber like ? "; 
//			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.gateway.line.project.name asc,mo.gateway.line.name asc,mo.gateway.name asc,mo.name asc ";
		return valveDao.pageList(queryString,p,page,size);
	}
	//根据网关id和外设地址查询出唯一的外设对象
	public Valve getValveByGatewayIdAndValveaddress(int gatewayid, int valveaddress) {
		String queryString = "from Valve mo where mo.gateway.id=:gatewayid and mo.valveaddress=:valveaddress";
		String[] paramNames = new String[] { "gatewayid","valveaddress" };
		Object[] values = new Object[] { gatewayid,valveaddress };
		return valveDao.queryByNamedParam(queryString, paramNames, values);
	}
	//根据网关id、开关位置号以及外设地址来查询出该外设对象，并修改其控制值以及控制状态
	public void updateStatusByConditionAndGatewayid(int controlvalue,
			int status, int valveaddress, int locatenumber, int gatewayid) {
		String queryString = "update Valve mo set mo.controlvalue=:controlvalue,mo.status=:status where mo.valveaddress=:valveaddress and mo.locatenumber=:locatenumber and mo.gateway.id=:gatewayid ";
		String[] paramNames = new String[] {"controlvalue", "status", "valveaddress", "locatenumber", "gatewayid" };
		Object[] values = new Object[] {controlvalue, status, valveaddress, locatenumber, gatewayid};
		valveDao.updateByHql(queryString, paramNames, values);
	}
	
	
	
	
}
