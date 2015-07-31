package com.jlj.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Sensor entity.
 * 
 * @author MyEclipse Persistence Tools
 */
/**
 * @author Administrator
 *
 */
@Entity
@Table(name = "sensor", catalog = "sensor")
public class Sensor implements java.io.Serializable {

	// Fields
	private Integer id;
	private Gateway gateway;
	private String name;
	private Integer orderid;
	private Integer sensortype;
	private String devicetype;
	private String lng;
	private String lat;
	private String location;
	private Integer sensoraddress;
	private String streetpic;
	
	private Float alarmtemp;
	private Float alarmtempdown;
	private Float alarmpressure;
	private Float alarmvoltage;
	private Float alarmflow;
	private Float alarmbmtemp;
	
	private Float normaltemp;
	private Float normaltempdown;
	private Float normalpressure;
	private Float normalvoltage;
	private Float normalflow;
	private Float normalbmtemp;
	
	private Integer intervaltime;
	private Float nowtemp;
	private Float nowpressure;
	private Float nowvoltage;
	private Float nowflow;
	private Float nowbmtemp;
	private Integer status;
	private Integer iscanalarm;
	private Integer iscanalarm2;

	private Date lasttime;
	private String alarmphones;
	private Set<Sensordata> sensordatas = new HashSet<Sensordata>(0);
	private Set<Alarmrecord> alarmrecords = new HashSet<Alarmrecord>(0);
	// Constructors

	/** default constructor */
	public Sensor() {
	}

	/** full constructor */
	public Sensor(Gateway gateway, String name, Integer orderid,
			Integer sensortype,String devicetype, String lng, String lat, String location,
			Integer sensoraddress, String streetpic,Integer iscanalarm2,
			Float alarmtemp,Float alarmtempdown,Float alarmpressure, Float alarmvoltage, Float alarmflow,
			Float alarmbmtemp,Float normaltemp,Float normaltempdown,Float normalpressure, Float normalvoltage, Float normalflow,
			Float normalbmtemp, Float nowtemp,Float nowpressure, Float nowvoltage, Float nowflow,
			Float nowbmtemp,Integer status,Integer iscanalarm,Date lasttime,String alarmphones,Integer intervaltime, Set<Sensordata> sensordatas, Set<Alarmrecord> alarmrecords) {
		this.gateway = gateway;
		this.name = name;
		this.orderid = orderid;
		this.sensortype = sensortype;
		this.devicetype = devicetype;
		this.lng = lng;
		this.lat = lat;
		this.location = location;
		this.sensoraddress = sensoraddress;
		this.streetpic = streetpic;
		this.alarmtemp = alarmtemp;
		this.alarmtempdown = alarmtempdown;
		this.alarmpressure = alarmpressure;
		this.alarmvoltage = alarmvoltage;
		this.alarmflow = alarmflow;
		this.alarmbmtemp = alarmbmtemp;
		this.normaltemp = normaltemp;
		this.alarmtempdown = alarmtempdown;
		this.normalpressure = normalpressure;
		this.normalvoltage = normalvoltage;
		this.normalflow = normalflow;
		this.normalbmtemp = normalbmtemp;
		this.intervaltime = intervaltime;
		this.sensordatas = sensordatas;
		this.alarmrecords = alarmrecords;
		this.status = status;
		this.iscanalarm = iscanalarm;
		this.iscanalarm2 = iscanalarm2;
		this.nowtemp = nowtemp;
		this.nowpressure = nowpressure;
		this.nowvoltage = nowvoltage;
		this.nowflow = nowflow;
		this.nowbmtemp = nowbmtemp;
		this.lasttime = lasttime;
		this.alarmphones = alarmphones;
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
	@JoinColumn(name = "gatewayid")
	public Gateway getGateway() {
		return this.gateway;
	}

	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "sensortype")
	public Integer getSensortype() {
		return this.sensortype;
	}

	public void setSensortype(Integer sensortype) {
		this.sensortype = sensortype;
	}

