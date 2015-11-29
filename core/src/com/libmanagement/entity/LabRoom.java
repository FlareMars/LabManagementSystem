package com.libmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 * 实验室数据表
 */
@Entity
@Table(name = "lms_lab_room")
public class LabRoom extends Describertable {

    public static final Integer TYPE_COMPUTER_SCIENCE = 1;
    public static final Integer TYPE_PHYSICS  = 2;
    public static final Integer TYPE_BIOLOGY = 3;
    public static final Integer TYPE_CHEMISTRY = 4;

    //所在大楼
    private String department;

    //课室号
    private String roomNumber;

    //课室简介
    private String description;

    //实验室类别
    private Integer type;

    //管理员
    @OneToOne
    private User manager;

    //使用情况列表
    @OneToMany
    @JoinColumn(name = "lab_room_id")
    @JsonIgnore
    private List<LabRoomUsage> usageList;

    //设备仪器列表
    @OneToMany
    @JoinColumn(name = "lab_room_id")
    @JsonIgnore
    private List<LabRoomEquipment> equipmentList;

    //低值品列表
    @OneToMany
    @JoinColumn(name = "lab_room_id")
    @JsonIgnore
    private List<LabRoomConsumptionGoods> consumptionGoodsList;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<LabRoomUsage> getUsageList() {
        return usageList;
    }

    public void setUsageList(List<LabRoomUsage> usageList) {
        this.usageList = usageList;
    }

    public List<LabRoomEquipment> getEquipmentList() {
        return equipmentList;
    }

    public void setEquipmentList(List<LabRoomEquipment> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<LabRoomConsumptionGoods> getConsumptionGoodsList() {
        return consumptionGoodsList;
    }

    public void setConsumptionGoodsList(List<LabRoomConsumptionGoods> consumptionGoodsList) {
        this.consumptionGoodsList = consumptionGoodsList;
    }
}
