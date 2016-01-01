package com.libmanagement.portal.rest;

import com.libmanagement.common.exception.LMSServerException;
import com.libmanagement.entity.*;
import com.libmanagement.portal.web.common.RestBaseBean;
import com.libmanagement.portal.web.common.Result;
import com.libmanagement.service.*;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by FlareMars on 2015/12/31
 */
@Controller
@RequestMapping("/experiment")
public class ExperimentProjectRest extends RestBaseBean {
    private Log logger = LogFactory.getLog(ExperimentProjectRest.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    @Autowired
    private ExperimentProjectService experimentProjectService;

    @Autowired
    private ExperimentPlanService experimentPlanService;

    @Autowired
    private LabRoomUsageService labRoomUsageService;

    @Autowired
    private ClassesService classesService;

    @Autowired
    private LabRoomService labRoomService;

    @Autowired
    private ExperimentLessionService experimentLessionService;

    @Autowired
    private ExperimentResultService experimentResultService;

    @Autowired
    private StudentUserService studentUserService;

    @RequestMapping("/getListByTeacherId")
    public @ResponseBody
    Result getNormalAnnouncements(@RequestParam("userId") String teacherId,
            @RequestParam("pageNum") Integer requestPageNum) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("获取成功");

        try {
            Long count = experimentProjectService.getExperimentsCount(teacherId);
            List<ExperimentProject> list = experimentProjectService.getExperimentProjects(teacherId,requestPageNum);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count",count);
            jsonObject.put("currentPage",requestPageNum);
            jsonObject.put("array",list);
            result.setData(jsonObject);
        } catch (LMSServerException e) {
            result.setStatusCode(210);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @RequestMapping("/postData")
    public @ResponseBody
    Result updateExperimentProject(@RequestParam("experimentName") String experimentName,
                                    @RequestParam("experimentContent") String experimentContent,
                                        @RequestParam("type") Integer category,
                                            @RequestParam("experimentId") String id,
                                                @RequestParam("userId") String teacherId) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("实验库添加/修改成功");

        try {
            ExperimentProject temp = new ExperimentProject();
            temp.setCategory(category.toString());
            temp.setBaseInfo(experimentContent);
            temp.setName(experimentName);
            temp.setId(id);
            TeacherUser teacherUser = new TeacherUser();
            teacherUser.setId(teacherId);
            temp.setTeacher(teacherUser);
            result.put("dataId", experimentProjectService.updateExperimentProject(temp));
        } catch (LMSServerException e) {

            result.setStatusCode(210);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/getExperimentFiles")
    public @ResponseBody
    Result getExperimentFiles(@RequestParam("experimentId") String id) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("获取实验资源文件成功");

        try {
            List<ExperimentResource> list = experimentProjectService.getExperimentResources(id);
            List<ExperimentResourceBean> dataList = new ArrayList<>(list.size());

            for (ExperimentResource temp : list) {
                ExperimentResourceBean bean = new ExperimentResourceBean();
                bean.name = temp.getName();
                bean.experimentId = temp.getExpeirmentId();
                bean.id = temp.getId();
                bean.size = temp.getSize();
                dataList.add(bean);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count",list.size());
            jsonObject.put("array",dataList);
            result.setData(jsonObject);
        } catch (LMSServerException e) {

            result.setStatusCode(210);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    private class ExperimentResourceBean {
        private String name;

        private String experimentId;

        private String id;

        private Long size;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getExperimentId() {
            return experimentId;
        }

        public void setExperimentId(String experimentId) {
            this.experimentId = experimentId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Long getSize() {
            return size;
        }

        public void setSize(Long size) {
            this.size = size;
        }
    }

    @RequestMapping("/getPlans")
    public @ResponseBody
    Result getPlans(@RequestParam("pageNum")Integer pageNum,
                        @RequestParam("userId")String teacherId) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("获取实验安排列表成功");

        try {
            Long count = experimentPlanService.countPlans(teacherId);
            List<ExperimentPlan> list = experimentPlanService.getPlans(teacherId,pageNum);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count",count);
            jsonObject.put("currentPage",pageNum);
            jsonObject.put("array",list);
            result.setData(jsonObject);
        } catch (LMSServerException e) {
            result.setStatusCode(210);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @RequestMapping("/createPlan")
    public @ResponseBody
    Result createPlan(@RequestParam("experimentId")String experimentId,
                        @RequestParam("labRoomId")String labRoomId,
                            @RequestParam("studentClassId")String classId,
                                @RequestParam("date")String date,
                                    @RequestParam("time")Integer time,
                                        @RequestParam("userId")String teacherId) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("实验安排创建成功");

        try {
            ExperimentPlan temp = new ExperimentPlan();
            Classes targetClass = classesService.findById(classId);
            List<StudentUser> studentList = studentUserService.listByClassId(classId);
            Integer totalStudentSize = studentUserService.studentNumInClass(classId);
            temp.setTotalNum(totalStudentSize);
            temp.setCurrentNum(0);
            temp.setTargetClassId(targetClass.getId());
            temp.setTargetClassName(targetClass.getName());
            temp.setDate(date);
            ExperimentProject projectEntity = experimentProjectService.findById(experimentId);
            temp.setExperimentId(experimentId);
            temp.setExperimentName(projectEntity.getName());
            LabRoom labRoom = labRoomService.findById(labRoomId);
            temp.setLabRoom(labRoom.getDepartment() + labRoom.getRoomNumber());
            temp.setTime(time);
            temp.setTeacherId(teacherId);
            experimentPlanService.savePlan(temp);

            //TODO 重构，过多的重复代码，数据库结构不合理
            ExperimentLession experimentLession = new ExperimentLession();
            try {
                experimentLession.setTargetDate(DATE_FORMAT.parse(date));
                experimentLession.setTargetTime(time);
                experimentLession.setExperimentProject(projectEntity);
                experimentLession.setLabRoomName(labRoom.getDepartment() + labRoom.getRoomNumber());
                experimentLession.setTargetClass(targetClass);
                experimentLession.setReceivedCount(0);
                experimentLession.setTargetCount(totalStudentSize);
                experimentLession.setTeacherId(teacherId);
                experimentLessionService.addLession(experimentLession);

                //生成所有学生的Result容器
                List<ExperimentResult> results = new ArrayList<>();
                ExperimentResult tempResult;
                for (StudentUser student : studentList) {
                    tempResult = new ExperimentResult();
                    tempResult.setTargetStudent(student);
                    tempResult.setExperimentLessionId(experimentLession.getId());
                    results.add(tempResult);
                }
                experimentResultService.saveAll(results);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            LabRoomUsage labRoomUsage = new LabRoomUsage();
            try {
                labRoomUsage.setTargetDate(DATE_FORMAT.parse(date));
                labRoomUsage.setStatus(LabRoomUsage.TYPE_BOOKING);
                labRoomUsage.setLabRoomId(labRoomId);
                labRoomUsage.setTargetTime(time);
                labRoomUsage.setExperimentLession(experimentLession);
                labRoomUsageService.addUsage(labRoomUsage);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            result.put("dataId",temp.getId());
        } catch (LMSServerException e) {

            result.setStatusCode(210);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
