package com.mrathena.spring.boot.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrathena.spring.boot.dao.SYSUserMapper;
import com.mrathena.spring.boot.entity.SYSResource;
import com.mrathena.spring.boot.entity.SYSUser;
import com.mrathena.spring.boot.tool.SessionKit;

@Service("SYSUserService")
public class SYSUserServiceImpl implements SYSUserService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SYSUserMapper userDao;

	@Override
	public boolean insertUser(SYSUser user) throws Exception {
		// 并发类型 select+insert,可用悲观锁(select ... for update)
		// 并发类型 select+update/deleter,可用悲观锁/乐观锁
		// 1.用悲观锁防止并发(需要在where条件中明确指定唯一主键值才是行锁,不然就是表锁.这里username不是主键,不能用悲观锁)
		// 2.用乐观锁处理并发(需要更新时间做验证是否已过期.貌似只适用于update,delete,因为insert没有where条件)
		SYSUser oldUser = getUserByUsername(user.getUsername());
		if (oldUser != null) {
			throw new Exception("该用户名已被注册");
		}
		// id是自增的
		user.withId(null).encryptPassword().withCreateTime(new Date()).withAvailable(false);
		return userDao.insertSelective(user) != 0;
	}

	@Override
	public boolean deleteUsersByUserIds(Long... userIds) throws Exception {
		Long currentUserId = SessionKit.getUserIdOfCurrentSYSUser();
		for (Long id : userIds) {
			if (id.equals(currentUserId)) {
				throw new Exception("不允许操作当前登录账号");
			}
		}
		return userDao.deleteByIds(userIds) != 0;
	}

	@Override
	public boolean enableUsersByUserIds(Long... userIds) throws Exception {
		Long currentUserId = SessionKit.getUserIdOfCurrentSYSUser();
		for (Long id : userIds) {
			if (id.equals(currentUserId)) {
				throw new Exception("不允许操作当前登录账号");
			}
		}
		return userDao.enableByIds(userIds) != 0;
	}

	@Override
	public boolean disableUsersByUserIds(Long... userIds) throws Exception {
		Long currentUserId = SessionKit.getUserIdOfCurrentSYSUser();
		for (Long id : userIds) {
			if (id.equals(currentUserId)) {
				throw new Exception("不允许操作当前登录账号");
			}
		}
		return userDao.disableByIds(userIds) != 0;
	}

	@Override
	public boolean authorizeUsersByUserIds(Long[] userIds, String roleIds, String includeIds, String excludeIds) throws Exception {
		return userDao.authorizeByIds(userIds, roleIds, includeIds, excludeIds) != 0;
	}

	@Override
	public boolean updatePasswordByUserId(Long userId, String newPassword) throws Exception {
		SYSUser user = new SYSUser().withId(userId).withPassword(newPassword).encryptPassword();
		return userDao.updateByPrimaryKeySelective(user) != 0;
	}

	@Override
	public SYSUser getUserByUserId(Long userId) {
		return userDao.selectByPrimaryKey(userId);
	}

	@Override
	public SYSUser getUserByUsername(String username) {
		return userDao.selectByUsername(username);
	}

	@Override
	public List<SYSUser> getAllUsers() {
		return userDao.selectAll();
	}

	@Override
	public Set<String> getUserRolesByUsername(String username) {
		return userDao.selectRolesByUsername(username);
	}

	@Override
	public Set<String> getUserPermissionsByUsername(String username) {
		return userDao.selectPermissionsByUsername(username);
	}

	@Override
	public List<SYSResource> getResourcesByUsername(String username) {
		return userDao.selectResourcesByUsername(username);
	}

}