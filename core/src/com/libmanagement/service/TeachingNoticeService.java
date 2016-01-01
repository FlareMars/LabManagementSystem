package com.libmanagement.service;

import com.libmanagement.entity.TeachingNotice;
import com.libmanagement.repository.TeachingNoticeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class TeachingNoticeService {
    private Log logger = LogFactory.getLog(TeachingNoticeService.class);

    @Autowired
    private TeachingNoticeRepository teachingNoticeRepository;

    public String addNotice(TeachingNotice data) {
        teachingNoticeRepository.save(data);
        return data.getId();
    }

    public List<TeachingNotice> findByTargetId(String targetId,Integer toWhat) {
        return teachingNoticeRepository.findByTargetId(targetId,toWhat);
    }
}
