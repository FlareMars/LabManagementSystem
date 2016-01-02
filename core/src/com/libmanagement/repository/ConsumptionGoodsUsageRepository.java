package com.libmanagement.repository;

import com.libmanagement.entity.ConsumptionGoodsUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/2
 */
public interface ConsumptionGoodsUsageRepository extends JpaRepository<ConsumptionGoodsUsage,String> {

    @Query("select u from ConsumptionGoodsUsage as u where u.consumptionGoodsId = ?1")
    List<ConsumptionGoodsUsage> findByGoodsId(String goodsId);
}
