package com.appleframework.auto.calculate.fence.service;

import java.util.Map;

import com.appleframework.auto.calculate.fence.model.FenceLocation;

public interface FenceLocationService {

	public Map<String, FenceLocation> get(String key);

	public void update(String key, Map<String, FenceLocation> map);

	public void delete(String key);
}
