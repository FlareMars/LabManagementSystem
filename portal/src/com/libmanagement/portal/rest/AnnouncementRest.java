package com.libmanagement.portal.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libmanagement.common.exception.LMSServerException;
import com.libmanagement.entity.Classes;
import com.libmanagement.entity.StudentUser;
import com.libmanagement.entity.SystemNotice;
import com.libmanagement.entity.TeachingNotice;
import com.libmanagement.portal.web.common.RestBaseBean;
import com.libmanagement.portal.web.common.Result;
import com.libmanagement.service.StudentUserService;
import com.libmanagement.service.SystemNoticeService;
import com.libmanagement.service.TeachingNoticeService;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by FlareMars on 2015/12/31
 */
@Controller
@RequestMapping("/announcement")
public class AnnouncementRest extends RestBaseBean {
    private org.apache.commons.logging.Log logger = LogFactory.getLog(AnnouncementRest.class);

    @Autowired
    private SystemNoticeService systemNoticeService;

    @Autowired
    private StudentUserService studentUserService;

    @Autowired
    private TeachingNoticeService teachingNoticeService;

    @RequestMapping("/normal")
    public @ResponseBody
    Result getNormalAnnouncements(@RequestParam("pageNum") Integer requestPageNum) {
        Result result = new Result();
        result.setStatusCode(200);
        result.setMessage("获取成功");

        try {
            Long count = systemNoticeService.getSystemNoticeSize();
            List<SystemNotice> list = systemNoticeService.getSystemNotices(requestPageNum);
            List<SystemNoticeBean> dataList = new ArrayList<>(list.size());
            for (SystemNotice temp : list) {
                SystemNoticeBean bean = new SystemNoticeBean();
                bean.title = temp.getTitle();
                bean.content = temp.getContent();
                bean.createTimeStr = temp.getCreateTimeStr();
                bean.operator = "管理员:" + temp.getOperator().getUserName();
                dataList.add(bean);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count",count);
            jsonObject.put("currentPage",requestPageNum);
            jsonObject.put("array",dataList);
            result.setData(jsonObject);
        } catch (LMSServerException e) {
            result.setStatusCode(210);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @RequestMapping("/teaching")
    public @ResponseBody
    Result getTeachingAnnouncement(@RequestParam("studentId")String studentId) {
        Result result = new Result();
        result.setMessage("get teaching announcement success!");
        result.setStatusCode(200);

        StudentUser student = studentUserService.findById(studentId);
        Classes targetClass = student.getCurrentClass();
        if (targetClass != null) {
            List<TeachingNotice> list = new ArrayList<>();
            list.addAll(teachingNoticeService.findByTargetId(studentId,TeachingNotice.TO_PERSON));
            list.addAll(teachingNoticeService.findByTargetId(targetClass.getId(),TeachingNotice.TO_CLASS));
            Collections.sort(list, new Comparator<TeachingNotice>() {
                @Override
                public int compare(TeachingNotice o1, TeachingNotice o2) {
                    return o1.getCreateTime().compareTo(o2.getCreateTime());
                }
            });

            List<TeachingNoticeBean> dataList = new ArrayList<>(list.size());
            for (TeachingNotice temp : list) {
                TeachingNoticeBean bean = new TeachingNoticeBean();
                bean.title = temp.getTitle();
                bean.content = temp.getContent();
                bean.date = temp.getCreateTimeStr().substring(0,temp.getCreateTimeStr().indexOf(" "));
                bean.teacher = temp.getSender().getRealName();
                try {
                    Integer.valueOf(bean.content);
                    bean.type = 2;
                } catch (NumberFormatException e) {
                    bean.type = 1;
                }
                dataList.add(bean);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count",list.size());
            jsonObject.put("array",dataList);
            result.setData(jsonObject);
        } else {
            result.setStatusCode(210);
            result.setMessage("can't find the target class");
        }

        return result;
    }

    private class TeachingNoticeBean {
        private String title;

        private String content;

        private String teacher;

        private String date;

        private Integer type;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }

    private class SystemNoticeBean {
        private String title;

        private String content;

        private String operator;

        private String createTimeStr;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getCreateTimeStr() {
            return createTimeStr;
        }

        public void setCreateTimeStr(String createTimeStr) {
            this.createTimeStr = createTimeStr;
        }
    }
}
