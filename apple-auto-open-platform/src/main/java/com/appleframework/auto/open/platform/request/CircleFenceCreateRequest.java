package com.appleframework.auto.open.platform.request;

import javax.validation.constraints.NotNull;

import com.appleframework.rop.AbstractRopRequest;

public class CircleFenceCreateRequest extends AbstractRopRequest {

	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

	@NotNull
	private Double radius;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

}
