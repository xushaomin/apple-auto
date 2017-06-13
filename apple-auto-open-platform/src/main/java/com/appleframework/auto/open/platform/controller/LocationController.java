package com.appleframework.auto.open.platform.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import com.appleframework.auto.open.platform.request.LocationListRequest;
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
@ServiceMethodBean(version = "1.0", group = "apple.auto", groupTitle = "公共模块")
public class LocationController {

	@Resource
	private LocationSearchService locationSearchService;
	
	@ServiceMethod(method = "apple.auto.location.search", needInSession = NeedInSessionType.NO, title = "轨迹回放")
	public Object locationSearch(LocationSearchRequest request) {
		LocationSearchResponse response = new LocationSearchResponse();
		String account = request.getAccount();
		long startTime = request.getStartTime();
		long endTime = request.getEndTime();
		int mapType = request.getMapType();
		try {			
			List<Location> list = locationSearchService.search(account, startTime, endTime, mapType);
			response.setList(list);
		} catch(ServiceException e) {
			return new ServiceExceptionResponse(request.getRopRequestContext(), e);
		} catch (Exception e) {
			return new ServiceUnavailableErrorResponse(request.getRopRequestContext());
		}
		return response;
	}
	
	@ServiceMethod(method = "apple.auto.location.list", needInSession = NeedInSessionType.NO, title = "轨迹回放")
	public Object locationList(LocationListRequest request) {
		LocationSearchResponse response = new LocationSearchResponse();
		String account = request.getAccount();
		String startTime = request.getStartTime();
		String endTime = request.getEndTime();
		int mapType = request.getMapType();
		try {
	        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	        long startTimeLong = dateFormat.parse(startTime).getTime();
	        long endTimeLong = dateFormat.parse(endTime).getTime();
			List<Location> list = locationSearchService.search(account, startTimeLong, endTimeLong, mapType);
			response.setList(list);
		} catch(ServiceException e) {
			return new ServiceExceptionResponse(request.getRopRequestContext(), e);
		} catch (Exception e) {
			return new ServiceUnavailableErrorResponse(request.getRopRequestContext());
		}
		return response;
	}

}