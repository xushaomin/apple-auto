package com.appleframework.auto.fence.calculate;

import java.util.Map;
import java.util.Properties;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;

import com.appleframework.auto.bean.location.Location;

/**
 * 简单的按照空格进行切分后，发射到下一阶段bolt
 */
public class FenceNotifyBolt extends BaseFenceNotifyBolt {

	private static final long serialVersionUID = 2029919818959082300L;

	@Override
	@SuppressWarnings("rawtypes")
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		// this.outputCollector = outputCollector;
	}

	public FenceNotifyBolt(Properties props) {
		this.props = props;
	}

	@Override
	public void execute(Tuple tuple) {
		String account = tuple.getString(0);
		Location location = (Location) tuple.getValue(1);
		String fenceId = tuple.getString(2);
		Integer type = tuple.getInteger(3);
		this.notify(account, location, fenceId, type);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		// 声明输出的filed
		//outputFieldsDeclarer.declare(new Fields("account", "location", "fenceId", "type"));
	}

}
