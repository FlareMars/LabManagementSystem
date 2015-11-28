package com.libmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libmanagement.common.entity.EntityUUID;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 * ѧԺ��(�����)
 */
@Entity
@Table(name="lms_academy")
public class Academy extends EntityUUID {

    //ѧԺ����
    private String name;

    //�༶�б�
    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "academy_id")
    private List<Classes> classList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Classes> getClassList() {
        return classList;
    }

    public void setClassList(List<Classes> classList) {
        this.classList = classList;
    }
}
