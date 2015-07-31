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
 * Operation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "operation", catalog = "sensor")
public class Operation implements java.io.Serializable {

	// Fields

	private Integer id;
	private Project project;
	private Date optime;
	private String optype;
	private String content;

	// Constructors

	/** default constructor */
	public Operation() {
	}

	/** full constructor */
	public Operation(Project project, Date optime, String optype, String content) {
		this.project = project;
		this.optime = optime;
		this.optype = optype;
		this.content = content;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "optime", length = 19)
	public Date getOptime() {
		return this.optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	@Column(name = "optype", length = 50)
	public String getOptype() {
		return this.optype;
	}

	public void setOptype(String optype) {
		this.optype = optype;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}