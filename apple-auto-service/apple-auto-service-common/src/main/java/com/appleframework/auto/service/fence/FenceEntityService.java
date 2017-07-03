package com.appleframework.auto.service.fence;


import java.util.List;

import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;

/**
 * 围栏管理接口
 *
 */
public interface FenceEntityService {
	
	/*public void create(Fence fence) throws ServiceException;
	
	public void update(Fence fence) throws ServiceException;
	
	public void delete(String id) throws ServiceException;*/
	
	public List<FenceEntityWithBLOBs> findAll();
	
}
