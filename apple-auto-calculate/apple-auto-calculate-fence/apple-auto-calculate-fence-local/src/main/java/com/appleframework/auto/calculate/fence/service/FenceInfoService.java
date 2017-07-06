package com.appleframework.auto.calculate.fence.service;

import com.appleframework.auto.bean.fence.Fence;

public interface FenceInfoService {
	
	public void create(Fence newFence);
	
	public void update(Fence oldFence, Fence newFence);
	
	public void delete(Fence oldFence);
	
	public Fence get(String fenceId);
}
