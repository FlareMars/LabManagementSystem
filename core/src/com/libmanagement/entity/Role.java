package com.libmanagement.entity;


import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="lms_role")
public class Role extends Describertable {

	private static final long serialVersionUID = -4266489039258343768L;
	@Column(unique=true)
	private String roleName;
	private String roleDesc;

	@ManyToMany(cascade={CascadeType.PERSIST})
	@JoinTable(name="lms_role_permission",
			joinColumns=@JoinColumn(name="role_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(name="permission_id", referencedColumnName="id"))
	private List<Permission> permissionList;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
}
