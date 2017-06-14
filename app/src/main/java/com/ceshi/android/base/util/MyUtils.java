package com.ceshi.android.base.util;

/**
 * Created by killer on 16/2/24.
 */
public class MyUtils {
    /**
     * 随机生成图片名称
     * @return
     */
    public static String getImageName(){
        String imageName=getCheckNum()+System.currentTimeMillis()+".jpg";//6位随机数+时间戳，组成图片名
        return imageName;
    }

    /**
     * 生成一个6位的随机验证码
     * @return 6位的随机验证码
     */
    public static String getCheckNum() {
        String num = (int) ((Math.random() * 9 + 1) * 100000) + "";
        return num;
    }
}
