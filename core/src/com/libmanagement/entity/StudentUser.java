package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/28
 * 学生用户表
 */
@Entity
@Table(name = "lms_student_user")
public class StudentUser extends Describertable {

    //用户名(一般为邮箱)
    private String username;

    //密码
    private String password;

    //学号
    private String studentNumber;

    //学校电子账号密码
    private String studentPassword;

    //真实名字
    private String realName;

    //当前所在班级
    @OneToOne
    @JoinColumn(name = "current_class_id")
    private Classes currentClass;

    public StudentUser() {

    }

    public StudentUser(String id) {
        setId(id);
    }



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

    public Classes getCurrentClass() {
        return currentClass;
    }

    public void setCurrentClass(Classes currentClass) {
        this.currentClass = currentClass;
    }

    public String getCurrentClassName() {
        return currentClass.getName()+"_"+currentClass.getId();
    }
}
