package com.libmanagement.service;

import com.libmanagement.entity.ConsumptionGoods;
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

    public List<ConsumptionGoods> listConsumptionGoods() {return consumptionGoodsrepository.listConsumptionGoods();}

    public String addConsumptionGoods(ConsumptionGoods temp) {
        //todo 检查参数

        consumptionGoodsrepository.save(temp);
        return temp.getId();
    }

    public void updateConsumptionGoods(ConsumptionGoods data) {
        List<ConsumptionGoods> temp = consumptionGoodsrepository.findByName(data.getName());
        if(temp.size() > 0){
            ConsumptionGoods entity = temp.get(0);
            entity.setModel(data.getModel());
            entity.setInformation(data.getInformation());
            entity.setTotalStock(data.getTotalStock());
            consumptionGoodsrepository.save(entity);
        }
    }

    public boolean deleteConsumptionGoods(List<String> ids) {
        List<ConsumptionGoods> list = new ArrayList<>(ids.size());
        for (int i = 0;i < ids.size();i++) {
            list.get(i).setId(ids.get(i));
        }
        consumptionGoodsrepository.delete(list);
        return true;
    }

    public boolean isValidConsumptionGoods (ConsumptionGoods temp) {
        return true;
    }
}
