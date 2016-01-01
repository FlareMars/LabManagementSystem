package com.libmanagement.service;

import com.libmanagement.entity.ExperimentResource;
import com.libmanagement.repository.ExperimentResourceRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class ExperimentResourceService {

    private Log logger = LogFactory.getLog(ExperimentResourceService.class);

    @Autowired
    private ExperimentResourceRepository experimentResourceRepository;

    public ExperimentResource findById(String id) {
        return experimentResourceRepository.findOne(id);
    }

    public String addResuource(ExperimentResource data) {
        experimentResourceRepository.save(data);
        return data.getId();
    }
}
