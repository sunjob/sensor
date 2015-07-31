package com.jlj.dao;

import java.util.List;

import com.jlj.model.Alarmrecord;

public interface IAlarmrecordDao {

	//保存一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAddresslistDao#save(com.jlj.model.Addresslist)
	 */
	public abstract void save(Alarmrecord alarmrecord);

	//保存一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#savereturn(com.jlj.model.Alarmrecord)
	 */
	public abstract Integer savereturn(Alarmrecord alarmrecord);

	//删除一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#delete(com.jlj.model.Alarmrecord)
	 */
	public abstract void delete(Alarmrecord alarmrecord);

	//根据ID删除一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#deleteById(int)
	 */
	public abstract void deleteById(int id);

	//修改一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#update(com.jlj.model.Alarmrecord)
	 */
	public abstract void update(Alarmrecord alarmrecord);

	//根据hql语句、条件、条件值修改某些记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#updateByHql(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public abstract void updateByHql(final String hql,
			final String[] paramNames, final Object[] values);

	//获得所有记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getAlarmrecords()
	 */
	public abstract List<Alarmrecord> getAlarmrecords();

	//根据hql语句来查询所有记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#queryList(java.lang.String)
	 */
	public abstract List<Alarmrecord> queryList(String queryString);

	//根据hql、条件值查询某些记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getObjectsByCondition(java.lang.String, java.lang.Object[])
	 */
	public abstract List<Alarmrecord> getObjectsByCondition(String queryString,
			Object[] p);

	//根据hql语句、条件、条件值查询某些记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#queryList(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public abstract List<Alarmrecord> queryList(String queryString,
			String[] paramNames, Object[] values);

	//根据hql、id列表查询某些记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getObjectsByIdList(java.lang.String, java.util.List)
	 */
	public abstract List<Alarmrecord> getObjectsByIdList(final String hql,
			final List<Integer> idList);

	//根据hql语句、条件值、分页查询某些记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#pageList(java.lang.String, java.lang.Object[], java.lang.Integer, java.lang.Integer)
	 */
	public abstract List<Alarmrecord> pageList(final String queryString,
			final Object[] p, final Integer page, final Integer size);

	//根据hql、条件值获得一个唯一值
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#getUniqueResult(java.lang.String, java.lang.Object[])
	 */
	public abstract int getUniqueResult(final String queryString,
			final Object[] p);

	//根据id查询一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#loadById(int)
	 */
	public abstract Alarmrecord loadById(int id);

	//根据hql语句、条件、值来查询一条记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#queryByNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public abstract Alarmrecord queryByNamedParam(String queryString,
			String[] paramNames, Object[] values);

	//根据hql语句、条件值来查询是否存在该记录
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#checkAlarmrecordExistsWithName(java.lang.String, java.lang.Object[])
	 */
	public abstract boolean checkAlarmrecordExistsWithName(String queryString,
			Object[] p);

	//根据hql批量修改
	/* (non-Javadoc)
	 * @see com.jlj.dao.imp.IAlarmrecordDao#updateAlarmrecordByhql(java.lang.String, java.lang.Object[])
	 */
	public abstract int updateAlarmrecordByhql(String queryString, Object[] p);

}