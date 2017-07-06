package com.appleframework.auto.calculate.fence.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.fence.PolygonFence;
import com.appleframework.auto.bean.fence.RectangleFence;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;

@Service("fenceCacheService")
public class FenceCacheService {

	protected final static Logger logger = Logger.getLogger(FenceCacheService.class);

	private static RTree<String, Geometry> tree = RTree.create();

	public void init(List<Fence> list) {
		for (Fence fence : list) {
			create(fence);
		}
	}
	
	private Geometry genCircleGeometry(CircleFence fence) {
		double x = fence.getPoint().getLongitude();
		double y = fence.getPoint().getLatitude();
		double r = fence.getRadius() / 1000;
		return Geometries.circle(x, y, r);
	}
	
	private Geometry genRectangleGeometry(RectangleFence fence) {
		double lon1 = fence.getPointA().getLongitude();
		double lat1 = fence.getPointA().getLatitude();
		double lon2 = fence.getPointB().getLongitude();
		double lat2 = fence.getPointB().getLatitude();
		return Geometries.rectangleGeographic(lon1, lat1, lon2, lat2);
	}
	
	private Geometry genPolygonGeometry(PolygonFence fence) {
		List<Double> polygonXA = fence.getPolygonXA();
		List<Double> polygonYA = fence.getPolygonYA();
		return Geometries.polygon(polygonXA, polygonYA);
	}

	public void create(Fence newFence) {
		try {
			if (newFence instanceof CircleFence) {
				tree = tree.add(newFence.getId(), genCircleGeometry((CircleFence)newFence));
			}
			else if (newFence instanceof RectangleFence) {
				tree = tree.add(newFence.getId(), genRectangleGeometry((RectangleFence)newFence));
			}
			else {
				tree = tree.add(newFence.getId(), genPolygonGeometry((PolygonFence)newFence));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(Fence oldFence, Fence newFence) {
		try {
			this.delete(oldFence);
			this.create(newFence);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(Fence oldFence) {
		try {
			if (oldFence instanceof CircleFence) {
				tree = tree.delete(oldFence.getId(), genCircleGeometry((CircleFence)oldFence));
			}
			else if (oldFence instanceof RectangleFence) {
				tree = tree.delete(oldFence.getId(), genRectangleGeometry((RectangleFence)oldFence));
			}
			else {
				tree = tree.delete(oldFence.getId(), genPolygonGeometry((PolygonFence)oldFence));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RTree<String, Geometry> getTree() {
		return tree;
	}

}
