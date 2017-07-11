package com.appleframework.auto.fence.calculate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Fence;
import com.appleframework.auto.bean.fence.PolygonFence;
import com.appleframework.auto.bean.fence.RectangleFence;
import com.appleframework.auto.bean.fence.SyncOperate;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.factory.RedisFactory;
import com.appleframework.cache.jedis.factory.PoolFactory;
import com.github.davidmoten.grumpy.core.Position;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Circle;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Polygon;
import com.github.davidmoten.rtree.geometry.Rectangle;

import rx.Observable;
import rx.functions.Func1;

public abstract class BaseFenceCalculateRTreeBolt extends BaseFenceCalculateBolt {

	private static final Logger logger = LoggerFactory.getLogger(BaseFenceCalculateRTreeBolt.class);

	private static final long serialVersionUID = 1L;

	protected static RTree<String, Geometry> tree = RTree.create();

	public void init() {
		PoolFactory poolFactory = RedisFactory.getInstance(props);
		List<Fence> list = this.get(poolFactory);
		for (Fence fence : list) {
			create(fence);
		}
		super.init(poolFactory);
	}

	public Set<String> calculate(Location location) {
		double x = location.getLongitude();
		double y = location.getLatitude();
		try {
			return this.search(x, y);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public void processMessage(Object message) {
		if (message instanceof SyncOperate) {
			SyncOperate operate = (SyncOperate) message;
			if (logger.isInfoEnabled()) {
				logger.info("SyncOperate===========" + operate.toString());
			}
			if (operate.getOperateType() == SyncOperate.CREATE) {
				this.create(operate.getNewFence());
			} else if (operate.getOperateType() == SyncOperate.UPDATE) {
				this.update(operate.getOldFence(), operate.getNewFence());
			} else {
				this.delete(operate.getOldFence());
			}
		}
	}
	
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
			logger.error(e.getMessage());
		}

	}

	public void update(Fence oldFence, Fence newFence) {
		try {
			this.delete(oldFence);
			this.create(newFence);
		} catch (Exception e) {
			logger.error(e.getMessage());
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
			logger.error(e.getMessage());
		}
	}
	
	private Set<String> search(double x, double y) {
		Set<String> fenceSet = new HashSet<>();
		Observable<Entry<String, Geometry>> result = search(Geometries.point(x, y));
		/*result.forEach(item -> {
			fenceSet.add(item.value());
		});*/
		Iterable<Entry<String, Geometry>> it = result.toBlocking().toIterable();
		for (Entry<String, Geometry> entry : it) {
			fenceSet.add(entry.value());
		}
		return fenceSet;
	}
	
	private Observable<Entry<String, Geometry>> search(final Point point) {
        return tree.search(point).filter(new Func1<Entry<String, Geometry>, Boolean>() {
            @Override
            public Boolean call(Entry<String, Geometry> entry) {
            	Geometry geo = entry.geometry();
            	if(geo instanceof Circle) {
            		Position from = Position.create(point.y(), point.x());
            		Circle circle = (Circle)entry.geometry();
                    Position centre = Position.create(circle.y(), circle.x());
                    return from.getDistanceToKm(centre) < circle.radius();
            	}
            	else if(geo instanceof Rectangle) {
            		Rectangle rectangle = (Rectangle)entry.geometry();
            		boolean isContains = rectangle.contains(point.x(), point.y());
            		return isContains;
            	}
            	else {
            		Polygon polygon = (Polygon)entry.geometry();
            		boolean isContains = polygon.contains(point.x(), point.y());
            		return isContains;
            	}
            }
        });
    }

}
