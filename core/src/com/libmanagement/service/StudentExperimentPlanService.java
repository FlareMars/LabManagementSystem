package com.libmanagement.service;

import com.libmanagement.entity.StudentExperimentPlan;
import com.libmanagement.repository.StudentExperimentPlanRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class StudentExperimentPlanService {
    private Log logger = LogFactory.getLog(StudentExperimentPlanService.class);

    @Autowired
    private StudentExperimentPlanRepository planRepository;

    public void saveAll(List<StudentExperimentPlan> list) {
        planRepository.save(list);
    }

    public List<StudentExperimentPlan> findByStudentId(String studentId) {
        return planRepository.findByStudentId(studentId);
    }

    public StudentExperimentPlan findById(String id) {
        return planRepository.findOne(id);
    }

    public void toBook(StudentExperimentPlan data) {
        data.setIsBooked(true);
        planRepository.save(data);
    }
}
