package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/29
 * ѧ��ʵ������ļ�
 */
@Entity
@Table(name = "lms_experiment_result_file")
public class ExperimentResultFile extends Describertable {
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
}
