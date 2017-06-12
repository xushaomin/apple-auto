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
package com.appleframework.auto.acquisition.socket.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.appleframework.cim.sdk.server.constant.CIMConstant;
import com.appleframework.cim.sdk.server.session.AbstractCIMSession;
import com.appleframework.cim.sdk.server.session.CIMSession;
import com.appleframework.cim.sdk.server.session.SessionManager;
import com.hazelcast.core.HazelcastInstance;

import io.netty.channel.Channel;

/** 
 * 集群 session管理实现示例， 各位可以自行实现 AbstractSessionManager接口来实现自己的 session管理
 *服务器集群时 须要将CIMSession 信息存入数据库或者nosql 等 第三方存储空间中，便于所有服务器都可以访问
 */
public class ClusterSessionManager implements SessionManager {
	
	private static HashMap<String, Channel> sessions = new HashMap<String, Channel>();
	
	private HazelcastInstance instance;
	
	public void setInstance(HazelcastInstance instance) {
		this.instance = instance;
	}
	
	private Map<String, AbstractCIMSession> getSessionMap() {
		Map<String, AbstractCIMSession> sessions = instance.getMap("Session");
		return sessions;
	}

	private static final AtomicInteger connectionsCounter = new AtomicInteger(0);

	public void add(AbstractCIMSession abstractSession) {
		CIMSession session = (CIMSession) abstractSession;
		if (session != null) {
			session.setAttribute(CIMConstant.SESSION_KEY, session.getAccount());
			getSessionMap().put(session.getAccount(), session);
			sessions.put(session.getNid(), session.getSession());
			connectionsCounter.incrementAndGet();
		}
	}

	public AbstractCIMSession get(String account) {
		AbstractCIMSession session = getSessionMap().get(account);
		if(null == session)
			return null;
		session.setSession(sessions.get(session.getNid()));
		return session;
	}

	public List<AbstractCIMSession> queryAll() {
		List<AbstractCIMSession> list = new ArrayList<AbstractCIMSession>();
		list.addAll(getSessionMap().values());
		return list;
	}

	public void remove(AbstractCIMSession session) {
		sessions.remove(session.getNid());
		getSessionMap().remove(session.getAttribute(CIMConstant.SESSION_KEY));
	}

	public void remove(String account) {
		AbstractCIMSession session = getSessionMap().get(account);
		sessions.remove(session.getNid());
		getSessionMap().remove(account);
	}

	public boolean containsCIMSession(String account) {
		return getSessionMap().containsKey(account);
	}

	@Override
	public void update(AbstractCIMSession abstractSession) {
		CIMSession session = (CIMSession) abstractSession;
		sessions.put(session.getNid(), session.getSession());
		getSessionMap().put(session.getAccount(), session);
	}

}
