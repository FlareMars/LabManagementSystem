package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/29
 * ʵ��γ�����ļ�����ѧ��Դ
 */
@Entity
@Table(name = "lms_experiment_resource")
public class ExperimentResource extends Describertable {

    //�ļ���
    private String name;

    //�ļ���׺
    private String suffix;

    //�ļ�����·��
    private String path;

    //�ļ�URL
    private String url;

    //�ļ���С
    private Long size;

    //�ϴ���id
    private String uploaderId;

    //����ʵ��γ�id
    @Column(name = "experiment_id")
    private String experimentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getExpeirmentId() {
        return experimentId;
    }

    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }
}
