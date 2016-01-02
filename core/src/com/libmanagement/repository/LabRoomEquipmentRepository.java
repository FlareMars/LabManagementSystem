package com.libmanagement.repository;

import com.libmanagement.entity.LabRoom;
import com.libmanagement.entity.LabRoomEquipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Hugo775128583 on 2016/1/1.
 */
public interface LabRoomEquipmentRepository extends JpaRepository<LabRoomEquipment,String> {

    @Query("select t from LabRoomEquipment as t where t.labRoomId=?1")
    List<LabRoomEquipment> findByLabRoomId(String roomId);

    @Query("select t from LabRoomEquipment as t where t.labRoomId=?1 and t.equipment.id=?2")
    List<LabRoomEquipment> findByRoomIdAndEquipmentId(String roomId,String equipmentId);
}
