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
 * Valve entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "valvedata", catalog = "sensor")
public class Valvedata implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer vtype;
	private Float vdata;
	private Date vtime;
	private Valve valve;

	// Constructors

	/** default constructor */
	public Valvedata() {
	}

	/** full constructor */
	public Valvedata(Valve valve, Integer vtype, Float vdata,
			Date vtime) {
		this.valve = valve;
		this.vtype = vtype;
		this.vdata = vdata;
		this.vtime = vtime;
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

	@Column(name = "vtype")
	public Integer getVtype() {
		return vtype;
	}

	public void setVtype(Integer vtype) {
		this.vtype = vtype;
	}

	@Column(name = "vdata", precision = 12)
	public Float getVdata() {
		return vdata;
	}

	public void setVdata(Float vdata) {
		this.vdata = vdata;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "vtime", length = 19)
	public Date getVtime() {
		return vtime;
	}

	public void setVtime(Date vtime) {
		this.vtime = vtime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "valveid")
	public Valve getValve() {
		return valve;
	}

	public void setValve(Valve valve) {
		this.valve = valve;
	}

	
	

	
	

	
	


	
	
	
}