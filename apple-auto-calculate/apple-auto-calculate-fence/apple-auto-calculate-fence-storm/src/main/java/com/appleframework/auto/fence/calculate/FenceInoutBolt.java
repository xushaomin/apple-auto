package com.appleframework.auto.fence.calculate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.model.FenceLocation;

/**
 * 简单的按照空格进行切分后，发射到下一阶段bolt Created by QinDongLiang on 2016/8/31.
 */
public class FenceInoutBolt extends BaseRichBolt {

	private static final long serialVersionUID = 2029919818959082300L;
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaSpout.class);

	private OutputCollector outputCollector;
	
	private static Map<String, Map<String, FenceLocation>> fenceLocationMapMap = new ConcurrentHashMap<>();

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Tuple tuple) {
		String account = tuple.getString(0);
		Location location = (Location) tuple.getValue(1);
		Set<String> fenceSet = (Set<String>) tuple.getValue(2);
		// System.out.println("线程"+Thread.currentThread().getName());
		// 简单的按照空格进行切分后，发射到下一阶段bolt
		//LOGGER.info("FenceInoutBolt:location:" + location.toString());
		//LOGGER.info("FenceInoutBolt:fenceSet:" + fenceSet.size());
		
		if (fenceSet == null || fenceSet.size() == 0) {
			// 不存在围栏信息，全部退出
			this.noExistsFence(location);
		} else {
			// 存在围栏记录
			this.existsFence(fenceSet, location);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		// 声明输出的filed
		outputFieldsDeclarer.declare(new Fields("location", "fenceSet"));
	}
	
	public void noExistsFence(Location location) {
		String account = location.getAccount();
		Map<String, FenceLocation> fenceLocationMap = fenceLocationMapMap.get(account);
		if (null != fenceLocationMap && fenceLocationMap.size() > 0) {
			for (String fenceId : fenceLocationMap.keySet()) {
				this.noExistsFence(fenceLocationMap, fenceId, account, location);
			}
			fenceLocationMapMap.put(account, fenceLocationMap);
		}
	}

	private void noExistsFence(Set<String> noExistFenceSet, Location location) {
		String account = location.getAccount();
		Map<String, FenceLocation> fenceLocationMap = fenceLocationMapMap.get(account);
		if (null != fenceLocationMap && fenceLocationMap.size() > 0 && noExistFenceSet.size() > 0) {
			for (String fenceId : noExistFenceSet) {
				this.noExistsFence(fenceLocationMap, fenceId, account, location);
			}
			fenceLocationMapMap.put(account, fenceLocationMap);
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
		Map<String, FenceLocation> fenceLocationMap = fenceLocationMapMap.get(account);
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
		fenceLocationMapMap.put(account, fenceLocationMap);
	}
}
