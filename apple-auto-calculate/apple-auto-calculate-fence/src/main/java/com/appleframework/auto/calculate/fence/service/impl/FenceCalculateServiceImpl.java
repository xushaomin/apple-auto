package com.appleframework.auto.calculate.fence.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.LocationProto;
import com.appleframework.auto.calculate.fence.service.FenceCalculateService;
import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.structure.kdtree.KDTree;

@Service
public class FenceCalculateServiceImpl implements FenceCalculateService {

	@Resource
	private FenceInfoService fenceInfoService;

	public void calculate(LocationProto.Model location) {
		KDTree<String> tree = fenceInfoService.getFenceInfo(location.getAccount());

		double x = location.getLatitude();
		double y = location.getLongitude();

		// 获取围栏信息
		double[] T = { x, y, 0 };
		try {
			Set<String> fenceIdSet = tree.nearestEuclideanReturnSet(T);
			for (String id : fenceIdSet) {
				System.out.println(id);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
