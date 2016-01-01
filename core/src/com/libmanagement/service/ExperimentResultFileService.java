package com.libmanagement.service;

import com.libmanagement.entity.ExperimentResultFile;
import com.libmanagement.repository.ExperimentResultFileRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class ExperimentResultFileService {

    private Log logger = LogFactory.getLog(ExperimentResultFileService.class);

    @Autowired
    private ExperimentResultFileRepository experimentResultFileRepository;

    public ExperimentResultFile findById(String fileId) {
        return experimentResultFileRepository.findOne(fileId);
    }
}
