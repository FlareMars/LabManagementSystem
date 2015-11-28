package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/28
 * ѧ���û���
 */
@Entity
@Table(name = "lms_student_user")
public class StudentUser extends Describertable {

    //�û���(һ��Ϊ����)
    private String username;

    //����
    private String password;

    //ѧ��
    private String studentNumber;

    //ѧУ�����˺�����
    private String studentPassword;

    //��ʵ����
    private String realName;

    //��ǰ���ڰ༶
    private String currentClassId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCurrentClassId() {
        return currentClassId;
    }

    public void setCurrentClassId(String currentClassId) {
        this.currentClassId = currentClassId;
    }
}
