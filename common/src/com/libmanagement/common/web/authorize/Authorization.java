package com.libmanagement.common.web.authorize;

import java.lang.annotation.*;

/**
 * 标记访问该方法需要授权
 *
 * @author wangre
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Authorization {
    String value() default "";

    public static final String VALUE_USER = "user";
    public static final String VALUE_CLIENT = "client";
}
