package com.appleframework.auto.fence.calculate;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import com.appleframework.auto.bean.location.Location;

/**
 * 简单的按照空格进行切分后，发射到下一阶段bolt
 */
public class FenceCalculateCircleBolt extends BaseFenceCalculateCircleBolt {

	private static final long serialVersionUID = 1L;	

	private OutputCollector outputCollector;

	public FenceCalculateCircleBolt(Properties props) {
		 this.props = props;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
		this.init();
	}

	@Override
	public void execute(Tuple tuple) {
		String account = tuple.getString(0);
		Location location = (Location) tuple.getValue(1);
		// System.out.println("线程"+Thread.currentThread().getName());
		// 简单的按照空格进行切分后，发射到下一阶段bolt
		// LOGGER.info("FenceCalculateBolt:" + location.toString());
		Set<String> fenceSet = this.calculate2(location);
		outputCollector.emit(new Values(account, location, fenceSet));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("account", "location", "fenceSet"));
	}
}
