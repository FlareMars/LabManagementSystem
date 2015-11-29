package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/28
 * 仪器设备使用情况表
 */
@Entity
@Table(name = "lms_equipment_usage")
public class EquipmentUsage extends Describertable {

    //类别：仪器录入系统
    public static final Integer TYPE_ADD = 1;
    //类别：仪器所在位置转移
    public static final Integer TYPE_MOVE = 2;
    //类别：仪器维修
    public static final Integer TYPE_REPAIR = 3;
    //类别：仪器报销
    public static final Integer TYPE_ABANDON = 4;

    //使用类型
    private Integer usageType;

    //所属设备
    @Column(name = "equipment_id")
    private String equipmentId;

    //使用详情
    private String detail;

    public Integer getUsageType() {
        return usageType;
    }

    public void setUsageType(Integer usageType) {
        this.usageType = usageType;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
