package com.libmanagement.repository;

import com.libmanagement.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
public interface ClassesRepository extends JpaRepository<Classes,String> {

    @Query("select new Classes(id,name) from Classes as c where c.academyId = ?1")
    List<Classes> getClassesByAcademy(String academyId);
}
