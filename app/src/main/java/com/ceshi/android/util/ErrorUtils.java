package com.ceshi.android.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @description 网络工具类
 * @author killer
 * @version 1.0
 */
public class ErrorUtils {
	private Context mContext;

	public ErrorUtils(Context context) {
		mContext = context;
	}

	/**
	 * 获取当前的网络状态
	 *
	 * @return
	 */
	public boolean showErrorToast(int id,String errorStr) {
		if (id==0){
			return true;
		}else{
			Toast.makeText(mContext,errorStr,Toast.LENGTH_SHORT).show();
			return false;
		}
	}

}
