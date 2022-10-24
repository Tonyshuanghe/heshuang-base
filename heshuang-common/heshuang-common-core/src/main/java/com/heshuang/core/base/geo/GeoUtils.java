//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.geo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.google.common.geometry.*;
import com.heshuang.core.base.exception.BusinessException;
import jdk.nashorn.internal.objects.Global;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author heshuang
 * @version v1.0
 * @CreationTime: - 2022年10月24日14:38:00
 * @Description: geoutils
 */
public class GeoUtils {
    private static final Logger log = LoggerFactory.getLogger(GeoUtils.class);

    public GeoUtils() {
    }

    public static S2CellId latLngToId(double lat, double lng) {
        S2LatLng s2LatLng = S2LatLng.fromDegrees(lat, lng);
        S2CellId celId = S2CellId.fromLatLng(s2LatLng);
        return celId;
    }

    public static S2LatLng idToLatLng(long id) {
        return (new S2CellId(id)).toLatLng();
    }

    public static double distance(double startLat, double startLon, double endLat, double endLon) {
        S2LatLng startS2 = S2LatLng.fromDegrees(startLat, startLon);
        S2LatLng endS2 = S2LatLng.fromDegrees(endLat, endLon);
        return startS2.getEarthDistance(endS2);
    }

    public static double distance(@NotNull String[] latLons) {
        double distance = 0.0D;
        if (latLons != null && latLons.length > 1) {
            Double preLat = null;
            Double preLon = null;
            String[] var5 = latLons;
            int var6 = latLons.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String latLon = var5[var7];
                if (StringUtils.isNotBlank(latLon) && latLon.indexOf(",") > 0) {
                    String[] arr = latLon.split(",");
                    if (arr.length == 2) {
                        try {
                            if (preLat != null && preLon != null) {
                                distance += distance(preLat, preLon, Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                            }

                            preLat = Double.parseDouble(arr[0]);
                            preLon = Double.parseDouble(arr[1]);
                        } catch (Exception var11) {
                            log.warn(var11.getMessage());
                        }
                    }
                }
            }

            return distance;
        } else {
            return distance;
        }
    }

    public static double distance(@NotNull String latLonStr) {
        return StringUtils.isBlank(latLonStr) ? 0.0D : distance(latLonStr.split(";"));
    }

    public static S2LatLngRect newRect(double startLat, double startLon, double endLat, double endLon) {
        S2LatLng startS2 = S2LatLng.fromDegrees(startLat, startLon);
        S2LatLng endS2 = S2LatLng.fromDegrees(endLat, endLon);
        return new S2LatLngRect(startS2, endS2);
    }

    public static S2Polygon newPolygon(@NotNull String[] latLons) {
        if (latLons != null && latLons.length > 0) {
            List<S2Point> vertices = Lists.newArrayList();
            String[] var2 = latLons;
            int var3 = latLons.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String latLon = var2[var4];
                if (StringUtils.isNotBlank(latLon) && latLon.indexOf(",") > 0) {
                    String[] arr = latLon.split(",");
                    if (arr.length == 2) {
                        try {
                            vertices.add(S2LatLng.fromDegrees(Double.parseDouble(arr[0]), Double.parseDouble(arr[1])).toPoint());
                        } catch (Exception var8) {
                            log.warn(var8.getMessage());
                        }
                    }
                }
            }

            if (!CollectionUtils.isEmpty(vertices)) {
                return new S2Polygon(new S2Loop(vertices));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static S2Polygon newPolygon(@NotNull String latLonStr) {
        return StringUtils.isBlank(latLonStr) ? null : newPolygon(latLonStr.split(";"));
    }

    public static S2Cap newCap(double radius, double lat, double lon) {
        double capHeight = 6.283185307179586D * (radius / 4.0075017E7D);
        S2LatLng s2LatLng = S2LatLng.fromDegrees(lat, lon);
        return S2Cap.fromAxisHeight(s2LatLng.toPoint(), capHeight * capHeight / 2.0D);
    }

    public static boolean contains(double lat, double lon, String latLonStr) {
        return newPolygon(latLonStr).contains(S2LatLng.fromDegrees(lat, lon).toPoint());
    }

    public static boolean contains(double lat, double lon, double radius, double capLat, double capLon) {
        return newCap(radius, capLat, capLon).contains(S2LatLng.fromDegrees(lat, lon).toPoint());
    }

    public static List<String> in(String[] latLons, S2Cap region) {
        if(region == null){
            throw BusinessException.of("区域不能为空");
        }
        List<String> result = Lists.newArrayList();
        String[] var3 = latLons;
        int var4 = latLons.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String latLon = var3[var5];
            if (StringUtils.isNotBlank(latLon) && latLon.indexOf(",") > 0) {
                String[] arr = latLon.split(",");
                if (arr.length == 2) {
                    try {
                        if (region.contains(S2LatLng.fromDegrees(Double.valueOf(arr[0]), Double.valueOf(arr[1])).toPoint())) {
                            result.add(latLon);
                        }
                    } catch (Exception var9) {
                        log.warn(var9.getMessage());
                    }
                }
            }
        }

        return result;
    }

    public static List<String> in(String latLonStr, S2Cap region) {
        return (List)(StringUtils.isBlank(latLonStr) ? Lists.newArrayList() : in(latLonStr.split(";"), region));
    }

    public static List<String> in(String[] latLons, S2Polygon region) {
        if(region == null){
            throw BusinessException.of("区域不能为空");
        }
        List<String> result = Lists.newArrayList();
        String[] var3 = latLons;
        int var4 = latLons.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            String latLon = var3[var5];
            if (StringUtils.isNotBlank(latLon) && latLon.indexOf(",") > 0) {
                String[] arr = latLon.split(",");
                if (arr.length == 2) {
                    try {
                        if (region.contains(S2LatLng.fromDegrees(Double.valueOf(arr[0]), Double.valueOf(arr[1])).toPoint())) {
                            result.add(latLon);
                        }
                    } catch (Exception var9) {
                        log.warn(var9.getMessage());
                    }
                }
            }
        }

        return result;
    }

