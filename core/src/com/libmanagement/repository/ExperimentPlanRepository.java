package com.libmanagement.repository;

import com.libmanagement.entity.ExperimentPlan;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
public interface ExperimentPlanRepository extends JpaRepository<ExperimentPlan,String> {

    @Query("select count(e) from ExperimentPlan as e where e.teacherId = ?1 and e.date >= ?2")
    Long count(String teacherId,String today);

    @Query("select e from ExperimentPlan as e where e.teacherId = ?1 and e.date >= ?2 order by e.date,e.time")
    List<ExperimentPlan> getPlans(String teacherId,String today,Pageable pageable);

    @Query("select e from ExperimentPlan as e where e.targetClassId = ?1")
    List<ExperimentPlan> findByClassId(String classId);
}
