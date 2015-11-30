package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/29
 * ʵ��ΰ���
 */
@Entity
@Table(name = "lms_experiment_lession")
public class ExperimentLession extends Describertable {

    //Ŀ������
    private Date targetDate;

    //Ŀ��ʱ���
    private Integer targetTime;

    //Ŀ��༶
    @OneToOne
    private Classes targetClass;

    //Ŀ��Դʵ��
    @OneToOne
    private ExperimentProject experimentProject;

    //ѧ��ʵ��ɹ�����
    @OneToMany
    @JoinColumn(name = "experiment_lession_id")
    private List<ExperimentResult> experimentResultList;

    //ʵ�����ڽ�������
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