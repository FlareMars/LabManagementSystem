package com.libmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 */
@Entity
@Table(name = "lms_equipment")
public class Equipment extends Describertable {

    //设备名字
    private String name;

    //设备型号
    private String model;

    //设备编号
    private Integer number;

    //设备功能简介
    private String function;

    //所在位置（实验室或者库存室)
    @OneToOne
    @JoinColumn(name = "lab_room_id")
    private LabRoom labRoom;

    //设备使用情况
    @OneToMany
    @JoinColumn(name = "equipment_id")
    @JsonIgnore
    private List<EquipmentUsage> usages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<EquipmentUsage> getUsages() {
        return usages;
    }

    public void setUsages(List<EquipmentUsage> usages) {
        this.usages = usages;
    }

    public LabRoom getLabRoom() {
        return labRoom;
    }

    public void setLabRoom(LabRoom labRoom) {
        this.labRoom = labRoom;
    }
}
