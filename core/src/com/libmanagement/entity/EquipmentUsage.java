package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/28
 * �����豸ʹ�������
 */
@Entity
@Table(name = "lms_equipment_usage")
public class EquipmentUsage extends Describertable {

    //�������¼��ϵͳ
    public static final Integer TYPE_ADD = 1;
    //�����������λ��ת��
    public static final Integer TYPE_MOVE = 2;
    //�������ά��
    public static final Integer TYPE_REPAIR = 3;
    //�����������
    public static final Integer TYPE_ABANDON = 4;

    //ʹ������
    private Integer usageType;

    //�����豸
    @Column(name = "equipment_id")
    private String equipmentId;

    //ʹ������
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
