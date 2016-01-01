package com.libmanagement.entity;

import com.libmanagement.common.entity.EntityUUID;

import javax.persistence.*;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 * 班级表(超简版)
 */
@Entity
@Table(name="lms_classes")
public class Classes extends EntityUUID {

    //班级名称
    private String name;

    //所在学院id
    @Column(name = "academy_id")
    private String academyId;

    //班级成员
    @ManyToMany
    @JoinColumn(name = "student_id")
    private List<StudentUser> classmates;

    public Classes() {

    }

    public Classes(String id,String name) {
        setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcademyId() {
        return academyId;
    }

    public void setAcademyId(String academyId) {
        this.academyId = academyId;
    }

    public List<StudentUser> getClassmates() {
        return classmates;
    }

    public void setClassmates(List<StudentUser> classmates) {
        this.classmates = classmates;
    }
}
