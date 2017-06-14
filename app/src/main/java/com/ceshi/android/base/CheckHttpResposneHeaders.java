package com.ceshi.android.base;

import com.ceshi.android.base.Const;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/5/18.
 */
public class CheckHttpResposneHeaders {

    public static void main(String[] args) throws IOException {
        URL url = new URL(Const.RegisterPicture);
        URLConnection conn = url.openConnection();

        Map headers = conn.getHeaderFields();
        Set<String> keys = headers.keySet();
        for( String key : keys ){
            String val = conn.getHeaderField(key);
            System.out.println(key+"    "+val);
        }

        System.out.println( conn.getLastModified() );
    }
}
