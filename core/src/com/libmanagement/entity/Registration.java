package com.libmanagement.entity;



import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author FlareMars
 *
 */
@Entity
@Table(name="lms_registration")
public class Registration extends Describertable {
    public static final int SEX_MALE = 1;
    public static final int SEX_FEMALE = 2;
    private static final long serialVersionUID = 4074469052331564678L;

    private String email;
    private String contactPhone;
    private String name;
    private String title;
    private Integer sex;
    private String company;
    private String comments;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}