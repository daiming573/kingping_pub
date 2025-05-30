package com.common.log;


import com.common.constant.CommonConstant;
import com.common.util.date.DateUtil;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;

/**
 * 线程缓存帮助类
 * @author daiming5
 */
public class ThreadLocalHelper {

    /** 缓存现场全局变量 */
    private static final ThreadLocal<HashMap<String, Object>> THREAD_LOCAL = ThreadLocal.withInitial(HashMap::new);

    public static void setVale(String key, Object value) {
        HashMap<String, Object> map = THREAD_LOCAL.get();
        if (ObjectUtils.isEmpty(map)) {
            map = new HashMap<>();
        }
        map.put(key, value);
        THREAD_LOCAL.set(map);
    }

    public static <T> T getVale(String key) {
        HashMap<String, Object> map = THREAD_LOCAL.get();
        if (ObjectUtils.isEmpty(map)) {
            map = new HashMap<>();
        }
        return (T) map.get(key);
    }

    /**
     * 获取当前线程的序列号
     */
    public static String setSeq() {
        HashMap<String, Object> map = THREAD_LOCAL.get();
        if (ObjectUtils.isEmpty(map)) {
            map = new HashMap<>();
        }
        String seq = DateUtil.getCurrentTimeSssStr();
        map.put(CommonConstant.REQUEST_SEQ, seq);
        THREAD_LOCAL.set(map);
        return seq;
    }

    /**
     * 获取当前线程的序列号
     */
    public static String getSeq() {
        HashMap<String, Object> map = THREAD_LOCAL.get();
        if (null == map || ObjectUtils.isEmpty(map.get(CommonConstant.REQUEST_SEQ))) {
            return "";
        }
        return String.valueOf(map.get(CommonConstant.REQUEST_SEQ));
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

}
