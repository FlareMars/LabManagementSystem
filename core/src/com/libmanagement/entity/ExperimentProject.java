package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/29
 * ʵ��γ�ʵ����
 */
@Entity
@Table(name = "lms_experiment_project")
public class ExperimentProject extends Describertable {

    //ʵ������
    private String name;

    //ʵ�������Ϣ
    private String baseInfo;

    //�ο���ʦ
    @OneToOne
    private TeacherUser teacher;

    //ʵ������
    private String category;

    //ʵ���ѧ��Դ
    @OneToMany
    @JoinColumn(name = "experiment_id")
    private List<ExperimentResource> teachingResources;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(String baseInfo) {
        this.baseInfo = baseInfo;
    }

    public TeacherUser getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherUser teacher) {
        this.teacher = teacher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ExperimentResource> getTeachingResources() {
        return teachingResources;
    }

    public void setTeachingResources(List<ExperimentResource> teachingResources) {
        this.teachingResources = teachingResources;
    }
}
