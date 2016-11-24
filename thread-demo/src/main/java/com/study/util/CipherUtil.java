package com.study.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 加解密工具类
 *
 * @author yutong on 16/9/5
 */
public class CipherUtil {

    private static Logger logger = LoggerFactory.getLogger(CipherUtil.class);

    /**
     * SHA1加密
     * @param decript 待加密字符串
     * @return 加密后字符串
     */
    public static String encrypyBySHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes(Charset.forName("UTF-8")));
            // Create Hex String
            return byte2Hex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("SHA-1 加密错误" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * AES加密
     * @param inputStr，待加密字符串
     * @param key 密钥
     * @return 加密后字符
     */
    public static String encrypt(String inputStr,String key){
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            /*SecureRandom 实现完全隨操作系统本身的內部状态，所以这里通过setSeed方法避免linux系统以及和windows系统的不一致*/
            secureRandom.setSeed(key.getBytes(Charset.forName("UTF-8")));
            kgen.init(128, secureRandom);

            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = inputStr.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return byte2Hex(result); // 加密

        } catch (Exception e) {
            logger.error("AES 加密错误" + e.getMessage(), e);
        }

        return null;
    }

    /**
     * AES解密
     * @param cipherHexStr，待解密16进制字符串
     * @param key 密钥
     * @return 解密字符
     */
    public static String decrypt(String cipherHexStr,String key){
        KeyGenerator kgen;
        try{
            kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );
            secureRandom.setSeed(key.getBytes(Charset.forName("UTF-8")));
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, keySpec);// 初始化
            //将16进制字符串转化为字节数组
            byte[] b = Hex2byte(cipherHexStr);

            byte[] result = cipher.doFinal(b);
            return new String(result, Charset.forName("UTF-8"));
        }catch (Exception e) {
            logger.error("AES 解密错误" + e.getMessage(), e);
        }

        return null;
    }



    /**
     * 将字节数组转换为16进制字符串
     * @param b 数组
     * @return 字符串
     */
    private static String byte2Hex(byte[] b){
        StringBuffer hexStrBuf = new StringBuffer();
        String tempStr = "";
        for(int i=0;i<b.length;i++){
            tempStr = Integer.toHexString(b[i]&0XFF);
            if(tempStr.length()==1){
                hexStrBuf.append("0"+tempStr);
            }else{
                hexStrBuf.append(tempStr);
            }
        }
        return hexStrBuf.toString();
    }

    /**
     * 将16进制字符串转化为字节数组
     * @param hexStr 字符串
     * @return 数组
     */
    private static byte[] Hex2byte(String hexStr){
        if(hexStr == null){
            return null;
        }
        //判断是否为16进制字符串
        int len = hexStr.length();
        if (len % 2 != 0) {
            return null;
        }
        byte[] b = new byte[len / 2];
        for (int i = 0; i != len / 2; i++) {
            b[i] = (byte) Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;

    }
}
