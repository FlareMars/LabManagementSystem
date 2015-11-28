package com.libmanagement.common.web.annotations;

import java.lang.annotation.*;

/**
 * 分页的注解封装
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageParam {
    String page() default "page";

    String size() default "size";

    String sortBy() default "sortBy";

    String sortOrder() default "sortOrder";

    int defaultSize() default 10;
}
