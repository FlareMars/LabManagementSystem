package com.libmanagement.service;

import com.libmanagement.entity.StudentUser;
import com.libmanagement.repository.StudentUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 * 封装老师数据操作服务
 */
@Service
public class StudentUserService {

    @Autowired
    private StudentUserRepository studentUserRepository;

    public List<StudentUser> listStudents() {
        return studentUserRepository.listStudents();
    }

    public String addStudentUser(StudentUser temp) {
        //todo 检查参数

        studentUserRepository.save(temp);
        return temp.getId();
    }

    public void updateStudentUser(StudentUser data) {
        List<StudentUser> temp = studentUserRepository.findByUsername(data.getUsername());
        if(temp.size() > 0){
            StudentUser entity = temp.get(0);
            entity.setCurrentClass(data.getCurrentClass());
            entity.setUsername(data.getUsername());
            studentUserRepository.save(entity);
        }
    }

    public boolean deleteStudentUser(List<String> ids) {
        List<StudentUser> list = new ArrayList<>(ids.size());
        for (int i = 0;i < ids.size();i++) {
            list.get(i).setId(ids.get(i));
        }
        studentUserRepository.delete(list);
        return true;
    }

    public boolean isValidStudent (StudentUser temp) {
        return true;
    }
}
