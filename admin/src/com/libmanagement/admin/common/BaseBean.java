package com.libmanagement.admin.common;

import java.io.Serializable;

/**
 * spring mvc基类
 *
 * @author wangre
 */

public abstract class BaseBean implements Serializable {
    public static final String LOGIN_USER = "LOGIN_USER";
    public abstract String getLoginUserId();
}
