package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/29
 * 学生实验成果
 */
@Entity
@Table(name = "lms_experiment_result")
public class ExperimentResult extends Describertable {

    @OneToOne
    private StudentUser targetStudent;

    //所属实验安排
    @Column(name = "experiment_lession_id")
    private String experimentLessionId;

    @OneToOne
    private ExperimentResultFile resultFile;

    private Float score = 0.0f;

    public StudentUser getTargetStudent() {
        return targetStudent;
    }

    public void setTargetStudent(StudentUser targetStudent) {
        this.targetStudent = targetStudent;
    }

    public String getExperimentLessionId() {
        return experimentLessionId;
    }

    public void setExperimentLessionId(String experimentLessionId) {
        this.experimentLessionId = experimentLessionId;
    }

    public ExperimentResultFile getResultFile() {
        return resultFile;
    }

    public void setResultFile(ExperimentResultFile resultFile) {
        this.resultFile = resultFile;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
