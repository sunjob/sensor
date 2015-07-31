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
 * Alarm entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "alarm", catalog = "sensor")
public class Alarm implements java.io.Serializable {

	// Fields

	private Integer id;
	private Project project;
	private Float temp;
	private Float tempdown;
	private Float normaltemp;
	private Float normaltempdown;
	private Integer temptime;
	private Float voltage;
	private Float normalvoltage;
	private Integer voltagetime;
	private Float flow;
	private Float normalflow;
	private Integer flowtime;
	private Float bmtemp;
	private Float normalbmtemp;
	private Integer bmtemptime;
	private Float pressure;
	private Float normalpressure;
	private Integer pressuretime;
	private String phones;
	private String smstemplate;

	// Constructors

	/** default constructor */
	public Alarm() {
	}

	/** full constructor */
	public Alarm(Project project, Float temp,Float tempdown, Float normaltemp,Float normaltempdown, Integer temptime, Float voltage, Float normalvoltage,
			Integer voltagetime, Float flow,Float normalflow, Integer flowtime, Float bmtemp,Float normalbmtemp,
			Integer bmtemptime,Float pressure,Float normalpressure,
			Integer pressuretime, String phones, String smstemplate) {
		this.project = project;
		this.temp = temp;
		this.normaltemp = normaltemp;
		this.temptime = temptime;
		this.voltage = voltage;
		this.normalvoltage = normalvoltage;
		this.voltagetime = voltagetime;
		this.flow = flow;
		this.normalflow = normalflow;
		this.flowtime = flowtime;
		this.bmtemp = bmtemp;
		this.normalbmtemp = normalbmtemp;
		this.bmtemptime = bmtemptime;
		this.pressure = pressure;
		this.normalpressure = normalpressure;
		this.pressuretime = pressuretime;
		this.tempdown = tempdown;
		this.normaltempdown = normaltempdown;
		this.phones = phones;
		this.smstemplate = smstemplate;
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

	@Column(name = "temp", precision = 6)
	public Float getTemp() {
		return this.temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	@Column(name = "temptime")
	public Integer getTemptime() {
		return this.temptime;
	}

	public void setTemptime(Integer temptime) {
		this.temptime = temptime;
	}

	@Column(name = "voltage", precision = 6)
	public Float getVoltage() {
		return this.voltage;
	}

	public void setVoltage(Float voltage) {
		this.voltage = voltage;
	}

	@Column(name = "voltagetime")
	public Integer getVoltagetime() {
		return this.voltagetime;
	}

	public void setVoltagetime(Integer voltagetime) {
		this.voltagetime = voltagetime;
	}

	@Column(name = "flow", precision = 6)
	public Float getFlow() {
		return this.flow;
	}

	public void setFlow(Float flow) {
		this.flow = flow;
	}

	@Column(name = "flowtime")
	public Integer getFlowtime() {
		return this.flowtime;
	}

	public void setFlowtime(Integer flowtime) {
		this.flowtime = flowtime;
	}

	@Column(name = "bmtemp", precision = 6)
	public Float getBmtemp() {
		return this.bmtemp;
	}

	public void setBmtemp(Float bmtemp) {
		this.bmtemp = bmtemp;
	}

	@Column(name = "bmtemptime")
	public Integer getBmtemptime() {
		return this.bmtemptime;
	}

	public void setBmtemptime(Integer bmtemptime) {
		this.bmtemptime = bmtemptime;
	}

	@Column(name = "phones", length = 65535)
	public String getPhones() {
		return this.phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	@Column(name = "smstemplate", length = 65535)
	public String getSmstemplate() {
		return this.smstemplate;
	}

	public void setSmstemplate(String smstemplate) {
		this.smstemplate = smstemplate;
	}

	@Column(name = "normaltemp", precision = 6)
	public Float getNormaltemp() {
		return normaltemp;
	}

	public void setNormaltemp(Float normaltemp) {
		this.normaltemp = normaltemp;
	}

	@Column(name = "normalvoltage", precision = 6)
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

	@Column(name = "pressure", precision = 6)
	public Float getPressure() {
		return pressure;
	}

	public void setPressure(Float pressure) {
		this.pressure = pressure;
	}

	@Column(name = "normalpressure", precision = 6)
	public Float getNormalpressure() {
		return normalpressure;
	}

	public void setNormalpressure(Float normalpressure) {
		this.normalpressure = normalpressure;
	}

	@Column(name = "pressuretime")
	public Integer getPressuretime() {
		return pressuretime;
	}

	public void setPressuretime(Integer pressuretime) {
		this.pressuretime = pressuretime;
	}

	@Column(name = "tempdown", precision = 6)
	public Float getTempdown() {
		return tempdown;
	}

	public void setTempdown(Float tempdown) {
		this.tempdown = tempdown;
	}

	@Column(name = "normaltempdown", precision = 6)
	public Float getNormaltempdown() {
		return normaltempdown;
	}

	public void setNormaltempdown(Float normaltempdown) {
		this.normaltempdown = normaltempdown;
	}
	
	
}