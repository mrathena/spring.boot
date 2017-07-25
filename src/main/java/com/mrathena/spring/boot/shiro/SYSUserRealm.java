package com.mrathena.spring.boot.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.mrathena.spring.boot.entity.SYSUser;
import com.mrathena.spring.boot.service.SYSUserService;

public class SYSUserRealm extends AuthorizingRealm {

	@Autowired
	private SYSUserService userService;
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 授权
		// 获取登录principal
		String username = (String) principals.getPrimaryPrincipal();
		// 获取该principal的角色与权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(userService.getUserRolesByUsername(username));
		authorizationInfo.setStringPermissions(userService.getUserPermissionsByUsername(username));
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 认证
		// 获取登录principal
		String username = (String) token.getPrincipal();
		// 执行认证
		SYSUser user = userService.getUserByUsername(username);
		if (user == null) {
			throw new UnknownAccountException();// 未注册,没找到帐号
		}
		if (Boolean.FALSE.equals(user.getAvailable())) {
			throw new LockedAccountException(); // 已注册,但是被锁定
		}
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
			user.getUsername(), // 用户名
			user.getPassword(), // 密码
			new MySimpleByteSource(user.getSalt()), // salt=username+salt(为了简单这里只用了salt)
			getName() // realm name
		);
		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}