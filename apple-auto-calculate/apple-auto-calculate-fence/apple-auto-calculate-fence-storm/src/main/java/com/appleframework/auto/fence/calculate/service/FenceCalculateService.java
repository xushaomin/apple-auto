package com.appleframework.auto.fence.calculate.service;

import java.util.Set;

import com.appleframework.auto.bean.location.Location;

public interface FenceCalculateService {

	public void calculate(Location location);
	
	public Set<String> calculate2(Location location);
	
	public void noExistsFence(Location location);
	
	public void existsFence(Set<String> fenceIdSet, Location location);
}
