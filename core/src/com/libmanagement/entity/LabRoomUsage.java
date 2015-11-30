package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by FlareMars on 2015/11/28
 * 实验室使用/预约情况
 */
@Entity
@Table(name = "lms_lab_room_usage")
public class LabRoomUsage extends Describertable{

    //类别：预约
    public static final Integer TYPE_BOOKING = 1;
    //类别：完成
    public static final Integer TYPE_FINISHED = 2;

    //所属实验室
    @Column(name = "lab_room_id")
    private String labRoomId;

    //目标日期 格式：yyyy-MM-dd
    private Date targetDate;

    //目标时间段 只能从1-3中选取
    //1, 早上8:00 到 早上11:50；2, 下午2:00 到 下午5:40；3, 晚上7:00 到 晚上9:00
    private Integer targetTime;

    //所处状态
    private Integer status;

    //所属实验课
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
