package com.jlj.vo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Usermo")
public class Usermo {
	private Integer projectid;
	private String projectname;
	private String username;
	private String password;
	private Integer limits;
	private Integer upuserid;
	private String linetext;
	
	
	public Usermo() {
		super();
	}
	public Usermo(Integer projectid, String projectname, String username,
			String password, Integer limits, Integer upuserid, String linetext) {
		super();
		this.projectid = projectid;
		this.projectname = projectname;
		this.username = username;
		this.password = password;
		this.limits = limits;
		this.upuserid = upuserid;
		this.linetext = linetext;
	}
	public Integer getProjectid() {
		return projectid;
	}
	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getLimits() {
		return limits;
	}
	public void setLimits(Integer limits) {
		this.limits = limits;
	}
	public Integer getUpuserid() {
		return upuserid;
	}
	public void setUpuserid(Integer upuserid) {
		this.upuserid = upuserid;
	}
	public String getLinetext() {
		return linetext;
	}
	public void setLinetext(String linetext) {
		this.linetext = linetext;
	}
	
	
}
