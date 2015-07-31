package com.jlj.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Addresslist entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "addresslist", catalog = "sensor")
public class Addresslist implements java.io.Serializable {

	// Fields

	private Integer id;
	private Project project;
	private String username;
	private String phone;

	// Constructors

	/** default constructor */
	public Addresslist() {
	}

	/** full constructor */
	public Addresslist(Project project, String username, String phone) {
		this.project = project;
		this.username = username;
		this.phone = phone;
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
	@JoinColumn(name = "projectid")
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Column(name = "username", length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "phone", length = 20)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "接收人:"+this.username+",手机号码:"+this.phone;
	}
	
	

}