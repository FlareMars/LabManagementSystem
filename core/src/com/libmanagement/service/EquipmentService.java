package com.libmanagement.service;

import com.libmanagement.entity.Equipment;
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

    public List<Equipment> listEquipments() {return equipmentRepository.listEquipments();}

    public String addEquipment(Equipment temp) {
        //todo 检查参数

        equipmentRepository.save(temp);
        return temp.getId();
    }

    public void updateEquipment(Equipment data) {
        List<Equipment> temp = equipmentRepository.findByName(data.getName());
        if(temp.size() > 0){
            Equipment entity = temp.get(0);
            entity.setModel(data.getModel());
            entity.setNumber(data.getNumber());
            entity.setFunction(data.getFunction());
            equipmentRepository.save(entity);
        }
    }

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
