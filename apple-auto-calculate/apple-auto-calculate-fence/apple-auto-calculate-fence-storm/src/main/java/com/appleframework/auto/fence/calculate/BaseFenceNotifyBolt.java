package com.appleframework.auto.fence.calculate;

import java.util.Properties;

import org.apache.storm.topology.base.BaseRichBolt;

import com.appleframework.auto.bean.fence.FenceResult;
import com.appleframework.auto.bean.location.Location;
import com.appleframework.jms.core.utils.ByteUtils;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

/**
 * 简单的按照空格进行切分后，发射到下一阶段bolt
 */
@SuppressWarnings("deprecation")
public abstract class BaseFenceNotifyBolt extends BaseRichBolt {

	//private static final Logger logger = LoggerFactory.getLogger(BaseFenceNotifyBolt.class);

	private static final long serialVersionUID = 1L;

	protected Properties props;
	
	private Producer<String, byte[]> producer;
	
	protected void init() {
        props.put("serializer.class", "kafka.serializer.DefaultEncoder");
        props.put("producer.type", "async");
        props.put("request.required.acks", "1");
        props.put("partitioner.class", "com.appleframework.jms.kafka.partitions.RandomPartitioner");
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        ProducerConfig producerConfig = new ProducerConfig(props);
        producer = new Producer<String, byte[]>(producerConfig);
	}

	public void notify(String account, Location location, String fenceId, Integer type) {
		try {
			FenceResult result = new FenceResult();
			result.setAccount(account);
			result.setFenceId(fenceId);
			result.setLocation(location);
			result.setType(type);
			this.send(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void send(FenceResult result) {
		String topic = props.getProperty("producer.topic");
		KeyedMessage<String, byte[]> producerData 
			= new KeyedMessage<String, byte[]>(topic, String.valueOf(-1), ByteUtils.toBytes(result));
		producer.send(producerData);
	}
	
	protected void close() {
		producer.close();
	}
}
