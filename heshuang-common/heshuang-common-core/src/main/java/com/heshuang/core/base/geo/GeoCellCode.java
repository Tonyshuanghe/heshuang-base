//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.geo;

import com.heshuang.core.base.exception.BusinessRtException;

import java.util.Iterator;
/**
 * @author heshuang
 * @version v1.0
 * @CreationTime: - 2022年10月24日14:38:00
 * @Description: 84坐标系转北斗网格码
 */
public class GeoCellCode {
    public static Integer MAX_LAT = 88;
    public static Integer ASIIC_A = 65;
    public static Integer MAX_LNG = 180;
    public static Integer MIN_LNG = -180;
    public static Integer MAX_LEVEL = 10;

    public GeoCellCode() {
    }

    public static void main(String[] args) {
        Double lng = dmsToLatLng(116, 18, 45.37D);
        Double lat = dmsToLatLng(39, 59, 35.38D);
        System.out.println(String.format("%s, %s", lngLatToDMS(lng), lngLatToDMS(lat)));
        CellNo cellNo = toLevel(lng, lat, 128.0D, 8);
        System.out.println(cellNo.getCode());
        System.out.println(toLevel(lng, lat, 8).getCode());
        Iterator var4 = cellNo.getScopes().values().iterator();

        while(var4.hasNext()) {
            CellNoScope scope = (CellNoScope)var4.next();
            System.out.println(String.format("%d, %s, %s", scope.getLevel(), lngLatToDMS(scope.getMinLng()), lngLatToDMS(scope.getMinLat())));
        }

    }

    public static LngLat toLngLat(String code) {
        return new LngLat();
    }

    public static CellNo toLevel(Double lng, Double lat) {
        return toLevel(lng, lat, (Double)null, MAX_LEVEL);
    }

    /**
     *
     * @param lng 经度
     * @param lat 纬度
     * @param level 网格码级别 1-10
     * @return
     */
    public static CellNo toLevel(Double lng, Double lat, Integer level) {
        return toLevel(lng, lat, (Double)null, level);
    }

    public static CellNo toLevel(Double lng, Double lat, Double height) {
        return toLevel(lng, lat, height, MAX_LEVEL);
    }

    public static CellNo toLevel(Double lng, Double lat, Double height, Integer level) {
        if (!(lng < (double)(-MAX_LNG)) && !(lng > (double)MAX_LNG)) {
            if (!(lat < (double)(-MAX_LAT)) && !(lat > (double)MAX_LAT)) {
                CellNo cellNo = toLevel1(lng, lat, height);
                return toLevel10(cellNo, lng, lat, height, level);
            } else {
                throw BusinessRtException.of(String.format("lat[%d,%d] Out of range", -MAX_LAT, MAX_LAT));
            }
        } else {
            throw BusinessRtException.of(String.format("lng[%d,%d] Out of range", -MAX_LNG, MAX_LNG));
        }
    }

