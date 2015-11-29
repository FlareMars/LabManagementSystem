package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * Created by FlareMars on 2015/11/28
 * ʵ�����豸����
 */
@Entity
@Table(name = "lab_room_equipment")
public class LabRoomEquipment extends Describertable {

    //�豸��������
    @OneToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    //����
    private Integer quantity = 1;

    //�����Ƿ���Ҫ������λ�ļ�¼

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
