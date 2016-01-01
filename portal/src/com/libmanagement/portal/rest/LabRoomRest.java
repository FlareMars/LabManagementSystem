package com.libmanagement.portal.rest;

import com.libmanagement.entity.LabRoom;
import com.libmanagement.entity.LabRoomUsage;
import com.libmanagement.portal.web.common.RestBaseBean;
import com.libmanagement.portal.web.common.Result;
import com.libmanagement.service.LabRoomService;
import com.libmanagement.service.LabRoomUsageService;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by FlareMars on 2016/1/1
 */
@Controller
@RequestMapping("/labroom")
public class LabRoomRest extends RestBaseBean {

    private Log logger = LogFactory.getLog(LabRoomRest.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    @Autowired
    private LabRoomService labRoomService;

    @Autowired
    private LabRoomUsageService labRoomUsageService;

    @RequestMapping("/getByType")
    public @ResponseBody
    Result getLabRoomList(@RequestParam("type")Integer type) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("获取实验室列表成功");

        List<LabRoom> list = labRoomService.getByType(type);
        Integer count = list.size();
        List<LabRoomBean> dataList = new ArrayList<>(count);
        for (LabRoom temp : list) {
            LabRoomBean bean = new LabRoomBean();
            bean.labRoomId = temp.getId();
            bean.name = temp.getDepartment() + temp.getRoomNumber();
            dataList.add(bean);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",count);
        jsonObject.put("array",dataList);
        result.setData(jsonObject);

        return result;
    }

    @RequestMapping("/getInvalidTime")
    public @ResponseBody
    Result getInvalidTime(@RequestParam("labRoomId")String labRoomId) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("获取不可用时间成功");

        List<LabRoomUsage> list = labRoomUsageService.findByDate(labRoomId);
        Integer count = list.size();
        List<InvalidTimeBean> dataList = new ArrayList<>(count);
        for (LabRoomUsage temp : list) {
            InvalidTimeBean bean = new InvalidTimeBean();
            bean.date = DATE_FORMAT.format(temp.getTargetDate());
            bean.time = temp.getTargetTime();
            dataList.add(bean);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",count);
        jsonObject.put("array",dataList);
        result.setData(jsonObject);

        return result;
    }

    private class InvalidTimeBean {
        private String date;

        private Integer time;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }
    }

    private class LabRoomBean {
        private String name;

        private String labRoomId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLabRoomId() {
            return labRoomId;
        }

        public void setLabRoomId(String labRoomId) {
            this.labRoomId = labRoomId;
        }
    }
}
