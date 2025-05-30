package com.common.safe;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author daiming5
 */
@Slf4j
public class AesUtils {

    /**
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private final String IV_PARAMETER = "AaBbCcDd1234!@#$";

    private final static String ALGORITHM = "AES";

    private final static String AES_ALGORITHM = "AES/CBC/PKCS5Padding";

    /** 工具类实例  */
    private volatile static AesUtils instance = null;

    private AesUtils() {
    }

    /**
     * 可用静态内部类方式实现单例 线程安全
     */
//    private static class  AesUtilsHolder{
//        private static AesUtils instance = new AesUtils();
//    }

    public static AesUtils getInstance() {
        if (instance == null) {
            synchronized (AesUtils.class) {
                if (null == instance) {
                    instance = new AesUtils();
                }
            }
        }
        return instance;
    }


    // 加密
    public String encrypt(String sSrc, String sKey) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
            //return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。 JDK 不推荐使用
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            log.error("Encrypt2 have exception:", ex);
            return null;
        }
    }

    // 解密
    public String decrypt(String sSrc, String sKey) {
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decodeBase64(sSrc);
            // byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密 JDK 不推荐使用
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            log.error("Decrypt2 have exception:", ex);
            return null;
        }
    }

    // 解密
    public String decryptForWeixin(String sSrc, String sKey, String ivStr) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(Base64.decodeBase64(sKey), ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(ivStr));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decodeBase64(sSrc);
            // byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);// 先用base64解密 JDK 不推荐使用
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            log.error("Decrypt2 have exception:", ex);
            return null;
        }
    }

    public static String encodeBytes(byte[] bytes) {
        StringBuilder strBuf = new StringBuilder();

        for (byte aByte : bytes) {
            strBuf.append((char) (((aByte >> 4) & 0xF) + ((int) 'a')));
            strBuf.append((char) ((aByte & 0xF) + ((int) 'a')));
        }

        return strBuf.toString();
    }

    public static void main(String[] args) {
        AesUtils raesOperator = new AesUtils();
        String s = raesOperator.encrypt("ZJ100001+201026230638.832+m53+24", "WwXxYyZz1234!@#$");

        System.out.println(s);
        String decrypt = raesOperator.decrypt(s, "WwXxYyZz1234!@#$");
        System.out.println(decrypt);
    }

}
