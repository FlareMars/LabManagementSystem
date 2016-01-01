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
        result.setMessage("��ȡ�ɹ�");

        try {
            Long count = systemNoticeService.getSystemNoticeSize();
            List<SystemNotice> list = systemNoticeService.getSystemNotices(requestPageNum);
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
}
