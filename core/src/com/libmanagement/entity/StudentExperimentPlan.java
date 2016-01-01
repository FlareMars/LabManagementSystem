package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2016/1/1
 */
@Entity
@Table(name = "lms_student_experiment_plan")
public class StudentExperimentPlan extends Describertable {
    private String experimentName;

    private String experimentId;

    private String date;

    private String labRoom;

    private String teacher;

    private Boolean isBooked;

    private String owner;

    private String parentPlanId;

    public String getParentPlanId() {
        return parentPlanId;
    }

    public void setParentPlanId(String parentPlanId) {
        this.parentPlanId = parentPlanId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public String getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLabRoom() {
        return labRoom;
    }

    public void setLabRoom(String labRoom) {
        this.labRoom = labRoom;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(Boolean isBooked) {
        this.isBooked = isBooked;
    }
}
