package com.appleframework.auto.calculate.fence.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Location;
import com.github.davidmoten.grumpy.core.Position;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.geometry.Circle;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Point;
import com.github.davidmoten.rtree.geometry.Polygon;
import com.github.davidmoten.rtree.geometry.Rectangle;

import rx.Observable;
import rx.functions.Func1;

@Service("fenceCalculateService")
public class FenceCalculateService extends FenceInoutService {

	protected final static Logger logger = Logger.getLogger(FenceCalculateService.class);

	@Resource
	private FenceNotifyService fenceNotifyService;
	
	@Resource
	private FenceCacheService fenceCacheService;

	public void calculate(Location location) {
		double x = location.getLongitude();
		double y = location.getLatitude();

		try {
			Set<String> fenceIdSet = this.search(x, y);
			if (fenceIdSet == null || fenceIdSet.size() == 0) {
				// 不存在围栏信息，全部退出
				noExistsFence(location);
			} else {
				// 存在围栏记录
				existsFence(fenceIdSet, location);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Set<String> search(double x, double y) {
		Set<String> fenceSet = new HashSet<>();
		Observable<Entry<String, Geometry>> result = search(Geometries.point(x, y));
		result.forEach(item -> {
			fenceSet.add(item.value());
		});
		return fenceSet;
	}
	
	private Observable<Entry<String, Geometry>> search(final Point point) {
        return fenceCacheService.getTree().search(point).filter(new Func1<Entry<String, Geometry>, Boolean>() {
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
