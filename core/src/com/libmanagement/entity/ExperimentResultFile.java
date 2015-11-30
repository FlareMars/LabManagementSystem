package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/29
 * 学生实验结论文件
 */
@Entity
@Table(name = "lms_experiment_result_file")
public class ExperimentResultFile extends Describertable {
    //文件名
    private String name;

    //文件后缀
    private String suffix;

    //文件物理路径
    private String path;

    //文件URL
    private String url;

    //文件大小
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
