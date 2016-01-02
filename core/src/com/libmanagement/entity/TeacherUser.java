package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/28
 * ��ʦ�û���
 */
@Entity
@Table(name = "lms_teacher_user")
public class TeacherUser extends Describertable {

    //�û���(һ��Ϊ����)
    private String username;

    //����
    private String password;

    //���ʺ�
    private String teacherNumber;

    //ѧУ�����˺�����
    private String teacherPassword;

    //��ʵ����
    private String realName;

    //����ѧԺ
    @OneToOne
    @JoinColumn(name = "current_academy")
    private Academy currentAcademy;

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

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public String getTeacherPassword() {
        return teacherPassword;
    }

    public void setTeacherPassword(String teacherPassword) {
        this.teacherPassword = teacherPassword;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Academy getCurrentAcademy() {
        return currentAcademy;
    }

    public void setCurrentAcademy(Academy currentAcademy) {
        this.currentAcademy = currentAcademy;
    }

    public String getCurrentAcademyName() {
        return currentAcademy.getName() + "_" + currentAcademy.getId();
    }
}
