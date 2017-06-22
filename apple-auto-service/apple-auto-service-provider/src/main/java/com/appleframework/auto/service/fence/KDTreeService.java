package com.appleframework.auto.service.fence;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.exception.ServiceException;

public interface KDTreeService {
	
	public void create(Fence fence) throws ServiceException;
	
	public void update(Fence oldFence, Fence newFence) throws ServiceException;
	
	public void delete(Fence fence) throws ServiceException;

}
