package com.appleframework.auto.fence.calculate;

import java.util.Map;
import java.util.Set;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.service.FenceCalculateService;
import com.appleframework.auto.fence.calculate.service.impl.FenceCalculateServiceImpl;

/**
 * 简单的按照空格进行切分后，发射到下一阶段bolt Created by QinDongLiang on 2016/8/31.
 */
public class FenceCalculateBolt extends BaseRichBolt {

	private static final long serialVersionUID = 2029919818959082300L;

	//private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSpout.class);

	private OutputCollector outputCollector;

	private FenceCalculateService fenceCalculateService;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
		this.outputCollector = outputCollector;
		fenceCalculateService = FenceCalculateServiceImpl.getInstance();
	}

	@Override
	public void execute(Tuple tuple) {
		String account = tuple.getString(0);
		Location location = (Location) tuple.getValue(1);
		// System.out.println("线程"+Thread.currentThread().getName());
		// 简单的按照空格进行切分后，发射到下一阶段bolt
		//LOGGER.info("FenceCalculateBolt:" + location.toString());

		Set<String> fenceSet = fenceCalculateService.calculate2(location);
		outputCollector.emit(new Values(account, location, fenceSet));
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
		outputFieldsDeclarer.declare(new Fields("account", "location", "fenceSet"));
	}
}
