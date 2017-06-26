package com.appleframework.auto.calculate.fence.service;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.structure.kdtree.KDTree;

public interface FenceInfoService {

	public KDTree<String> getKdTree();
	
	public void create(Fence fence);
	
	public void update(Fence oldFence, Fence newFence);
	
	public void delete(Fence oldFence);
}
