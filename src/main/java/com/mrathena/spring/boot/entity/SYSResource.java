package com.mrathena.spring.boot.entity;

/** sys_resource */
public class SYSResource {

	/** sys_resource.id */
	private Long id;
	/** sys_resource.type 资源类型 */
	private String type;
	/** sys_resource.name 资源名称 */
	private String name;
	/** sys_resource.ico 资源图标, 菜单类资源专用 */
	private String ico;
	/** sys_resource.url 资源url, 菜单类资源专用 */
	private String url;
	/** sys_resource.priority 显示顺序 */
	private Integer priority;
	/** sys_resource.permission 权限字符串 */
	private String permission;
	/** sys_resource.description 资源描述 */
	private String description;
	/** sys_resource.parentId 父编号 */
	private Long parentId;
	/** sys_resource.available 是否可用. 1.true.可用, 0.false.不可用 */
	private Boolean available;

	/** sys_resource.id */
	public Long getId() {
		return id;
	}
	/** sys_resource.id */
	public SYSResource withId(Long id) {
		this.setId(id);
		return this;
	}
	/** sys_resource.id */
	public void setId(Long id) {
		this.id = id;
	}
	/** sys_resource.type 资源类型 */
	public String getType() {
		return type;
	}
	/** sys_resource.type 资源类型 */
	public SYSResource withType(String type) {
		this.setType(type);
		return this;
	}
	/** sys_resource.type 资源类型 */
	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}
	/** sys_resource.name 资源名称 */
	public String getName() {
		return name;
	}
	/** sys_resource.name 资源名称 */
	public SYSResource withName(String name) {
		this.setName(name);
		return this;
	}
	/** sys_resource.name 资源名称 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	/** sys_resource.ico 资源图标, 菜单类资源专用 */
	public String getIco() {
		return ico;
	}
	/** sys_resource.ico 资源图标, 菜单类资源专用 */
	public SYSResource withIco(String ico) {
		this.setIco(ico);
		return this;
	}
	/** sys_resource.ico 资源图标, 菜单类资源专用 */
	public void setIco(String ico) {
		this.ico = ico == null ? null : ico.trim();
	}
	/** sys_resource.url 资源url, 菜单类资源专用 */
	public String getUrl() {
		return url;
	}
	/** sys_resource.url 资源url, 菜单类资源专用 */
	public SYSResource withUrl(String url) {
		this.setUrl(url);
		return this;
	}
	/** sys_resource.url 资源url, 菜单类资源专用 */
	public void setUrl(String url) {
		this.url = url == null ? null : url.trim();
	}
	/** sys_resource.priority 显示顺序 */
	public Integer getPriority() {
		return priority;
	}
	/** sys_resource.priority 显示顺序 */
	public SYSResource withPriority(Integer priority) {
		this.setPriority(priority);
		return this;
	}
	/** sys_resource.priority 显示顺序 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	/** sys_resource.permission 权限字符串 */
	public String getPermission() {
		return permission;
	}
	/** sys_resource.permission 权限字符串 */
	public SYSResource withPermission(String permission) {
		this.setPermission(permission);
		return this;
	}
	/** sys_resource.permission 权限字符串 */
	public void setPermission(String permission) {
		this.permission = permission == null ? null : permission.trim();
	}
	/** sys_resource.description 资源描述 */
	public String getDescription() {
		return description;
	}
	/** sys_resource.description 资源描述 */
	public SYSResource withDescription(String description) {
		this.setDescription(description);
		return this;
	}
	/** sys_resource.description 资源描述 */
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}
	/** sys_resource.parentId 父编号 */
	public Long getParentId() {
		return parentId;
	}
	/** sys_resource.parentId 父编号 */
	public SYSResource withParentId(Long parentId) {
		this.setParentId(parentId);
		return this;
	}
	/** sys_resource.parentId 父编号 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	/** sys_resource.available 是否可用. 1.true.可用, 0.false.不可用 */
	public Boolean getAvailable() {
		return available;
	}
	/** sys_resource.available 是否可用. 1.true.可用, 0.false.不可用 */
	public SYSResource withAvailable(Boolean available) {
		this.setAvailable(available);
		return this;
	}
	/** sys_resource.available 是否可用. 1.true.可用, 0.false.不可用 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}

}