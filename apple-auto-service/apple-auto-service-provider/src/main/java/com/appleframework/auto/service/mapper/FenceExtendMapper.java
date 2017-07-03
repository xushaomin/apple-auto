package com.appleframework.auto.service.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;

@Repository
public interface FenceExtendMapper {

	List<FenceEntityWithBLOBs> selectAll();

}