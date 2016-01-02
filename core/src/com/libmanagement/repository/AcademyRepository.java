package com.libmanagement.repository;

import com.libmanagement.entity.Academy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
public interface AcademyRepository extends JpaRepository<Academy,String> {

    @Query("select a from Academy as a where a.name = ?1")
    List<Academy> findByName(String academyName);

    @Query("select a from Academy as a")
    List<Academy> listAllAcademies();
}
