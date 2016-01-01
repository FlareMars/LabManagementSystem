package com.libmanagement.service;

import com.libmanagement.entity.StudentUser;
import com.libmanagement.entity.TeacherUser;
import com.libmanagement.repository.StudentUserRepository;
import com.libmanagement.repository.TeacherUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Integer studentNumInClass(String classId) {
        return studentUserRepository.studentNumInClass(classId);
    }

    public List<StudentUser> listByClassId(String classId) {
        return studentUserRepository.listByClassId(classId);
    }
}
