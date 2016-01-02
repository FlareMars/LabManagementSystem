package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.*;

/**
 * Created by FlareMars on 2015/11/28
 */
@Entity
@Table(name = "lab_room_consumption_goods")
public class LabRoomConsumptionGoods extends Describertable {

    //设备仪器对象
    @OneToOne
    @JoinColumn(name = "consumption_goods_id")
    private ConsumptionGoods consumptionGoods;

    //数量
    private Integer quantity = 1;

    //实验室
    @Column(name="lab_room_id")
    private String labRoomId;

    //考虑是否需要计量单位的记录
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
