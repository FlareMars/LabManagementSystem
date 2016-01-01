package com.libmanagement.service;

import com.libmanagement.entity.ExperimentResult;
import com.libmanagement.repository.ExperimentResultRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class ExperimentResultService {
    private Log logger  = LogFactory.getLog(ExperimentResultService.class);

    @Autowired
    private ExperimentResultRepository experimentResultRepository;

    public ExperimentResult findById(String id) {
        return experimentResultRepository.findOne(id);
    }

    public String addHomework(ExperimentResult data) {
        experimentResultRepository.save(data);
        return data.getId();
    }

    public void saveAll(List<ExperimentResult> list) {
        experimentResultRepository.save(list);
    }

    public List<ExperimentResult> findByLessionAndStudent(String lessionId,String studentId) {
        return experimentResultRepository.findByLessionAndStudent(lessionId,studentId);
    }

    public void update(ExperimentResult data) {
        experimentResultRepository.save(data);
    }
}
