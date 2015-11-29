package com.libmanagement.admin.common;

import com.libmanagement.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * spring mvc基类
 *
 * @author wangre
 */

public abstract class WebBaseBean extends BaseBean implements Serializable {
    private static Log logger = LogFactory.getLog(WebBaseBean.class);

//    public static final String LOGIN_USER = "LOGIN_USER";

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
//    protected User loginUser;
//    protected String userId;//会话用户ID

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse respones, HttpSession session) {
        this.request = request;
        this.response = respones;
        this.session = session;
    }


    public void setLoginUser(User loginUser) {
        session.setAttribute(LOGIN_USER,loginUser);
//        session.setAttribute(LOGIN_USER_ID,loginUser.getUserName());
    }

    public User getLoginUser() {
        return (User)session.getAttribute(LOGIN_USER);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public HttpSession getSession() {
        return session;
    }

    @Override
    public String getLoginUserId() {
        User user =  (User)session.getAttribute(LOGIN_USER);
        if(user!=null){
            return user.getId();
        }else return null;
    }


}
