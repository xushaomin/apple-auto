package com.appleframework.auto.open.platform.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.rop.session.Session;
import com.appleframework.rop.session.SessionManager;

public class SampleSessionManager implements SessionManager{
	
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private final static Map<String, Session> sessionCache = new ConcurrentHashMap<String, Session>(128, 0.75f, 32);
    

    @Override
    public void addSession(String sessionId, Session session) {
        sessionCache.put(sessionId, session);
    }

    @Override
    public Session getSession(String sessionId) {
        return sessionCache.get(sessionId);
    }

    @Override
    public void removeSession(String sessionId) {
        sessionCache.remove(sessionId);
    }

	@Override
	public Session createSession(Map<String, Object> arg0) {
		return null;
	}
}