    public static List<String> in(String latLonStr, S2Polygon region) {
        return (List)(StringUtils.isBlank(latLonStr) ? Lists.newArrayList() : in(latLonStr.split(";"), region));
    }

    public static List<GeoData> position(List<String> ms) {
        return position(ms, false);
    }

    public static List<GeoData> position(List<String> ms, boolean online) {
        RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("geoRedisTemplate");
        List<GeoData> data = Lists.newArrayList();
        if (redisTemplate != null && !CollectionUtils.isEmpty(ms)) {
            List<Point> points = redisTemplate.opsForGeo().position(String.format("lbs:%s:geo", getProjectId()), ms.toArray());
            List<String> times = redisTemplate.opsForHash().multiGet(String.format("lbs:%s:time", getProjectId()), ms);
            List<String> onlineKey = Lists.newArrayList();
            if (!online) {
                Set<String> keys = redisTemplate.keys(String.format("lbs:%s:%s:online", getProjectId(), "*"));
                if (!CollectionUtils.isEmpty(keys)) {
                    onlineKey = redisTemplate.opsForValue().multiGet(keys);
                }
            }

            if (!CollectionUtils.isEmpty(points)) {
                for(int i = 0; i < ms.size(); ++i) {
                    GeoData d = null;
                    Point p = (Point)points.get(i);
                    if (p != null) {
                        GeoData.GeoDataBuilder b = GeoData.builder().id((String)ms.get(i)).lon(p.getX()).lat(p.getY()).time(DateUtil.parseDateTime(times.get(i)));
                        if (!online) {
                            if (((List)onlineKey).contains(ms.get(i))) {
                                b.isOnline(true);
                            }
                        } else {
                            b.isOnline(true);
                        }

                        d = b.build();
                    }

                    data.add(d);
                }
            }
        }

        return data;
    }

    public static List<GeoData> onlinePosition() {
        RedisTemplate redisTemplate = (RedisTemplate) SpringUtil.getBean("geoRedisTemplate");
        if (redisTemplate != null) {
            Set<String> keys = redisTemplate.keys(String.format("lbs:%s:%s:online", getProjectId(), "*"));
            if (!CollectionUtils.isEmpty(keys)) {
                List<String> onlineKey = redisTemplate.opsForValue().multiGet(keys);
                return position(onlineKey, true);
            }
        }

        return null;
    }

    public static List<GeoData> onlinePosition(Double radius, Double lat, Double lon) {
        List<GeoData> geoData = onlinePosition();
        if (!CollectionUtils.isEmpty(geoData)) {
            S2Cap cap = newCap(radius, lat, lon);
            geoData = (List)geoData.stream().filter((geo) -> {
                return geo != null && cap.contains(S2LatLng.fromDegrees(geo.getLat(), geo.getLon()).toPoint());
            }).collect(Collectors.toList());
        }

        return geoData;
    }

    public static String getProjectId() {
        String projectId = "null";
        //TODO 想id
        return projectId;
    }
}
