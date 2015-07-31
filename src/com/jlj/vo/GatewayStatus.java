package com.jlj.vo;

public class GatewayStatus {
	private int gatewayid;
	private String gatewayname;
	private int status;
	
	public GatewayStatus() {
		super();
	}
	public GatewayStatus(int gatewayid, String gatewayname, int status) {
		super();
		this.gatewayid = gatewayid;
		this.gatewayname = gatewayname;
		this.status = status;
	}
	public int getGatewayid() {
		return gatewayid;
	}
	public void setGatewayid(int gatewayid) {
		this.gatewayid = gatewayid;
	}
	public String getGatewayname() {
		return gatewayname;
	}
	public void setGatewayname(String gatewayname) {
		this.gatewayname = gatewayname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
