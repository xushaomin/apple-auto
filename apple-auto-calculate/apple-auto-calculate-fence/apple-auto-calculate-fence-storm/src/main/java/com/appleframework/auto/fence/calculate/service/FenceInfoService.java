package com.appleframework.auto.fence.calculate.service;

import java.util.List;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.cache.jedis.factory.PoolFactory;
import com.appleframework.structure.kdtree.KDTree;

public interface FenceInfoService {

	public KDTree<String> getKdTree();
	
	public void create(Fence fence);

	public void update(Fence oldFence, Fence newFence);

	public void delete(Fence oldFence);

	public List<Fence> get();
	
	public void setPoolFactory(PoolFactory poolFactory);
	
	public void init();
}
