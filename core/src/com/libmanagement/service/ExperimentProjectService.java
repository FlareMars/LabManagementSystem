package com.libmanagement.service;

import com.libmanagement.common.exception.LMSServerException;
import com.libmanagement.common.utils.StringUtils;
import com.libmanagement.entity.ExperimentProject;
import com.libmanagement.entity.ExperimentResource;
import com.libmanagement.entity.SystemNotice;
import com.libmanagement.entity.TeacherUser;
import com.libmanagement.repository.ExperimentProjectRepository;
import com.libmanagement.repository.ExperimentResourceRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FlareMars on 2015/12/31
 */
@Service
public class ExperimentProjectService {
    private Log logger = LogFactory.getLog(ExperimentProjectService.class);

    @Autowired
    private ExperimentProjectRepository experimentProjectRepository;

    @Autowired
    private ExperimentResourceRepository experimentResourceRepository;

    public Long getExperimentsCount(String teacherId) {
        return experimentProjectRepository.countExperiments(teacherId);
    }

    public List<ExperimentProject> getExperimentProjects(String teacherId,Integer pageNum) {
        return experimentProjectRepository.getExperimentProjectsByTeacher(teacherId,buildPageRequest(pageNum, 10, Sort.Direction.ASC, "createTime"));
    }

    private PageRequest buildPageRequest(int pageNumber,int pageSize,Sort.Direction sortDirection,String sortKey) {
        Sort sort = new Sort(sortDirection,sortKey);
        return new PageRequest(pageNumber - 1,pageSize,sort);
    }

    public String updateExperimentProject(ExperimentProject data) {
        String id = data.getId();
        if (id.equals("")) {
            return addExperimentProject(data);
        } else {
            ExperimentProject entity = experimentProjectRepository.findOne(id);
            if (entity == null) {
                throw new LMSServerException("查无id[" + id + "]的数据");
            }
            entity.setName(data.getName());
            entity.setBaseInfo(data.getBaseInfo());
            entity.setCategory(data.getCategory());
            experimentProjectRepository.save(entity);
            return id;
        }
    }

    public String addExperimentProject(ExperimentProject data) {
        data.setId(StringUtils.INSTANCE.generateUUID());
        experimentProjectRepository.save(data);
        return data.getId();
    }

    public List<ExperimentResource> getExperimentResources(String experimentId) {
        return experimentResourceRepository.getByExperimentId(experimentId);
    }

    public ExperimentProject findById(String id) {
        return experimentProjectRepository.findOne(id);
    }
}
