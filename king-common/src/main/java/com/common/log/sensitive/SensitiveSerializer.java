package com.common.log.sensitive;

import com.common.log.LogConstant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * 序列化工具，主要功能识别注解上的脱敏模式和真正实现隐藏功能
 *
 * @author fangqin
 */
public class SensitiveSerializer extends StdSerializer<Object> implements ContextualSerializer {

    private String mode;

    public SensitiveSerializer() {
        super(Object.class);
    }

    public SensitiveSerializer(String mode) {
        super(Object.class);
        this.mode = mode;
    }

    /**
     * createContextual的调用是先于serialize的，createContextual传入了BeanProperty，通过它能获取了到属性的注解
     *
     * @param serializerProvider
     * @param beanProperty
     * @return
     * @throws JsonMappingException
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        String mode = null;
        if (beanProperty != null) {
            SensitiveInfo anno = beanProperty.getAnnotation(SensitiveInfo.class);
            if (anno != null) {
                mode = anno.mode();
            }
        }
        return new SensitiveSerializer(mode);
    }

    /**
     * 属性的序列化
     *
     * @param o
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (serializerProvider.isEnabled(MapperFeature.USE_ANNOTATIONS)) {
            if (LogConstant.MASK_MODE_MID.equals(mode)) {
                jsonGenerator.writeString(maskFieldWithMid(o.toString()));
            } else {
                jsonGenerator.writeString("*");
            }
        } else {
            jsonGenerator.writeString(o == null ? "" : o.toString());
        }
    }

    /**
     * 安全红线中对IP、手机号、邮箱等有特殊要求，其他一般是隐藏至少30%
     *
     * @param originalStr
     * @return
     */
    private String maskFieldWithMid(String originalStr) {
        if (StringUtils.isEmpty(originalStr)) {
            return "";
        }
        if (LogConstant.IP_PATTERN.matcher(originalStr).matches()) {
            //ip:192.xx*.*.120
            return SensitiveLogUtil.maskIpStr(originalStr);
        } else if (LogConstant.PHONE_PATTERN.matcher(originalStr).matches()) {
            //137******50
            return SensitiveLogUtil.maskPhoneStr(originalStr);
        } else if (LogConstant.EMAIL_PATTERN.matcher(originalStr).matches()) {
            //邮箱：首字母和@后缀显示，其余隐藏
            return SensitiveLogUtil.maskEmailStr(originalStr);
        }else if (LogConstant.DNS_PATTERN.matcher(originalStr).matches()){
            return SensitiveLogUtil.maskDnsStr(originalStr);
        }

        return SensitiveLogUtil.maskStrInMid(originalStr);
    }

}
