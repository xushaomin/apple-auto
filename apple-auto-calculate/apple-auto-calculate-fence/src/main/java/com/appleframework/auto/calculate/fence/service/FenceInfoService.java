package com.appleframework.auto.calculate.fence.service;

import com.appleframework.structure.kdtree.KDTree;

public interface FenceInfoService {

	public KDTree<String> getFenceInfo(String account);
}
