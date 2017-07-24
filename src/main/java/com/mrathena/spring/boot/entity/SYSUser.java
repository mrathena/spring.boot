package com.mrathena.spring.boot.entity;

import java.util.Date;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/** sys_user */
public class SYSUser {

	/** sys_user.id */
	private Long id;
	/** sys_user.username 用户名 */
	private String username;
	/** sys_user.password 密码 */
	private String password;
	/** sys_user.salt 随机码(密码安全) */
	private String salt;
	/** sys_user.roleIds 角色Ids, 用[,]分割 */
	private String roleIds;
	/** sys_user.includeIds 除了角色绑定的授权资源外额外拥有的授权资源列表, 用[,]分割 */
	private String includeIds;
	/** sys_user.excludeIds 最终一定要移除授权的资源列表, 用[,]分割 */
	private String excludeIds;
	/** sys_user.createTime 创建时间 */
	private Date createTime;
	/** sys_user.available 是否可用. 1.true.可用, 0.false.不可用 */
	private Boolean available;

	/** sys_user.id */
	public Long getId() {
		return id;
	}
	/** sys_user.id */
	public SYSUser withId(Long id) {
		this.setId(id);
		return this;
	}
	/** sys_user.id */
	public void setId(Long id) {
		this.id = id;
	}
	/** sys_user.username 用户名 */
	public String getUsername() {
		return username;
	}
	/** sys_user.username 用户名 */
	public SYSUser withUsername(String username) {
		this.setUsername(username);
		return this;
	}
	/** sys_user.username 用户名 */
	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}
	/** sys_user.password 密码 */
	public String getPassword() {
		return password;
	}
	/** sys_user.password 密码 */
	public SYSUser withPassword(String password) {
		this.setPassword(password);
		return this;
	}
	/** sys_user.password 密码 */
	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}
	/** sys_user.salt 随机码(密码安全) */
	public String getSalt() {
		return salt;
	}
	/** sys_user.salt 随机码(密码安全) */
	public SYSUser withSalt(String salt) {
		this.setSalt(salt);
		return this;
	}
	/** sys_user.salt 随机码(密码安全) */
	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}
	/** sys_user.roleIds 角色Ids, 用[,]分割 */
	public String getRoleIds() {
		return roleIds;
	}
	/** sys_user.roleIds 角色Ids, 用[,]分割 */
	public SYSUser withRoleIds(String roleIds) {
		this.setRoleIds(roleIds);
		return this;
	}
	/** sys_user.roleIds 角色Ids, 用[,]分割 */
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds == null ? null : roleIds.trim();
	}
	/** sys_user.includeIds 除了角色绑定的授权资源外额外拥有的授权资源列表, 用[,]分割 */
	public String getIncludeIds() {
		return includeIds;
	}
	/** sys_user.includeIds 除了角色绑定的授权资源外额外拥有的授权资源列表, 用[,]分割 */
	public SYSUser withIncludeIds(String includeIds) {
		this.setIncludeIds(includeIds);
		return this;
	}
	/** sys_user.includeIds 除了角色绑定的授权资源外额外拥有的授权资源列表, 用[,]分割 */
	public void setIncludeIds(String includeIds) {
		this.includeIds = includeIds == null ? null : includeIds.trim();
	}
	/** sys_user.excludeIds 最终一定要移除授权的资源列表, 用[,]分割 */
	public String getExcludeIds() {
		return excludeIds;
	}
	/** sys_user.excludeIds 最终一定要移除授权的资源列表, 用[,]分割 */
	public SYSUser withExcludeIds(String excludeIds) {
		this.setExcludeIds(excludeIds);
		return this;
	}
	/** sys_user.excludeIds 最终一定要移除授权的资源列表, 用[,]分割 */
	public void setExcludeIds(String excludeIds) {
		this.excludeIds = excludeIds == null ? null : excludeIds.trim();
	}
	/** sys_user.createTime 创建时间 */
	public Date getCreateTime() {
		return createTime;
	}
	/** sys_user.createTime 创建时间 */
	public SYSUser withCreateTime(Date createTime) {
		this.setCreateTime(createTime);
		return this;
	}
	/** sys_user.createTime 创建时间 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/** sys_user.available 是否可用. 1.true.可用, 0.false.不可用 */
	public Boolean getAvailable() {
		return available;
	}
	/** sys_user.available 是否可用. 1.true.可用, 0.false.不可用 */
	public SYSUser withAvailable(Boolean available) {
		this.setAvailable(available);
		return this;
	}
	/** sys_user.available 是否可用. 1.true.可用, 0.false.不可用 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}

	// 以下为密码加密部分
	public SYSUser encryptPassword() throws Exception {
		// 需要user.password不能为null
		if (this.getPassword() == null) {
			throw new Exception();
		}
		this.setSalt(new SecureRandomNumberGenerator().nextBytes().toHex());
		String newPassword = new SimpleHash("md5", this.getPassword(), ByteSource.Util.bytes(this.getSalt()), 2).toHex();
		this.setPassword(newPassword);
		return this;
	}
	// 以上为密码加密部分
}