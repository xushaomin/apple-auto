/**
* 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.appleframework.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package com.appleframework.auto.acquisition.socket.handler;

import org.apache.log4j.Logger;

import com.appleframework.auto.bean.location.Journey;
import com.appleframework.cim.sdk.server.constant.CIMConstant;
import com.appleframework.cim.sdk.server.handler.CIMRequestHandler;
import com.appleframework.cim.sdk.server.model.ReplyBody;
import com.appleframework.cim.sdk.server.model.SentBody;
import com.appleframework.cim.sdk.server.session.AbstractCIMSession;
import com.appleframework.cim.util.ContextHolder;
import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.jms.core.producer.MessageProducer3;

/**
 * 行程上报
 * 
 */
public class JourneyHandler implements CIMRequestHandler {

	protected final Logger logger = Logger.getLogger(JourneyHandler.class);

	private static String topic = PropertyConfigurer.getString("producer.topic.journey", "journey");

	private MessageProducer3 messageProducer3 = ContextHolder.getBean(MessageProducer3.class);

	public ReplyBody process(AbstractCIMSession newSession, SentBody message) {

		ReplyBody reply = new ReplyBody();
		reply.setCode(CIMConstant.ReturnCode.CODE_200);
		try {

			String account = message.get("account");
			String startTime = message.get("startTime");
			String endTime = message.get("endTime");
			String totalTime = message.get("totalTime");

			Long startTimeLong = Long.parseLong(startTime);
			Long endTimeLong = Long.parseLong(endTime);

			Journey journey = new Journey();
			journey.setAccount(account);
			journey.setStartTime(startTimeLong);
			journey.setEndTime(endTimeLong);
			journey.setTotalTime(Integer.parseInt(totalTime));

			if (logger.isInfoEnabled())
				logger.info(journey.toString());

			messageProducer3.sendObject(topic, account, journey);
		} catch (Exception e) {
			reply.setCode(CIMConstant.ReturnCode.CODE_500);
		}
		return reply;
	}
}
