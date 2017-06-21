package com.appleframework.auto.service.fence;


import java.util.Set;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.exception.ServiceException;

/**
 * 围栏管理接口
 *
 */
public interface FenceInfoService {
	
	public void create(Fence fence) throws ServiceException;
	
	public Set<Fence> get() throws ServiceException;
	
}
