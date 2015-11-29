package com.libmanagement.portal.web.authorize;


import com.libmanagement.common.web.authorize.Authorization;
import com.libmanagement.portal.web.common.RestBaseBean;
import com.libmanagement.portal.web.common.Result;
import com.libmanagement.portal.web.common.WebBaseBean;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;

/**
 * 拦截需要授权的方法，分别针对web以及rest，
 * web通过session进行认证
 * rest通过requestHeader进行认证
 *
 * @author FlareMars
 */

public class AuthorizationAdvice {
    private static org.apache.commons.logging.Log logger = LogFactory.getLog(AuthorizationAdvice.class);

    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        Object obj = pjp.getTarget();
        String methodName = pjp.getSignature().getName();
        Method method = getMethodByClassAndName(obj.getClass(), methodName);    //得到拦截的方法
        Authorization annotation = method.getAnnotation(Authorization.class);

        if(obj instanceof RestBaseBean){
            RestBaseBean target = (RestBaseBean)obj ;
            return checkBaseRestApi(annotation.value(),target,pjp);
        } else if(obj instanceof WebBaseBean){
            WebBaseBean target = (WebBaseBean)obj;
            return checkUser(target,pjp,method);
        }else{
            return pjp.proceed();
        }
    }

        public Method getMethodByClassAndName(Class c , String methodName){
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if(method.getName().equals(methodName)){
                return method ;
            }
        }
        return null;
    }


    private Object checkBaseRestApi(String checkType,RestBaseBean target,ProceedingJoinPoint pjp) throws Throwable {

        if(checkType==null){
            checkType = "";
        }
//
//        logger.debug("LoginUserId:"+target.getLoginUserId());
//        logger.debug("ClientId:"+target.getClientId());

        if(checkType.equals(Authorization.VALUE_CLIENT)){
            if(target.getLoginUserId()!=null){
                return pjp.proceed();
            }
        }else if (checkType.equals(Authorization.VALUE_USER)){
            if(target.getLoginUserId()!=null){
                return pjp.proceed();
            }
        }else {
            if(target.getClientId()!=null || target.getLoginUserId()!=null){
                return pjp.proceed();
            }
        }

        return new Result(Result.CODE_NOT_AUTHORIZATION,"授权认证失败");

    }

    private Object checkUser(WebBaseBean target,ProceedingJoinPoint pjp,Method method) throws Throwable {
        if (null != target.getLoginUserId()) {
            return pjp.proceed();
        } else {
            Class returnType = method.getReturnType();

            logger.debug(returnType.toString());

            logger.debug(returnType.isAssignableFrom(String.class));

            if(returnType.isAssignableFrom(String.class)){

                return "redirect:/index";
            }else if(returnType.isAssignableFrom(Result.class)){
                return new Result(Result.CODE_NOT_AUTHORIZATION,"授权认证失败");
            }else{
                logger.debug("授权认证失败");
                return null;
            }
        }
    }
}

