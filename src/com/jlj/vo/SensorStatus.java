package com.jlj.vo;

public class SensorStatus {
	//sensorlist[0]=new Array(9);//传感器名称sensorlist[0][0]、经度sensorlist[0][1]、纬度sensorlist[0][2]、当前温度sensorlist[0][3]、当前电压sensorlist[0][4]、
	//状态sensorlist[0][5]、街景sensorlist[0][6]、编号sensorlist[0][7]、最新时间sensorlist[0][8]
	private int sensorid;
	private String sensorname;
	private String lng;
	private String lat;
	private String nowtemp;
	private String nowvoltage;
	private int status;
	private String streetpic;
	private String lasttime;
	
	
	public SensorStatus() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SensorStatus(int sensorid, String sensorname, String lng,
			String lat, String nowtemp, String nowvoltage, int status,
			String streetpic, String lasttime) {
		super();
		this.sensorid = sensorid;
		this.sensorname = sensorname;
		this.lng = lng;
		this.lat = lat;
		this.nowtemp = nowtemp;
		this.nowvoltage = nowvoltage;
		this.status = status;
		this.streetpic = streetpic;
		this.lasttime = lasttime;
	}



	public int getSensorid() {
		return sensorid;
	}
	public void setSensorid(int sensorid) {
		this.sensorid = sensorid;
	}
	public String getSensorname() {
		return sensorname;
	}
	public void setSensorname(String sensorname) {
		this.sensorname = sensorname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStreetpic() {
		return streetpic;
	}
	public void setStreetpic(String streetpic) {
		this.streetpic = streetpic;
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
	public String getNowtemp() {
		return nowtemp;
	}
	public void setNowtemp(String nowtemp) {
		this.nowtemp = nowtemp;
	}
	public String getNowvoltage() {
		return nowvoltage;
	}
	public void setNowvoltage(String nowvoltage) {
		this.nowvoltage = nowvoltage;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	
	
}
