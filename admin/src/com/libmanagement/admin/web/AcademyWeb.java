package com.libmanagement.admin.web;

import com.libmanagement.admin.common.AdminWebBean;
import com.libmanagement.entity.Academy;
import com.libmanagement.entity.Classes;
import com.libmanagement.service.AcademyService;
import com.libmanagement.service.ClassesService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/academy")
public class AcademyWeb extends AdminWebBean {
    private static Log logger = LogFactory.getLog(AcademyWeb.class);

    @Autowired
    private AcademyService academyService;

    @RequestMapping("/list_all_academies")
    public @ResponseBody List<Map<String,String>> listAllAcademies(){
        List<Academy> list = academyService.listAllAcademies();
        List<Map<String,String>> beanList = new ArrayList<>(list.size());
        for (Academy temp : list) {
            Map<String,String> bean = new HashMap<>();
            bean.put(temp.getId(),temp.getName());
            beanList.add(bean);
        }
        return beanList;
    }
}
