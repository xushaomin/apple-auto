package com.appleframework.auto.calculate.fence.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.calculate.fence.model.FenceLocation;
import com.appleframework.auto.calculate.fence.service.FenceCalculateService;
import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.auto.calculate.fence.service.FenceLocationService;
import com.appleframework.structure.kdtree.KDTree;

@Service
public class FenceCalculateServiceImpl implements FenceCalculateService {

	protected final static Logger logger = Logger.getLogger(FenceCalculateServiceImpl.class);

	@Resource
	private FenceInfoService fenceInfoService;

	@Resource
	private FenceLocationService fenceLocationService;
	
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
	
	private void noExistsFence(Location location) throws Exception {
		String account = location.getAccount();
		Map<String, FenceLocation> fenceLocationMap = fenceLocationService.get(account);
		if (null != fenceLocationMap && fenceLocationMap.size() > 0) {
			for (String fenceId : fenceLocationMap.keySet()) {
				this.noExistsFence(fenceLocationMap, fenceId, account, location);
			}
		}
		fenceLocationService.put(account, fenceLocationMap);
	}

	private void noExistsFence(Map<String, FenceLocation> fenceLocationMap, Set<String> existFenceSet, Location location) {
		String account = location.getAccount();
		for (String key : fenceLocationMap.keySet()) {  
			if(!existFenceSet.contains(key)) {
				this.noExistsFence(fenceLocationMap, key, account, location);
			}			  
		}
	}
	
	private void noExistsFence(Map<String, FenceLocation> fenceLocationMap, String fenceId, String account, Location location) {
		FenceLocation fenceLocation = fenceLocationMap.get(fenceId);
		if (null != fenceLocation) {
			int outCnt = fenceLocation.addOutCount();
			if (outCnt == 2) {
				fenceLocationMap.remove(fenceId);
				logger.warn(
						"\t退出围栏:" + fenceId + ":" + account + "\t退出时间:" + format.format(new Date(location.getTime()))
								+ "\tlat:" + location.getLatitude() + " \tlng:" + location.getLongitude());
			}
		}
	}

	public void existsFence(Set<String> fenceIdSet, Location location) {
		String account = location.getAccount();
		// key是FenceID, Value是FenceID与进入围栏的Location点信息
		Map<String, FenceLocation> fenceLocationMap = fenceLocationService.get(account);
		// 存在最新轨迹点
		if (null != fenceLocationMap && fenceLocationMap.size() > 0) {
			for (String fenceId : fenceIdSet) {
				FenceLocation fenceLocation = fenceLocationMap.get(fenceId);
				if (null != fenceLocation) {
					int inCnt = fenceLocation.addInCount();
					if (inCnt == 2) {
						fenceLocationMap.put(fenceId, fenceLocation);
						logger.warn("\t进入围栏:" + fenceId + ":" + account + "\t进入时间:"
								+ format.format(new Date(location.getTime())) + "\tlat:" + location.getLatitude()
								+ " \tlng:" + location.getLongitude());
					}
				} else {
					fenceLocationMap.put(fenceId, FenceLocation.create(location));
				}
			}
			this.noExistsFence(fenceLocationMap, fenceIdSet, location);
		} else {
			fenceLocationMap = new HashMap<String, FenceLocation>();
			// 写入围栏列表
			for (String fenceId : fenceIdSet) {
				fenceLocationMap.put(fenceId, FenceLocation.create(location));
			}
		}
		// 刷新内存
		fenceLocationService.put(account, fenceLocationMap);
	}
}
