package com.libmanagement.service;

import com.libmanagement.entity.EquipmentUsage;
import com.libmanagement.repository.EquipmentUsageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/2
 */
@Service
public class EquipmentUsageService {
    private Log logger = LogFactory.getLog(EquipmentUsageService.class);

    @Autowired
    private EquipmentUsageRepository usageRepository;

    public List<EquipmentUsage> findByEquipmentId(String equipmentId) {
        return usageRepository.findByEquipmentId(equipmentId);
    }

    public void addUsage(EquipmentUsage data) {
        usageRepository.save(data);
    }
}
