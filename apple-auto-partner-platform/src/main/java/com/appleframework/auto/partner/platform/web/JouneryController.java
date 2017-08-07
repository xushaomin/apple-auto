package com.appleframework.auto.partner.platform.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appleframework.auto.service.journey.JourneySearchService;
import com.appleframework.model.page.Pagination;
import com.appleframework.web.springmvc.controller.BaseController;

/**
 * 围栏
 */
@Controller
@RequestMapping("/journey")
public class JouneryController extends BaseController {

	@Resource
	private JourneySearchService journeySearchService;

	private String viewModel = "journey/";

	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, String account, Long time) {
		page.setPageSize(10);
		if(null == time)
			time = System.currentTimeMillis();
		page = journeySearchService.search(page, account, time);
		model.addAttribute("page", page);
		model.addAttribute("account", account);
		model.addAttribute("time", time);
		return viewModel + "list";
	}
	
}