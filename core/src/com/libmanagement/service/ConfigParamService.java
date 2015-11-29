package com.libmanagement.service;

import com.libmanagement.entity.ConfigParam;
import com.libmanagement.repository.ConfigParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Administrator
 */
@Service
public class ConfigParamService {

    @Autowired
    private ConfigParamRepository configParamRepository;

    public static final String REPORT_PATH = "REPORT_PATH"; //报告处理目录
    public static final String REPORT_URL = "REPORT_URL"; //报告访问目录
    public static final String BAIDU_OAUTH_CLIENTID = "BAIDU_OAUTH_CLIENTID";//百度Oauth id
    public static final String BAIDU_OAUTH_CLIENTSECRET = "BAIDU_OAUTH_CLIENTSECRET";//百度Oauth_secret
    public static final String BAIDU_OAUTH_REDIRECTURL = "BAIDU_OAUTH_REDIRECTURL";//百度回调地址
    public static final String BAIDU_OAUTH_SCOPE = "BAIDU_OAUTH_SCOPE";//百度权限
    public static final String BAIDU_APP_ROOTPATH = "BAIDU_APP_ROOTPATH";//百度云应用根目录
    public static final String USER_FILE_ROOTPATH = "USER_FILE_ROOTPATH";//系统用户文件根目录
    public static final String HDFS_FILE_ROOTPATH = "HDFS_FILE_ROOTPATH";//系统用户文件根目录

    public static final String COMPUTING_ROUTER_URL = "COMPUTING_ROUTER_URL";//计算中心调度器
    public static final String COMPUTING_ROUTER_UPDATE_URL = "COMPUTING_ROUTER_UPDATE_URL";// 计算中心调度器任务更新地址
//    public static final String COMPUTING_ROUTER_HOST = "COMPUTING_ROUTER_HOST";//计算中心调度器

    public static final String HDFS_URL = "HDFS_URL";//hdfs com.libmanagement.admin.web url

    public static final String HDFS_SUPERUSER = "HDFS_SUPERUSER";

    public static final String HDFS_SUPERGROUP = "HDFS_SUPERGROUP";

    public static final String USER_FILE_TYPE = "USER_FILE_TYPE";// 文件系统类型，分为hadoop和local

    public static final String MAIL_SMTP_HOST = "MAIL_SMTP_HOST";

    public static final String MAIL_SMTP_PORT = "MAIL_SMTP_PORT";

    public static final String MAIL_FROM = "MAIL_FROM";

    public static final String MAIL_USER = "MAIL_USER";

    public static final String MAIL_PASSWORD = "MAIL_PASSWORD";

    public String findParamValue(String key) {
        ConfigParam cp = configParamRepository.findOne(key);
        return null == cp ? "" : cp.getParamValue();

    }


    public ConfigParam findParam(String key) {
        return configParamRepository.findOne(key);
    }

    public void setParam(String key, String value, String paramName) {

        ConfigParam configParam = new ConfigParam();
        configParam.setId(key);
        configParam.setParamValue(value);
        configParam.setParamName(paramName);

        configParamRepository.save(configParam);
    }

    public void delParam(String key) {
        configParamRepository.delete(key);
    }


    public List<ConfigParam> listParam() {
        return configParamRepository.findAll();
    }

}
