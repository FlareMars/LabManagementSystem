package com.libmanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 * 低值易耗品
 */
@Entity
@Table(name = "lms_consumption_goods")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumptionGoods extends Describertable {
    //名字
    private String name;

    //型号
    private String model;

    //额外信息
    private String information;

    //库存总量
    private Integer totalStock;

    //库存情况列表
    @OneToMany
    @JoinColumn(name = "consumption_goods_id")
    private List<ConsumptionGoodsUsage> usageList;

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

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Integer getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Integer totalStock) {
        this.totalStock = totalStock;
    }

    public List<ConsumptionGoodsUsage> getUsageList() {
        return usageList;
    }

    public void setUsageList(List<ConsumptionGoodsUsage> usageList) {
        this.usageList = usageList;
    }
}
