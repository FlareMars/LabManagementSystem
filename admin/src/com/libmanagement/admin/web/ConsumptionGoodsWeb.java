package com.libmanagement.admin.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libmanagement.admin.common.AdminWebBean;
import com.libmanagement.admin.common.Result;
import com.libmanagement.entity.*;
import com.libmanagement.repository.ConsumptionGoodsUsageRepository;
import com.libmanagement.service.*;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 * 用户管理控制器
 */
@Controller
@RequestMapping("/consumption_goods")
public class ConsumptionGoodsWeb extends AdminWebBean {
    private static Log logger = LogFactory.getLog(ConsumptionGoodsWeb.class);

    @Autowired
    private ConsumptionGoodsService consumptionGoodsService;

    @Autowired
    private ConsumptionGoodsUsageService usageService;

    @RequestMapping("/consumption_goods_page")
    public String listConsumptionGood() {
        return "/pages/consumption_goods/consumption_goods_list";
    }

    @RequestMapping("/consumption_goods_list")
    public @ResponseBody
    Result listConsumptionGoods() {

        List<ConsumptionGoods> consumptionGoodsList = consumptionGoodsService.listConsumptionGoods();
        for (ConsumptionGoods goods : consumptionGoodsList) {
            goods.setName(goods.getName() + "_" + goods.getId());
        }
        Result result = new Result();
        ObjectMapper mapper = new ObjectMapper();
        try {
            result.setData(mapper.writeValueAsString(consumptionGoodsList));
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setStatusCode(210);
        }
        return result;
    }


    @RequestMapping("/editdata")
    public @ResponseBody
    Result editData(@RequestParam("json") String json) {
        ObjectMapper mapper = new ObjectMapper();
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("ok");
        try {
            List<LinkedHashMap<String, Object>> source = mapper.readValue(json, List.class);
            LinkedHashMap<String, Object> temp;

            ConsumptionGoods tempEntity;
            for (Object aSource : source) {
                temp = (LinkedHashMap<String, Object>) aSource;
                tempEntity = mapper.readValue(mapper.writeValueAsString(temp), ConsumptionGoods.class);
                if (temp.containsKey("addFlag")) {
                    consumptionGoodsService.addConsumptionGoods(tempEntity);
                } else {
                    consumptionGoodsService.updateConsumptionGoods(tempEntity);
                }
            }

        }catch(IOException e){
            e.printStackTrace();
            result.setStatusCode(210);
            result.setMessage("json parse fail~");
        }
        return result;
    }

    @RequestMapping("/modifyStock")
    public @ResponseBody
    Result modifyStock(@RequestParam("id")String id,@RequestParam("type")Integer type,
                        @RequestParam("number")Integer number,
                            @RequestParam("detail")String detail) {
        Result result = new Result();
        result.setMessage("modify success");
        result.setStatusCode(200);
        if (consumptionGoodsService.modifyStock(id,type,number)) {
            ConsumptionGoodsUsage goodsUsage = new ConsumptionGoodsUsage();
            goodsUsage.setConsumptionGoodsId(id);
            goodsUsage.setDetail(detail);
            goodsUsage.setQuantity(number);
            goodsUsage.setType(type);
            usageService.addUsage(goodsUsage);
        } else {
            result.setStatusCode(210);
            if (type == ConsumptionGoodsUsage.TYPE_OUT) {
                result.setMessage("current stock is smaller than target number");
            } else {
                result.setMessage("fail");
            }
        }


        return result;
    }
}
