package com.jlj.model;

import java.util.ArrayList;
import java.util.List;
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

/**
 * Gateway entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "gateway", catalog = "sensor")
public class Gateway implements java.io.Serializable {

	// Fields

	private Integer id;
	private Line line;
	private String name;
	private String phonenumber;
	private Integer orderid;
	private String ip;
	private Integer gateaddress;
	private String macaddress;
	private Integer channel;
	private String lng;
	private String lat;
	private String location;
	private Integer status;
	private String streetpic;
	private List<Sensor> sensors = new ArrayList<Sensor>();
	private List<Valve> valves = new ArrayList<Valve>();

	// Constructors

	/** default constructor */
	public Gateway() {
	}

	/** full constructor */
	public Gateway(Line line, String name, String phonenumber, Integer orderid,
			String ip, Integer gateaddress,String macaddress, Integer channel, String lng,
			String lat, String location,Integer status, String streetpic,List<Sensor> sensors,List<Valve> valves) {
		this.line = line;
		this.name = name;
		this.phonenumber = phonenumber;
		this.orderid = orderid;
		this.ip = ip;
		this.gateaddress = gateaddress;
		this.macaddress = macaddress;
		this.channel = channel;
		this.lng = lng;
		this.lat = lat;
		this.location = location;
		this.status = status;
		this.streetpic = streetpic;
		this.sensors = sensors;
		this.valves = valves;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lid")
	public Line getLine() {
		return this.line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	@Column(name = "name", length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "phonenumber", length = 20)
	public String getPhonenumber() {
		return this.phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "ip", length = 20)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "gateaddress")
	public Integer getGateaddress() {
		return this.gateaddress;
	}

	public void setGateaddress(Integer gateaddress) {
		this.gateaddress = gateaddress;
	}
	
	@Column(name = "macaddress", length = 50)
	public String getMacaddress() {
		return this.macaddress;
	}

	public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}

	@Column(name = "channel")
	public Integer getChannel() {
		return this.channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
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

	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "streetpic")
	public String getStreetpic() {
		return this.streetpic;
	}

	public void setStreetpic(String streetpic) {
		this.streetpic = streetpic;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gateway")
	public List<Sensor> getSensors() {
		return this.sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gateway")
	public List<Valve> getValves() {
		return valves;
	}

	public void setValves(List<Valve> valves) {
		this.valves = valves;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "网关别名:"+this.name+",位置:"+this.location+",IP地址:"+this.ip+",网关地址:"+this.gateaddress+",网关硬件UID:"+this.macaddress+",无线数据通道:"+this.channel;
	}

	

}