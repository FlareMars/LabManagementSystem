package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/28
 * 老师用户表
 */
@Entity
@Table(name = "lms_teacher_user")
public class TeacherUser extends Describertable {

    //用户名(一般为邮箱)
    private String username;

    //密码
    private String password;

    //工资号
    private String teacherNumber;

    //学校电子账号密码
    private String teacherPassword;

    //真实名字
    private String realName;

    //所在学院
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
