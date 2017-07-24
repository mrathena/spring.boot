package com.mrathena.spring.boot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mrathena.spring.boot.entity.SYSRole;
@Mapper
public interface SYSRoleMapper {

	/** sys_role */
	int insertSelective(SYSRole record);

	/** sys_role */
	SYSRole selectByPrimaryKey(Long id);

	/** sys_role */
	int updateByPrimaryKeySelective(SYSRole record);

	SYSRole selectByRolename(String rolename);
	
	List<SYSRole> selectAll();
	
	List<SYSRole> selectAvaliable();
	
	int deleteByIds(@Param("ids") Long[] ids);
	int enableByIds(@Param("ids") Long[] ids);
	int disableByIds(@Param("ids") Long[] ids);

}