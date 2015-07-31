package com.jlj.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jlj.dao.ISysconfDao;
import com.jlj.model.Sysconf;
import com.jlj.model.SysconfInfo;
import com.jlj.service.ISysconfService;
import com.jlj.util.DateTimeKit;
@Path("/sysconfservice")
public class SysconfServiceImp implements ISysconfService {
	private ISysconfDao sysconfDao;
	public ISysconfDao getSysconfDao() {
		return sysconfDao;
	}
	@Resource
	public void setSysconfDao(ISysconfDao sysconfDao) {
		this.sysconfDao = sysconfDao;
	}
	//添加对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISysconfService#add(com.jlj.model.Sysconf)
	 */
	public void add(Sysconf sysconf) throws Exception {
		sysconfDao.save(sysconf);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISysconfService#delete(com.jlj.model.Sysconf)
	 */
	public void delete(Sysconf sysconf) {
		sysconfDao.delete(sysconf);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISysconfService#deleteById(int)
	 */
	public void deleteById(int id) {
		sysconfDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISysconfService#update(com.jlj.model.Sysconf)
	 */
	public void update(Sysconf sysconf) {
		sysconfDao.update(sysconf);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISysconfService#getSysconfs()
	 */
	public List<Sysconf> getSysconfs() {
		return sysconfDao.getSysconfs();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISysconfService#loadById(int)
	 */
	public Sysconf loadById(int id) {
		return sysconfDao.loadById(id);
	}
	//后台管理-页数获取
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISysconfService#getPageCount(int, java.lang.String, int)
	 */
	public int getPageCount(int con, String convalue,int size) {
		int totalCount=this.getTotalCount(con, convalue);
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISysconfService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue) {
		String queryString = "select count(*) from Sysconf mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.id desc ";
		return sysconfDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISysconfService#queryList(int, java.lang.String, int, int)
	 */
	public List<Sysconf> queryList(int con, String convalue, int page, int size) {
		String queryString = "from Sysconf mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.id desc ";
		return sysconfDao.pageList(queryString,p,page,size);
	}
	
	//webservice部分
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.ISysconfService#getSysconfsJson()
	 */
	@GET
	@Path("/sysconflist")
	@Produces({MediaType.APPLICATION_JSON})
	public List<SysconfInfo> getSysconfsJson() {
		List<Sysconf> sysconfs=sysconfDao.getSysconfs();
		List<SysconfInfo> sysconfinfos=new ArrayList<SysconfInfo>();
		for (Sysconf sysconf : sysconfs) {
			SysconfInfo sysconfinfo=new SysconfInfo(sysconf.getName(),DateTimeKit.getDateString(sysconf.getDeadline()),sysconf.getStatus());
			sysconfinfos.add(sysconfinfo);
		}
		return sysconfinfos;
	}
	/**
	 * 修改配置信息
	 */
	@GET
	@Path("/upconfdata/{date}/{status}/{sysid}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public int updateConfStatus(@PathParam("date") String date,@PathParam("status") int status,@PathParam("sysid") int sysid) {
		String queryString ="update Sysconf mo set mo.deadline='"+date+"',mo.status=? where mo.id=?";
		Object[] p =new Object[]{status,sysid};
		return sysconfDao.updateSysconfByhql(queryString, p);
	}
	/**
	 * 修改配置状态
	 */
	@GET
	@Path("/upconfstatus/{status}/{sysid}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void updateStatus(@PathParam("status") int status,@PathParam("sysid") int sysid) {
		String hql ="update Sysconf mo set mo.status=:status where mo.id=:sysid";
		String[] paramNames = new String[]{"status","sysid"};
		Object[] values =new Object[]{status,sysid};
		sysconfDao.updateByHql(hql, paramNames, values);
	}
}
