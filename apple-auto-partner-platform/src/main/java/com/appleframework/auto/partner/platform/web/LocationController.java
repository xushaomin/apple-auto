package com.appleframework.auto.partner.platform.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appleframework.auto.bean.location.Location;
import com.appleframework.auto.service.location.LocationSearchService;
import com.appleframework.web.springmvc.controller.BaseController;

/**
 * 围栏
 */
@Controller
@RequestMapping("/location")
public class LocationController extends BaseController {

	@Resource
	private LocationSearchService locationSearchService;

	private String viewModel = "location/";
	
	@RequestMapping(value = "/show")
	public String show(Model model, String account, Long startTime, Long endTime, Integer mapType) {
		List<Location> list = null;
		if(null == mapType)
			mapType = 5;
		try {			
			list = locationSearchService.search(account, startTime, endTime, mapType);
			model.addAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("account", account);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("mapType", mapType);
		return viewModel + "show";
	}
	
}