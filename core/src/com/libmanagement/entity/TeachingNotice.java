package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/30
 * ��ѧ����
 */
@Entity
@Table(name = "lms_teaching_notice")
public class TeachingNotice extends Describertable {

    //���������� �༶���߸���
    public static final Integer TO_CLASS = 1;
    public static final Integer TO_PERSON = 2;

    //����
    private String title;

    //����
    private String content;

    //���淢����
    @OneToOne
    private TeacherUser sender;

    //���͵�
    private Integer sendToWhat;

    //Ŀ����Ⱥ�����id
    private String targetId;

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

    public TeacherUser getSender() {
        return sender;
    }

    public void setSender(TeacherUser sender) {
        this.sender = sender;
    }

    public Integer getSendToWhat() {
        return sendToWhat;
    }

    public void setSendToWhat(Integer sendToWhat) {
        this.sendToWhat = sendToWhat;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }
}
