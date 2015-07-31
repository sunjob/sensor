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
 * Command entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "receivelog", catalog = "sensor")
public class Receivelog implements java.io.Serializable {

	// Fields

	private Integer id;
	private Project project;
	private String loginfo;
	private Date nowtime;

	// Constructors

	/** default constructor */
	public Receivelog() {
	}

	/** full constructor */
	public Receivelog(Project project, String loginfo,Date nowtime) {
		this.project = project;
		this.loginfo = loginfo;
		this.nowtime = nowtime;
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

	@Column(name = "loginfo", length = 30)
	public String getLoginfo() {
		return loginfo;
	}

	public void setLoginfo(String loginfo) {
		this.loginfo = loginfo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "nowtime", length = 19)
	public Date getNowtime() {
		return nowtime;
	}

	public void setNowtime(Date nowtime) {
		this.nowtime = nowtime;
	}

	
	

}