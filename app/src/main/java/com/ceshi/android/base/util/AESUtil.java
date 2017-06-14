package com.ceshi.android.base.util;

import android.util.Base64;

import com.ceshi.android.util.Logger;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by killer on 16/2/17.
 * AES-
 */
public class AESUtil {
    public final static String key = "zhongyou@16!bsb*";

    /**
     * 加密
     * @param sSrc
     * @return
     */
    public static String Encrypt(String sSrc){
        return Encrypt(sSrc,key);
    }

    /**
     * 加密
     * @param sSrc
     * @param sKey
     * @return
     */
    public static String Encrypt(String sSrc, String sKey){
        if (sKey == null) {
            Logger.d("AESUtil", "Key为空null");
            return null;
        }
        if (sKey.length() != 16) {// 判断Key是否为16位
            Logger.d("AESUtil","Key长度不是16位");
            return null;
        }

        try {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AESUtil");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        } catch (BadPaddingException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 解密
     * @param sSrc
     * @return
     */
    public static String Decrypt(String sSrc){
        return Decrypt(sSrc,key);
    }

    /**
     * 解密
     * @param sSrc
     * @param sKey
     * @return
     */
    public static String Decrypt(String sSrc, String sKey){
        try {
            // 判断Key是否正确
            if (sKey == null) {
                Logger.d("AESUtil", "Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                Logger.d("AESUtil", "Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AESUtil");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1=Base64.decode(sSrc,Base64.DEFAULT);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                Logger.d("AESUtil", e.toString());
                return null;
            }
        } catch (Exception ex) {
            Logger.d("AESUtil", ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "1234567890123456";
        // 需要加密的字串
        String cSrc = "我爱你这是一些需要加密的数据，加密之后即使被拦截袭来，不得到秘钥，也是无法破解查查看的";
        System.out.println(cSrc);
        // 加密
        String enString = AESUtil.Encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AESUtil.Decrypt(enString, cKey);
        System.out.println("解密后的字串是：" + DeString);
    }

}
