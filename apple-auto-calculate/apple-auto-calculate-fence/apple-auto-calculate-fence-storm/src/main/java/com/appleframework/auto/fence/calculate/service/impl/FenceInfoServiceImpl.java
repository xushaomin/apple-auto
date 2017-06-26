package com.appleframework.auto.fence.calculate.service.impl;

import java.io.Serializable;
import java.util.UUID;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Point;
import com.appleframework.auto.fence.calculate.service.FenceInfoService;
import com.appleframework.auto.fence.calculate.utils.Constants;
import com.appleframework.auto.fence.calculate.utils.PoiUtils;
import com.appleframework.structure.kdtree.KDTree;
import com.appleframework.structure.kdtree.KeyDuplicateException;
import com.appleframework.structure.kdtree.KeySizeException;

public class FenceInfoServiceImpl implements FenceInfoService, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static FenceInfoServiceImpl impl;
	
	public static void instance() {
		impl = new FenceInfoServiceImpl();
	}
	
	public static FenceInfoService getInstance() {
		if(null == impl) {
			impl = new FenceInfoServiceImpl();
		}
		return impl;
	}
	

	private static KDTree<String> kdTree;
	
	static {
		kdTree = new KDTree<String>(3);
		
		double longitude = 113.96645;
		double latitude = 22.538192;
		
		CircleFence fence = new CircleFence();
		Point point = new Point();
		point.setLatitude(latitude);
		point.setLongitude(longitude);
		PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
		fence.setPoint(point);
		fence.setRadius(200d);
		fence.setId(UUID.randomUUID().toString());
		
		try {
			kdTree.insert(fence.toArray(), fence.getId());
		} catch (KeySizeException | KeyDuplicateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		longitude = 113.956497;
		latitude = 22.53866;
		
		fence = new CircleFence();
		point = new Point();
		point.setLatitude(latitude);
		point.setLongitude(longitude);
		PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
		fence.setPoint(point);
		fence.setRadius(200d);
		fence.setId(UUID.randomUUID().toString());
		
		try {
			kdTree.insert(fence.toArray(), fence.getId());
		} catch (KeySizeException | KeyDuplicateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		longitude = 113.960818;
		latitude = 22.540969;
		
		fence = new CircleFence();
		point = new Point();
		point.setLatitude(latitude);
		point.setLongitude(longitude);
		PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
		fence.setPoint(point);
		fence.setRadius(200d);
		fence.setId(UUID.randomUUID().toString());
		
		try {
			kdTree.insert(fence.toArray(), fence.getId());
		} catch (KeySizeException | KeyDuplicateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		longitude = 113.933131;
		latitude = 22.530473;
		
		fence = new CircleFence();
		point = new Point();
		point.setLatitude(latitude);
		point.setLongitude(longitude);
		PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
		fence.setPoint(point);
		fence.setRadius(500d);
		fence.setId(UUID.randomUUID().toString());
		
		try {
			kdTree.insert(fence.toArray(), fence.getId());
		} catch (KeySizeException | KeyDuplicateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		longitude = 113.960557;
		latitude = 22.526613;
		
		fence = new CircleFence();
		point = new Point();
		point.setLatitude(latitude);
		point.setLongitude(longitude);
		PoiUtils.fixPoi(point, Constants.MAP_BAIDU);
		fence.setPoint(point);
		fence.setRadius(500d);
		fence.setId(UUID.randomUUID().toString());
		
		try {
			kdTree.insert(fence.toArray(), fence.getId());
		} catch (KeySizeException | KeyDuplicateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public KDTree<String> getKdTree() {
		return kdTree;
	}
	
}
