package com.mrathena.spring.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mrathena.spring.boot.entity.SYSResource;

@Mapper
public interface SYSResourceMapper {

	/** sys_resource */
	int insertSelective(SYSResource record);

	/** sys_resource */
	int updateByPrimaryKeySelective(SYSResource record);
	
	List<SYSResource> selectAll();
	
	List<SYSResource> selectAvaliable();
	
	int deleteByIds(@Param("ids") Long[] ids);
	int enableByIds(@Param("ids") Long[] ids);
	int disableByIds(@Param("ids") Long[] ids);

}