package com.ceshi.android.base.util;

import java.text.DecimalFormat;


/**
 * 计算距离
 * @author killer
 *
 */
public class Distance {
	private final static double EARTH_RADIUS = 6378137.0;  
	public static String GetDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
	       double radLat1 = (lat_a * Math.PI / 180.0);
	       double radLat2 = (lat_b * Math.PI / 180.0);
	       double a = radLat1 - radLat2;
	       double b = (lng_a - lng_b) * Math.PI / 180.0;
	       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                   + Math.cos(radLat1) * Math.cos(radLat2)
                   * Math.pow(Math.sin(b / 2), 2)));
	       s = s * EARTH_RADIUS/1000;
	       //s = Math.round(s * 10000) / (10000);
	       DecimalFormat df = new DecimalFormat("#.0");
	       return df.format(s);
	}

    public static String GetDistance(String all, double lat_b, double lng_b) {
        if (all==null||all==""){
            return "未知";
        }
        if (all.contains(",")) {
            String alls[]=all.split(",");
            if (alls.length > 1) {
                double radLat1 = (Double.valueOf(alls[0]) * Math.PI / 180.0);
                double radLat2 = (lat_b * Math.PI / 180.0);
                double a = radLat1 - radLat2;
                double b = (Double.valueOf(alls[1]) - lng_b) * Math.PI / 180.0;
                double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                        + Math.cos(radLat1) * Math.cos(radLat2)
                        * Math.pow(Math.sin(b / 2), 2)));
                s = s * EARTH_RADIUS/1000;
                //s = Math.round(s * 10000) / (10000);
                DecimalFormat df = new DecimalFormat("0.0");
                return df.format(s)+"KM";

            } else {
                return "未知";
            }
        }else{
            return "未知";
        }
    }
}
