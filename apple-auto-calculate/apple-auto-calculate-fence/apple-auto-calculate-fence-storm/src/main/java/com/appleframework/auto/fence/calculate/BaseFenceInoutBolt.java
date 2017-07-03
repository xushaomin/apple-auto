package com.appleframework.auto.fence.calculate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.model.FenceLocation;

/**
 * 简单的按照空格进行切分后，发射到下一阶段bolt
 */
public abstract class BaseFenceInoutBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(KafkaSpout.class);

	private Map<String, Map<String, FenceLocation>> fenceLocationMapMap = new ConcurrentHashMap<>();

	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	protected OutputCollector outputCollector;

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
				logger.warn(
						"\t退出围栏:" + fenceId + ":" + account + "\t退出时间:" + format.format(new Date(location.getTime()))
								+ "\tlat:" + location.getLatitude() + " \tlng:" + location.getLongitude());
				outputCollector.emit(new Values(account, location, fenceId, 2));
			}

		}
		put(account, fenceLocationMap);
	}

	private void noExistsFence(Map<String, FenceLocation> fenceLocationMap, Set<String> existFenceSet,
			Location location) {
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
					logger.warn("\t退出围栏:" + fenceId + ":" + account + "\t退出时间:"
							+ format.format(new Date(location.getTime())) + "\tlat:" + location.getLatitude()
							+ " \tlng:" + location.getLongitude());
					outputCollector.emit(new Values(account, location, fenceId, 2));
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
						logger.warn("\t进入围栏:" + fenceId + ":" + account + "\t进入时间:"
								+ format.format(new Date(location.getTime())) + "\tlat:" + location.getLatitude()
								+ " \tlng:" + location.getLongitude());
						outputCollector.emit(new Values(account, location, fenceId, 1));
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
}
