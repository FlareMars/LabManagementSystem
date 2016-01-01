package com.libmanagement.service;

import com.libmanagement.entity.ExperimentLession;
import com.libmanagement.repository.ExperimentLessionRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class ExperimentLessionService {

    private Log logger = LogFactory.getLog(ExperimentLessionService.class);

    @Autowired
    private ExperimentLessionRepository experimentLessionRepository;

    public String addLession(ExperimentLession data) {
        experimentLessionRepository.save(data);
        return data.getId();
    }

    public Long countLessions(String teacherId) {
        return experimentLessionRepository.countByTeacherId(teacherId,new Date());
    }

    public List<ExperimentLession> listLessions(String teacherId,Integer pageNum) {
        return experimentLessionRepository.listByTeacherId(teacherId,new Date(),buildPageRequest(pageNum, 20, Sort.Direction.ASC, "targetDate"));
    }

    private PageRequest buildPageRequest(int pageNumber,int pageSize,Sort.Direction sortDirection,String sortKey) {
        Sort sort = new Sort(sortDirection,sortKey);
        return new PageRequest(pageNumber - 1,pageSize,sort);
    }

    public ExperimentLession findById(String id) {
        return experimentLessionRepository.findOne(id);
    }
}
