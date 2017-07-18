package com.appleframework.auto.service.fence;


import java.util.List;

import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.appleframework.auto.model.fence.FenceSo;
import com.appleframework.exception.AppleException;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;

/**
 * 围栏管理接口
 *
 */
public interface FenceEntityService {
		
	public List<FenceEntityWithBLOBs> findAll();
	
	public Pagination findPage(Pagination page, FenceSo so, Search se);
	
	public FenceEntityWithBLOBs get(Integer id);
	
	public Integer save(FenceEntityWithBLOBs entity) throws AppleException;
	
}
