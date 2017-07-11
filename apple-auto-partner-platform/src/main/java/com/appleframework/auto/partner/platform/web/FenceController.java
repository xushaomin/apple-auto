package com.appleframework.auto.partner.platform.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.appleframework.auto.model.fence.FenceSo;
import com.appleframework.auto.service.fence.FenceEntityService;
import com.appleframework.model.page.Pagination;
import com.appleframework.web.springmvc.controller.BaseController;

/**
 * 围栏
 */
@Controller
@RequestMapping("/fence")
public class FenceController extends BaseController {

	@Resource
	private FenceEntityService fenceEntityService;

	private String viewModel = "fence/";

	@RequestMapping(value = "/list")
	public String list(Model model, Pagination page, FenceSo so) {
		page = fenceEntityService.findPage(page, so);
		model.addAttribute("page", page);
		model.addAttribute("so", so);
		return viewModel + "list";
	}

	@RequestMapping(value = "/add")
	public String add(Model model) {
		return viewModel + "add";
	}

}
