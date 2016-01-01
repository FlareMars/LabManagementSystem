package com.libmanagement.repository;

import com.libmanagement.entity.ExperimentResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2015/12/31
 */
public interface ExperimentResourceRepository extends JpaRepository<ExperimentResource,String> {

    @Query("select id,name,e.size,experimentId from ExperimentResource as e where e.experimentId = ?1")
    List<ExperimentResource> getByExperimentId(String experimentId);
}
