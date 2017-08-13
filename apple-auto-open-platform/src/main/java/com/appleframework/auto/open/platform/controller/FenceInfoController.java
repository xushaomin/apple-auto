/*package com.appleframework.auto.open.platform.controller;

import java.util.UUID;

import javax.annotation.Resource;

import com.appleframework.auto.bean.fence.CircleFence;
import com.appleframework.auto.bean.fence.Point;
import com.appleframework.auto.open.platform.request.CircleFenceCreateRequest;
import com.appleframework.auto.open.platform.response.IsSuccessResponse;
import com.appleframework.auto.open.platform.response.ServiceExceptionResponse;
import com.appleframework.auto.open.platform.response.ServiceUnavailableErrorResponse;
import com.appleframework.auto.service.fence.FenceInfoService;
import com.appleframework.exception.ServiceException;
import com.appleframework.rop.annotation.NeedInSessionType;
import com.appleframework.rop.annotation.ServiceMethod;
import com.appleframework.rop.annotation.ServiceMethodBean;

*//**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @author 徐少敏
 * @version 1.0
 *//*
@ServiceMethodBean(version = "1.0", group = "apple.auto", groupTitle = "公共模块")
public class FenceInfoController {

	@Resource
	private FenceInfoService fenceInfoService;
	
	@ServiceMethod(method = "apple.auto.fence.create.circle", needInSession = NeedInSessionType.NO, title = "围栏创建")
	public Object locationSearch(CircleFenceCreateRequest request) {
		IsSuccessResponse response = new IsSuccessResponse();
		Double latitude = request.getLatitude();
		Double longitude = request.getLongitude();
		Double radius = request.getRadius();
		try {
			CircleFence fence = new CircleFence();
			Point point = new Point();
			point.setLatitude(latitude);
			point.setLongitude(longitude);
			fence.setPoint(point);
			fence.setRadius(radius);
			fence.setId(UUID.randomUUID().toString());
			
			fenceInfoService.create(fence);
			response.setSuccess(true);
		} catch(ServiceException e) {
			return new ServiceExceptionResponse(request.getRopRequestContext(), e);
		} catch (Exception e) {
			return new ServiceUnavailableErrorResponse(request.getRopRequestContext());
		}
		return response;
	}
	

}*/