package com.common.log;

import com.common.constant.CommonConstant;
import com.common.log.dto.ReqLogDto;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author daiming5
 */
public class RequestIp {

    private static final String IP_UNKNOWN = "unknown";

    private static final String REAL_IP = "X-Real-IP";

    private static final String FORWARDED_FOR = "X-Forwarded-For";

    private static final String CLIENT_IP = "Proxy-Client-IP";

    private static final String WL_CLIENT_IP = "WL-Proxy-Client-IP";

    private static final String REMOTE_ADDR = "RemoteAddr";

    private static final Pattern PATTERN = Pattern.compile(CommonConstant.IP_REGEX);

    /**
     * 获取request的ip信息
     *
     * @param request 请求
     * @param logEntity 日志参数
     */
    public static ReqLogDto getIpInfo(HttpServletRequest request, ReqLogDto logEntity) {
        if (null == logEntity) {
            return null;
        }
        String fromSource = REAL_IP;
        String ip = request.getHeader(fromSource);
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            fromSource = FORWARDED_FOR;
            ip = request.getHeader(fromSource);
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            fromSource = CLIENT_IP;
            ip = request.getHeader(fromSource);
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            fromSource = WL_CLIENT_IP;
            ip = request.getHeader(fromSource);
        }
        if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
            fromSource = REMOTE_ADDR;
            ip = request.getRemoteAddr();
        }
        logEntity.setIp(ip);
        logEntity.setFromSource(fromSource);
        ThreadLocalHelper.setVale(CommonConstant.REQUEST_IP, ip);
        return logEntity;
    }

    /**
     * 获取请求IP
     * @return  String
     */
    public static String getRequestIp() {
        return ThreadLocalHelper.getVale(CommonConstant.REQUEST_IP);
    }



    /**
     * 判断点分式IPV4格式是否正确
     *
     * @param ipAddress ip4地址
     * @return  boolean
     */
    public static boolean checkIp4Str(String ipAddress) {
        Matcher m = PATTERN.matcher(ipAddress);
        return m.matches();
    }

}
