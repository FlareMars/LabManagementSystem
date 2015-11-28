package com.libmanagement.entity;


import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;

@Entity
@Table(name="lms_user_role")
public class UserRole extends Describertable {

	@ManyToOne
	@JoinColumn
	private User user;

	@OneToOne
	@JoinColumn
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
