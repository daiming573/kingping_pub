package com.common.util;

import java.util.Random;

/**
 * 根据long类型数字 转换成唯一的字符串，long最大32位可生成6位的字符串
 *
 * @author daiming
 * @version 1.0
 * @date 2019-11-30
 * @Description:
 */
public class RandomCodeUtil {
    /**
     * 自定义进制（选择你想要的进制数，不能重复且最好不要0、1这些容易混淆的字符）
     */
    private static final char[] CHAR_SEDS = new char[]{'q', 'z', 'x', '9', 'c', '7', 'w', 'e', '8', 's', '2', 'd', 'p', '5', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 't', 'n', '6', 'b', 'g', 'h'};

    /**
     * 定义一个字符用来补全邀请码长度（该字符前面是计算出来的邀请码，后面是用来补全用的）
     */
    private static final char CHAR_FORMAT = 'a';

    /**
     * 补位字符串
     */
    private static final String CHARS_FORMAT = "qazwsx";

    /**
     * 进制长度
     */
    private static final int BIN_LEN = CHAR_SEDS.length;

    /**
     * 邀请码长度
     */
    private static final int CODE_MAX_LEN = 6;

    /**
     * 根据ID生成随机码，同一个ID随机码不同
     *
     * @param id ID
     * @return 随机码
     */
    public static String toRandomSerialCode(long id) {
        String str = getUniqueCode(id);
        // 不够长度的自动随机补全
        if (str.length() < CODE_MAX_LEN) {
            StringBuilder sb = new StringBuilder();
            sb.append(CHAR_FORMAT);
            Random rnd = new Random();
            for (int i = 1; i < CODE_MAX_LEN - str.length(); i++) {
                sb.append(CHAR_SEDS[rnd.nextInt(BIN_LEN)]);
            }
            str += sb.toString();
        }
        return str;
    }

    private static String getUniqueCode(long id) {
        char[] buf = new char[32];
        int charPos = 32;

        while ((id / BIN_LEN) > 0) {
            int ind = (int) (id % BIN_LEN);
            buf[--charPos] = CHAR_SEDS[ind];
            id /= BIN_LEN;
        }
        buf[--charPos] = CHAR_SEDS[(int) (id % BIN_LEN)];
        return new String(buf, charPos, (32 - charPos));
    }

    /**
     * 根据ID生成六位随机码，同一个ID随机码相同，补全码相同
     *
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCode(long id) {
        String str = getUniqueCode(id);
        // 不够长度的自动补全
        if (str.length() < CODE_MAX_LEN) {
            str += String.valueOf(CHARS_FORMAT.subSequence(0, CODE_MAX_LEN - str.length()));
        }
        return str;
    }

    /**
     * 根据随机码生成ID
     *
     * @param code 随机码
     * @return ID
     */
    public static long codeToId(String code) {
        char[] chs = code.toCharArray();
        long res = 0L;
        for (int i = 0; i < chs.length; i++) {
            int ind = 0;
            for (int j = 0; j < BIN_LEN; j++) {
                if (chs[i] == CHAR_SEDS[j]) {
                    ind = j;
                    break;
                }
            }
            if (chs[i] == CHAR_FORMAT) {
                break;
            }
            if (i > 0) {
                res = res * BIN_LEN + ind;
            } else {
                res = ind;
            }
        }
        return res;
    }
}
