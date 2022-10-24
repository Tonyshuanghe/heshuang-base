//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.geo;

import lombok.Data;

import java.util.Date;

@Data
public class GeoData {
    private String id;
    private Double lat;
    private Double lon;
    private Date time;
    private Boolean isOnline = false;

    GeoData(String id, Double lat, Double lon, Date time, Boolean isOnline) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
        this.isOnline = isOnline;
    }

    public static GeoData.GeoDataBuilder builder() {
        return new GeoData.GeoDataBuilder();
    }



    public static class GeoDataBuilder {
        private String id;
        private Double lat;
        private Double lon;
        private Date time;
        private Boolean isOnline;

        GeoDataBuilder() {
        }

        public GeoData.GeoDataBuilder id(String id) {
            this.id = id;
            return this;
        }

        public GeoData.GeoDataBuilder lat(Double lat) {
            this.lat = lat;
            return this;
        }

        public GeoData.GeoDataBuilder lon(Double lon) {
            this.lon = lon;
            return this;
        }

        public GeoData.GeoDataBuilder time(Date time) {
            this.time = time;
            return this;
        }

        public GeoData.GeoDataBuilder isOnline(Boolean isOnline) {
            this.isOnline = isOnline;
            return this;
        }

        public GeoData build() {
            return new GeoData(this.id, this.lat, this.lon, this.time, this.isOnline);
        }

    }
}
