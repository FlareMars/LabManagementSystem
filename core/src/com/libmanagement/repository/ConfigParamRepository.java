package com.libmanagement.repository;

import com.libmanagement.entity.ConfigParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigParamRepository extends JpaRepository<ConfigParam, String> {
}
