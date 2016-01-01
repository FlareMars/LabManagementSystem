package com.libmanagement.repository;

import com.libmanagement.entity.ExperimentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */

public interface ExperimentResultRepository extends JpaRepository<ExperimentResult,String> {

    @Query("select e from ExperimentResult as e where e.experimentLessionId = ?1 and e.targetStudent.id = ?2")
    List<ExperimentResult> findByLessionAndStudent(String lessionId,String studentId);
}
