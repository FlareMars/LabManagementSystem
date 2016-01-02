package com.libmanagement.service;

import com.libmanagement.common.exception.LMSServerException;
import com.libmanagement.common.utils.StringUtils;
import com.libmanagement.entity.ConsumptionGoods;
import com.libmanagement.entity.ConsumptionGoodsUsage;
import com.libmanagement.repository.ConsumptionGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 * 封装老师数据操作服务
 */
@Service
public class ConsumptionGoodsService {


    @Autowired
    private ConsumptionGoodsRepository consumptionGoodsrepository;

    @Autowired
    private ConsumptionGoodsUsageService usageService;

    public List<ConsumptionGoods> listConsumptionGoods() {return consumptionGoodsrepository.listConsumptionGoods();}

    public String addConsumptionGoods(ConsumptionGoods temp) {
        temp.setId(StringUtils.INSTANCE.generateUUID());
        consumptionGoodsrepository.save(temp);
        return temp.getId();
    }

    public void updateConsumptionGoods(ConsumptionGoods data) {
        ConsumptionGoods entity = consumptionGoodsrepository.findOne(data.getId());
        if (entity == null) {
            List<ConsumptionGoods> temp = consumptionGoodsrepository.findByNameAndModel(data.getName(),data.getModel());
            if (temp.size() > 0) {
                entity = temp.get(0);
            } else {
                throw new LMSServerException("no data!");
            }
        }

        entity.setName(data.getName());
        entity.setInformation(data.getInformation());
        entity.setModel(data.getModel());
        entity.setTotalStock(data.getTotalStock());
    }

    public boolean deleteConsumptionGoods(List<String> ids) {
        List<ConsumptionGoods> list = new ArrayList<>(ids.size());
        for (int i = 0;i < ids.size();i++) {
            list.get(i).setId(ids.get(i));
        }
        consumptionGoodsrepository.delete(list);
        return true;
    }

    public ConsumptionGoods findById(String id) {
        return consumptionGoodsrepository.findOne(id);
    }

    public boolean isValidConsumptionGoods (ConsumptionGoods temp) {
        return true;
    }

    public boolean modifyStock(String id,Integer type,Integer number) {
        ConsumptionGoods goods = findById(id);
        if (type.equals(ConsumptionGoodsUsage.TYPE_IN)) {
            goods.setTotalStock(goods.getTotalStock() + number);
        } else {
            if (goods.getTotalStock() < number) {
                return false;
            } else {
                goods.setTotalStock(goods.getTotalStock() - number);
            }
        }
        consumptionGoodsrepository.save(goods);
        return true;
    }

    public List<ConsumptionGoodsUsage> listUsage(String id) {
        return usageService.findByGoodsId(id);
    }
}
