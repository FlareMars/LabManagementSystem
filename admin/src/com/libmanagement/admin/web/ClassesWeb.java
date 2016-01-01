package com.libmanagement.admin.web;

import com.libmanagement.admin.common.AdminWebBean;
import com.libmanagement.entity.Classes;
import com.libmanagement.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/2
 */
@Controller
@RequestMapping("/fdfd")
public class ClassesWeb extends AdminWebBean {

    @Autowired
    private ClassesService classesService;

    @RequestMapping("/list_all_classes")
    public @ResponseBody List<Classes> getAllClasses() {
        return classesService.listAll();
    }
}
