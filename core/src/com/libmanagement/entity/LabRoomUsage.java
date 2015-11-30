package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by FlareMars on 2015/11/28
 * ʵ����ʹ��/ԤԼ���
 */
@Entity
@Table(name = "lms_lab_room_usage")
public class LabRoomUsage extends Describertable{

    //���ԤԼ
    public static final Integer TYPE_BOOKING = 1;
    //������
    public static final Integer TYPE_FINISHED = 2;

    //����ʵ����
    @Column(name = "lab_room_id")
    private String labRoomId;

    //Ŀ������ ��ʽ��yyyy-MM-dd
    private Date targetDate;

    //Ŀ��ʱ��� ֻ�ܴ�1-3��ѡȡ
    //1, ����8:00 �� ����11:50��2, ����2:00 �� ����5:40��3, ����7:00 �� ����9:00
    private Integer targetTime;

    //����״̬
    private Integer status;

    //����ʵ���
    @OneToOne
    @JoinColumn(name = "experiment_lession_id")
    private ExperimentLession experimentLession;

    public String getLabRoomId() {
        return labRoomId;
    }

    public void setLabRoomId(String labRoomId) {
        this.labRoomId = labRoomId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ExperimentLession getExperimentLession() {
        return experimentLession;
    }

    public void setExperimentLession(ExperimentLession experimentLession) {
        this.experimentLession = experimentLession;
    }
}
