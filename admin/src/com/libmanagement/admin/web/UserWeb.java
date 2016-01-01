package com.libmanagement.admin.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libmanagement.admin.common.AdminWebBean;
import com.libmanagement.admin.common.Result;
import com.libmanagement.entity.*;
import com.libmanagement.service.*;
import net.sf.json.JSONArray;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@RequestMapping("/user")
public class UserWeb extends AdminWebBean {
    private static Log logger = LogFactory.getLog(UserWeb.class);

    @Autowired
    private TeacherUserService teacherUserService;

    @RequestMapping("/teacher_page")
    public String listTeacher() {
        return "/pages/user/teacher_list";
    }

    @RequestMapping("/teacher_list")
    public @ResponseBody
    Result listTeachers() {

        List<TeacherUser> teacherList = teacherUserService.listTeachers();
        Result result = new Result();
        ObjectMapper mapper = new ObjectMapper();
        try {
            result.setData(mapper.writeValueAsString(teacherList));
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setStatusCode(210);
        }
        return result;
    }

    @Autowired
    private StudentUserService studentUserService;

    @RequestMapping("/student_page")
    public String listStudent() {
        return "/pages/user/student_list";
    }

    @RequestMapping("/student_list")
    public @ResponseBody
    Result listStudents() {

        List<StudentUser> studentList = studentUserService.listStudents();
        Result result = new Result();
        ObjectMapper mapper = new ObjectMapper();
        try {
            result.setData(mapper.writeValueAsString(studentList));
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result.setStatusCode(210);
        }
        return result;
    }


    @RequestMapping("/editdata")
    public @ResponseBody
    Result editData(@RequestParam("json") String json, @RequestParam("type") String type) {
        logger.debug(json);
        ObjectMapper mapper = new ObjectMapper();
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("ok");
        try {
            JSONArray source = mapper.readValue(json, JSONArray.class);
            LinkedHashMap<String, Object> temp;
            if (type.equals("student")) {
                StudentUser tempEntity;
                for (Object aSource : source) {
                    temp = (LinkedHashMap<String, Object>) aSource;
                    tempEntity = mapper.readValue(mapper.writeValueAsString(temp), StudentUser.class);
                    if (temp.containsKey("addFlag")) {
                        studentUserService.addStudentUser(tempEntity);
                    } else {
                        studentUserService.updateStudentUser(tempEntity);
                    }
                }
            }
            }catch(IOException e){
                e.printStackTrace();
                result.setStatusCode(210);
                result.setMessage("json parse fail~");
            }
        return result;
    }



    @RequestMapping("/deletedata")
    public @ResponseBody
    Result deleteData(@RequestParam("id") String id, @RequestParam("type") String type) {
        System.out.println(id);
        System.out.println(type);
        Result result = new Result(Result.CODE_OK,"test");

        return result;
    }

}
