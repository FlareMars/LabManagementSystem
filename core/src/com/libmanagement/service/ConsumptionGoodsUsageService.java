package com.libmanagement.service;

import com.libmanagement.entity.ConsumptionGoodsUsage;
import com.libmanagement.repository.ConsumptionGoodsUsageRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by FlareMars on 2016/1/2
 */
@Service
public class ConsumptionGoodsUsageService {
    private Log logger = LogFactory.getLog(ConsumptionGoodsUsageService.class);

    @Autowired
    private ConsumptionGoodsUsageRepository consumptionGoodsUsageRepository;

    public String addUsage(ConsumptionGoodsUsage data) {
        consumptionGoodsUsageRepository.save(data);
        return data.getId();
    }
}
