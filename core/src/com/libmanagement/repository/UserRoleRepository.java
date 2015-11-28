package com.libmanagement.repository;


import com.libmanagement.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRoleRepository extends JpaRepository<UserRole, String> {
	@Query("select u from UserRole as u where u.user.id=?1")
	List<UserRole> findByUserId(String userId);

	
}
