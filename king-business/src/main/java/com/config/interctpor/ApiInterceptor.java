package com.config.interctpor;

import com.common.anno.RestAuthAnnotation;
import com.common.constant.CommonConstant;
import com.common.errorcode.DefaultErrorCode;
import com.common.exception.BusinessException;
import com.common.log.LogConstant;
import com.common.log.LogHelper;
import com.common.log.ThreadLocalHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * 对api接口的请求全过程进行埋点日志记录，并且进行安全认证，如token的验证和防篡改字段的验证\n
 * </p>
 *
 * @author admin
 */
public class ApiInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //记录请求序列号
        LogHelper.traceStart(request.getHeader(LogConstant.TRACE_ID));
//        if (handler instanceof HandlerMethod) {
//            HandlerMethod methodHandler = (HandlerMethod) handler;
//            RestAuthAnnotation restAuthAnnotation = methodHandler.getMethodAnnotation(RestAuthAnnotation.class);
//            // 标记为不需要登录的校验
//            if (null != restAuthAnnotation && !restAuthAnnotation.valid()) {
//                if (logger.isInfoEnabled()) {
//                    logger.info("the request not need auth requestUrl={}", request.getRequestURI());
//                }
//                return true;
//            }
//            return validateToken(request.getHeader(CommonConstant.HEAD_TOKEN), request);
//        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //返回请求流水号
        response.setHeader(LogConstant.TRACE_ID, LogHelper.getTraceId());
        ThreadLocalHelper.remove();
    }

    /**
     * 校验Token
     *
     * @param token   请求token
     * @param request 请求
     * @return boolean
     */
    private boolean validateToken(String token, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(token)) {
            throw new BusinessException(DefaultErrorCode.REQUEST_TOKEN_CHECK_ERROR);
        }

        // 校验传输的token
        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
//        RpoSyUserService rpoSyUserService = (RpoSyUserService) factory.getBean("rpoSyUserService");
//        RpoSyUser rpoSyUser = rpoSyUserService.checkRpoUserToken(token);
//        ThreadLocalHelper.setSysUser(rpoSyUser);
        return true;
    }

}
