//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.geo;
/**
 * @author heshuang
 * @version v1.0
 * @CreationTime: - 2022年10月24日14:38:00
 * @Description: 坐标系转换utils
 */
public class CoordinateTransformUtil {
    static double X_PI = 52.35987755982988D;
    static double PI = 3.141592653589793D;
    static double A = 6378245.0D;
    static double EE = 0.006693421622965943D;


    public static GpsInfo bd09ToWgs84(double lng, double lat) {
        GpsInfo gcj = bd09ToGcj02(lng, lat);
        GpsInfo wgs84 = gcj02ToWgs84(gcj.getLon(), gcj.getLat());
        return wgs84;
    }

    public static GpsInfo wgs84ToBd09(double lng, double lat) {
        GpsInfo gcj = wgs84ToGcj02(lng, lat);
        GpsInfo bd09 = gcj02ToBd09(gcj.getLon(), gcj.getLat());
        return bd09;
    }

    public static GpsInfo gcj02ToBd09(double lng, double lat) {
        double z = Math.sqrt(lng * lng + lat * lat) + 2.0E-5D * Math.sin(lat * X_PI);
        double theta = Math.atan2(lat, lng) + 3.0E-6D * Math.cos(lng * X_PI);
        double bdLng = z * Math.cos(theta) + 0.0065D;
        double bdLat = z * Math.sin(theta) + 0.006D;
        return GpsInfo.of(bdLng, bdLat);
    }

    public static GpsInfo bd09ToGcj02(double bdLon, double bdLat) {
        double x = bdLon - 0.0065D;
        double y = bdLat - 0.006D;
        double z = Math.sqrt(x * x + y * y) - 2.0E-5D * Math.sin(y * X_PI);
        double theta = Math.atan2(y, x) - 3.0E-6D * Math.cos(x * X_PI);
        double ggLng = z * Math.cos(theta);
        double ggLat = z * Math.sin(theta);
        return GpsInfo.of(ggLng, ggLat);
    }

    public static GpsInfo wgs84ToGcj02(double lng, double lat) {
        if (outOfChina(lng, lat)) {
            return GpsInfo.of(lng, lat);
        } else {
            double dlat = transformLat(lng - 105.0D, lat - 35.0D);
            double dlng = transformLng(lng - 105.0D, lat - 35.0D);
            double radLat = lat / 180.0D * PI;
            double magic = Math.sin(radLat);
            magic = 1.0D - EE * magic * magic;
            double sqrtMagic = Math.sqrt(magic);
            dlat = dlat * 180.0D / (A * (1.0D - EE) / (magic * sqrtMagic) * PI);
            dlng = dlng * 180.0D / (A / sqrtMagic * Math.cos(radLat) * PI);
            double mgLat = lat + dlat;
            double mgLng = lng + dlng;
            return GpsInfo.of(mgLng, mgLat);
        }
    }

    public static GpsInfo gcj02ToWgs84(double lng, double lat) {
        if (outOfChina(lng, lat)) {
            return GpsInfo.of(lng, lat);
        } else {
            double dlat = transformLat(lng - 105.0D, lat - 35.0D);
            double dlng = transformLng(lng - 105.0D, lat - 35.0D);
            double radlat = lat / 180.0D * PI;
            double magic = Math.sin(radlat);
            magic = 1.0D - EE * magic * magic;
            double sqrtMagic = Math.sqrt(magic);
            dlat = dlat * 180.0D / (A * (1.0D - EE) / (magic * sqrtMagic) * PI);
            dlng = dlng * 180.0D / (A / sqrtMagic * Math.cos(radlat) * PI);
            double mgLat = lat + dlat;
            double mgLng = lng + dlng;
            return GpsInfo.of(lng * 2.0D - mgLng, lat * 2.0D - mgLat);
        }
    }

    public static double transformLat(double lng, double lat) {
        double ret = -100.0D + 2.0D * lng + 3.0D * lat + 0.2D * lat * lat + 0.1D * lng * lat + 0.2D * Math.sqrt(Math.abs(lng));
        ret += (20.0D * Math.sin(6.0D * lng * PI) + 20.0D * Math.sin(2.0D * lng * PI)) * 2.0D / 3.0D;
        ret += (20.0D * Math.sin(lat * PI) + 40.0D * Math.sin(lat / 3.0D * PI)) * 2.0D / 3.0D;
        ret += (160.0D * Math.sin(lat / 12.0D * PI) + 320.0D * Math.sin(lat * PI / 30.0D)) * 2.0D / 3.0D;
        return ret;
    }

    public static double transformLng(double lng, double lat) {
        double ret = 300.0D + lng + 2.0D * lat + 0.1D * lng * lng + 0.1D * lng * lat + 0.1D * Math.sqrt(Math.abs(lng));
        ret += (20.0D * Math.sin(6.0D * lng * PI) + 20.0D * Math.sin(2.0D * lng * PI)) * 2.0D / 3.0D;
        ret += (20.0D * Math.sin(lng * PI) + 40.0D * Math.sin(lng / 3.0D * PI)) * 2.0D / 3.0D;
        ret += (150.0D * Math.sin(lng / 12.0D * PI) + 300.0D * Math.sin(lng / 30.0D * PI)) * 2.0D / 3.0D;
        return ret;
    }

    public static boolean outOfChina(double lng, double lat) {
        if (!(lng < 72.004D) && !(lng > 137.8347D)) {
            return lat < 0.8293D || lat > 55.8271D;
        } else {
            return true;
        }
    }
}
