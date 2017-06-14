package com.ceshi.android.base;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestManager {

	public static RequestQueue getRequestQueue(Context context){
		return Volley.newRequestQueue(context); 
	}
}
