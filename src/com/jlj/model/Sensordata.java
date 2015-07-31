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
@Table(name = "sensordata", catalog = "sensor")
public class Sensordata implements java.io.Serializable {

	// Fields

	private Integer id;
	private Sensor sensor;
//	private Date sdate;
//	private Date stime;
	private Date sdatetime;
	private Float sdata;
	private Float vdata;
	private Integer stype;

	// Constructors

	/** default constructor */
	public Sensordata() {
	}

	/** full constructor */
	public Sensordata(Sensor sensor, Date sdatetime,
			Float sdata,Float vdata, Integer stype) {
		this.sensor = sensor;
//		this.sdate = sdate;
//		this.stime = stime;
		this.sdatetime = sdatetime;
		this.sdata = sdata;
		this.vdata = vdata;
		this.stype = stype;
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

//	@Temporal(TemporalType.DATE)
//	@Column(name = "sdate", length = 10)
//	public Date getSdate() {
//		return this.sdate;
//	}
//
//	public void setSdate(Date sdate) {
//		this.sdate = sdate;
//	}
//
//	@Temporal(TemporalType.TIME)
//	@Column(name = "stime", length = 8)
//	public Date getStime() {
//		return this.stime;
//	}
//
//	public void setStime(Date stime) {
//		this.stime = stime;
//	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sdatetime", length = 19)
	public Date getSdatetime() {
		return this.sdatetime;
	}

	public void setSdatetime(Date sdatetime) {
		this.sdatetime = sdatetime;
	}

	@Column(name = "sdata", precision = 8)
	public Float getSdata() {
		return this.sdata;
	}

	public void setSdata(Float sdata) {
		this.sdata = sdata;
	}

	@Column(name = "stype")
	public Integer getStype() {
		return this.stype;
	}

	public void setStype(Integer stype) {
		this.stype = stype;
	}

	@Column(name = "vdata", precision = 5)
	public Float getVdata() {
		return vdata;
	}

	public void setVdata(Float vdata) {
		this.vdata = vdata;
	}

	@Override
	public String toString() {
		String typename = "";
		switch (this.stype) {
		case 0:
			typename = "其他";
				break;
			case 1:
			typename = "温度";
				break;	
			case 2:
			typename = "压力";
				break;
			case 3:
			typename = "流量";
				break;
			case 4:
			typename = "电池电压";
				break;
			case 5:
			typename = "表面温度";
				break;
		default:
			typename = "其他";
			break;
		}
		return "所属传感器:"+this.sensor.getName()+",类型:"+typename+",接收数据:"+this.sdata+",接收电池电压数据:"+this.vdata;
	}
	
	
	
}