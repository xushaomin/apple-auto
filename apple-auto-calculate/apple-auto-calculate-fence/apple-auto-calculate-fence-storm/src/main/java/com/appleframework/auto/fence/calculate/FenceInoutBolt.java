package com.appleframework.auto.fence.calculate;

import java.util.Map;
import java.util.Set;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

import com.appleframework.auto.bean.location.Location;

/**
 * 简单的按照空格进行切分后，发射到下一阶段bolt
 */
public class FenceInoutBolt extends BaseFenceInoutBolt {

	private static final long serialVersionUID = 2029919818959082300L;
		
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Tuple tuple) {
		//String account = tuple.getString(0);
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
		outputFieldsDeclarer.declare(new Fields("account", "location", "fenceId", "type"));
	}
	
}
