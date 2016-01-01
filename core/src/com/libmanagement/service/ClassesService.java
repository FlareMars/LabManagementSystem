package com.libmanagement.service;

import com.libmanagement.entity.Academy;
import com.libmanagement.entity.Classes;
import com.libmanagement.repository.ClassesRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class ClassesService {

    private Log logger = LogFactory.getLog(ClassesService.class);

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private AcademyService academyService;

    public List<Classes> getClassesByAcademy(String academyName) {
        Academy academy = academyService.findByName(academyName);
        if (academy != null) {
            return classesRepository.getClassesByAcademy(academy.getId());
        } else {
            return null;
        }
    }

    public Classes findById(String id) {
        return classesRepository.findOne(id);
    }
}
