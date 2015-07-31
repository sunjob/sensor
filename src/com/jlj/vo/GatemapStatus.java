package com.jlj.vo;

public class GatemapStatus {
	//网关名称gatewaylist[0][0]、经度gatewaylist[0][1]、纬度gatewaylist[0][2]、状态gatewaylist[0][3]、街景gatewaylist[0][4]、编号gatewaylist[0][5]
	
	private String gatewayname;
	private String lng;
	private String lat;
	private int status;
	private String streetpic;
	private int gatewayid;
	public GatemapStatus() {
		super();
	}
	public GatemapStatus(String gatewayname,String lng,String lat, int status,String streetpic,int gatewayid ) {
		super();
		
		this.gatewayname = gatewayname;
		this.lng = lng;
		this.lat = lat;
		this.status = status;
		this.streetpic = streetpic;
		this.gatewayid = gatewayid;
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
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getStreetpic() {
		return streetpic;
	}
	public void setStreetpic(String streetpic) {
		this.streetpic = streetpic;
	}
	
	
}
