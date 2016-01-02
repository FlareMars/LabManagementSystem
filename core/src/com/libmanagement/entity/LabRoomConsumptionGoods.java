package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;

/**
 * Created by FlareMars on 2015/11/28
 */
@Entity
@Table(name = "lab_room_consumption_goods")
public class LabRoomConsumptionGoods extends Describertable {

    //�豸��������
    @OneToOne
    @JoinColumn(name = "consumption_goods_id")
    private ConsumptionGoods consumptionGoods;

    //����
    private Integer quantity = 1;

    //ʵ����
    @Column(name="lab_room_id")
    private String labRoomId;

    //�����Ƿ���Ҫ������λ�ļ�¼
    public ConsumptionGoods getConsumptionGoods() {
        return consumptionGoods;
    }

    public void setConsumptionGoods(ConsumptionGoods consumptionGoods) {
        this.consumptionGoods = consumptionGoods;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getLabRoomId() {
        return labRoomId;
    }

    public void setLabRoomId(String labRoomId) {
        this.labRoomId = labRoomId;
    }
}
