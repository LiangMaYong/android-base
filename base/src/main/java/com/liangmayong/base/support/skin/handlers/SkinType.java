package com.liangmayong.base.support.skin.handlers;

/**
 * Created by LiangMaYong on 2016/12/9.
 */

public enum SkinType {
    default_type(0), primary(1), success(2), info(3), warning(4), danger(5), white(6), gray(7), black(8);

    private int value = 0;

    private SkinType(int value) {
        this.value = value;
    }

    public static SkinType valueOf(int value) {
        switch (value) {
            case 0:
                return default_type;
            case 1:
                return primary;
            case 2:
                return success;
            case 3:
                return info;
            case 4:
                return warning;
            case 5:
                return danger;
            case 6:
                return white;
            case 7:
                return gray;
            case 8:
                return black;
            default:
                return default_type;
        }
    }

    public int value() {
        return this.value;
    }
}
