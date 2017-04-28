package com.liangmayong.android_base.air;

/**
 * Created by LiangMaYong on 2017/4/28.
 */

public class Demo {
    private String name = "";

    public Demo(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "name='" + name + '\'' +
                '}';
    }
}
