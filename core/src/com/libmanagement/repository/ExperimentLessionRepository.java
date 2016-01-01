package com.libmanagement.repository;

import com.libmanagement.entity.ExperimentLession;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
public interface ExperimentLessionRepository extends JpaRepository<ExperimentLession,String> {


    @Query("select count(e) from ExperimentLession as e where e.teacherId = ?1 and e.targetDate <= ?2")
    Long countByTeacherId(String teacherId,Date today);

    @Query("select e from ExperimentLession as e where e.teacherId = ?1 and e.targetDate <= ?2")
    List<ExperimentLession> listByTeacherId(String teacherId,Date today,Pageable pageable);

    @Query("select e from ExperimentLession as e where e.experimentPlanId = ?1")
    List<ExperimentLession> findByPlanId(String planId);
}
