package com.libmanagement.entity;

import com.libmanagement.common.entity.Describertable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by FlareMars on 2015/11/28
 * 低值品库存情况表
 */
@Entity
@Table(name = "lms_consumption_goods_usage")
public class ConsumptionGoodsUsage extends Describertable {

    //库存入
    public static final Integer TYPE_IN = 1;
    //库存出
    public static final Integer TYPE_OUT = 2;

    //使用类别
    private Integer type;

    //作用数目
    private Integer quantity;

    //具体去/来向
    private String detail;

    @Column(name = "consumption_goods_id")
    private String consumptionGoodsId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getConsumptionGoodsId() {
        return consumptionGoodsId;
    }

    public void setConsumptionGoodsId(String consumptionGoodsId) {
        this.consumptionGoodsId = consumptionGoodsId;
    }
}
