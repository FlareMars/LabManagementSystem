package com.libmanagement.repository;


import com.libmanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author ren-wei.wang
 */
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("select u from User as u")
	Page<User> listUsers(Pageable page);

	User findByUserName(String userName);
	
}
