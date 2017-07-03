package com.appleframework.auto.calculate.fence.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.fence.RectangleFence;
import com.appleframework.structure.rtree.Rectangle;
import com.appleframework.structure.rtree.SpatialIndex;
import com.appleframework.structure.rtree.rtree.RTree;

@Service("fenceRectangleService")
public class FenceRectangleService {

	protected final static Logger logger = Logger.getLogger(FenceRectangleService.class);

	private SpatialIndex rTree = new RTree();

	public void init(List<Fence> list) {
		rTree.init(null);
		for (Fence fence : list) {
			if (fence instanceof RectangleFence) {
				RectangleFence rectangleFence = (RectangleFence) fence;
				Rectangle rectangle = new Rectangle(rectangleFence.allToArray());
				try {
					rTree.add(rectangle, Integer.parseInt(rectangleFence.getId()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void create(RectangleFence newFence) {
		try {
			rTree.add(new Rectangle(newFence.allToArray()), Integer.parseInt(newFence.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(RectangleFence oldFence, RectangleFence newFence) {
		try {
			rTree.delete(new Rectangle(oldFence.allToArray()), Integer.parseInt(oldFence.getId()));
			rTree.add(new Rectangle(newFence.allToArray()), Integer.parseInt(newFence.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(RectangleFence oldFence) {
		try {
			rTree.delete(new Rectangle(oldFence.allToArray()), Integer.parseInt(oldFence.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SpatialIndex getRTree() {
		return rTree;
	}

}
