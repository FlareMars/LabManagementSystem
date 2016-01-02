package com.libmanagement.repository;

import com.libmanagement.entity.Academy;
import com.libmanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
public interface RoleRepository extends JpaRepository<Role,String> {

    @Query("select a from Role as a where a.roleName = ?1")
    List<Role> findByName(String roleName);

    @Query("select a from Role as a")
    List<Role> listAllRoles();
}
