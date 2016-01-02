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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/equipment")
public class EquipmentWeb extends AdminWebBean {
    private static Log logger = LogFactory.getLog(EquipmentWeb.class);

    @Autowired
    private EquipmentService equipmentService;

    @RequestMapping("/equipment_page")
    public String listEquipment() {
        return "/pages/equipment/equipment_list";
    }

    @RequestMapping("/equipment_list")
    public @ResponseBody
    Result listEquipments() {

        List<Equipment> equipmentList = equipmentService.listEquipments();
        Result result = new Result();
        ObjectMapper mapper = new ObjectMapper();
        try {
            result.setData(mapper.writeValueAsString(equipmentList));
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

            Equipment tempEntity;
            for (Object aSource : source) {
                temp = (LinkedHashMap<String, Object>) aSource;
                tempEntity = mapper.readValue(mapper.writeValueAsString(temp), Equipment.class);
                if (temp.containsKey("addFlag")) {
                    equipmentService.addEquipment(tempEntity);
                } else {
                    equipmentService.updateEquipment(tempEntity);
                }
            }

        }catch(IOException e){
            e.printStackTrace();
            result.setStatusCode(210);
            result.setMessage("json parse fail~");
        }
        return result;
    }

    @RequestMapping("/usage_statement")
    public String usageStatement(Model model,@RequestParam("equipment")String id) {
        model.addAttribute("equipmentId",id);
        Equipment equipment = equipmentService.findById(id);
        Integer number = equipment.getNumber();
        String numberStr = "";
        if (number != null) {
            numberStr = "_" + number;
        }
        model.addAttribute("name","【" +
                equipment.getName() + ":" +
                equipment.getModel() +
                numberStr +
                "】");
        return "/pages/equipment/usage_statement";
    }

    @RequestMapping("/equipment_usage_list")
    public @ResponseBody
    Result listUsage(@RequestParam("equipmentId")String equipmentId) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("get usage list success");
        ObjectMapper mapper = new ObjectMapper();
        try {
            result.setData(mapper.writeValueAsString(equipmentService.listUsage(equipmentId)));
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setStatusCode(210);
        }
        return result;
    }

    @RequestMapping("/doUsage")
    public @ResponseBody
    Result doUsage() {
        Result result = new Result();
        result.setMessage("success");
        result.setStatusCode(200);

        return result;
    }

    @RequestMapping("/equipmentList")
    public String equipmentList(Model model,@RequestParam("labRoomId")String labRoomId,
                            @RequestParam(value = "name",required = false)String name) {
        model.addAttribute("labRoomId",labRoomId);
        Page<Equipment> equipmentPage;
        if (name == null || name.equals("")) {
            equipmentPage = equipmentService.listEquipmentsPage(labRoomId);
        } else {
            equipmentPage = equipmentService.listEquipmentsPageByName(labRoomId,"%" + name + "%");
        }
        model.addAttribute("equipmentPage",equipmentPage);
        return "/pages/equipment/equipmentList";
    }
}
