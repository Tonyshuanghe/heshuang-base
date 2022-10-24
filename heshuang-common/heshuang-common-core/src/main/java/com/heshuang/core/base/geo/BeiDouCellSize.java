//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.geo;

public enum BeiDouCellSize {
    CELL_1(dtoD(6), dtoD(4), 1, 445280.0D, "22*60", "445.28KM"),
    CELL_2(mtoD(30), mtoD(30), 2, 55660.0D, "12*8", "55.66KM*55.66KM"),
    CELL_3(mtoD(15), mtoD(10), 3, 27830.0D, "2*3", "27.83KM*18.55KM"),
    CELL_4(mtoD(1), mtoD(1), 4, 1850.0D, "15*10", "1.85KM*1.85KM"),
    CELL_5(stoD(4), stoD(4), 5, 123.69D, "15*15", "123.69M*123.69M"),
    CELL_6(stoD(2), stoD(2), 6, 61.84D, "2*2", "61.84M*61.84M"),
    CELL_7(stoD(1) / 4.0D, stoD(1) / 4.0D, 7, 7.73D, "8*8", "7.73M*7.73M"),
    CELL_8(stoD(1) / 32.0D, stoD(1) / 32.0D, 8, 0.97D, "8*8", "0.97M*0.97M"),
    CELL_9(stoD(1) / 256.0D, stoD(1) / 256.0D, 9, 0.121D, "8*8", "12.0CM*12.0CM"),
    CELL_10(stoD(1) / 2048.0D, stoD(1) / 2048.0D, 10, 0.015D, "8*8", "1.5CM*1.5CM");

    private Double x;
    private Double y;
    private Integer level;
    private Double height;
    private String size;
    private String describe;

    public static Double stoD(Integer d) {
        return (double)d / 3600.0D;
    }

    public static Double mtoD(Integer d) {
        return (double)d / 60.0D;
    }

    public static Double dtoD(Integer d) {
        return (double)d;
    }

    private BeiDouCellSize(Double x, Double y, Integer level, Double height, String size, String describe) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.size = size;
        this.describe = describe;
        this.height = height;
    }

    public Double getHeight() {
        return this.height;
    }

    public Integer getLevel() {
        return this.level;
    }

    public Double getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }

    public Integer getXInt() {
        return this.x.intValue();
    }

    public Integer getYInt() {
        return this.y.intValue();
    }

    public static BeiDouCellSize of(Integer level) {
        BeiDouCellSize[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            BeiDouCellSize cell = var1[var3];
            if (cell.getLevel().equals(level)) {
                return cell;
            }
        }

        return null;
    }
}
