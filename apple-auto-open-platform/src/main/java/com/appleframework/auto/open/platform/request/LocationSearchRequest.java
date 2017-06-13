package com.appleframework.auto.open.platform.request;

import javax.validation.constraints.NotNull;

import com.appleframework.rop.AbstractRopRequest;

public class LocationSearchRequest extends AbstractRopRequest {

	@NotNull
	private String account;

	@NotNull
	private Long startTime;

	@NotNull
	private Long endTime;

	@NotNull
	private Integer mapType;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getMapType() {
		return mapType;
	}

	public void setMapType(Integer mapType) {
		this.mapType = mapType;
	}

}
