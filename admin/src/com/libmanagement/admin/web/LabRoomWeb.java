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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by FlareMars on 2015/11/28
 * 用户管理控制器
 */
@Controller
@RequestMapping("/labroom")
public class LabRoomWeb extends AdminWebBean {
    private static Log logger = LogFactory.getLog(LabRoomWeb.class);

    @Autowired
    private LabRoomService labRoomService;

    @RequestMapping("/labroom_page")
    public String listLabRoom() {
        return "/pages/labroom/labroom_list";
    }

    @RequestMapping("/labroom_list")
    public @ResponseBody
    Result listLabRooms() {

        List<LabRoom> labRoomList = labRoomService.listLabRooms();
        Result result = new Result();
        ObjectMapper mapper = new ObjectMapper();
        try {
            result.setData(mapper.writeValueAsString(labRoomList));
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setStatusCode(210);
        }
        return result;
    }

    @RequestMapping("/list_all_labrooms")
    public @ResponseBody List<Map<String,String>> listAllLabRooms(){
        List<LabRoom> list = labRoomService.listLabRooms();
        List<Map<String,String>> beanList = new ArrayList<>(list.size());
        for (LabRoom temp : list) {
            Map<String,String> bean = new HashMap<>();
            bean.put(temp.getId(),temp.getRoomNumber());
            beanList.add(bean);
        }
        return beanList;
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
