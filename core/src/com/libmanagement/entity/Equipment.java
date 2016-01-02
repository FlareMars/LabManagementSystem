package com.libmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 */
@Entity
@Table(name = "lms_equipment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Equipment extends Describertable {

    //�豸����
    private String name;

    //�豸�ͺ�
    private String model;

    //�豸���
    private Integer number;

    //�豸���ܼ��
    private String function;

    //����λ�ã�ʵ���һ��߿����)
    @OneToOne
    @JoinColumn(name = "lab_room_id")
    private LabRoom labRoom;

    private String position;

    //�豸ʹ�����
    @OneToMany
    @JoinColumn(name = "equipment_id")
    @JsonIgnore
    private List<EquipmentUsage> usages;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

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
