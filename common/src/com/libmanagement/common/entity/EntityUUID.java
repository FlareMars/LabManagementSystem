package com.libmanagement.common.entity;

import com.libmanagement.common.utils.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class EntityUUID implements Serializable {
    @Id
    @Column(length = 32)
    private String id = StringUtils.INSTANCE.generateUUID();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
