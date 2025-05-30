//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.common.util.json;

import com.common.util.date.DateFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.lang.annotation.Annotation;

public class CustomObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    public CustomObjectMapper() {
        this.setSerializationInclusion(Include.NON_NULL);
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.setDateFormat(new DateFormat());
        this.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            public boolean isAnnotationBundle(Annotation ann) {
                try {
                    Class sensitiveClass = Class.forName("com.hikvision.seashell.log.sensitive.SensitiveInfo");
                    if (ann.annotationType().equals(sensitiveClass)) {
                        return false;
                    }
                } catch (ClassNotFoundException var3) {
                }

                return super.isAnnotationBundle(ann);
            }
        });
    }
}
