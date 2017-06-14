package com.ceshi.android.db;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by killer on 16/1/10.
 */
public class MySharedPreferences {
    private SharedPreferences mUser;
    private SharedPreferences.Editor mUserEd;

    public MySharedPreferences(Context context) {
        this.mUser=context.getSharedPreferences("user", Activity.MODE_PRIVATE);
    }

    //UUID
    private String getUUID()    {
        return mUser.getString("UUID","");
    }
    public void setUUID(String uuid){
        this.mUserEd=this.mUser.edit();
        this.mUserEd.putString("UUID",uuid);
        this.mUserEd.commit();
    }
}
