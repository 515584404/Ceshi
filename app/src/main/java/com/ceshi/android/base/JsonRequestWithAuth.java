package com.ceshi.android.base;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/5/17.
 */
public class JsonRequestWithAuth <T> extends Request<T> {

    private final Gson gson = new Gson();
         private final Response.Listener<T> listener;
    private Map<String, String> mMap;
    private Response.Listener<Integer> mListener;
    public String cookieFromResponse;
    private JsonObject jsonObject;
    private String mHeader;
    private Map<String, String> sendHeader=new HashMap<String, String>(1);

         /**
           * 设置访问自己服务器时必须传递的参数，密钥等
           */


                 public JsonRequestWithAuth(String url, Response.Listener<T> listener,
                                             Response.ErrorListener errorListener)
           {
                 super(Method.GET, url, errorListener);
                 this.listener = listener;
             }



                 @Override
         protected void deliverResponse(T response)
         {
                 listener.onResponse(response);
             }

                 @Override
              protected Response<T> parseNetworkResponse(NetworkResponse response)
              {
                 try
                 {       /**
                           * 得到返回的数据
                           */
                      final   String jsonStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                         /**
                           * 转化成对象
                           */
                     mHeader = response.headers.toString();
//            Log.w("LOG","get headers in parseNetworkResponse "+response.headers.toString());
                     //使用正则表达式从reponse的头中提取cookie内容的子串
                     Pattern pattern=Pattern.compile("Set-Cookie.*?;");
                     Matcher m=pattern.matcher(mHeader);
                     if(m.find()){
                         cookieFromResponse =m.group();
//                Log.w("LOG","cookie from server "+ cookieFromResponse);
                     }
                     Integer  integer = new Integer(jsonStr);

                     if(cookieFromResponse!=null&&!cookieFromResponse.equals("")){
                         //去掉cookie末尾的分号
                         cookieFromResponse = cookieFromResponse.substring(11,cookieFromResponse.length()-1);
//                Log.w("LOG","cookie substring "+ cookieFromResponse);
                         //将cookie字符串添加到jsonObject中，该jsonObject会被deliverResponse递交，调用请求时则能在onResponse中得到
                           Integer.valueOf("Cookie", Integer.parseInt(cookieFromResponse));

//                Log.w("LOG","jsonObject "+ jsonObject.toString());
                     }
                         return (Response<T>) Response.success(integer, HttpHeaderParser.parseCacheHeaders(response));
                     } catch (UnsupportedEncodingException e)
                 {
                         return Response.error(new ParseError(e));
                     } catch (JsonSyntaxException e)
                 {
                         return Response.error(new ParseError(e));
                    }
           }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return sendHeader;
    }

    public void setSendCookie(String cookie){
        sendHeader.put("Cookie",cookie);
    }
}
