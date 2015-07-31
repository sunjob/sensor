package com.jlj.dao.imp;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.jlj.dao.IAlarmrecordDao;
import com.jlj.model.Alarmrecord;
@Component("alarmrecordDao")
public class AlarmrecordDaoImp implements IAlarmrecordDao  {
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	//保存一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAddresslistDao#save(com.jlj.model.Addresslist)
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#save(com.jlj.model.Alarmrecord)
	 */
	public void save(Alarmrecord alarmrecord) {
		this.hibernateTemplate.save(alarmrecord);
	}
	
	//保存一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#savereturn(com.jlj.model.Alarmrecord)
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#savereturn(com.jlj.model.Alarmrecord)
	 */
	public Integer savereturn(Alarmrecord alarmrecord) {
		return (Integer) this.hibernateTemplate.save(alarmrecord);
	}
	
	//删除一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#delete(com.jlj.model.Alarmrecord)
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#delete(com.jlj.model.Alarmrecord)
	 */
	public void delete(Alarmrecord alarmrecord) {
		this.hibernateTemplate.delete(alarmrecord);
	}
	//根据ID删除一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#deleteById(int)
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#deleteById(int)
	 */
	public void deleteById(int id) {
		this.hibernateTemplate.delete(this.loadById(id));
	}
	
	//修改一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#update(com.jlj.model.Alarmrecord)
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#update(com.jlj.model.Alarmrecord)
	 */
	public void update(Alarmrecord alarmrecord) {
		this.hibernateTemplate.update(alarmrecord);
	}
	
	//根据hql语句、条件、条件值修改某些记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#updateByHql(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#updateByHql(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public void updateByHql(final String hql,final String[] paramNames,final Object[] values) {
		this.hibernateTemplate.execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
				for (int i = 0; i < paramNames.length; i++) {
					query.setParameter(paramNames[i], values[i]);
				}
				query.executeUpdate();
				return null;
			}
			
		});
	}
	
	//获得所有记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getAlarmrecords()
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getAlarmrecords()
	 */
	public List<Alarmrecord> getAlarmrecords() {
		return this.hibernateTemplate.loadAll(Alarmrecord.class);
	}
	
	//根据hql语句来查询所有记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#queryList(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#queryList(java.lang.String)
	 */
	public List<Alarmrecord> queryList(String queryString) {
		return this.hibernateTemplate.find(queryString);
	}
	
	//根据hql、条件值查询某些记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getObjectsByCondition(java.lang.String, java.lang.Object[])
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getObjectsByCondition(java.lang.String, java.lang.Object[])
	 */
	public List<Alarmrecord> getObjectsByCondition(String queryString, Object[] p) {
		return this.hibernateTemplate.find(queryString,p);
	}
	
	//根据hql语句、条件、条件值查询某些记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#queryList(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#queryList(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public List<Alarmrecord> queryList(String queryString, String[] paramNames,
			Object[] values)
	{
		List list =  this.hibernateTemplate.findByNamedParam(queryString, paramNames, values);
		return list;
	}
	
	
	//根据hql、id列表查询某些记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getObjectsByIdList(java.lang.String, java.util.List)
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getObjectsByIdList(java.lang.String, java.util.List)
	 */
	public List<Alarmrecord> getObjectsByIdList(final String hql,final List<Integer> idList) {
		return this.hibernateTemplate.executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
				query.setParameterList("idList", idList);
				return query.list();
			}
			
		});
	}
	
	//根据hql语句、条件值、分页查询某些记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#pageList(java.lang.String, java.lang.Object[], java.lang.Integer, java.lang.Integer)
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#pageList(java.lang.String, java.lang.Object[], java.lang.Integer, java.lang.Integer)
	 */
	public List<Alarmrecord> pageList(final String queryString,final Object[] p,final Integer page,
			final Integer size) {
		return this.hibernateTemplate.executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query=session.createQuery(queryString);
				if(p!=null&&p.length>0){
					for (int i = 0; i < p.length; i++) {
						query.setParameter(i, p[i]);
					}
				}
				if(page!=null&&page>0&&size!=null&&size>0){
					query.setFirstResult((page-1)*size).setMaxResults(size);
				}
				return query.list();
			}
			
		});
	}
	
	
	
	//根据hql、条件值获得一个唯一值
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getUniqueResult(java.lang.String, java.lang.Object[])
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getUniqueResult(java.lang.String, java.lang.Object[])
	 */
	public int getUniqueResult(final String queryString,final Object[] p) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query= session.createQuery(queryString);
		if(p!=null&&p.length>0){
			for (int i = 0; i < p.length; i++) {
				query.setParameter(i, p[i]);
			}
		}
		Object obj=query.uniqueResult();
		return ((Long)obj).intValue();
			
	}
	
	//根据id查询一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#loadById(int)
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#loadById(int)
	 */
	public Alarmrecord loadById(int id) {
		return (Alarmrecord) this.hibernateTemplate.load(Alarmrecord.class, id);
	}
	
	//根据hql语句、条件、值来查询一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#queryByNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#queryByNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public Alarmrecord queryByNamedParam(String queryString, String[] paramNames,
			Object[] values) {
		List list=this.hibernateTemplate.findByNamedParam(queryString, paramNames, values);
		return list.size()>0?(Alarmrecord)list.get(0):null;
	}
	
	//根据hql语句、条件值来查询是否存在该记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#checkAlarmrecordExistsWithName(java.lang.String, java.lang.Object[])
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#checkAlarmrecordExistsWithName(java.lang.String, java.lang.Object[])
	 */
	public boolean checkAlarmrecordExistsWithName(String queryString, Object[] p) {
		List list =  this.hibernateTemplate.find(queryString,p);
		return list.size()>0 ? true : false;
	}
	//根据hql批量修改
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#updateAlarmrecordByhql(java.lang.String, java.lang.Object[])
	 */
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#updateAlarmrecordByhql(java.lang.String, java.lang.Object[])
	 */
	public int updateAlarmrecordByhql(String queryString, Object[] p) {
		Session session=this.hibernateTemplate.getSessionFactory().getCurrentSession();
		Query query= session.createQuery(queryString);
		if(p!=null&&p.length>0){
			for (int i = 0; i < p.length; i++) {
				query.setParameter(i, p[i]);
			}
		}
		//返回受影响的行数
		return query.executeUpdate();
	}


}