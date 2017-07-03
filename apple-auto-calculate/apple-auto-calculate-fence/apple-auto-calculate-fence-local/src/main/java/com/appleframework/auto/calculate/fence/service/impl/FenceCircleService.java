package com.appleframework.auto.calculate.fence.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.structure.kdtree.KDTree;

@Service("fenceCircleService")
public class FenceCircleService {

	protected final static Logger logger = Logger.getLogger(FenceCircleService.class);

	private KDTree<String> kdTree = new KDTree<String>(4);
	
	public void init(List<Fence> list) {
		for (Fence fence : list) {
			if (fence instanceof CircleFence) {
				CircleFence circleFence = (CircleFence) fence;
				try {
					kdTree.insert(circleFence.allToArray(), circleFence.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void create(CircleFence newFence) {
		try {
			kdTree.insert(newFence.allToArray(), newFence.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(CircleFence oldFence, CircleFence newFence) {
		try {
			kdTree.delete(oldFence.allToArray());
			kdTree.insert(newFence.allToArray(), newFence.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(CircleFence oldFence) {
		try {
			kdTree.delete(oldFence.allToArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public KDTree<String> getKdTree() {
		return kdTree;
	}

}
