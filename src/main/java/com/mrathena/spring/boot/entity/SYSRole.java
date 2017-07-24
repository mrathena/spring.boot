package com.mrathena.spring.boot.entity;

/** sys_role */
public class SYSRole {

	/** sys_role.id */
	private Long id;
	/** sys_role.name 角色名称 */
	private String name;
	/** sys_role.description 角色描述 */
	private String description;
	/** sys_role.resourceIds 授权的资源列表 */
	private String resourceIds;
	/** sys_role.available 是否可用. 1.true.可用, 0.false.不可用 */
	private Boolean available;

	/** sys_role.id */
	public Long getId() {
		return id;
	}
	/** sys_role.id */
	public SYSRole withId(Long id) {
		this.setId(id);
		return this;
	}
	/** sys_role.id */
	public void setId(Long id) {
		this.id = id;
	}
	/** sys_role.name 角色名称 */
	public String getName() {
		return name;
	}
	/** sys_role.name 角色名称 */
	public SYSRole withName(String name) {
		this.setName(name);
		return this;
	}
	/** sys_role.name 角色名称 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	/** sys_role.description 角色描述 */
	public String getDescription() {
		return description;
	}
	/** sys_role.description 角色描述 */
	public SYSRole withDescription(String description) {
		this.setDescription(description);
		return this;
	}
	/** sys_role.description 角色描述 */
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}
	/** sys_role.resourceIds 授权的资源列表 */
	public String getResourceIds() {
		return resourceIds;
	}
	/** sys_role.resourceIds 授权的资源列表 */
	public SYSRole withResourceIds(String resourceIds) {
		this.setResourceIds(resourceIds);
		return this;
	}
	/** sys_role.resourceIds 授权的资源列表 */
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds == null ? null : resourceIds.trim();
	}
	/** sys_role.available 是否可用. 1.true.可用, 0.false.不可用 */
	public Boolean getAvailable() {
		return available;
	}
	/** sys_role.available 是否可用. 1.true.可用, 0.false.不可用 */
	public SYSRole withAvailable(Boolean available) {
		this.setAvailable(available);
		return this;
	}
	/** sys_role.available 是否可用. 1.true.可用, 0.false.不可用 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}

}