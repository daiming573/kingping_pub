// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UUIDUtil.java

package com.common.util;

import java.util.UUID;

// Referenced classes of package org.mobj.com.lang:
//			FString, RLong, RString, RInteger

public class UUIDUtil {

    public UUIDUtil() {
    }


    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }


    public static void main(String[] args) {
        System.out.println(get32UUID());
    }
}
