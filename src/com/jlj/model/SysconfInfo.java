package com.jlj.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Line entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@XmlRootElement(name="SysconfInfo")
public class SysconfInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String deadline;
	private Integer status;
	
	// Constructors

	/** default constructor */
	public SysconfInfo() {
	}

	/** full constructor */
	public SysconfInfo(String name,String deadline,Integer status) {
		this.name = name;
		this.deadline = deadline;
		this.status = status;
	}

	// Property accessors
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}