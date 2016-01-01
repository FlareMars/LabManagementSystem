package com.libmanagement.repository;

import com.libmanagement.entity.LabRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 */
public interface LabRoomRepository extends JpaRepository<LabRoom, String> {

    @Query("select t from LabRoom as t")
    Page<LabRoom> listLabRooms(Pageable page);

    @Query("select t from LabRoom as t")
    List<LabRoom> listLabRooms();

    @Query("select t from LabRoom as t where t.department=?1 and t.roomNumber = ?1")
    List<LabRoom> findByRoomNumber(String department, String roomNumber);
}
