package com.libmanagement.repository;

import com.libmanagement.entity.ConsumptionGoods;
import com.libmanagement.entity.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 */
public interface ConsumptionGoodsRepository extends JpaRepository<ConsumptionGoods, String> {


    @Query("select t from ConsumptionGoods as t")
    Page<ConsumptionGoods> listConsumptionGoods(Pageable page);

    @Query("select t from ConsumptionGoods as t")
    List<ConsumptionGoods> listConsumptionGoods();

    @Query("select t from ConsumptionGoods as t where t.name=?1")
    List<ConsumptionGoods> findByName(String name);

    @Query("select t from ConsumptionGoods as t where t.name = ?1 and t.model = ?2")
    List<ConsumptionGoods> findByNameAndModel(String name,String model);

}
