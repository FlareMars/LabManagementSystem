package com.libmanagement.admin.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libmanagement.admin.common.AdminWebBean;
import com.libmanagement.admin.common.Result;
import com.libmanagement.entity.Classes;
import com.libmanagement.entity.StudentUser;
import com.libmanagement.entity.TeacherUser;
import com.libmanagement.service.ClassesService;
import com.libmanagement.service.StudentUserService;
import com.libmanagement.service.TeacherUserService;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.util.SystemOutLogger;
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
@RequestMapping("/classes")
public class ClassWeb extends AdminWebBean {
    private static Log logger = LogFactory.getLog(ClassWeb.class);

    @Autowired
    private ClassesService classesService;

    @RequestMapping("/list_all_classes")
    public @ResponseBody List<Classes> listAllClasses(){
        List<Classes> list = classesService.listAllClasses();
        StringBuilder sb = new StringBuilder("[\n");
        System.out.println("Here");
        System.out.println("Here");
        System.out.println(list);
        return list;
    }

}
