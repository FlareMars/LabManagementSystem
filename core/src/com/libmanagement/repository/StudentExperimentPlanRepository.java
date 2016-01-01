package com.libmanagement.repository;

import com.libmanagement.entity.StudentExperimentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
public interface StudentExperimentPlanRepository extends JpaRepository<StudentExperimentPlan,String> {

    @Query("select p from StudentExperimentPlan as p where p.owner = ?1")
    List<StudentExperimentPlan> findByStudentId(String studentId);
}
