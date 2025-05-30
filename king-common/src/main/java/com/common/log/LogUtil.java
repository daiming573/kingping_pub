package com.common.log;

import com.common.anno.Anno;
import com.common.log.dto.ReqLogDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Program: king
 * @Description: 日志工具
 * @Author: daiming5
 * @Date: 2021-03-01 16:15
 * @Version 1.0
 **/
public class LogUtil {


    /**
     * 打印请求日志
     *
     * @param pjp 切点
     */
    public static ReqLogDto getRequestLog(ProceedingJoinPoint pjp) {
        ReqLogDto reqLog = new ReqLogDto();
        try {
            //做好异常处理，避免记录日志造成请求不正确
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Map<String, String[]> queryMap = request.getParameterMap();
            reqLog.setAppId(request.getHeader(LogConstant.APP_ID));
            reqLog.setHttpMethod(request.getMethod());
            reqLog.setMethod(pjp.getSignature().getName());
            reqLog.setMethodName(getControllerMethodDescription(pjp));
            reqLog.setClassName(pjp.getSignature().getDeclaringTypeName());
            reqLog = RequestIp.getIpInfo(request, reqLog);
            reqLog.setReqestBody(pjp.getArgs());
            reqLog.setRequestQuery(queryMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reqLog;
    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception 异常
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    Anno n = method.getAnnotation(Anno.class);
                    if (null != n) {
                        description = n.value();
                    }
                    break;
                }
            }
        }
        return description;
    }

}
