package com.libmanagement.service;

import com.libmanagement.entity.Academy;
import com.libmanagement.entity.Role;
import com.libmanagement.repository.AcademyRepository;
import com.libmanagement.repository.RoleRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FlareMars on 2016/1/1
 */
@Service
public class RoleService {

    private Log logger = LogFactory.getLog(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name) {
        List<Role> temp = roleRepository.findByName(name);
        System.out.println(name);
        if (temp.size() > 0) {
            return temp.get(0);
        } else {
            System.out.println("查找学院失败");
            return null;
        }
    }

    public List<Role> listAllRoles(){
        return roleRepository.listAllRoles();
    }
}
