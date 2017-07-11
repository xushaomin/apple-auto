package com.appleframework.auto.model.fence;

import java.io.Serializable;

public class FenceSo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private Integer fenceType;

	private Boolean isEnable;

	private Integer dealType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getFenceType() {
		return fenceType;
	}

	public void setFenceType(Integer fenceType) {
		this.fenceType = fenceType;
	}

	public Boolean getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Integer getDealType() {
		return dealType;
	}

	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}

}