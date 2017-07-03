package com.appleframework.auto.calculate.fence.service.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.structure.rtree.Point;
import com.appleframework.structure.rtree.SpatialIndex;

import gnu.trove.procedure.TIntProcedure;

@Service("calculateRectangleService")
public class CalculateRectangleService extends FenceCalculateService {

	protected final static Logger logger = Logger.getLogger(CalculateRectangleService.class);

	@Resource
	private FenceRectangleService fenceRectangleService;

	public void calculate(Location location) {
		try {
			Set<String> fenceIdSet = this.nearest(location);
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

	private Set<String> nearest(Location location) {
		SpatialIndex tree = fenceRectangleService.getRTree();
		Point point = new Point(location.getLatitude(), location.getLongitude());
		final Set<String> fenceIdSet = new HashSet<>();
		TIntProcedure v = new TIntProcedure() {
			public boolean execute(int i) {
				fenceIdSet.add(String.valueOf(i));
				return true;
			}
		};
		tree.nearest(point, v, 0);
		return fenceIdSet;
	}

}
