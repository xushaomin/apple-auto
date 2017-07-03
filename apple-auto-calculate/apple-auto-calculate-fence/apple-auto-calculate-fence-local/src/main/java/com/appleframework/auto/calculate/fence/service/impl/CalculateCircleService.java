package com.appleframework.auto.calculate.fence.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.structure.kdtree.KDTree;

@Service("calculateCircleService")
public class CalculateCircleService extends FenceCalculateService {

	protected final static Logger logger = Logger.getLogger(CalculateCircleService.class);

	@Resource
	private FenceCircleService fenceCircleService;
	
	public void calculate(Location location) {
		KDTree<String> tree = fenceCircleService.getKdTree();
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		// 获取围栏信息
		double[] T = { latitude, longitude, 0, 0 };
		try {
			Set<String> fenceIdSet = tree.nearestEuclideanReturnSet(T);
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

}
