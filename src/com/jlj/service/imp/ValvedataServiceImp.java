package com.jlj.service.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jlj.dao.IValvedataDao;
import com.jlj.model.Valvedata;
import com.jlj.service.IValvedataService;
@Component("valvedataservice")
public class ValvedataServiceImp implements IValvedataService {
	private IValvedataDao valvedataDao;
	public IValvedataDao getValvedataDao() {
		return valvedataDao;
	}
	@Resource
	public void setValvedataDao(IValvedataDao valvedataDao) {
		this.valvedataDao = valvedataDao;
	}
	//添加对象
	
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValvedataService#add(com.jlj.model.Valvedata)
	 */
	public void add(Valvedata valvedata) throws Exception {
		valvedataDao.save(valvedata);
	}
	//删除对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValvedataService#delete(com.jlj.model.Valvedata)
	 */
	public void delete(Valvedata valvedata) {
		valvedataDao.delete(valvedata);
	}
	//删除某个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValvedataService#deleteById(int)
	 */
	public void deleteById(int id) {
		valvedataDao.deleteById(id);
	}
	//修改对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValvedataService#update(com.jlj.model.Valvedata)
	 */
	public void update(Valvedata valvedata) {
		valvedataDao.update(valvedata);
	}
	//获取所有对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValvedataService#getValvedatas()
	 */
	public List<Valvedata> getValvedatas() {
		return valvedataDao.getValvedatas();
	}
	//加载一个id的对象
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValvedataService#loadById(int)
	 */
	public Valvedata loadById(int id) {
		return valvedataDao.loadById(id);
	}
	//后台管理-获取总记录数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValvedataService#getTotalCount(int, java.lang.String)
	 */
	public int getTotalCount(int con, String convalue) {
		String queryString = "select count(*) from Valvedata mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		return valvedataDao.getUniqueResult(queryString,p);
	}
	//后台管理-获取符合条件的记录
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValvedataService#queryList(int, java.lang.String, int, int)
	 */
	public List<Valvedata> queryList(int con, String convalue, int page, int size) {
		String queryString = "from Valvedata mo where 1=1 ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//线路名称
			if(con==1){
				queryString += "and mo.name like ? "; 
			}
			p = new Object[]{'%'+convalue+'%'};
		}
		queryString += " order by mo.project.name asc,mo.name asc ";
		return valvedataDao.pageList(queryString,p,page,size);
	}

	//获取总页数
	/* (non-Javadoc)
	 * @see com.jlj.service.imp.IValvedataService#getPageCount(int, int)
	 */
	public int getPageCount(int totalCount, int size) {
		return totalCount%size==0?totalCount/size:(totalCount/size+1);
	}
	//根据外设id查询该外设的历史数据的总记录数
	public int getValveTotalCount(int con, String convalue, int valveid) {
		String queryString = "select count(*) from Valvedata mo where mo.valve.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//外设编号
			if(con==1){
				queryString += " and mo.valve.name like ? "; 
			}
			p = new Object[]{valveid,'%'+convalue+'%'};
		}else{
			p = new Object[]{valveid};
		}
		return valvedataDao.getUniqueResult(queryString,p);
	}
	//根据外设id查询该外设的历史数据
	public List<Valvedata> queryValveList(int con, String convalue,
			int valveid, int page, int size) {
		String queryString = "from Valvedata mo where mo.valve.id=? ";
		Object[] p = null;
		if(con!=0&&convalue!=null&&!convalue.equals("")){
			//外设编号
			if(con==1){
				queryString += " and mo.valve.name like ? "; 
			}
			p = new Object[]{valveid,'%'+convalue+'%'};
		}else{
			p = new Object[]{valveid};
		}
		queryString += " order by mo.id desc ";
		return valvedataDao.pageList(queryString,p,page,size);
	}
	
}
