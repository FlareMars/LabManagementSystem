package com.libmanagement.service;

import com.libmanagement.entity.TeacherUser;
import com.libmanagement.repository.TeacherUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by FlareMars on 2015/11/28
 * 封装老师数据操作服务
 */
@Service
public class TeacherUserService {

    @Autowired
    private TeacherUserRepository teacherUserRepository;

    public List<TeacherUser> listTeachers() {
        return teacherUserRepository.listTeachers();
    }
}
