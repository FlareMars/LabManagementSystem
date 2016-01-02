package com.libmanagement.service;

import com.libmanagement.common.utils.StringUtils;
import com.libmanagement.entity.Equipment;
import com.libmanagement.entity.EquipmentUsage;
import com.libmanagement.entity.LabRoom;
import com.libmanagement.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 * 封装老师数据操作服务
 */
@Service
public class EquipmentService {


    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private LabRoomService labRoomService;

    @Autowired
    private EquipmentUsageService usageService;

    public List<Equipment> listEquipments() {return equipmentRepository.listEquipments();}

    public String addEquipment(Equipment temp) {
        temp.setId(StringUtils.INSTANCE.generateUUID());
        LabRoom targetLabRoom;
        if (temp.getPosition() == null || temp.getPosition().equals("")) {
            //默认仓库
            targetLabRoom = labRoomService.findByFullName("鉴主大楼1111");
        } else {
            targetLabRoom = labRoomService.findByFullName(temp.getPosition());
        }
        temp.setLabRoom(targetLabRoom);
        temp.setPosition(targetLabRoom.getDepartment() + targetLabRoom.getRoomNumber());
        equipmentRepository.save(temp);

        EquipmentUsage usage = new EquipmentUsage();
        usage.setDetail("仪器 " + temp.getName() + "_" + temp.getModel() + " 录入系统");
        usage.setEquipmentId(temp.getId());
        usage.setUsageType(EquipmentUsage.TYPE_ADD);
        usageService.addUsage(usage);
        return temp.getId();
    }

    public void updateEquipment(Equipment data) {
        Equipment temp = equipmentRepository.findOne(data.getId());
        if(temp != null){
            temp.setModel(data.getModel());
            temp.setNumber(data.getNumber());
            temp.setFunction(data.getFunction());
            temp.setNumber(data.getNumber());
            temp.setPosition(data.getPosition());
            temp.setName(data.getName());
            LabRoom targetLabRoom;
            if (data.getPosition() == null || data.getPosition().equals("")) {
                //默认仓库
                targetLabRoom = labRoomService.findByFullName("鉴主大楼1111");
            } else {
                targetLabRoom = labRoomService.findByFullName(data.getPosition());
            }
            temp.setLabRoom(targetLabRoom);
            equipmentRepository.save(temp);
        }
    }

    public Equipment findById(String id) {
        return equipmentRepository.findOne(id);
    }

    public List<EquipmentUsage> listUsage(String equipmentId) {
        return usageService.findByEquipmentId(equipmentId);
    }

    public List<Equipment> findByRoomId(String labRoomId){ return equipmentRepository.findByLabRoomId(labRoomId);}

    public boolean deleteEquipment(List<String> ids) {
        List<Equipment> list = new ArrayList<>(ids.size());
        for (int i = 0;i < ids.size();i++) {
            list.get(i).setId(ids.get(i));
        }
        equipmentRepository.delete(list);
        return true;
    }

    public boolean isValidEquipment (Equipment temp) {
        return true;
    }
}
