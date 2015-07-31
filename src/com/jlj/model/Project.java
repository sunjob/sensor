package com.jlj.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Project entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "project", catalog = "sensor")
public class Project implements java.io.Serializable {

	// Fields

	private Integer id;
	private String number;
	private Integer orderid;
	private String name;
	private Date startdate;
	private Date enddate;
	private String sgdw;
	private String gdcs;
	private String owner;
	private String lng;
	private String lat;
	private Date createtime;
	private Set<Alarm> alarms = new HashSet<Alarm>(0);
	private Set<Operation> operations = new HashSet<Operation>(0);
	private Set<Line> lines = new HashSet<Line>(0);
	private Set<Command> commands = new HashSet<Command>(0);
	private Set<Addresslist> addresslists = new HashSet<Addresslist>(0);
	private Set<User> users = new HashSet<User>(0);
	private List<Receivelog> receivelogs = new ArrayList<Receivelog>();
	// Constructors

	/** default constructor */
	public Project() {
	}

	/** full constructor */
	public Project(String number, Integer orderid, String name, Date startdate,
			Date enddate, String sgdw, String gdcs, String owner, String lng,
			String lat, Date createtime, Set<Alarm> alarms,
			Set<Operation> operations, Set<Line> lines, Set<Command> commands,
			Set<Addresslist> addresslists, Set<User> users,List<Receivelog> receivelogs) {
		this.number = number;
		this.orderid = orderid;
		this.name = name;
		this.startdate = startdate;
		this.enddate = enddate;
		this.sgdw = sgdw;
		this.gdcs = gdcs;
		this.owner = owner;
		this.lng = lng;
		this.lat = lat;
		this.createtime = createtime;
		this.alarms = alarms;
		this.operations = operations;
		this.lines = lines;
		this.commands = commands;
		this.addresslists = addresslists;
		this.users = users;
		this.receivelogs = receivelogs;
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

	@Column(name = "number", length = 30)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "startdate", length = 10)
	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "enddate", length = 10)
	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	@Column(name = "sgdw", length = 50)
	public String getSgdw() {
		return this.sgdw;
	}

	public void setSgdw(String sgdw) {
		this.sgdw = sgdw;
	}

	@Column(name = "gdcs", length = 50)
	public String getGdcs() {
		return this.gdcs;
	}

	public void setGdcs(String gdcs) {
		this.gdcs = gdcs;
	}

	@Column(name = "owner", length = 50)
	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
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

	@Temporal(TemporalType.DATE)
	@Column(name = "createtime", length = 19)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Alarm> getAlarms() {
		return this.alarms;
	}

	public void setAlarms(Set<Alarm> alarms) {
		this.alarms = alarms;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Operation> getOperations() {
		return this.operations;
	}

	public void setOperations(Set<Operation> operations) {
		this.operations = operations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Line> getLines() {
		return this.lines;
	}

	public void setLines(Set<Line> lines) {
		this.lines = lines;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Command> getCommands() {
		return this.commands;
	}

	public void setCommands(Set<Command> commands) {
		this.commands = commands;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<Addresslist> getAddresslists() {
		return this.addresslists;
	}

	public void setAddresslists(Set<Addresslist> addresslists) {
		this.addresslists = addresslists;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "project")
	public List<Receivelog> getReceivelogs() {
		return receivelogs;
	}

	public void setReceivelogs(List<Receivelog> receivelogs) {
		this.receivelogs = receivelogs;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "项目ID:"+this.id+",项目编号:"+this.number+",项目名称:"+this.name;
	}

}