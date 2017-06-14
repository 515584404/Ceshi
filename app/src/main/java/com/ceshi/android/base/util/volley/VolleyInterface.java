package com.ceshi.android.base.util.volley;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by killer on 16/2/18.
 */
public abstract class VolleyInterface {

    public static Response.Listener<String> listener;
    public static Response.ErrorListener errorListener;

    public abstract void onSuccess(String result);

    public abstract void onError(VolleyError error);

    public Response.Listener<String> okListener() {
        listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onSuccess(response);
                //onSuccess(AESUtil.Decrypt(response, AESUtil.key));
            }
        };
        return listener;
    }

    public Response.ErrorListener errorListener() {
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onError(error);
            }
        };
        return errorListener;
    }
}
