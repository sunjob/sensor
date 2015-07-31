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
 * Command entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "command", catalog = "sensor")
public class Command implements java.io.Serializable {

	// Fields

	private Integer id;
	private Project project;
	private String name;
	private String command;

	// Constructors

	/** default constructor */
	public Command() {
	}

	/** full constructor */
	public Command(Project project, String name, String command) {
		this.project = project;
		this.name = name;
		this.command = command;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "command", length = 100)
	public String getCommand() {
		return this.command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "命令名称:"+this.name+",命令指令:"+this.command;
	}
	

}