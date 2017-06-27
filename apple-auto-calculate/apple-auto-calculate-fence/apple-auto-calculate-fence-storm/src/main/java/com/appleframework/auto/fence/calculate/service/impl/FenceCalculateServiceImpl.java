package com.appleframework.auto.fence.calculate.service.impl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.model.FenceLocation;
import com.appleframework.auto.fence.calculate.service.FenceCalculateService;
import com.appleframework.auto.fence.calculate.service.FenceInfoService;
import com.appleframework.auto.fence.calculate.service.FenceLocationService;
import com.appleframework.structure.kdtree.KDTree;

public class FenceCalculateServiceImpl implements FenceCalculateService, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(FenceCalculateServiceImpl.class);

	@Resource
	private FenceInfoService fenceInfoService;

	@Resource
	private FenceLocationService fenceLocationService;
	

	public void setFenceInfoService(FenceInfoService fenceInfoService) {
		this.fenceInfoService = fenceInfoService;
	}

	public void setFenceLocationService(FenceLocationService fenceLocationService) {
		this.fenceLocationService = fenceLocationService;
	}

	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  

	public void calculate(Location location) {
		KDTree<String> tree = fenceInfoService.getKdTree();
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		// 获取围栏信息
		double[] T = { latitude, longitude, 0 };
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
	
	public Set<String> calculate2(Location location) {
		KDTree<String> tree = fenceInfoService.getKdTree();
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		// 获取围栏信息
		double[] T = { latitude, longitude, 0 };
		try {
			return tree.nearestEuclideanReturnSet(T);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void noExistsFence(Location location) {
		String account = location.getAccount();
		Map<String, FenceLocation> fenceLocationMap = fenceLocationService.get(account);
		if (null != fenceLocationMap && fenceLocationMap.size() > 0) {
			for (String fenceId : fenceLocationMap.keySet()) {
				this.noExistsFence(fenceLocationMap, fenceId, account, location);
			}
			fenceLocationService.update(account, fenceLocationMap);
		}
	}

	private void noExistsFence(Set<String> noExistFenceSet, Location location) {
		String account = location.getAccount();
		Map<String, FenceLocation> fenceLocationMap = fenceLocationService.get(account);
		if (null != fenceLocationMap && fenceLocationMap.size() > 0 && noExistFenceSet.size() > 0) {
			for (String fenceId : noExistFenceSet) {
				this.noExistsFence(fenceLocationMap, fenceId, account, location);
			}
			fenceLocationService.update(account, fenceLocationMap);
		}
	}
	
	private void noExistsFence(Map<String, FenceLocation> fenceLocationMap, String fenceId, String account, Location location) {
		FenceLocation locationCount = fenceLocationMap.get(fenceId);
		if(null != locationCount) {
			int outCnt = locationCount.addOutCount();
			if (outCnt == 1) {
				fenceLocationMap.put(fenceId, locationCount);
			} else if (outCnt == 2) {
				// sendEventToMQ(location, fenceId, "2");
				// sendRecordToMQ(locationCount.getInLocation(), location, fenceId);
				fenceLocationMap.remove(fenceId);
				logger.warn("\t退出围栏:" + fenceId + ":" + account + " \t退出时间:" + format.format(new Date(location.getTime())) + " " + "\tlat:"
						+ location.getLatitude() + " \tlng:" + location.getLongitude());
			} else {
				
			}
		}
	
	}

	public void existsFence(Set<String> fenceIdSet, Location location) {
		String account = location.getAccount();
		// key是FenceID, Value是FenceID与进入围栏的Location点信息
		Map<String, FenceLocation> fenceLocationMap = fenceLocationService.get(account);
		// 存在最新轨迹点
		if (null != fenceLocationMap && fenceLocationMap.size() > 0) {
			Set<String> noExistFenceSet = fenceLocationMap.keySet();
			for (String fenceId : fenceIdSet) {
				FenceLocation locationCount = fenceLocationMap.get(fenceId);
				if(null != locationCount) {
					int inCnt = locationCount.addInCount();
					if (inCnt == 2) {
						// 发送进入围栏记录
						// sendEventToMQ(location, fenceId, "1");
						fenceLocationMap.put(fenceId, locationCount);
						logger.warn("\t进入围栏:" + fenceId + ":" + account + "\t进入时间:" + format.format(new Date(location.getTime())) + " " + "\tlat:"
								+ location.getLatitude() + " \tlng:" + location.getLongitude());
					} else {
						
					}
				} else {
					locationCount = FenceLocation.create(location);
					fenceLocationMap.put(account, locationCount);
				}
				noExistFenceSet.remove(fenceId);
			}
			this.noExistsFence(noExistFenceSet, location);
		} else {
			fenceLocationMap = new HashMap<String, FenceLocation>();
			// 写入围栏列表
			for (String fenceId : fenceIdSet) {
				fenceLocationMap.put(fenceId, FenceLocation.create(location));
			}
		}
		// 刷新内存
		fenceLocationService.update(account, fenceLocationMap);
	}
}
