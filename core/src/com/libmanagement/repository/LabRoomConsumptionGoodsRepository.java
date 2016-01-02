package com.libmanagement.repository;

import com.libmanagement.entity.LabRoomConsumptionGoods;
import com.libmanagement.entity.LabRoomEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Hugo775128583 on 2016/1/1.
 */
public interface LabRoomConsumptionGoodsRepository extends JpaRepository<LabRoomConsumptionGoods,String> {

    @Query("select t from LabRoomConsumptionGoods as t where t.labRoomId=?1")
    List<LabRoomConsumptionGoods> findByLabRoomId(String roomId);



}
