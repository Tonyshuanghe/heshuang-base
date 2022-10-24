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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CellNoScope)) {
            return false;
        } else {
            CellNoScope other = (CellNoScope)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$part = this.getPart();
                Object other$part = other.getPart();
                if (this$part == null) {
                    if (other$part != null) {
                        return false;
                    }
                } else if (!this$part.equals(other$part)) {
                    return false;
                }

                Object this$level = this.getLevel();
                Object other$level = other.getLevel();
                if (this$level == null) {
                    if (other$level != null) {
                        return false;
                    }
                } else if (!this$level.equals(other$level)) {
                    return false;
                }

                Object this$minLng = this.getMinLng();
                Object other$minLng = other.getMinLng();
                if (this$minLng == null) {
                    if (other$minLng != null) {
                        return false;
                    }
                } else if (!this$minLng.equals(other$minLng)) {
                    return false;
                }

                label62: {
                    Object this$maxLng = this.getMaxLng();
                    Object other$maxLng = other.getMaxLng();
                    if (this$maxLng == null) {
                        if (other$maxLng == null) {
                            break label62;
                        }
                    } else if (this$maxLng.equals(other$maxLng)) {
                        break label62;
                    }

                    return false;
                }

                label55: {
                    Object this$minLat = this.getMinLat();
                    Object other$minLat = other.getMinLat();
                    if (this$minLat == null) {
                        if (other$minLat == null) {
                            break label55;
                        }
                    } else if (this$minLat.equals(other$minLat)) {
                        break label55;
                    }

                    return false;
                }

                Object this$maxLat = this.getMaxLat();
                Object other$maxLat = other.getMaxLat();
                if (this$maxLat == null) {
                    if (other$maxLat != null) {
                        return false;
                    }
                } else if (!this$maxLat.equals(other$maxLat)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof CellNoScope;
    }

    public int hashCode() {
        int PRIME = true;
        int result = 1;
        Object $part = this.getPart();
        int result = result * 59 + ($part == null ? 43 : $part.hashCode());
        Object $level = this.getLevel();
        result = result * 59 + ($level == null ? 43 : $level.hashCode());
        Object $minLng = this.getMinLng();
        result = result * 59 + ($minLng == null ? 43 : $minLng.hashCode());
        Object $maxLng = this.getMaxLng();
        result = result * 59 + ($maxLng == null ? 43 : $maxLng.hashCode());
        Object $minLat = this.getMinLat();
        result = result * 59 + ($minLat == null ? 43 : $minLat.hashCode());
        Object $maxLat = this.getMaxLat();
        result = result * 59 + ($maxLat == null ? 43 : $maxLat.hashCode());
        return result;
    }

    public String toString() {
        return "CellNoScope(part=" + this.getPart() + ", level=" + this.getLevel() + ", minLng=" + this.getMinLng() + ", maxLng=" + this.getMaxLng() + ", minLat=" + this.getMinLat() + ", maxLat=" + this.getMaxLat() + ")";
    }
}
