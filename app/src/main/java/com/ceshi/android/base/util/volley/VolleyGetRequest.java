package com.ceshi.android.base.util.volley;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.ceshi.android.base.BaseApplication;

import java.util.Map;

/**
 * Created by killer on 16/2/18.
 */
public class VolleyGetRequest {


    public static void GetRequest(String url,String tag,final Map<String,String> params,
                                   VolleyInterface mInterface){
        StringRequest mRequest=new StringRequest(
                Request.Method.GET,
                url,
                mInterface.okListener(),
                mInterface.errorListener()
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };;
        mRequest.setTag(tag);
        BaseApplication.getInstence().getRequestQueue().add(mRequest);
    }

}
