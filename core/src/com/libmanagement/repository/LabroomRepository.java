package com.libmanagement.repository;

import com.libmanagement.entity.LabRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Hugo775128583 on 2016/1/1.
 */
public interface LabRoomRepository extends JpaRepository<LabRoom,String> {

    @Query("select new LabRoom(id,department,roomNumber,manager) from LabRoom as l where l.type = ?1")
    List<LabRoom> getByType(Integer type);

    @Query("select l from LabRoom as l where l.department = ?1 and l.roomNumber = ?2")
    List<LabRoom> findByRoomNumber(String department,String roomNumber);
}
