package com.libmanagement.service;

import com.libmanagement.entity.SystemNotice;
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
        //todo ¼ì²é²ÎÊý

        systemNoticeRepository.save(temp);
        return temp.getId();
    }

    public void updateSystemNotice(SystemNotice data) {
        List<SystemNotice> temp = systemNoticeRepository.findByTitle(data.getTitle());
        if(temp.size() > 0){
            SystemNotice entity = temp.get(0);
            entity.setContent(data.getContent());
            entity.setOperator(data.getOperator());
            systemNoticeRepository.save(entity);
        }
    }

    public boolean deleteSystemNotices(List<String> ids) {
        List<SystemNotice> list = new ArrayList<>(ids.size());
        for (int i = 0;i < ids.size();i++) {
            list.get(i).setId(ids.get(i));
        }
        systemNoticeRepository.delete(list);
        return true;
    }

    public boolean isValidSystemNotice (SystemNotice temp) {
        return true;
    }
}
