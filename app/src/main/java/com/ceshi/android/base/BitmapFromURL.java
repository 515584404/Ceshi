package com.ceshi.android.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by ztr on 2016/05/03.
 */
public class BitmapFromURL {
    public static String jsessionId = null;
    public static Bitmap getHttpBitmap(String url){
        URL myFileURL;
        Bitmap bitmap=null;
        try{
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
            conn.setConnectTimeout(6000);
            Log.i("luobo","获取图片验证码的时候cookie是"+ com.ceshi.android.base.BaseApplication.getInstence().mUserCookie);
//            if(BaseApplication.getInstence().mUserCookie!=null && !"".equals(BaseApplication.getInstence().mUserCookie)){
//                Log.i("luobo","获取图片验证码的时候设置了cookie");
//                conn.setRequestProperty("Cookie", BaseApplication.getInstence().mUserCookie);
//            }
            //连接设置获得数据流
            conn.setDoInput(true);
            //不使用缓存
            conn.setUseCaches(false);
            Map<String, List<String>> map = conn.getHeaderFields();

            System.out.println("显示响应Header信息...\n");

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println("Key : " + entry.getKey() +
                        " ,Value : " + entry.getValue());
            }

                String str = String.valueOf(map.get("jsessionId"));
            Log.d("TAG", "JessonIdstr:" + str);
            jsessionId=str.substring(1,str.length()-1);

            System.out.println("jjjjjjjj"+jsessionId);

            //这句可有可无，没有影响
            //conn.connect();
            //得到数据流
            InputStream is = conn.getInputStream();
            //解析得到图片
            bitmap = BitmapFactory.decodeStream(is);
            //关闭数据流
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return bitmap;

    }

    public static String getJsessionId() {
        return jsessionId;
    }

    public static void setJsessionId(String jsessionId) {
        BitmapFromURL.jsessionId = jsessionId;
    }


}
