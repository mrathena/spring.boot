package com.mrathena.spring.boot.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrathena.spring.boot.dao.SYSRoleMapper;
import com.mrathena.spring.boot.entity.SYSRole;

@Service("SYSRoleService")
public class SYSRoleServiceImpl implements SYSRoleService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SYSRoleMapper roleDao;
	
	@Override
	public boolean insertRole(SYSRole role) throws Exception {
		SYSRole oldRole = getRoleByRolename(role.getName());
		if (oldRole != null) {
			throw new Exception("该资源名已被使用");
		}
		role.withId(null).withAvailable(false);
		return roleDao.insertSelective(role) != 0;
	}

	@Override
	public boolean deleteRolesByRoleIds(Long... roleIds) {
		return roleDao.deleteByIds(roleIds) != 0;
	}

	@Override
	public boolean enableRolesByRoleIds(Long... roleIds) {
		return roleDao.enableByIds(roleIds) != 0;
	}

	@Override
	public boolean disableRolesByRoleIds(Long... roleIds) {
		return roleDao.disableByIds(roleIds) != 0;
	}

	@Override
	public SYSRole getRoleByRoleId(Long roleId) {
		return roleDao.selectByPrimaryKey(roleId);
	}

	@Override
	public List<SYSRole> getAllRoles() {
		return roleDao.selectAll();
	}

	@Override
	public List<SYSRole> getAvaliableRoles() {
		return roleDao.selectAvaliable();
	}

	@Override
	public boolean updateRoleByRoleId(SYSRole role) {
		return roleDao.updateByPrimaryKeySelective(role) != 0;
	}

	@Override
	public SYSRole getRoleByRolename(String rolename) {
		return roleDao.selectByRolename(rolename);
	}

}