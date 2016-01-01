package com.libmanagement.service;

import com.libmanagement.entity.ExperimentPlan;
import com.libmanagement.repository.ExperimentPlanRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class ExperimentPlanService {
    private Log logger = LogFactory.getLog(ExperimentPlanService.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    @Autowired
    private ExperimentPlanRepository experimentPlanRepository;

    public Long countPlans(String teacherId) {
        return experimentPlanRepository.count(teacherId,DATE_FORMAT.format(new Date()));
    }

    public List<ExperimentPlan> getPlans(String teacherId,Integer pageNum) {
        return experimentPlanRepository.getPlans(teacherId,DATE_FORMAT.format(new Date()),buildPageRequest(pageNum,10));
    }

    public String savePlan(ExperimentPlan entity) {
        experimentPlanRepository.save(entity);
        return entity.getId();
    }

    public List<ExperimentPlan> findByClass(String classId) {
        return experimentPlanRepository.findByClassId(classId);
    }

    private PageRequest buildPageRequest(int pageNumber,int pageSize) {
        return new PageRequest(pageNumber - 1,pageSize,null);
    }

    public ExperimentPlan findById(String id) {
        return experimentPlanRepository.findOne(id);
    }

    public void addBookedNum(ExperimentPlan data) {
        data.setCurrentNum(data.getCurrentNum()+1);
        experimentPlanRepository.save(data);
    }
}
