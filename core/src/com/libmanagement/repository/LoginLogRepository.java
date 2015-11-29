package com.libmanagement.repository;

import com.libmanagement.entity.LoginLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoginLogRepository extends JpaRepository<LoginLog, String> {
	@Query("select l from LoginLog as l")
	Page<LoginLog> list(Pageable page);
}
