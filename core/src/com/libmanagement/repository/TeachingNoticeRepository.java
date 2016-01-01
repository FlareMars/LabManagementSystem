package com.libmanagement.repository;

import com.libmanagement.entity.TeachingNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
public interface TeachingNoticeRepository extends JpaRepository<TeachingNotice,String> {

    @Query("select t from TeachingNotice as t where t.targetId = ?1 and t.sendToWhat = ?2")
    List<TeachingNotice> findByTargetId(String targetId,Integer toWhat);
}