    public static CellNo toLevel10(CellNo cellNo, Double lng, Double lat, Double height, Integer level) {
        if (level == null || level <= 0 || level > MAX_LEVEL) {
            level = MAX_LEVEL;
        }

        if (cellNo.getPtr() >= level) {
            return cellNo;
        } else {
            String hCode = "";
            String code = "";
            Integer ptr = cellNo.getPtr() + 1;
            BeiDouCellSize cell = BeiDouCellSize.of(ptr);
            CellNoScope preScope = cellNo.getScope(ptr - 1);
            Double lngIndex = Math.floor((lng - preScope.getMinLng()) / cell.getX());
            Double latIndex = Math.floor((lat - preScope.getMinLat()) / cell.getY());
            if (height != null) {
                if (cellNo.hasHptr()) {
                    hCode = hexBit(0.0D);
                } else {
                    BeiDouCellSize preCell = BeiDouCellSize.of(ptr - 1);
                    double hIndex = 0.0D;
                    if (ptr == 2) {
                        hIndex = height / preCell.getHeight();
                        hCode = hex2Bit(hIndex);
                    } else {
                        hIndex = height / (preCell.getHeight() - cell.getHeight());
                        hCode = hexBit(hIndex);
                    }

                    if (Math.floor(hIndex) > 0.0D) {
                        cellNo.setHPtr(ptr);
                    }
                }

                cellNo.append(hCode);
            }

            if (ptr != 3 && ptr != 6) {
                code = String.format("%X%X", lngIndex.intValue(), latIndex.intValue());
            } else {
                code = hexBit((double)(lngIndex.intValue() + latIndex.intValue() * 2));
            }

            hCode = "";
            if (height != null && ptr == 10) {
                if (cellNo.hasHptr()) {
                    hCode = hexBit(0.0D);
                } else {
                    Double hIndex = height / cell.getHeight();
                    if (hIndex > 0.0D) {
                        cellNo.setHPtr(ptr);
                    }

                    hCode = hexBit(hIndex);
                }
            }

            cellNo.append(code + hCode);
            CellNoScope scope = CellNoScope.of(ptr);
            scope.setPart(code);
            scope.setLng(preScope.getMinLng() + lngIndex * cell.getX(), preScope.getMinLng() + (lngIndex + 1.0D) * cell.getX());
            scope.setLat(preScope.getMinLat() + latIndex * cell.getY(), preScope.getMinLat() + (latIndex + 1.0D) * cell.getY());
            scope.setLevel(ptr);
            cellNo.putScope(scope);
            return toLevel10(cellNo, lng, lat, height, level);
        }
    }

    public static CellNo toLevel1(Double lng, Double lat, Double height) {
        BeiDouCellSize cell = BeiDouCellSize.CELL_1;
        CellNo cellNo = new CellNo();
        CellNoScope scope = CellNoScope.of(cell.getLevel());
        String code = "N";
        if (lat < 0.0D) {
            code = "S";
        }

        if (height != null) {
            code = String.format("%s%s", code, height > 0.0D ? 0 : 1);
        }

        int p = MIN_LNG;

        for(int i = 1; p < MAX_LNG; ++i) {
            if (Math.floor(lng) >= (double)p && Math.floor(lng) < (double)(p + cell.getXInt())) {
                scope.setLng(p, p + cell.getXInt());
                code = String.format("%s%s", code, i);
            }

            p += cell.getXInt();
        }

        for(p = 0; p <= MAX_LAT; p += cell.getYInt()) {
            if (Math.floor(Math.abs(lat)) >= (double)p && Math.floor(Math.abs(lat)) < (double)(p + cell.getYInt())) {
                scope.setLat(p, p + cell.getYInt());
                code = String.format("%s%s", code, (char)(p / 4 + ASIIC_A));
                break;
            }
        }

        scope.setPart(code);
        cellNo.setCode(code);
        cellNo.putScope(scope);
        return cellNo;
    }

    public static Double dmsToLatLng(int d, int m, double s) {
        return (double)d + (double)m / 60.0D + s / 3600.0D;
    }

    public static String lngLatToDMS(Double lngLat) {
        Double d = Math.floor(lngLat);
        Double m = Math.floor((lngLat - d) * 60.0D);
        Double s = (lngLat - d - m / 60.0D) * 3600.0D;
        String dms = String.format("%s°", d.intValue());
        if (m > 0.0D) {
            if (Math.round(s) >= 60L) {
                m = m + 1.0D;
                s = s - 60.0D;
            }

            dms = String.format("%s%s′", dms, m.intValue());
            if (s > 0.0D) {
                dms = String.format("%s%.2f″", dms, s);
            }

            dms = dms.replace("′0.00″", "′");
            dms = dms.replace(".00″", "″");
        }

        return dms;
    }

    public static String hex2Bit(double data) {
        return String.format("%02X", Double.valueOf(Math.floor(data)).intValue());
    }

    public static String hexBit(double data) {
        return String.format("%X", Double.valueOf(Math.floor(data)).intValue());
    }
}
