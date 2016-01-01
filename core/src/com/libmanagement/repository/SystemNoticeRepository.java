package com.libmanagement.repository;

import com.libmanagement.entity.SystemNotice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2015/12/31
 */
public interface SystemNoticeRepository extends JpaRepository<SystemNotice, String> {

    @Query("select count(s) from SystemNotice as s")
    Long countSystemNotice();

    @Query("select s from SystemNotice as s")
    List<SystemNotice> getAllSystemNotice(Pageable pageable);

    @Query("select t from SystemNotice as t where t.title=?1")
    List<SystemNotice> findByTitle(String title);

    @Query("select t from SystemNotice as t")
    Page<SystemNotice> listSystemNotices(Pageable page);

    @Query("select t from SystemNotice as t")
    List<SystemNotice> listSystemNotices();

}
