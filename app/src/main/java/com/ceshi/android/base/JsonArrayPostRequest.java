package com.ceshi.android.base;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ztr on 2016/04/27.
 */
public class JsonArrayPostRequest extends Request<JSONArray> {
    private Response.Listener<JSONArray> mListener;
    private String mHeader;
    private Map<String, String> mMap;
    public String cookieFromResponse;
    private Map<String, String> sendHeader=new HashMap<String, String>(1);
    public JsonArrayPostRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener, Map map) {
        super(Request.Method.POST, url, errorListener);
        mListener=listener;
        mMap=map;

    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            mHeader = response.headers.toString();
            //使用正则表达式从reponse的头中提取cookie内容的子串
            Pattern pattern=Pattern.compile("Set-Cookie.*?;");
            Matcher m=pattern.matcher(mHeader);
            if(m.find()){
                cookieFromResponse =m.group();
                Log.w("LOG","cookie from server "+ cookieFromResponse);
            }
            JSONArray jsonArray = new JSONArray(jsonString);
            if(cookieFromResponse!=null&&!cookieFromResponse.equals("")){
                //去掉cookie末尾的分号
                cookieFromResponse = cookieFromResponse.substring(11,cookieFromResponse.length()-1);
                Log.w("LOG","cookie substring "+ cookieFromResponse);
                //将cookie字符串添加到jsonObject中，该jsonObject会被deliverResponse递交，调用请求时则能在onResponse中得到

                jsonArray.put(Integer.parseInt("Cookie"),cookieFromResponse);
                Log.w("LOG","jsonObject "+ jsonArray.toString());
            }
            return Response.success(jsonArray,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONArray  response) {
        mListener.onResponse(response);
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return sendHeader;
    }
    public void setSendCookie(String cookie){
        sendHeader.put("Cookie",cookie);
    }
}
