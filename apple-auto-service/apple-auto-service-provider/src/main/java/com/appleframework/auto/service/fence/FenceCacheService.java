package com.appleframework.auto.service.fence;

import java.util.List;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.exception.ServiceException;

public interface FenceCacheService {
	
	public void create(Fence fence) throws ServiceException;
	
	public void update(Fence fence) throws ServiceException;
	
	public void delete(String id) throws ServiceException;
	
	public List<Fence> get() throws ServiceException;
	
	public void clear() throws ServiceException;
	
}
