package org.casual.yummy.utils;

import org.casual.yummy.model.Anchor;

public class DistanceUtil {

    private static final double EARTH_RADIUS = 6378137;

    private static final double VELOCITY = 200.0;

    private static double distance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * Math.PI / 180.0;
        double radLat2 = lat2 * Math.PI / 180.0;
        double radLng1 = lng1 * Math.PI / 180.0;
        double radLng2 = lng2 * Math.PI / 180.0;
        double a = radLat1 - radLat2;
        double b = radLng1 - radLng2;
        return 2 * Math.asin(
                Math.sqrt(
                        Math.pow(Math.sin(a / 2), 2) +
                                Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)
                )
        ) * EARTH_RADIUS;
    }

    public static int predictMinutesToArrive(Anchor from, Anchor to) {
        // location regex like: 北京市,北京市，西城区
        String[] fromRegions = from.getLocation().split(",");
        String[] toRegions = to.getLocation().split(",");
        int len = fromRegions.length < toRegions.length ? fromRegions.length : toRegions.length;
        len = 2 < len ? 2 : len; // 最多只比较前两个区域
        for (int i = 0; i < len; i++) {
            if (!fromRegions[i].equalsIgnoreCase(toRegions[i]))
                return -1;
        }
        // 在同一区域，计算距离
        double miles = distance(from.getLng(), from.getLat(), to.getLng(), to.getLat());
        return (int) (miles / VELOCITY);
    }
}
