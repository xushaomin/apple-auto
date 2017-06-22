package com.appleframework.auto.calculate.fence.service.impl;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.structure.kdtree.KDTree;
import com.hazelcast.core.HazelcastInstance;

@Service
public class FenceInfoServiceImpl implements FenceInfoService {
	
	@Resource
	private HazelcastInstance hazelcastInstance;

	private KDTree<String> kdTree;
	
	/*@PostConstruct
	public void init() {
		if (null != kdTree)
			kdTree.delete(key);
		kdTree = new KDTree<String>(3);
		Set<Fence> fenceSet = hazelcastInstance.getDistributedObject("KD_TREE", KDTree.class);
		for (Fence fence : fenceSet) {
			if(fence instanceof CircleFence) {
				CircleFence circleFence = (CircleFence)fence;
				try {
					kdTree.insert(circleFence.toArray(), circleFence.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}*/
	
	

	public KDTree<String> getKdTree() {
		kdTree = hazelcastInstance.getDistributedObject("fenceInfoService", "kdtree");
		return kdTree;
	}
	
}
