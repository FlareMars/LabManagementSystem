package com.libmanagement.repository;

import com.libmanagement.entity.LabRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
public interface LabRoomRepository extends JpaRepository<LabRoom,String> {

    @Query("select id,department,roomNumber,manager from LabRoom as l where l.type = ?1")
    List<LabRoom> getByType(Integer type);
}