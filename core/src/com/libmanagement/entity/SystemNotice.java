package com.libmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.libmanagement.common.entity.Describertable;
import org.junit.Ignore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/28
 * 系统公告实体类
 */
@Entity
@Table(name="lms_system_notice")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemNotice extends Describertable {

    //公告标题
    private String title;

    //公告内容
    private String content;

    //操作员
    @ManyToOne
    private User operator;

    private String operatorName;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }
}
