package com.libmanagement.repository;


import com.libmanagement.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ren-wei.wang
 */
public interface RegistrationRepository extends JpaRepository<Registration, String> {

    Registration findByEmail(String email);

}