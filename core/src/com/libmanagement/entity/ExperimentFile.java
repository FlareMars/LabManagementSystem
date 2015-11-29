package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

/**
 * Created by FlareMars on 2015/11/29
 * 实验课程相关文件，包括教学资源以及学生的成果文件
 */
public class ExperimentFile extends Describertable {

    public static final Integer TYPE_TEACHING_RESOURCE = 1;
    public static final Integer TYPE_STUDENT_FILE = 2;

    //文件名
    private String name;

    //文件后缀
    private String suffix;

    //文件物理路径
    private String path;

    //文件URL
    private String url;

    //文件大类型
    private Integer type;

    //文件大小
    private Long size;

    //上传者id
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
