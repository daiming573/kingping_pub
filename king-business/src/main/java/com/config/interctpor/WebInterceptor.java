package com.config.interctpor;

import com.common.log.LogConstant;
import com.common.log.LogHelper;
import com.common.log.ThreadLocalHelper;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>对前端的请求全过程进行追踪日志记录(根据需要选择配置)</p>
 *
 * @author admin
 */
public class WebInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //记录请求流水号
        LogHelper.traceStart(request.getHeader(LogConstant.TRACE_ID));
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Object exception = request.getAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE);
        //返回请求流水号
        response.setHeader(LogConstant.TRACE_ID, LogHelper.getTraceId());
        ThreadLocalHelper.remove();
    }
}
