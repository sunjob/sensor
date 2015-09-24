package com.jlj.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import com.jlj.dao.IGatewayDao;
import com.jlj.model.Gateway;
import com.jlj.model.User;
import com.jlj.service.IGatewayService;
@Component("gatewayservice")
public class GatewayServiceImp implements IGatewayService{
	private IGatewayDao gatewayDao;
	public IGatewayDao getGatewayDao() {
		return gatewayDao;
	}
	@Resource
	public void setGatewayDao(IGatewayDao gatewayDao) {
		this.gatewayDao = gatewayDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IGatewayService#add(com.jlj.model.Gateway)
	 */
	public void add(Gateway gateway) throws Exception {
		gatewayDao.save(gateway);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IGatewayService#delete(com.jlj.model.Gateway)
	 */
	public void delete(Gateway gateway) {
		gatewayDao.delete(gateway);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IGatewayService#deleteById(int)
	 */
	public void deleteById(int id) {
		gatewayDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IGatewayService#update(com.jlj.model.Gateway)
	 */
	public void update(Gateway gateway) {
		gatewayDao.update(gateway);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IGatewayService#getGateways()
	 */
	public List<Gateway> getGateways() {
		return gatewayDao.getGateways();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IGatewayService#loadById(int)
	 */
	public Gateway loadById(int id) {
		return gatewayDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IGatewayService#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int con, String convalue,int size) {
		int totalCount=this.getTotalCount(con, convalue);
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IGatewayService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue) {
		String queryString = "select count(*) from Gateway mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		return gatewayDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IGatewayService#queryList(int, java.lang.String, int, int)
	 */
	public List<Gateway> queryList(int con, String convalue, int page, int size) {
		String queryString = "from Gateway mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.line.name like ? "; 
			}
			if(con==2){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.line.project.name asc,mo.line.name asc,mo.name asc ";
		return gatewayDao.pageList(queryString,p,page,size);
	}
	public int getPageCount(int totalCount, int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	
	
	public int getTotalCount(int con, String convalue, User user) {
		String queryString = "select count(*) from Gateway mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.line.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==2){
				queryString += "and mo.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==3){
				if(NumberUtils.isNumber(convalue))
				{
					queryString += "and mo.gateaddress="+Integer.parseInt(convalue); 
				}
				p = new Object[]{};
			}
			if(con==4){
				queryString += "and mo.macaddress like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==5){
				if(NumberUtils.isNumber(convalue))
				{
					queryString += "and mo.channel="+Integer.parseInt(convalue); 
				}
				p = new Object[]{};
			}
			if(con==6){
				queryString += "and mo.phonenumber like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.line.project.name asc,mo.line.name asc,mo.name asc ";
			break;
		case 1://超级管理员
			queryString += "and mo.line.project.id="+user.getProject().getId();
			queryString += " order by mo.line.project.name asc,mo.line.name asc,mo.name asc ";
			break;
		case 2://普通管理员
			queryString += "and mo.line.id in("+user.getLinetext()+")";  
			queryString += " order by mo.line.project.name asc,mo.line.name asc,mo.name asc ";
			break;
		default:
			break;
		}
		return gatewayDao.getUniqueResult(queryString,p);
	}
	
	
	
	public List<Gateway> queryList(int con, String convalue, User user,
			int page, int size) {
		String queryString = "from Gateway mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.line.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==2){
				queryString += "and mo.name like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==3){
				if(NumberUtils.isNumber(convalue))
				{
					queryString += "and mo.gateaddress="+Integer.parseInt(convalue); 
				}
				p = new Object[]{};
			}
			if(con==4){
				queryString += "and mo.macaddress like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
			if(con==5){
				if(NumberUtils.isNumber(convalue))
				{
					queryString += "and mo.channel="+Integer.parseInt(convalue); 
				}
				p = new Object[]{};
			}
			if(con==6){
				queryString += "and mo.phonenumber like ? "; 
				p = new Object[]{'%'+convalue+'%'};
			}
		}
		switch (user.getLimits()) {
		case 0://系统管理员
			queryString += " order by mo.line.project.name asc,mo.line.name asc,mo.name asc ";
			break;
		case 1://超级管理员
			queryString += "and mo.line.project.id="+user.getProject().getId();
			queryString += " order by mo.line.project.name asc,mo.line.name asc,mo.name asc ";
			break;
		case 2://普通管理员
			queryString += "and  mo.line.id in("+user.getLinetext()+")";  
			queryString += " order by mo.line.project.name asc,mo.line.name asc,mo.name asc ";
			break;
		default:
			break;
		}
		return gatewayDao.pageList(queryString,p,page,size);
	}
	public Gateway getGatewayById(int id) {
		// TODO Auto-generated method stub
		return gatewayDao.getGatewayById(id);
	}
	public Gateway getGatewayByChannel(int channel) {
		// TODO Auto-generated method stub
		String queryString="from Gateway mo where mo.channel=:channel";
		String[] paramNames=new String[]{"channel"};
		Object[] values=new Object[]{channel};
		return gatewayDao.queryByNamedParam(queryString, paramNames, values);
	}
	//根据硬件地址UID查询网关对象
	public Gateway getGatewayByMacaddress(String macaddress) {
		String queryString = "from Gateway mo where mo.macaddress=:macaddress";
		String[] paramNames = new String[] { "macaddress" };
		Object[] values = new Object[] { macaddress };
		return gatewayDao.queryByNamedParam(queryString, paramNames, values);
	}
	//根据网关地址查询网关对象
	public Gateway getGatewayByGateaddress(int gateaddress) {
		String queryString = "from Gateway mo where mo.gateaddress=:gateaddress";
		String[] paramNames = new String[] { "gateaddress" };
		Object[] values = new Object[] { gateaddress };
		return gatewayDao.queryByNamedParam(queryString, paramNames, values);
	}
	//根据线路id获取该线路的所有网关对象
	public List<Gateway> getGatewaysByLineid(int lineid) {
		String queryString = "from Gateway mo where mo.line.id=:lineid";
		String[] paramNames = new String[] { "lineid" };
		Object[] values = new Object[] { lineid };
		return gatewayDao.queryList(queryString, paramNames, values);
	}
	//更新网关对象最新的ip地址
	public void updateNewGatewayIpById(String sessionIP, Integer gatewayid) {
		String queryString = "update Gateway mo set mo.ip=:sessionIP where mo.id=:gatewayid";
		String[] paramNames = new String[] { "sessionIP","gatewayid" };
		Object[] values = new Object[] { sessionIP,gatewayid };
		gatewayDao.updateByHql(queryString, paramNames, values);
	}
	//根据项目id查询所有网关信息
	public List<Gateway> getGatewaysByProjectId(Integer projectid) {
		String queryString = "from Gateway mo where mo.line.project.id=:projectid order by mo.line.project.name asc,mo.line.name asc,mo.name asc";
		String[] paramNames = new String[] { "projectid" };
		Object[] values = new Object[] { projectid };
		return gatewayDao.queryList(queryString, paramNames, values);
	}
	public List<Gateway> getGatewaysByLinetext(int projectid, String linetext) {
		String queryString = "from Gateway mo where mo.line.project.id=:projectid and mo.line.id in("+linetext+") order by mo.line.project.name asc,mo.line.name asc,mo.name asc";
		String[] paramNames = new String[] { "projectid" };
		Object[] values = new Object[] { projectid };
		return gatewayDao.queryList(queryString, paramNames, values);
	}
	//根据ip查询某网关
	public Gateway getGatewayByIp(String sessionIP1) {
		String queryString = "from Gateway mo where mo.ip=:ip";
		String[] paramNames = new String[] { "ip" };
		Object[] values = new Object[] { sessionIP1 };
		return gatewayDao.queryByNamedParam(queryString, paramNames, values);
	}
	//修改该网关的无线通信数据
	public void updateChannelById(int channel, int gatewayid) {
		String queryString = "update Gateway mo set mo.channel=:channel where mo.id=:gatewayid";
		String[] paramNames = new String[] { "channel","gatewayid" };
		Object[] values = new Object[] { channel,gatewayid };
		gatewayDao.updateByHql(queryString, paramNames, values);
	}
	//根据网关地址和线路id查看对象
	public Gateway getGatewayByGateaddressAndLineid(int gateaddress, int lineid) {
		String queryString = "from Gateway mo where mo.gateaddress=:gateaddress and mo.line.id=:lineid";
		String[] paramNames = new String[] { "gateaddress","lineid" };
		Object[] values = new Object[] { gateaddress,lineid };
		return gatewayDao.queryByNamedParam(queryString, paramNames, values);
	}
	
	
}
