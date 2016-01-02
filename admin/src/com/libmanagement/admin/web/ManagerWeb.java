package com.libmanagement.admin.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libmanagement.admin.common.AdminWebBean;
import com.libmanagement.entity.Classes;
import com.libmanagement.entity.User;
import com.libmanagement.service.UserService;
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
 * Created by FlareMars on 2016/1/2
 */
@Controller
@RequestMapping("/roommanager")
public class ManagerWeb extends AdminWebBean {
    private Log logger = LogFactory.getLog(ManagerWeb.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/list_all_managers")
    public @ResponseBody
    List<Map<Object,String>> listAllManagers() {
        List<User> list = userService.listUsers();
        List<Map<Object,String>> beanList = new ArrayList<>(list.size());
        for (User temp : list) {
            Map<Object,String> bean = new HashMap<>();
            bean.put(temp.getRealName() + "_" + temp.getId(),temp.getRealName());
            beanList.add(bean);
        }
        return beanList;
    }
}
