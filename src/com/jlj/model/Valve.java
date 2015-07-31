package com.jlj.model;

import java.util.ArrayList;
import java.util.List;

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
 * Valve entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "valve", catalog = "sensor")
public class Valve implements java.io.Serializable {

	// Fields

	private Integer id;
	private Gateway gateway;
	private String name;
	private Integer valveaddress;
	private Integer locatenumber;
	private String location;
	private Integer controlvalue;
	private Integer status;
	private List<Valvedata> valvedatas = new ArrayList<Valvedata>();
	// Constructors

	/** default constructor */
	public Valve() {
	}

	/** full constructor */
	public Valve(Gateway gateway, String name, Integer valveaddress,
			Integer locatenumber, String location,Integer controlvalue, Integer status,List<Valvedata> valvedatas) {
		this.gateway = gateway;
		this.name = name;
		this.valveaddress = valveaddress;
		this.locatenumber = locatenumber;
		this.location = location;
		this.controlvalue = controlvalue;
		this.status = status;
		this.valvedatas = valvedatas;
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
		return gateway;
	}

	public void setGateway(Gateway gateway) {
		this.gateway = gateway;
	}
	
	@Column(name = "location", length = 255)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "valveaddress")
	public Integer getValveaddress() {
		return valveaddress;
	}

	public void setValveaddress(Integer valveaddress) {
		this.valveaddress = valveaddress;
	}

	@Column(name = "locatenumber")
	public Integer getLocatenumber() {
		return locatenumber;
	}

	public void setLocatenumber(Integer locatenumber) {
		this.locatenumber = locatenumber;
	}

	@Column(name = "controlvalue")
	public Integer getControlvalue() {
		return controlvalue;
	}

	public void setControlvalue(Integer controlvalue) {
		this.controlvalue = controlvalue;
	}

	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "valve")
	public List<Valvedata> getValvedatas() {
		return valvedatas;
	}

	public void setValvedatas(List<Valvedata> valvedatas) {
		this.valvedatas = valvedatas;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String statusstr = "";
		if(this.status==1){
			statusstr="开启状态";
		}else{
			statusstr="关闭状态";
		}
		return "外设编号:"+this.name+",外设地址:"+this.valveaddress+",开关位置号:"+this.locatenumber+",开关控制值:"+this.controlvalue+",开关状态:"+statusstr+",位置描述:"+this.location;
	}

	
	
	
}