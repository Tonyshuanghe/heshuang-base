//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.geo;

public class CellNoScope {
    private String part;
    private Integer level;
    private Double minLng;
    private Double maxLng;
    private Double minLat;
    private Double maxLat;

    public void setLng(Double minLng, Double maxLng) {
        this.minLng = minLng;
        this.maxLng = maxLng;
    }

    public void setLng(Integer minLng, Integer maxLng) {
        this.minLng = (double)minLng;
        this.maxLng = (double)maxLng;
    }

    public void setLat(Double minLat, Double maxLat) {
        this.minLat = minLat;
        this.maxLat = maxLat;
    }

    public void setLat(Integer minLat, Integer maxLat) {
        this.minLat = (double)minLat;
        this.maxLat = (double)maxLat;
    }

    public static CellNoScope of(Integer level) {
        CellNoScope scope = new CellNoScope();
        scope.setLevel(level);
        return scope;
    }

    public CellNoScope() {
    }

    public String getPart() {
        return this.part;
    }

    public Integer getLevel() {
        return this.level;
    }

    public Double getMinLng() {
        return this.minLng;
    }

    public Double getMaxLng() {
        return this.maxLng;
    }

    public Double getMinLat() {
        return this.minLat;
    }

    public Double getMaxLat() {
        return this.maxLat;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setMinLng(Double minLng) {
        this.minLng = minLng;
    }

    public void setMaxLng(Double maxLng) {
        this.maxLng = maxLng;
    }

    public void setMinLat(Double minLat) {
        this.minLat = minLat;
    }

    public void setMaxLat(Double maxLat) {
        this.maxLat = maxLat;
    }

}
