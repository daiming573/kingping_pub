package com.common.safe;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;

/**
 * DES加密算法
 * 这个加密算法是对称的加密算法
 * 这个加密算法，在本系统中的应用，主要是因为该加密算法加密后
 * 密文都是字符串和数字的，没有其他字符。这样的密文可以应用在url地址上，
 * 不用担心被URL 地址上传递的数字被转义
 * 密钥 大于8位，并且是偶数
 * 此加密方式主要应用于安全级别不是特别高的场景
 *
 * @author Administrator
 */

public class DesUtils {

    /**
     * 加密算法,可用 DES,DESede,Blowfish.
     */
    private final static String ALGORITHM = "DES";

    private final static String DEFAULT_CHARSET_NAME = "GBK";
    private static final byte[] DES_IV = initIv();
    private final static String DES_ALGORITHM = "DES/CBC/PKCS5Padding";


    /**
     * 初始向量的方法, 全部为0。针对DES算法,IV值一定是64位的(8字节).
     */
    private static byte[] initIv() {
        int blockSize = 8;
        byte[] iv = new byte[blockSize];
        for (int i = 0; i < blockSize; ++i) {
            iv[i] = 0;
        }
        return iv;
    }

    /**
     * DES解密算法
     *
     * @param data
     * @param cryptKey 密钥 要是偶数
     *
     * @return
     *
     * @throws Exception
     */
    public static String decrypt(String data, String cryptKey) throws Exception {
        return new String(decrypt(hex2byte(data.getBytes()), cryptKey.getBytes()));
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key
     *
     * @return
     *
     * @throws IOException
     * @throws Exception
     */
    public static String hmzxdecrypt(String data, String key) throws Exception {
        if (data == null)
            return null;
        //解密对象从16进制装换成byte
        byte[] buf = decodeHex(data.toCharArray());
        byte[] result = hmzxdecrypt(buf, key.getBytes(DEFAULT_CHARSET_NAME));
        return new String(result, DEFAULT_CHARSET_NAME);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key 加密键byte数组
     *
     * @return
     *
     * @throws Exception
     */
    private static byte[] hmzxdecrypt(byte[] data, byte[] key) throws Exception {
        // 从原始密钥数据创建DESKeySpec对象
        Key securekey = generateKey(key);
        //参数
        IvParameterSpec iv = new IvParameterSpec(DES_IV);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, iv);

        return cipher.doFinal(data);
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     *
     * @return Key 密钥
     */
    private static Key generateKey(byte[] key) throws Exception {
        // 实例化Des密钥
        DESKeySpec dks = new DESKeySpec(key);
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        // 生成密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    public static byte[] decodeHex(char[] data) throws Exception {

        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new Exception("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    public static int toDigit(char ch, int index) throws Exception {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new Exception("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

    /**
     * DES加密算法
     *
     * @param data
     * @param cryptKey
     *
     * @return
     *
     * @throws Exception
     */
    public final static String encrypt(String data, String cryptKey) throws Exception {
        return byte2hex(encrypt(data.getBytes(), cryptKey.getBytes()));
    }

    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(data);
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }
	
	/*public static void main(String[] args) {
		String cryptKey = "schoolcodekeyqxq";
		String data = "1";
		try {
			String t1 = RDes.encrypt(data, cryptKey);
			String t2 = RDes.decrypt(t1, cryptKey);
			System.out.println(t1);
			System.out.println(t2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

    public static void main(String[] args) throws Exception {
        String encrypt = "38f44b346a5f99ec03f1d979669bff647018273ef4defaa89adf5a769ff03dbb9c05577915c900bd223281ac31d4ebaca12fce92eba84a40efeec95c7a3b778ab45ce37e13cf6c92724cd6a8101682210461da7866859f96d4b9455684612366ff4cf513d3e438c04e6674b83d999cb73f949217bb6748e0e35556f054fcd902ab79225b69885c11c5caac2982b05f2be27c417b97d0f6ddfebc8be4179a56dfea909db9bd13f581e8be20285d6767ddbfe40986df2b0a8eaacd57a8caada26190f4c21b50cc35bb0993d0729088d2d6ed7c2d6093caeab254220b1841c9335a";
        System.out.println("decrypt:" + hmzxdecrypt(encrypt, "7r2lpy0gv1owkeq6"));
    }
}