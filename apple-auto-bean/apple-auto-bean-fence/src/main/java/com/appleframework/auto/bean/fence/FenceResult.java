package com.appleframework.auto.bean.fence;

import java.io.Serializable;

import com.appleframework.auto.bean.location.Location;


public class FenceResult implements Serializable {

	private static final long serialVersionUID = 1L;

	public static int TYPE_IN  = 1;
	public static int TYPE_OUT = 2;

	private String account;
	private Location location;
	private String fenceId;
	private Integer type;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getFenceId() {
		return fenceId;
	}

	public void setFenceId(String fenceId) {
		this.fenceId = fenceId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FenceResult [account=");
		builder.append(account);
		builder.append(", location=");
		builder.append(location);
		builder.append(", fenceId=");
		builder.append(fenceId);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}
