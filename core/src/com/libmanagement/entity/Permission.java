package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="lms_permission")
public class Permission extends Describertable {
	private static final long serialVersionUID = -7972670474723048121L;
	@Column(unique=true)
	private String permissionName;
	private String permissionDesc;

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}
}
