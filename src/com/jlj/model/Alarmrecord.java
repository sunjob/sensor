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
 * Sensordata entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "alarmrecord", catalog = "sensor")
public class Alarmrecord implements java.io.Serializable {

	// Fields

	private Integer id;
	private Sensor sensor;
	private Date alarmtime;
	private Integer alarmtype;
	private Float alarmdata;
	private String phones;
	private String sendreturn;
	// Constructors

	/** default constructor */
	public Alarmrecord() {
	}

	/** full constructor */
	public Alarmrecord(Sensor sensor, Date alarmtime, Integer alarmtype,
			Float alarmdata,String phones,String sendreturn) {
		this.sensor = sensor;
		this.alarmtime = alarmtime;
		this.alarmtype = alarmtype;
		this.alarmdata = alarmdata;
		this.phones = phones;
		this.sendreturn = sendreturn;
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
	@JoinColumn(name = "sensorid")
	public Sensor getSensor() {
		return this.sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "alarmtime", length = 19)
	public Date getAlarmtime() {
		return this.alarmtime;
	}

	public void setAlarmtime(Date alarmtime) {
		this.alarmtime = alarmtime;
	}

	@Column(name = "alarmtype")
	public Integer getAlarmtype() {
		return alarmtype;
	}

	public void setAlarmtype(Integer alarmtype) {
		this.alarmtype = alarmtype;
	}

	@Column(name = "alarmdata", precision = 6)
	public Float getAlarmdata() {
		return alarmdata;
	}

	public void setAlarmdata(Float alarmdata) {
		this.alarmdata = alarmdata;
	}

	@Column(name = "phones", length = 65535)
	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	@Column(name = "sendreturn", length = 65535)
	public String getSendreturn() {
		return sendreturn;
	}

	public void setSendreturn(String sendreturn) {
		this.sendreturn = sendreturn;
	}

	

}