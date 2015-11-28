package com.libmanagement.repository;

import com.libmanagement.entity.TeacherUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 */
public interface TeacherUserRepository extends JpaRepository<TeacherUser, String> {

    @Query("select t from TeacherUser as t")
    Page<TeacherUser> listTeachers(Pageable page);

    @Query("select t from TeacherUser as t")
    List<TeacherUser> listTeachers();
}
