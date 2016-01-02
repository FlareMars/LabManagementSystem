package com.libmanagement.service;

import com.libmanagement.common.exception.LMSServerException;
import com.libmanagement.entity.LabRoomConsumptionGoods;
import com.libmanagement.entity.LabRoomEquipment;
import com.libmanagement.repository.LabRoomConsumptionGoodsRepository;
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
public class LabRoomConsumptionGoodsService {
    private Log logger = LogFactory.getLog(LabRoomUsageService.class);

    @Autowired
    private LabRoomConsumptionGoodsRepository consumptionGoodsRepository;

    public List<LabRoomConsumptionGoods> findByRoomId(String roomId){
        return consumptionGoodsRepository.findByLabRoomId(roomId);
    }

    public void update(LabRoomConsumptionGoods data) {
        consumptionGoodsRepository.save(data);
    }

    public LabRoomConsumptionGoods findById(String id){
        return consumptionGoodsRepository.findOne(id);
    }

    public void addLabRoomGoods(LabRoomConsumptionGoods data) {
        consumptionGoodsRepository.save(data);
    }
}
