package com.libmanagement.repository;

import com.libmanagement.entity.ExperimentProject;
import com.libmanagement.entity.SystemNotice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2015/12/31
 */
public interface ExperimentProjectRepository extends JpaRepository<ExperimentProject,String> {

    @Query("select count(e) from ExperimentProject as e where e.teacher.id = ?1")
    Long countExperiments(String teacherId);

    @Query("select e from ExperimentProject as e where e.teacher.id = ?1")
    List<ExperimentProject> getExperimentProjectsByTeacher(String teacherId ,Pageable pageable);
}
