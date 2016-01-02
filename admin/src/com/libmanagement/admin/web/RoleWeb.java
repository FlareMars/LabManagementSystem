package com.libmanagement.admin.web;

import com.libmanagement.admin.common.AdminWebBean;
import com.libmanagement.entity.Academy;
import com.libmanagement.entity.Role;
import com.libmanagement.service.AcademyService;
import com.libmanagement.service.RoleService;
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
@RequestMapping("/role")
public class RoleWeb extends AdminWebBean {
    private static Log logger = LogFactory.getLog(RoleWeb.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list_all_roles")
    public @ResponseBody List<Map<String,String>> listAllAcademies(){
        List<Role> list = roleService.listAllRoles();
        List<Map<String,String>> beanList = new ArrayList<>(list.size());
        for (Role temp : list) {
            Map<String,String> bean = new HashMap<>();
            bean.put(temp.getId(),temp.getRoleName());
            beanList.add(bean);
        }
        return beanList;
    }
}
