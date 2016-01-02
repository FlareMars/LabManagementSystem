package com.libmanagement.repository;

import com.libmanagement.entity.EquipmentUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/2
 */
public interface EquipmentUsageRepository extends JpaRepository<EquipmentUsage,String> {

    @Query("select u from EquipmentUsage as u where u.equipmentId = ?1")
    List<EquipmentUsage> findByEquipmentId(String equipmentId);
}
