package com.libmanagement.common.web;

import com.libmanagement.common.utils.StringUtils;
import com.libmanagement.common.web.annotations.PageParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageParamsResolver implements HandlerMethodArgumentResolver {
    private static Log logger = LogFactory.getLog(PageParamsResolver.class);
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest request,
                                  WebDataBinderFactory binderFactory) throws Exception {
        PageParam pageAnno = parameter.getParameterAnnotation(PageParam.class);

//        logger.debug("Page:"+pageAnno.page());

        Object pageHolder = request.getParameter(pageAnno.page());
        Object pageSizeHolder = request.getParameter(pageAnno.size());

        int pageSizeValue = pageAnno.defaultSize();
        if(pageSizeHolder != null) {
            pageSizeValue = Integer.valueOf(pageSizeHolder.toString());
        }

        if (pageHolder != null) {
            int pageValue = Integer.valueOf(pageHolder.toString());

            logger.debug("PageValue:"+pageValue);
            PageRequest pr = null;
            Object sortBy = request.getParameter(pageAnno.sortBy()), sortOrder = request
                    .getParameter(pageAnno.sortOrder());
            if (!StringUtils.INSTANCE.isAnyEmpty((String) sortBy, (String) sortOrder)) {
                pr = new PageRequest(pageValue > 0 ? pageValue - 1 : 0,
                        pageSizeValue,
                        Direction.fromString((String) sortOrder),
                        (String) sortBy);

            } else {
                pr = new PageRequest(pageValue > 0 ? pageValue - 1 : 0,
                        pageSizeValue);
            }
            return pr;
        } else {
            return new PageRequest(0, pageSizeValue);
        }

    }

    @Override
    public boolean supportsParameter(MethodParameter arg0) {
        return arg0.hasParameterAnnotation(PageParam.class);
    }
}
