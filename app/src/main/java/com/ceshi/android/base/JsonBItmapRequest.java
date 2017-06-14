package com.ceshi.android.base;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.ceshi.android.base.util.SharePrefUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ztr on 2016/04/27.
 */
public class JsonBItmapRequest extends Request<String> {
    private Map<String, String> mMap;
    private Response.Listener<String> mListener;
    public String cookieFromResponse;
    private String mHeader;
    private Context context;
    String jssonid;
    private Map<String, String> sendHeader=new HashMap<String, String>(1);
    public JsonBItmapRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mListener = listener;
    }

    //当http请求是post时，则需要该使用该函数设置往里面添加的键值对
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mMap;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            mHeader = response.headers.toString();
            Pattern pattern=Pattern.compile("jsessionId");
            System.out.println("mHeadermHeadermHeadermHeadermHeadermHeader"+mHeader);
            String spStr[] = mHeader.split(",|=");
            jssonid=spStr[11];
            System.out.println("idididididididid"+jssonid);
            SharePrefUtil.saveString(context,"id",jssonid);
            //  android.webkit.CookieManager.getInstance().
            Matcher m=pattern.matcher(mHeader);
            if(m.find()){
                cookieFromResponse =m.group();
                System.out.println("cookieFromResponsecookieFromResponsecookieFromResponse"+cookieFromResponse);
            }

            // mHeader.
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
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
