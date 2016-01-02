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

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 * 用户管理控制器
 */
@Controller
@RequestMapping("/system_notice")
public class SystemNoticeWeb extends AdminWebBean {
    private static Log logger = LogFactory.getLog(SystemNoticeWeb.class);

    @Autowired
    private SystemNoticeService systemNoticeService;

    @RequestMapping("/system_notice_page")
    public String listSystemNotice() {
        return "/pages/system_notice/system_notice_list";
    }

    @RequestMapping("/system_notice_list")
    public @ResponseBody
    Result listSystemNotices() {

        List<SystemNotice> systemNoticeList = systemNoticeService.listSystemNotices();
        Result result = new Result();
        ObjectMapper mapper = new ObjectMapper();
        try {
            result.setData(mapper.writeValueAsString(systemNoticeList));
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

            SystemNotice tempEntity;
            for (Object aSource : source) {
                temp = (LinkedHashMap<String, Object>) aSource;
                tempEntity = mapper.readValue(mapper.writeValueAsString(temp), SystemNotice.class);
                if (temp.containsKey("addFlag")) {
                    systemNoticeService.addSystemNotice(tempEntity);
                } else {
                    systemNoticeService.updateSystemNotice(tempEntity);
                }
            }

        }catch(IOException e){
            e.printStackTrace();
            result.setStatusCode(210);
            result.setMessage("json parse fail~");
        }
        return result;
    }

    @RequestMapping("/deletedata")
    public @ResponseBody
    Result deletePipelineChapter(@RequestParam(value = "id") String id) {
        Result result = new Result();
        if (id.contains(",")) {
            String [] ids = id.split(",");
            List<SystemNotice> tempList = new ArrayList<>();
            SystemNotice tempEntity;
            for(String temp : ids) {
                tempEntity = new SystemNotice();
                tempEntity.setId(temp);
                tempList.add(tempEntity);
            }
            systemNoticeService.deleteSystemNotices(tempList);
        } else {
            systemNoticeService.deleteSystemNotice(id);
        }
        result.setStatusCode(200);
        result.setMessage("ok");
        return result;
    }
}
