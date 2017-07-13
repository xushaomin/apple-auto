package com.appleframework.auto.calculate.fence.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.calculate.fence.model.FenceLocation;
import com.appleframework.config.core.PropertyConfigurer;

public abstract class FenceInoutService {

	protected final static Logger logger = Logger.getLogger(FenceInoutService.class);

	private Map<String, Map<String, FenceLocation>> fenceLocationMapMap = new ConcurrentHashMap<>();

	@Resource
	protected FenceNotifyService fenceNotifyService;
	
	private String fenceLocationPath = PropertyConfigurer.getValue("fence.location.map.path", "/work/data/fence/location/map.db");

	public void noExistsFence(Location location) {
		String account = location.getAccount();
		Map<String, FenceLocation> fenceLocationMap = this.get(account);
		Iterator<Map.Entry<String, FenceLocation>> it = fenceLocationMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, FenceLocation> entry = it.next();
			String fenceId = entry.getKey();
			FenceLocation fenceLocation = entry.getValue();
			int outCnt = fenceLocation.addOutCount();
			if (outCnt == 2) {
				it.remove();
				fenceNotifyService.notify(account, location, fenceId, 2);
			}

		}
		put(account, fenceLocationMap);
	}

	private void noExistsFence(Map<String, FenceLocation> fenceLocationMap, Set<String> existFenceSet, Location location) {
		String account = location.getAccount();
		Iterator<Map.Entry<String, FenceLocation>> it = fenceLocationMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, FenceLocation> entry = it.next();
			String fenceId = entry.getKey();

			if (!existFenceSet.contains(fenceId)) {
				FenceLocation fenceLocation = entry.getValue();
				int outCnt = fenceLocation.addOutCount();
				if (outCnt == 2) {
					it.remove();
					fenceNotifyService.notify(account, location, fenceId, 2);
				}
			}

		}
	}

	public void existsFence(Set<String> fenceIdSet, Location location) {
		String account = location.getAccount();
		// key是FenceID, Value是FenceID与进入围栏的Location点信息
		Map<String, FenceLocation> fenceLocationMap = this.get(account);
		// 存在最新轨迹点
		if (null != fenceLocationMap && fenceLocationMap.size() > 0) {
			for (String fenceId : fenceIdSet) {
				FenceLocation fenceLocation = fenceLocationMap.get(fenceId);
				if (null != fenceLocation) {
					int inCnt = fenceLocation.addInCount();
					if (inCnt == 2) {
						fenceLocationMap.put(fenceId, fenceLocation);
						fenceNotifyService.notify(account, location, fenceId, 1);
					}
				} else {
					fenceLocationMap.put(fenceId, FenceLocation.create(location));
				}
			}
			this.noExistsFence(fenceLocationMap, fenceIdSet, location);
		} else {
			fenceLocationMap = new ConcurrentHashMap<String, FenceLocation>();
			// 写入围栏列表
			for (String fenceId : fenceIdSet) {
				fenceLocationMap.put(fenceId, FenceLocation.create(location));
			}
		}
		// 刷新内存
		this.put(account, fenceLocationMap);
	}

	private void put(String account, Map<String, FenceLocation> fenceLocationMap) {
		if (null == fenceLocationMap) {
			fenceLocationMap = new ConcurrentHashMap<>();
			fenceLocationMapMap.put(account, fenceLocationMap);
		} else {
			fenceLocationMapMap.put(account, fenceLocationMap);
		}
	}

	private Map<String, FenceLocation> get(String account) {
		Map<String, FenceLocation> fenceLocationMap = fenceLocationMapMap.get(account);
		if (null == fenceLocationMap) {
			fenceLocationMap = new ConcurrentHashMap<>();
		}
		return fenceLocationMap;
	}
	
	@PreDestroy
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void writeToDisk() {
		try {
			DB db = DBMaker.fileDB(fenceLocationPath).make();
			ConcurrentMap map = db.hashMap("fenceLocation").createOrOpen();
			map.clear();
			map.putAll(fenceLocationMapMap);
			db.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@PostConstruct
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void readFromDisk() {
		try {
			DB db = DBMaker.fileDB(fenceLocationPath).make();
			ConcurrentMap map = db.hashMap("fenceLocation").createOrOpen();
			if(map.size() > 0) {
				fenceLocationMapMap.putAll(map);
			}
			db.close();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
