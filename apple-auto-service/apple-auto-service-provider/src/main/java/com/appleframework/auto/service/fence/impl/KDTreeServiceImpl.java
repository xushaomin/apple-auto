package com.appleframework.auto.service.fence.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.service.fence.KDTreeService;
import com.appleframework.exception.ServiceException;
import com.appleframework.structure.kdtree.KDTree;
import com.hazelcast.core.HazelcastInstance;

@Service("kdtreeService")
public class KDTreeServiceImpl implements KDTreeService {

	protected final static Logger logger = Logger.getLogger(KDTreeServiceImpl.class);

	private static String KEY_KDTREE = "KEY_KDTREE";

	@Resource
	private HazelcastInstance hazelcastInstance;

	@Override
	public void create(Fence fence) throws ServiceException {
		KDTree<String> kdTree = this.getKDTree();
		if (fence instanceof CircleFence) {
			CircleFence circleFence = (CircleFence) fence;
			try {
				kdTree.insert(circleFence.toArray(), circleFence.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.updateKDTree(kdTree);
	}

	public void update(Fence oldFence, Fence newFence) throws ServiceException {
		KDTree<String> kdTree = this.getKDTree();
		if (oldFence instanceof CircleFence) {
			CircleFence circleFence = (CircleFence) oldFence;
			try {
				kdTree.delete(circleFence.toArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (newFence instanceof CircleFence) {
			CircleFence circleFence = (CircleFence) newFence;
			try {
				kdTree.insert(circleFence.toArray(), circleFence.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.updateKDTree(kdTree);
	}

	@Override
	public void delete(Fence fence) throws ServiceException {
		KDTree<String> kdTree = this.getKDTree();
		if (fence instanceof CircleFence) {
			CircleFence circleFence = (CircleFence) fence;
			try {
				kdTree.delete(circleFence.toArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.updateKDTree(kdTree);

	}

	private KDTree<String> getKDTree() throws ServiceException {
		Map<String, KDTree<String>> kdTreeMap = hazelcastInstance.getMap(KEY_KDTREE);
		KDTree<String> kdTree = kdTreeMap.get(KEY_KDTREE);
		if (null == kdTree) {
			kdTree = new KDTree<String>(3);
			hazelcastInstance.getMap(KEY_KDTREE).put(KEY_KDTREE, kdTree);
		}
		return kdTree;
	}

	private void updateKDTree(KDTree<String> kdTree) throws ServiceException {
		hazelcastInstance.getMap(KEY_KDTREE).put(KEY_KDTREE, kdTree);
	}

}