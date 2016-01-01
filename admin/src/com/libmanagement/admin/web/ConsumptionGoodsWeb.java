package com.libmanagement.admin.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libmanagement.admin.common.AdminWebBean;
import com.libmanagement.admin.common.Result;
import com.libmanagement.entity.*;
import com.libmanagement.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/consumption_goods_page")
    public String listConsumptionGood() {
        return "/pages/consumption_goods/consumption_goods_list";
    }

    @RequestMapping("/consumption_goods_list")
    public @ResponseBody
    Result listConsumptionGoods() {

        List<ConsumptionGoods> consumptionGoodsList = consumptionGoodsService.listConsumptionGoods();
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
    Result editData(@RequestParam("json") String json, @RequestParam("type") String type) {
        System.out.println(json);
        System.out.println(type);
        Result result = new Result(Result.CODE_OK,"test");

        return result;
    }
}
