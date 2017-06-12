package com.appleframework.auto.calculate.fence.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.appleframework.auto.calculate.fence.service.FenceInfoService;
import com.appleframework.structure.kdtree.KDTree;

@Service
public class FenceInfoServiceImpl implements FenceInfoService {

	private KDTree<String> tree;
	
	@PostConstruct
	public void init() {
		tree = new KDTree<String>(3);
		double x = 22.53885893;
		double y = 113.94917554;
		double radius = 200;
		double[] ll = { x, y, radius};

		String value = "111";
		
		try {
			tree.insert(ll, value);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public KDTree<String> getFenceInfo(String account) {
		return tree;
	}
}
