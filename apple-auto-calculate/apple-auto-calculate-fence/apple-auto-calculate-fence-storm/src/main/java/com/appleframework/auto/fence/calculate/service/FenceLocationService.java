package com.appleframework.auto.fence.calculate.service;

import java.util.Map;

import com.appleframework.auto.fence.calculate.model.FenceLocation;

public interface FenceLocationService {

	public Map<String, FenceLocation> get(String key);

	public void update(String key, Map<String, FenceLocation> map);

	public void delete(String key);
}
