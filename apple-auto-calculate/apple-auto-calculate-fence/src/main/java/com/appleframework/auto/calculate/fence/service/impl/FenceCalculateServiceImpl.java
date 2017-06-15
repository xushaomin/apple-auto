package com.appleframework.auto.calculate.fence.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.calculate.fence.service.FenceCalculateService;
import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.structure.kdtree.KDTree;

@Service
public class FenceCalculateServiceImpl implements FenceCalculateService {

	protected final static Logger logger = Logger.getLogger(FenceCalculateServiceImpl.class);

	@Resource
	private FenceInfoService fenceInfoService;

	public void calculate(Location location) {
		KDTree<String> tree = fenceInfoService.getKdTree();

		double latitude = location.getLatitude();
		double longitude = location.getLongitude();

		if(logger.isDebugEnabled()) {
			logger.debug(location);
		}
		// 获取围栏信息
		double[] T = { latitude, longitude, 0 };
		try {
			Set<String> fenceIdSet = tree.nearestEuclideanReturnSet(T);
			for (String id : fenceIdSet) {
				System.out.println(id + "/n" + location);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
