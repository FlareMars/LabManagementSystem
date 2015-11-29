package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * Created by FlareMars on 2015/11/28
 * 实验室设备仪器
 */
@Entity
@Table(name = "lab_room_equipment")
public class LabRoomEquipment extends Describertable {

    //设备仪器对象
    @OneToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    //数量
    private Integer quantity = 1;

    //考虑是否需要计量单位的记录

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
