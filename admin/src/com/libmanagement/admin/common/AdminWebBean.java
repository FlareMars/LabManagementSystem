package com.libmanagement.admin.common;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * spring mvc基类
 * 
 *
 */
public abstract class AdminWebBean {
	protected static Log logger = LogFactory.getLog(AdminWebBean.class);




	public static String WEB_BASE_URL = "/";
	

	
	@ModelAttribute
	public void initReqAndResp(@CookieValue(value="LOGIN_USER", defaultValue="") String cookieLoginUser,
							   HttpServletRequest request,HttpServletResponse respones,HttpSession session,Model model){


		WEB_BASE_URL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}



}
