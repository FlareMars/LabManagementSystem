package com.libmanagement.admin.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libmanagement.admin.common.AdminWebBean;
import com.libmanagement.admin.common.Result;
import com.libmanagement.entity.*;
import com.libmanagement.repository.LabRoomRepository;
import com.libmanagement.service.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;

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

    @Autowired
    private LabRoomUsageService labRoomUsageService;

    @Autowired
    private LabRoomConsumptionGoodsService labRoomConsumptionGoodsService;

    @Autowired
    private LabRoomEquipmentService labRoomEquipmentService;

    @Autowired
    private ConsumptionGoodsService consumptionGoodsService;

    @Autowired
    private ConsumptionGoodsUsageService consumptionGoodsUsageService;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentUsageService equipmentUsageService;

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
            bean.put(temp.getDepartment() + temp.getRoomNumber(),temp.getDepartment() + temp.getRoomNumber());
            beanList.add(bean);
        }
        return beanList;
    }

    @RequestMapping("/labroom_usage_page")
    public String listLabRoomUsage(@RequestParam("labRoomId") String labRoomId, Model model) {
        model.addAttribute("labRoomId", labRoomId);
        return "/pages/labroom/lab_usage_statement";
    }

    @RequestMapping("/list_usage")
    public @ResponseBody Result listUsage(@RequestParam("labRoomId") String labRoomId){
        List<LabRoomUsage> usageList = labRoomUsageService.findByRoomId(labRoomId);
        Result result = new Result();
        ObjectMapper mapper = new ObjectMapper();
        try {
            for(LabRoomUsage temp: usageList) {
                if (!temp.getStatus().equals(LabRoomUsage.TYPE_FINISHED)) {
                    Date current = new Date();
                    Calendar target = Calendar.getInstance();
                    target.setTime(temp.getTargetDate());
                    target.set(Calendar.HOUR_OF_DAY, LabRoomUsage.END_HOURS[temp.getTargetTime()]);
                    target.set(Calendar.MINUTE, LabRoomUsage.END_MINUTES[temp.getTargetTime()]);
                    if(current.after(target.getTime())){
                        temp.setStatus(LabRoomUsage.TYPE_FINISHED);
                        labRoomUsageService.updateUsage(temp);
                    }
                }
            }
            result.setData(mapper.writeValueAsString(usageList));
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setStatusCode(210);
        }
        return result;
    }

    @RequestMapping("/lab_equipment_own_page")
    public String listLabRoomEquipmentUsage(@RequestParam("labRoomId") String labRoomId, Model model) {
        model.addAttribute("labRoomId", labRoomId);
        LabRoom labRoom = labRoomService.findById(labRoomId);
        model.addAttribute("title","【" + labRoom.getDepartment() + labRoom.getRoomNumber() + "】仪器设备列表");
        return "/pages/labroom/lab_equipment_own_statement";
    }

    @RequestMapping("/lab_equipment_own_statement")
    public @ResponseBody Result listEquipment(@RequestParam("labRoomId") String labRoomId){
        List<LabRoomEquipment> equipmentList = labRoomEquipmentService.findByRoomId(labRoomId);
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

    @RequestMapping("/lab_consumption_goods_own_page")
    public String listLabRoomConsumptionGoodsUsage(@RequestParam("labRoomId") String labRoomId, Model model) {
        model.addAttribute("labRoomId", labRoomId);
        LabRoom labRoom = labRoomService.findById(labRoomId);
        model.addAttribute("title","【" + labRoom.getDepartment() + labRoom.getRoomNumber() + "】消耗品列表");
        return "/pages/labroom/lab_consumption_goods_own_statement";
    }

    @RequestMapping("/lab_consumption_goods_own_statement")
    public @ResponseBody Result listConsumptionGoods(@RequestParam("labRoomId") String labRoomId){
        List<LabRoomConsumptionGoods> usageList = labRoomConsumptionGoodsService.findByRoomId(labRoomId);
        Result result = new Result();
        ObjectMapper mapper = new ObjectMapper();
        try {
            result.setData(mapper.writeValueAsString(usageList));
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setStatusCode(210);
        }
        return result;
    }

    @RequestMapping("/modifyConsumptionGoodsStock")
    public String modifyConsumptionGoodsStock(Model model,
                                                @RequestParam("middleId")String middleId){
        LabRoomConsumptionGoods entity = labRoomConsumptionGoodsService.findById(middleId);
        ConsumptionGoods goods = entity.getConsumptionGoods();
        model.addAttribute("middleId", middleId);
        model.addAttribute("stockNum", goods.getTotalStock());
        return "/pages/labroom/modifyConsumptionGoodsStock";
    }

    @RequestMapping("/submitConsumptionGoodsStock")
    public @ResponseBody Result submitConsumptionGoodsStock(Model model,
                                              @RequestParam("middleId")String middleId,
                                                    @RequestParam("number")Integer number,
                                                        @RequestParam("detail")String detail){
        LabRoomConsumptionGoods entity = labRoomConsumptionGoodsService.findById(middleId);
        ConsumptionGoods goods = entity.getConsumptionGoods();
        Result result = new Result(Result.CODE_OK, "Success");
        if (consumptionGoodsService.modifyStock(goods.getId(),ConsumptionGoodsUsage.TYPE_OUT,number)) {
            ConsumptionGoodsUsage goodsUsage = new ConsumptionGoodsUsage();
            goodsUsage.setConsumptionGoodsId(goods.getId());
            goodsUsage.setDetail(detail);
            goodsUsage.setQuantity(number);
            goodsUsage.setType(ConsumptionGoodsUsage.TYPE_OUT);
            consumptionGoodsUsageService.addUsage(goodsUsage);
        } else {

            result.setStatusCode(Result.CODE_ERROR);
            result.setMessage("Fuck Java");
        }

        entity.setQuantity(entity.getQuantity()+number);
        labRoomConsumptionGoodsService.update(entity);
        return result;
    }

    @RequestMapping("/addConsumptionGoodsAction")
    public @ResponseBody
    Result addConsumptionGoodsAction(@RequestParam("goodsId")String goodsId,@RequestParam("labRoomId")String labRoomId) {
        Result result = new Result(Result.CODE_OK,"success");
        ConsumptionGoods goods = consumptionGoodsService.findById(goodsId);
        LabRoomConsumptionGoods labRoomGoods = new LabRoomConsumptionGoods();
        labRoomGoods.setConsumptionGoods(goods);
        labRoomGoods.setLabRoomId(labRoomId);
        labRoomGoods.setQuantity(0);
        labRoomConsumptionGoodsService.addLabRoomGoods(labRoomGoods);
        return result;
    }

    @RequestMapping("/addEquipmentAction")
         public @ResponseBody
         Result addEquipmentAction(@RequestParam("equipmentId")String equipmentId,@RequestParam("labRoomId")String labRoomId) {
        Result result = new Result(Result.CODE_OK,"success");
        Equipment equipment = equipmentService.findById(equipmentId);
        String oldPosition = equipment.getPosition();

        List<LabRoomEquipment> oldMiddleEntity = labRoomEquipmentService.findByRoomIdAndEquipmentId(equipment.getLabRoom().getId(),equipmentId);
        labRoomEquipmentService.delete(oldMiddleEntity);

        LabRoomEquipment labRoomEquipment = new LabRoomEquipment();
        labRoomEquipment.setEquipment(equipment);
        labRoomEquipment.setLabRoomId(labRoomId);
        labRoomEquipment.setQuantity(1);
        labRoomEquipmentService.addRoomEquipment(labRoomEquipment);

        LabRoom targetLabRoom = labRoomService.findById(labRoomId);
        equipment.setPosition(targetLabRoom.getDepartment()+targetLabRoom.getRoomNumber());
        equipment.setLabRoom(targetLabRoom);
        equipmentService.updateEquipment(equipment);

        EquipmentUsage equipmentUsage = new EquipmentUsage();
        equipmentUsage.setEquipmentId(equipmentId);
        equipmentUsage.setDetail("从 " + oldPosition + " 转移到 " + equipment.getPosition());
        equipmentUsage.setUsageType(EquipmentUsage.TYPE_MOVE);
        equipmentUsageService.addUsage(equipmentUsage);
        return result;
    }


    @RequestMapping("/editdata")
    public @ResponseBody
    Result editData(@RequestParam("json") String json) {
        ObjectMapper mapper = new ObjectMapper();
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("success");
        try {
            List<LinkedHashMap<String, Object>> source = mapper.readValue(json, List.class);
            LinkedHashMap<String, Object> temp;

            LabRoom tempEntity;
            for (Object aSource : source) {
                temp = (LinkedHashMap<String, Object>) aSource;
                tempEntity = mapper.readValue(mapper.writeValueAsString(temp), LabRoom.class);
                if (temp.containsKey("addFlag")) {
                    labRoomService.addLabRoom(tempEntity);
                } else {
                    labRoomService.updateLabRoom(tempEntity);
                }
            }

        }catch(IOException e){
            e.printStackTrace();
            result.setStatusCode(210);
            result.setMessage("json parse fail~");
        }
        return result;
    }
}
