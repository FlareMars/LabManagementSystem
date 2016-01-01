package com.libmanagement.portal.rest;

import com.libmanagement.entity.Classes;
import com.libmanagement.portal.web.common.RestBaseBean;
import com.libmanagement.portal.web.common.Result;
import com.libmanagement.service.ClassesService;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
@Controller
@RequestMapping("/studentclass")
public class ClassesRest extends RestBaseBean {

    private Log logger = LogFactory.getLog(ClassesRest.class);

    @Autowired
    private ClassesService classesService;

    @RequestMapping("/getByAcademy")
    public @ResponseBody
    Result getClasses(@RequestParam("academy")String academyName) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("获取班级列表成功");

        try {
            academyName = URLDecoder.decode(academyName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Classes> list = classesService.getClassesByAcademy(academyName);
        Integer count = list.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",count);
        jsonObject.put("array",list);
        result.setData(jsonObject);

        return result;
    }
}
