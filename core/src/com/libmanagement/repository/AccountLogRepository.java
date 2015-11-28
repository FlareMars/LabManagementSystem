package com.libmanagement.repository;


import com.libmanagement.entity.AccountLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AccountLogRepository extends JpaRepository<AccountLog, String> {
	


	@Query("select t from AccountLog as t  where t.createTime between ?1 and ?2 and t.logType = ?3 and t.user.id = ?4 order by t.createTime desc")
	List<AccountLog> findAccountDetail(Long beginTime, Long endTime, Integer type, String userId);

	@Query("select t from AccountLog as t  where t.createTime between ?1 and ?2 and t.user.id = ?3 order by t.createTime desc")
	List<AccountLog> findAccountDetail(Long beginTime, Long endTime, String userId);
	
	@Query("select t from AccountLog as t")
	Page<AccountLog> list(Pageable page);
}
