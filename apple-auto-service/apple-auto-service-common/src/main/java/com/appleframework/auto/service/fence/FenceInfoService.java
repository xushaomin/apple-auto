package com.appleframework.auto.service.fence;


import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.exception.ServiceException;

/**
 * 围栏管理接口
 *
 */
public interface FenceInfoService {
	
	public void create(Fence fence) throws ServiceException;
	
}
