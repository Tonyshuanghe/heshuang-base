//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.geo;

import lombok.Data;

@Data
public class GpsInfo {
    private Double lon;
    private Double lat;

    public GpsInfo(Double lon, Double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public static GpsInfo of(Double lon, Double lat) {
        return new GpsInfo(lon, lat);
    }


}
