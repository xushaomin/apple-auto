package com.appleframework.auto.service.journey;

import com.appleframework.model.page.Pagination;

public interface JourneySearchService {

	public Pagination search(Pagination page, String account, long startTime);
}