	@Column(name = "devicetype", length = 20)
	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	@Column(name = "lng", length = 20)
	public String getLng() {
		return this.lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	@Column(name = "lat", length = 20)
	public String getLat() {
		return this.lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Column(name = "location", length = 50)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	@Column(name = "sensoraddress")
	public Integer getSensoraddress() {
		return sensoraddress;
	}

	public void setSensoraddress(Integer sensoraddress) {
		this.sensoraddress = sensoraddress;
	}

	@Column(name = "streetpic")
	public String getStreetpic() {
		return this.streetpic;
	}

	public void setStreetpic(String streetpic) {
		this.streetpic = streetpic;
	}

	@Column(name = "alarmtemp", precision = 6)
	public Float getAlarmtemp() {
		return this.alarmtemp;
	}

	public void setAlarmtemp(Float alarmtemp) {
		this.alarmtemp = alarmtemp;
	}

	@Column(name = "alarmvoltage", precision = 8)
	public Float getAlarmvoltage() {
		return this.alarmvoltage;
	}

	public void setAlarmvoltage(Float alarmvoltage) {
		this.alarmvoltage = alarmvoltage;
	}

	@Column(name = "alarmflow", precision = 6)
	public Float getAlarmflow() {
		return this.alarmflow;
	}

	public void setAlarmflow(Float alarmflow) {
		this.alarmflow = alarmflow;
	}

	@Column(name = "alarmbmtemp", precision = 6)
	public Float getAlarmbmtemp() {
		return this.alarmbmtemp;
	}

	public void setAlarmbmtemp(Float alarmbmtemp) {
		this.alarmbmtemp = alarmbmtemp;
	}

	@Column(name = "intervaltime")
	public Integer getIntervaltime() {
		return this.intervaltime;
	}

	public void setIntervaltime(Integer intervaltime) {
		this.intervaltime = intervaltime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sensor")
	public Set<Sensordata> getSensordatas() {
		return this.sensordatas;
	}

	public void setSensordatas(Set<Sensordata> sensordatas) {
		this.sensordatas = sensordatas;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sensor")
	public Set<Alarmrecord> getAlarmrecords() {
		return alarmrecords;
	}

	public void setAlarmrecords(Set<Alarmrecord> alarmrecords) {
		this.alarmrecords = alarmrecords;
	}

	@Column(name = "alarmpressure", precision = 6)
	public Float getAlarmpressure() {
		return alarmpressure;
	}

	public void setAlarmpressure(Float alarmpressure) {
		this.alarmpressure = alarmpressure;
	}

	@Column(name = "nowtemp", precision = 6)
	public Float getNowtemp() {
		return nowtemp;
	}

	public void setNowtemp(Float nowtemp) {
		this.nowtemp = nowtemp;
	}

	@Column(name = "nowpressure", precision = 6)
	public Float getNowpressure() {
		return nowpressure;
	}

	public void setNowpressure(Float nowpressure) {
		this.nowpressure = nowpressure;
	}

	@Column(name = "nowvoltage", precision = 8)
	public Float getNowvoltage() {
		return nowvoltage;
	}

	public void setNowvoltage(Float nowvoltage) {
		this.nowvoltage = nowvoltage;
	}

	@Column(name = "nowflow", precision = 6)
	public Float getNowflow() {
		return nowflow;
	}

	public void setNowflow(Float nowflow) {
		this.nowflow = nowflow;
	}

	@Column(name = "nowbmtemp", precision = 6)
	public Float getNowbmtemp() {
		return nowbmtemp;
	}

	public void setNowbmtemp(Float nowbmtemp) {
		this.nowbmtemp = nowbmtemp;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	@Column(name = "normaltemp", precision = 6)
	public Float getNormaltemp() {
		return normaltemp;
	}

	public void setNormaltemp(Float normaltemp) {
		this.normaltemp = normaltemp;
	}

	@Column(name = "normalpressure", precision = 6)
	public Float getNormalpressure() {
		return normalpressure;
	}

	public void setNormalpressure(Float normalpressure) {
		this.normalpressure = normalpressure;
	}

	@Column(name = "normalvoltage", precision = 8)
	public Float getNormalvoltage() {
		return normalvoltage;
	}

	public void setNormalvoltage(Float normalvoltage) {
		this.normalvoltage = normalvoltage;
	}

	@Column(name = "normalflow", precision = 6)
	public Float getNormalflow() {
		return normalflow;
	}

	public void setNormalflow(Float normalflow) {
		this.normalflow = normalflow;
	}

	@Column(name = "normalbmtemp", precision = 6)
	public Float getNormalbmtemp() {
		return normalbmtemp;
	}

	public void setNormalbmtemp(Float normalbmtemp) {
		this.normalbmtemp = normalbmtemp;
	}

	
	@Column(name = "iscanalarm")
	public Integer getIscanalarm() {
		return iscanalarm;
	}

	public void setIscanalarm(Integer iscanalarm) {
		this.iscanalarm = iscanalarm;
	}

	@Column(name = "iscanalarm2")
	public Integer getIscanalarm2() {
		return iscanalarm2;
	}

	public void setIscanalarm2(Integer iscanalarm2) {
		this.iscanalarm2 = iscanalarm2;
	}
	
	@Column(name = "alarmphones", length = 65535)
	public String getAlarmphones() {
		return alarmphones;
	}

	public void setAlarmphones(String alarmphones) {
		this.alarmphones = alarmphones;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lasttime", length = 19)
	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	@Column(name = "alarmtempdown", precision = 6)
	public Float getAlarmtempdown() {
		return alarmtempdown;
	}

	public void setAlarmtempdown(Float alarmtempdown) {
		this.alarmtempdown = alarmtempdown;
	}

	@Column(name = "normaltempdown", precision = 6)
	public Float getNormaltempdown() {
		return normaltempdown;
	}

	public void setNormaltempdown(Float normaltempdown) {
		this.normaltempdown = normaltempdown;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub （0：其他、1：温度、2：压力、3：流量、4：电池电压、5：表面温度）
		String typename = "";
		switch (this.sensortype) {
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
		return "所属网关:"+this.gateway.getName()+",传感器类型:"+typename+",设备型号:"+this.devicetype+",传感器地址:"+this.sensoraddress;
	}
}