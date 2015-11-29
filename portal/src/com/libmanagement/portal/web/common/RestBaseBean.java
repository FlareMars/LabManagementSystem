package com.libmanagement.portal.web.common;

import com.libmanagement.common.utils.StringUtils;
import com.libmanagement.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;


/**
 * spring mvc rest api基类
 * 
 *
 */
public abstract class RestBaseBean extends BaseBean implements Serializable{

    private static Log logger = LogFactory.getLog(RestBaseBean.class);
    public static final String ACCESS_TOKEN = "Access-Token"; // API 访问认证
    public static final String SESSION_ID = "Session-Id"; // 会话ID

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
//    private String userId;//会话用户ID
    private String clientId;

    @ModelAttribute
    public void init(HttpServletRequest request,
                     HttpServletResponse respones,
                     HttpSession session){
        this.request = request;
        this.response = response;
        this.session = session;

            String accessToken = request.getHeader(ACCESS_TOKEN);
            if(accessToken==null){
                accessToken = request.getParameter(ACCESS_TOKEN);
            }
            // 根据sessionId查找对应的Session
            if(StringUtils.INSTANCE.notEmpty(accessToken)) {
                clientId = accessToken;
            }else {
                clientId = null;
            }

    }

    public void setLoginUser(User user) {
        logger.debug("set login user:"+user.getId());
        session.setAttribute(LOGIN_USER,user);
    }

    /**
     * 获得可操作用户名
     * @param userId
     * @return
     */
    protected String getAccessUserId(String userId){
        if(StringUtils.INSTANCE.notEmpty(clientId)){
            return userId;
        }else {
            return getLoginUserId();
        }
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

    public String getClientId() {
        return clientId;
    }

    @Override
    public String getLoginUserId() {
        User user =  (User)session.getAttribute(LOGIN_USER);
        if(user!=null){
            return user.getId();
        }else return null;
    }
}
