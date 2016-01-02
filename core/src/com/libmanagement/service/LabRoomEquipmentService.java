package com.libmanagement.service;

import com.libmanagement.entity.LabRoomEquipment;
import com.libmanagement.repository.LabRoomEquipmentRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo775128583 on 2016/1/3.
 */
@Service
public class LabRoomEquipmentService {
    private Log logger = LogFactory.getLog(LabRoomUsageService.class);

    @Autowired
    private LabRoomEquipmentRepository roomEquipmentRepository;

    public List<LabRoomEquipment> findByRoomId(String roomId){
        return roomEquipmentRepository.findByLabRoomId(roomId);
    }

    public void addRoomEquipment(LabRoomEquipment roomEquipment) {
        roomEquipmentRepository.save(roomEquipment);
    }

    public List<LabRoomEquipment> findByRoomIdAndEquipmentId(String roomId,String equipmentId) {
        return roomEquipmentRepository.findByRoomIdAndEquipmentId(roomId,equipmentId);
    }

    public void delete(List<LabRoomEquipment> data) {
        roomEquipmentRepository.delete(data);
    }
}
