package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/28
 * 系统公告实体类
 */
@Entity
@Table(name="lms_system_notice")
public class SystemNotice extends Describertable {

    //公告标题
    private String title;

    //公告内容
    private String content;

    //操作员
    @ManyToOne
    private User operator;

    //创建时间 隐含在基类Describertable
    //createTime

    //附近文件列表，有时间再考虑做不做
    //List<LMSFile> attachments

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
