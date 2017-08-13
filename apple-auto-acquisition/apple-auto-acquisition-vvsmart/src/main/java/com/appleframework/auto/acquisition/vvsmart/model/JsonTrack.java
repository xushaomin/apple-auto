package com.appleframework.auto.acquisition.vvsmart.model;

import java.io.Serializable;
import java.util.Arrays;

public class JsonTrack implements Serializable {

	private static final long serialVersionUID = -7259022096010155381L;
	
	private Integer DeviceId;
	private Boolean Online;
	private Boolean Driving;
	private String Time;
	private String ParkingTime;
	private Double Lat;
	private Double Lng;
	private Double BLat;
	private Double BLng;

	private int Status[];
	private int Alarm[];

	private Float Speed;
	private Integer Direction;
	private Long ReceiveCount;
	private String UpdateTime;
	private String offlineTime;
	private String StopTime;
	private Boolean Idling;

	public Integer getDeviceId() {
		return DeviceId;
	}

	public void setDeviceId(Integer deviceId) {
		DeviceId = deviceId;
	}

	public Boolean getOnline() {
		return Online;
	}

	public void setOnline(Boolean online) {
		Online = online;
	}

	public Boolean getDriving() {
		return Driving;
	}

	public void setDriving(Boolean driving) {
		Driving = driving;
	}

	public String getTime() {
		return Time;
	}

	public void setTime(String time) {
		Time = time;
	}

	public String getParkingTime() {
		return ParkingTime;
	}

	public void setParkingTime(String parkingTime) {
		ParkingTime = parkingTime;
	}

	public Double getLat() {
		return Lat;
	}

	public void setLat(Double lat) {
		Lat = lat;
	}

	public Double getLng() {
		return Lng;
	}

	public void setLng(Double lng) {
		Lng = lng;
	}

	public Double getBLat() {
		return BLat;
	}

	public void setBLat(Double bLat) {
		BLat = bLat;
	}

	public Double getBLng() {
		return BLng;
	}

	public void setBLng(Double bLng) {
		BLng = bLng;
	}

	public int[] getStatus() {
		return Status;
	}

	public void setStatus(int[] status) {
		Status = status;
	}

	public int[] getAlarm() {
		return Alarm;
	}

	public void setAlarm(int[] alarm) {
		Alarm = alarm;
	}

	public Float getSpeed() {
		return Speed;
	}

	public void setSpeed(Float speed) {
		Speed = speed;
	}

	public Integer getDirection() {
		return Direction;
	}

	public void setDirection(Integer direction) {
		Direction = direction;
	}

	public Long getReceiveCount() {
		return ReceiveCount;
	}

	public void setReceiveCount(Long receiveCount) {
		ReceiveCount = receiveCount;
	}

	public String getUpdateTime() {
		return UpdateTime;
	}

	public void setUpdateTime(String updateTime) {
		UpdateTime = updateTime;
	}

	public String getOfflineTime() {
		return offlineTime;
	}

	public void setOfflineTime(String offlineTime) {
		this.offlineTime = offlineTime;
	}

	public String getStopTime() {
		return StopTime;
	}

	public void setStopTime(String stopTime) {
		StopTime = stopTime;
	}

	public Boolean getIdling() {
		return Idling;
	}

	public void setIdling(Boolean idling) {
		Idling = idling;
	}

	@Override
	public String toString() {
		return "JsonTrack [DeviceId=" + DeviceId + ", Online=" + Online + ", Driving=" + Driving + ", Time=" + Time
				+ ", ParkingTime=" + ParkingTime + ", Lat=" + Lat + ", Lng=" + Lng + ", BLat=" + BLat + ", BLng=" + BLng
				+ ", Status=" + Arrays.toString(Status) + ", Alarm=" + Arrays.toString(Alarm) + ", Speed=" + Speed
				+ ", Direction=" + Direction + ", ReceiveCount=" + ReceiveCount + ", UpdateTime=" + UpdateTime
				+ ", offlineTime=" + offlineTime + ", StopTime=" + StopTime + "]";
	}

}
