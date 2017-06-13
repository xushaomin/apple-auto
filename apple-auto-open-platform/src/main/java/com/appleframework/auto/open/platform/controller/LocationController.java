package com.appleframework.auto.open.platform.controller;

import java.util.List;

import javax.annotation.Resource;

import com.appleframework.auto.open.platform.request.LocationSearchRequest;
import com.appleframework.auto.open.platform.response.LocationSearchResponse;
import com.appleframework.auto.open.platform.response.ServiceExceptionResponse;
import com.appleframework.auto.open.platform.response.ServiceUnavailableErrorResponse;
import com.appleframework.auto.service.location.LocationSearchService;
import com.appleframework.bean.location.Location;
import com.appleframework.exception.ServiceException;
import com.appleframework.rop.annotation.NeedInSessionType;
import com.appleframework.rop.annotation.ServiceMethod;
import com.appleframework.rop.annotation.ServiceMethodBean;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @author 徐少敏
 * @version 1.0
 */
@ServiceMethodBean(version = "1.0", group = "jz.common", groupTitle = "公共模块")
public class LocationController {

	@Resource
	private LocationSearchService locationSearchService;
	
	@ServiceMethod(method = "apple.auto.location.search", needInSession = NeedInSessionType.NO, title = "获取验证码")
	public Object mobileCaptcha(LocationSearchRequest request) {
		LocationSearchResponse response = new LocationSearchResponse();
		String account = request.getAccount();
		long startTime = request.getStartTime();
		long endTime = request.getEndTime();		
		try {			
			List<Location> list = locationSearchService.search(account, startTime, endTime);
			response.setList(list);
		} catch(ServiceException e) {
			return new ServiceExceptionResponse(request.getRopRequestContext(), e);
		} catch (Exception e) {
			return new ServiceUnavailableErrorResponse(request.getRopRequestContext());
		}
		return response;
	}

}