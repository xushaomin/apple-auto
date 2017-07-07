package com.appleframework.auto.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.appleframework.auto.entity.fence.FenceEntity;
import com.appleframework.auto.entity.fence.FenceEntityWithBLOBs;
import com.appleframework.auto.model.fence.FenceSo;
import com.appleframework.model.page.Pagination;

@Repository
public interface FenceExtendMapper {

	List<FenceEntityWithBLOBs> selectAll();

	List<FenceEntity> selectPage(@Param("page") Pagination page, @Param("so") FenceSo so);

}