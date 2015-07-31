package com.jlj.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="LineInfo")
public class LineInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer orderid;

	// Constructors

	/** default constructor */
	public LineInfo() {
	}

	/** full constructor */
	public LineInfo(Integer id, String name, Integer orderid) {
		this.id = id;
		this.name = name;
		this.orderid = orderid;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}


}