package com.jlj.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * User entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user", catalog = "sensor")
public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private Project project;
	private String username;
	private String password;
	private String phone;
	private String email;
	private Date createdate;
	private Integer limits;
	private Integer upuserid;
	private String linetext;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** full constructor */
	public User(Project project, String username, String password,
			String phone, String email, Date createdate, Integer limits,Integer upuserid,
			String linetext) {
		this.project = project;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.createdate = createdate;
		this.limits = limits;
		this.upuserid = upuserid;
		this.linetext = linetext;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectid")
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "username", length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 30)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createdate", length = 10)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "limits")
	public Integer getLimits() {
		return this.limits;
	}

	public void setLimits(Integer limits) {
		this.limits = limits;
	}

	@Column(name = "linetext", length = 65535)
	public String getLinetext() {
		return this.linetext;
	}

	public void setLinetext(String linetext) {
		this.linetext = linetext;
	}

	@Column(name = "upuserid")
	public Integer getUpuserid() {
		return upuserid;
	}

	public void setUpuserid(Integer upuserid) {
		this.upuserid = upuserid;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String limit = "";
		switch (this.limits) {
		case 0:
			limit = "系统管理员";
			break;
			case 1:
			limit = "超级管理员";
			break;	
			case 2:
			limit = "普通管理员";
			break;
		default:
			limit = "游客";
			break;
		}
		return "用户名:"+this.username+",用户权限:"+limit;
	}
}