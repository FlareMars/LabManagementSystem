package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/29
 * 实验课安排
 */
@Entity
@Table(name = "lms_experiment_lession")
public class ExperimentLession extends Describertable {

    //目标日期
    private Date targetDate;

    //目标时间段
    private Integer targetTime;

    //目标班级
    @OneToOne
    private Classes targetClass;

    //目标源实验
    @OneToOne
    private ExperimentProject experimentProject;

    //学生实验成果集合
    @OneToMany
    @JoinColumn(name = "experiment_lession_id")
    private List<ExperimentResult> experimentResultList;

    //实验所在教室名称
    private String labRoomName;

    public String getLabRoomName() {
        return labRoomName;
    }

    public void setLabRoomName(String labRoomName) {
        this.labRoomName = labRoomName;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Integer getTargetTime() {
        return targetTime;
    }

    public void setTargetTime(Integer targetTime) {
        this.targetTime = targetTime;
    }

    public Classes getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Classes targetClass) {
        this.targetClass = targetClass;
    }

    public ExperimentProject getExperimentProject() {
        return experimentProject;
    }

    public void setExperimentProject(ExperimentProject experimentProject) {
        this.experimentProject = experimentProject;
    }

    public List<ExperimentResult> getExperimentResultList() {
        return experimentResultList;
    }

    public void setExperimentResultList(List<ExperimentResult> experimentResultList) {
        this.experimentResultList = experimentResultList;
    }
}
