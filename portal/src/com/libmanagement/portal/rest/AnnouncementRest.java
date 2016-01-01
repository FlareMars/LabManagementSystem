package com.libmanagement.portal.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libmanagement.common.exception.LMSServerException;
import com.libmanagement.entity.SystemNotice;
import com.libmanagement.portal.web.common.RestBaseBean;
import com.libmanagement.portal.web.common.Result;
import com.libmanagement.service.SystemNoticeService;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
