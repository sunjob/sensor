package com.jlj.model;

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

/**
 * Line entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "line", catalog = "sensor")
public class Line implements java.io.Serializable {

	// Fields

	private Integer id;
	private Project project;
	private String name;
	private Integer orderid;
	private String lng;
	private String lat;
	private Set<Gateway> gatewaies = new HashSet<Gateway>(0);

	// Constructors

	/** default constructor */
	public Line() {
	}

	/** full constructor */
	public Line(Project project, String name, Integer orderid, String lng,
			String lat, Set<Gateway> gatewaies) {
		this.project = project;
		this.name = name;
		this.orderid = orderid;
		this.lng = lng;
		this.lat = lat;
		this.gatewaies = gatewaies;
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
	@JoinColumn(name = "projectid")
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "name", length = 30)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "line")
	public Set<Gateway> getGatewaies() {
		return this.gatewaies;
	}

	public void setGatewaies(Set<Gateway> gatewaies) {
		this.gatewaies = gatewaies;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "线路名称:"+this.name;
	}
}