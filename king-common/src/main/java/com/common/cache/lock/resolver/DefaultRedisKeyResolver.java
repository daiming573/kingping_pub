package com.common.cache.lock.resolver;

import com.common.cache.lock.RedisLock;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ObjectUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: redis锁key默认生成器
 *  <p>借助spring EL表达式解析能力，内置默认变量名</p>
 *  <p>#c_name 目标类全名</p>
 *  <p>#m_name 目标方法名</p>
 *  <p>#args0、#args1、#args2、...对应按照顺序从前往后的方法参数</p>
 * @Program: king
 * @Author: daiming5
 * @Date: 2021-04-01 14:17
 * @Version 1.0
 **/
public class DefaultRedisKeyResolver implements RedisKeyResolver {

    private static final String CLASS_VAR = "c_name";
    private static final String METHOD_VAR = "m_name";

    private static final Pattern CLASS_PATTERN = Pattern.compile("#" + CLASS_VAR);
    private static final Pattern METHOD_PATTERN = Pattern.compile("#" + METHOD_VAR);

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    @Override
    public String resolve(ProceedingJoinPoint pjp, RedisLock redisLock) {
        String keyExpression = redisLock.keyExpression();
        String classVar = null;
        String methodVar = null;
        if (ObjectUtils.isEmpty(keyExpression)) {
            Matcher matcher1 = CLASS_PATTERN.matcher(keyExpression);
            if (matcher1.find()) {
                classVar = pjp.getTarget().getClass().getName();
            }
            Matcher matcher2 = METHOD_PATTERN.matcher(keyExpression);
            if (matcher2.find()) {
                methodVar = pjp.getSignature().getName();
            }
        }
        return translate(redisLock.keyExpression(), classVar, methodVar, pjp.getArgs());
    }


    /**
     * 格式校验
     * <p>SpEL表达式中嵌入变量占位符:#args0,#args1,#args2,...，比如第2个参数为#arg1</p>
     *
     * @param spel       SpEL表达式
     * @param className  类名
     * @param methodName 方法名
     * @param args       参数
     * @return String
     */
    public String translate(String spel, String className, String methodName, Object... args) {
        if (StringUtils.isBlank(spel)) {
            return null;
        }
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (className != null) {
            context.setVariable(CLASS_VAR, className);
        }
        if (methodName != null) {
            context.setVariable(METHOD_VAR, methodName);
        }
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Object var = args[i];
                context.setVariable("args" + i, var);
            }
        }
        try {
            return EXPRESSION_PARSER.parseExpression(spel).getValue(context, String.class);
        } catch (Exception e) {
            throw new RuntimeException("SpEL parse error. SpEL: " + spel, e);
        }
    }

}
