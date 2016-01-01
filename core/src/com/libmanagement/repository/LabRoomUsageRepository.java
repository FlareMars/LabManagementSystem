package com.libmanagement.repository;

import com.libmanagement.entity.LabRoomUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
public interface LabRoomUsageRepository extends JpaRepository<LabRoomUsage,String> {

    @Query("select l from LabRoomUsage as l where l.labRoomId = ?1 and l.targetDate >= ?2 order by targetDate,targetTime")
    List<LabRoomUsage> findByLabRoomId(String labRoomId,Date today);
}
