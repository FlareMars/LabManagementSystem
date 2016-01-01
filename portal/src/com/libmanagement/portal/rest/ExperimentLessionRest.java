package com.libmanagement.portal.rest;

import com.libmanagement.entity.*;
import com.libmanagement.portal.web.common.RestBaseBean;
import com.libmanagement.portal.web.common.Result;
import com.libmanagement.service.ExperimentLessionService;
import com.libmanagement.service.ExperimentResultService;
import com.libmanagement.service.TeachingNoticeService;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by FlareMars on 2016/1/1
 */
@Controller
@RequestMapping("/lession")
public class ExperimentLessionRest extends RestBaseBean {
    private Log logger = LogFactory.getLog(ExperimentLessionRest.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    @Autowired
    private ExperimentLessionService experimentLessionService;

    @Autowired
    private ExperimentResultService experimentResultService;

    @Autowired
    private TeachingNoticeService teachingNoticeService;

    @RequestMapping("/getList")
    public @ResponseBody
    Result getLessionList(@RequestParam("pageNum")Integer requestPageNum,
                            @RequestParam("userId")String teacherId) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("获取可评分课程列表成功");

        Long count = experimentLessionService.countLessions(teacherId);
        List<ExperimentLession> list = experimentLessionService.listLessions(teacherId,requestPageNum);

        List<LessionBean> dataList = new ArrayList<>(list.size());
        for (ExperimentLession temp : list) {
            LessionBean bean = new LessionBean();
            bean.setLessionId(temp.getId());
            bean.setExperimentName(temp.getExperimentProject().getName());
            bean.setTargetClassName(temp.getTargetClass().getName());
            bean.setTargetClassId(temp.getTargetClass().getId());
            bean.setIsCompleted(temp.getIsCompleted());
            bean.setDate(DATE_FORMAT.format(temp.getTargetDate()));
            bean.setReceivedCount(temp.getReceivedCount());
            bean.setTargetCount(temp.getTargetCount());
            dataList.add(bean);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("currentPage",requestPageNum);
        jsonObject.put("count",count);
        jsonObject.put("array",dataList);
        result.setData(jsonObject);

        return result;
    }

    @RequestMapping("/getHomeworks")
    public @ResponseBody
    Result getHomeworks(@RequestParam("lessionId")String lessionId) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("获取作业列表成功");

        ExperimentLession lession = experimentLessionService.findById(lessionId);
        List<ExperimentResult> homeworkList = lession.getExperimentResultList();
        List<HomeworkBean> dataList = new ArrayList<>(homeworkList.size());
        for (ExperimentResult temp : homeworkList) {
            HomeworkBean bean = new HomeworkBean();

            bean.studentId = temp.getTargetStudent().getId();
            bean.studentName = temp.getTargetStudent().getRealName();
            bean.studentNum = temp.getTargetStudent().getStudentNumber();
            bean.lessionId = temp.getExperimentLessionId();
            bean.homeworkId = temp.getId();
            ExperimentResultFile resultFile = temp.getResultFile();
            if (resultFile == null) {
                bean.resultFileName = "";
                bean.fileSize = 0L;
            } else {
                bean.resultFileName = resultFile.getName();
                bean.fileSize = resultFile.getSize();
            }
            bean.score = temp.getScore();

            dataList.add(bean);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count",homeworkList.size());
        jsonObject.put("array",dataList);
        result.setData(jsonObject);

        return result;
    }

    @RequestMapping("/correctHomeworkScore")
    public @ResponseBody
    Result correctHomeworkScore(@RequestParam("homeworkId")String homeworkId,
                                 @RequestParam("score")Integer score,
                                    @RequestParam("teacherId")String teacherId) {
        Result result = new Result();
        result.setMessage("评分成功");
        result.setStatusCode(200);

        ExperimentResult experimentResult = experimentResultService.findById(homeworkId);
        experimentResult.setScore(score);
        experimentResultService.update(experimentResult);

        ExperimentLession lession = experimentLessionService.findById(experimentResult.getExperimentLessionId());

        TeachingNotice teachingNotice = new TeachingNotice();
        TeacherUser teacherUser = new TeacherUser();
        teacherUser.setId(teacherId);
        teachingNotice.setSender(teacherUser);
        teachingNotice.setSendToWhat(TeachingNotice.TO_PERSON);
        teachingNotice.setTitle(lession.getExperimentProject().getName());
        teachingNotice.setContent(score + "");
        teachingNotice.setTargetId(experimentResult.getTargetStudent().getId());
        teachingNoticeService.addNotice(teachingNotice);

        return result;

    }

    @RequestMapping("/postTeachingAnnouncement")
    public @ResponseBody
    Result postTeachingAnnouncement(@RequestParam("targetId") String classId,
                                        @RequestParam("content")String content,
                                            @RequestParam("title")String title,
                                                @RequestParam("sender")String teacherId) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("发出教学公告成功");

        TeachingNotice teachingNotice = new TeachingNotice();
        TeacherUser teacherUser = new TeacherUser();
        teacherUser.setId(teacherId);
        teachingNotice.setSender(teacherUser);
        teachingNotice.setSendToWhat(TeachingNotice.TO_CLASS);
        try {
            teachingNotice.setTitle(new String(title.getBytes("ISO-8859-1"),"utf-8"));
            teachingNotice.setContent(new String(content.getBytes("ISO-8859-1"),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        teachingNotice.setTargetId(classId);
        teachingNoticeService.addNotice(teachingNotice);

        return result;
    }


    private class HomeworkBean {
        private String studentId;

        private String studentName;

        private String studentNum;

        private String lessionId;

        private String homeworkId;

        private String resultFileName;

        private Long fileSize;

        private Integer score;

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStudentNum() {
            return studentNum;
        }

        public void setStudentNum(String studentNum) {
            this.studentNum = studentNum;
        }

        public String getLessionId() {
            return lessionId;
        }

        public void setLessionId(String lessionId) {
            this.lessionId = lessionId;
        }

        public String getHomeworkId() {
            return homeworkId;
        }

        public void setHomeworkId(String homeworkId) {
            this.homeworkId = homeworkId;
        }

        public String getResultFileName() {
            return resultFileName;
        }

        public void setResultFileName(String resultFileName) {
            this.resultFileName = resultFileName;
        }

        public Long getFileSize() {
            return fileSize;
        }

        public void setFileSize(Long fileSize) {
            this.fileSize = fileSize;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }
    }

    private class LessionBean {
        private String lessionId;

        private String experimentName;

        private String targetClassName;

        private String targetClassId;

        private Boolean isCompleted;

        private String date;

        private Integer receivedCount;

        private Integer targetCount;

        public String getLessionId() {
            return lessionId;
        }

        public void setLessionId(String lessionId) {
            this.lessionId = lessionId;
        }

        public String getExperimentName() {
            return experimentName;
        }

        public void setExperimentName(String experimentName) {
            this.experimentName = experimentName;
        }

        public String getTargetClassName() {
            return targetClassName;
        }

        public void setTargetClassName(String targetClassName) {
            this.targetClassName = targetClassName;
        }

        public String getTargetClassId() {
            return targetClassId;
        }

        public void setTargetClassId(String targetClassId) {
            this.targetClassId = targetClassId;
        }

        public Boolean getIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(Boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getReceivedCount() {
            return receivedCount;
        }

        public void setReceivedCount(Integer receivedCount) {
            this.receivedCount = receivedCount;
        }

        public Integer getTargetCount() {
            return targetCount;
        }

        public void setTargetCount(Integer targetCount) {
            this.targetCount = targetCount;
        }
    }
}
