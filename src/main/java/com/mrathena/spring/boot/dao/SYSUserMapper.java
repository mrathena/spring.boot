package com.mrathena.spring.boot.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mrathena.spring.boot.entity.SYSResource;
import com.mrathena.spring.boot.entity.SYSUser;

@Mapper
public interface SYSUserMapper {

	/** sys_user */
	int insertSelective(SYSUser record);

	/** sys_user */
	SYSUser selectByPrimaryKey(Long id);

	/** sys_user */
	int updateByPrimaryKeySelective(SYSUser record);
	
	SYSUser selectByUsername(String username);
	
	List<SYSUser> selectAll();
	
	int deleteByIds(@Param("ids") Long[] ids);
	int enableByIds(@Param("ids") Long[] ids);
	int disableByIds(@Param("ids") Long[] ids);
	// 这个用map的话会更简洁,不需要指定参数名
	int authorizeByIds(@Param("ids") Long[] ids, @Param("roleIds") String roleIds, @Param("includeIds") String includeIds, @Param("excludeIds") String excludeIds);
	
	Set<String> selectRolesByUsername(String username);
	Set<String> selectPermissionsByUsername(String username);
	List<SYSResource> selectResourcesByUsername(String username);

}