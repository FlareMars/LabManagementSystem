package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

/**
 * Created by FlareMars on 2015/11/29
 * ʵ��γ�����ļ���������ѧ��Դ�Լ�ѧ���ĳɹ��ļ�
 */
public class ExperimentFile extends Describertable {

    public static final Integer TYPE_TEACHING_RESOURCE = 1;
    public static final Integer TYPE_STUDENT_FILE = 2;

    //�ļ���
    private String name;

    //�ļ���׺
    private String suffix;

    //�ļ�����·��
    private String path;

    //�ļ�URL
    private String url;

    //�ļ�������
    private Integer type;

    //�ļ���С
    private Long size;

    //�ϴ���id
    private String uploaderId;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
