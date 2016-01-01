package com.libmanagement.repository;

import com.libmanagement.entity.StudentUser;
import com.libmanagement.entity.TeacherUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 */
public interface StudentUserRepository extends JpaRepository<StudentUser, String> {

    @Query("select t from StudentUser as t")
    Page<StudentUser> listStudents(Pageable page);

    @Query("select t from StudentUser as t")
    List<StudentUser> listStudents();

    @Query("select count(*) from StudentUser as s where s.currentClass.id = ?1")
    Integer studentNumInClass(String classId);

    @Query("select new StudentUser(id) from StudentUser as s where s.currentClass.id = ?1")
    List<StudentUser> listByClassId(String classId);

    @Query("select t from StudentUser as t where t.username = ?1")
    List<StudentUser> findByUsername(String username);
}
