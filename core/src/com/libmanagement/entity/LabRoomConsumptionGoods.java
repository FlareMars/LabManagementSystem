package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
}
