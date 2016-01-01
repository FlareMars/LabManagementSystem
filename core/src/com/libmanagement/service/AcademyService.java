package com.libmanagement.service;

import com.libmanagement.entity.Academy;
import com.libmanagement.repository.AcademyRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class AcademyService {

    private Log logger = LogFactory.getLog(AcademyService.class);

    @Autowired
    private AcademyRepository academyRepository;

    public Academy findByName(String name) {
        List<Academy> temp = academyRepository.findByName(name);
        if (temp.size() > 0) {
            return temp.get(0);
        } else {
            return null;
        }
    }
}
