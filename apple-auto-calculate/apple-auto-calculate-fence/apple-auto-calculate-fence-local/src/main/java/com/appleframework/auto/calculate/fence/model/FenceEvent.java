package com.appleframework.auto.calculate.fence.model;

import java.util.Set;

import com.appleframework.auto.bean.location.Location;

public class FenceEvent {

	private Location location;
	private Set<String> fenceIdSet;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Set<String> getFenceIdSet() {
		return fenceIdSet;
	}

	public void setFenceIdSet(Set<String> fenceIdSet) {
		this.fenceIdSet = fenceIdSet;
	}

	public FenceEvent setFenceEvent(Location location, Set<String> fenceIdSet) {
		this.location = location;
		this.fenceIdSet = fenceIdSet;
		return this;

	}

}
