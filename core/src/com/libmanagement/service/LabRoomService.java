package com.libmanagement.service;

import com.libmanagement.entity.LabRoom;
import com.libmanagement.repository.LabRoomRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class LabRoomService {
    private Log logger = LogFactory.getLog(LabRoomService.class);

    @Autowired
    private LabRoomRepository labRoomRepository;

    public List<LabRoom> getByType(Integer type) {
        return labRoomRepository.getByType(type);
    }

    public LabRoom findById(String id) {
        return labRoomRepository.findOne(id);
    }
}
