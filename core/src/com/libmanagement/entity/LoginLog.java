package com.libmanagement.entity;



import com.libmanagement.common.entity.Describertable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="lms_login_log")
public class LoginLog extends Describertable {


	public static final Integer STATUS_SUCCESS = 1;
	public static final Integer STATUS_FAIL = 0;
	public static final Integer STATUS_PASSWORDERROR = -1;
	public static final Integer STATUS_USERNOTFOUND = -2;
	public static final Integer STATUS_AUTHORITYERROR = -3;
	public static final Integer STATUS_USERSTATUSERROR = -4;
	
	public static final String FROM_ADMIN = "ADMIN";
	public static final String FROM_PORTAL = "PORTAL";
	private static final long serialVersionUID = -6969590878595287572L;

	@ManyToOne
	@JoinColumn
	private User user;
	private String loginIp;
	private String fromSource;
	private Integer loginStatus;
	private String logMessage;
	
	public String getLoginIp() {
	
		return loginIp;
	}
	
	public void setLoginIp(String loginIp) {
	
		this.loginIp = loginIp;
	}
	
	public String getFromSource() {
	
		return fromSource;
	}
	
	public void setFromSource(String fromSource) {
	
		this.fromSource = fromSource;
	}


	public Integer getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getLogMessage() {
	
		return logMessage;
	}

	
	public void setLogMessage(String logMessage) {
	
		this.logMessage = logMessage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
