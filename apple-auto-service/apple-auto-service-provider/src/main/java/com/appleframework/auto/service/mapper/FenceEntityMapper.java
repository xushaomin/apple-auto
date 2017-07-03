package com.appleframework.auto.service.mapper;

import org.springframework.stereotype.Repository;

import com.appleframework.auto.entity.fence.FenceEntity;
import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;

@Repository
public interface FenceEntityMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(FenceEntityWithBLOBs record);

    int insertSelective(FenceEntityWithBLOBs record);

    FenceEntityWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FenceEntityWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(FenceEntityWithBLOBs record);

    int updateByPrimaryKey(FenceEntity record);
}