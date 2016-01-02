package com.libmanagement.repository;

import com.libmanagement.entity.Equipment;
import com.libmanagement.entity.LabRoom;
import com.sun.corba.se.impl.orbutil.threadpool.WorkQueueImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 */
public interface EquipmentRepository extends JpaRepository<Equipment, String> {

    @Query("select t from Equipment as t")
    Page<Equipment> listEquipments(Pageable page);

    @Query("select t from Equipment as t")
    List<Equipment> listEquipments();

    @Query("select t from Equipment as t where t.name=?1")
    List<Equipment> findByName(String name);

    @Query("select t from Equipment as t where t.labRoom.id=?1")
    List<Equipment> findByLabRoomId(String labRoomId);
}
