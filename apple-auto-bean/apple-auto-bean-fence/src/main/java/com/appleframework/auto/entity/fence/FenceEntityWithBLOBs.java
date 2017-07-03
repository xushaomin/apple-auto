package com.appleframework.auto.entity.fence;

import java.io.Serializable;

public class FenceEntityWithBLOBs extends FenceEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String parameter;

	private String latitudes;

	private String longitudes;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter == null ? null : parameter.trim();
	}

	public String getLatitudes() {
		return latitudes;
	}

	public void setLatitudes(String latitudes) {
		this.latitudes = latitudes == null ? null : latitudes.trim();
	}

	public String getLongitudes() {
		return longitudes;
	}

	public void setLongitudes(String longitudes) {
		this.longitudes = longitudes == null ? null : longitudes.trim();
	}
}