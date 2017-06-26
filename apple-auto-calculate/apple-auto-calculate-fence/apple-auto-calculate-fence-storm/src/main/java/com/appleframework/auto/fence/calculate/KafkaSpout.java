package com.appleframework.auto.fence.calculate;

import java.util.Map;
import java.util.Properties;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.fence.calculate.utils.ByteUtils;
import com.appleframework.jms.kafka.consumer.BaseMessageConsumer;

import kafka.consumer.ConsumerConfig;

public class KafkaSpout extends BaseMessageConsumer implements IRichSpout {

	private static final long serialVersionUID = -7107773519958260350L;

	private SpoutOutputCollector collector;
	
	private Properties props;

	public KafkaSpout(Properties props) {
		this.props = props;
	}

	@SuppressWarnings("rawtypes")
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;
	}

	
	@Override
	public void processByteMessage(byte[] message) {
		Location value = (Location)ByteUtils.fromByte(message);
		//LOGGER.info("(consumer)==>" + value);
		collector.emit(new Values(value.getAccount(), value));
	}

	public void close() {
		super.destroy();
	}

	public void activate() {
		
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");

		String topic = props.getProperty("consumer.topic");
		Integer partitionsNum = Integer.parseInt(props.getProperty("consumer.partitionsNum", "16"));

		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		
		super.setConsumerConfig(consumerConfig);
		super.setPartitionsNum(partitionsNum);
		super.setTopic(topic);
	
		super.init();
	}

	public void deactivate() {
		super.destroy();
	}

	public void nextTuple() {

	}

	public void ack(Object msgId) {

	}

	public void fail(Object msgId) {

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("account", "location"));
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}

}
