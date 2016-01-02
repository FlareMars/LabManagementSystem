package com.libmanagement.service;

import com.libmanagement.entity.SystemNotice;
import com.libmanagement.entity.User;
import com.libmanagement.repository.SystemNoticeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FlareMars on 2015/12/31
 */
@Service
public class SystemNoticeService {
    private Log logger = LogFactory.getLog(SystemNoticeService.class);

    @Autowired
    private SystemNoticeRepository systemNoticeRepository;

    public Long getSystemNoticeSize() {
        return systemNoticeRepository.countSystemNotice();
    }

    public List<SystemNotice> getSystemNotices(Integer pageNum) {
        return systemNoticeRepository.getAllSystemNotice(buildPageRequest(pageNum,10,Sort.Direction.DESC,"createTime"));
    }

    private PageRequest buildPageRequest(int pageNumber,int pageSize,Sort.Direction sortDirection,String sortKey) {
        Sort sort = new Sort(sortDirection, sortKey);
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    public String addSystemNotice(SystemNotice temp) {

        String operatorName = temp.getOperatorName();
        User operator = new User();
        if (operatorName != null && !operatorName.equals("")) {
            String operatorId = temp.getOperatorName().substring(temp.getOperatorName().indexOf("_") + 1);
            operator.setId(operatorId);
        } else {
            //default manager
            operator.setId("5c5a8bbb0a2b4bd68a522e64e5b6f392");
            temp.setOperatorName("π‹¿Ì‘±A_5c5a8bbb0a2b4bd68a522e64e5b6f392");
        }
        temp.setOperator(operator);
        systemNoticeRepository.save(temp);
        return temp.getId();
    }

    public void updateSystemNotice(SystemNotice data) {
        SystemNotice temp = systemNoticeRepository.findOne(data.getId());
        if(temp != null) {
            temp.setContent(data.getContent());
            temp.setTitle(data.getTitle());
            systemNoticeRepository.save(temp);
        }
    }

    public void deleteSystemNotice(String id) {
        systemNoticeRepository.delete(id);
    }

    public void deleteSystemNotices(List<SystemNotice> data) {
        systemNoticeRepository.delete(data);
    }

    public boolean isValidSystemNotice (SystemNotice temp) {
        return true;
    }

    public List<SystemNotice> listSystemNotices() {
        return systemNoticeRepository.findAll();
    }
}
