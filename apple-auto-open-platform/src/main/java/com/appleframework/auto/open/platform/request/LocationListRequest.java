package com.appleframework.auto.open.platform.request;

import javax.validation.constraints.NotNull;

import com.appleframework.rop.AbstractRopRequest;

public class LocationListRequest extends AbstractRopRequest {

	@NotNull
	private String account;

	@NotNull
	private String startTime;

	@NotNull
	private String endTime;

	@NotNull
	private Integer mapType;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getMapType() {
		return mapType;
	}

	public void setMapType(Integer mapType) {
		this.mapType = mapType;
	}

}
