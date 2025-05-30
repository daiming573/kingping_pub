package com.common.log.aspect;

import com.common.log.LogHelper;
import com.common.log.LogUtil;
import com.common.log.ThreadLocalHelper;
import com.common.util.json.JsonUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 对外第三方的接口请求的日志切面
 */
@Aspect
@Component
public class ApiAspect {

    //@Pointcut("execution(*  com..*.action.ext..*Action.*(..))")
    @Pointcut("(execution(*  com..*.controller.api..*Controller.*(..)))")
    public void extInterHander() {
    }

    @Around("extInterHander()")
    public Object doActionClient(ProceedingJoinPoint pjp) throws Throwable {
        // 记录后台请求报文日志
        LogHelper.interRequestLog(JsonUtil.toJson(LogUtil.getRequestLog(pjp)));

        // 异常处理
        Object o = pjp.proceed();

        // 记录返回报文
        try {
            String str = "";
            if (null != o) {
                str = JsonUtil.toJson(o);
            }
            LogHelper.interResponseLog(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //线程池会复用，所以清理掉
        ThreadLocalHelper.remove();
        return o;
    }

    @AfterThrowing(pointcut = "extInterHander()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
        // 记录返回报文
        LogHelper.interExceptionResponseLog(e.getMessage());
        //线程池会复用，所以清理掉
        ThreadLocalHelper.remove();
        throw e;
    }

}
