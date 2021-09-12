package com.tungnx.home;

import java.util.Arrays;

enum HoliDay {
    WESTERN_NEW_YEAR(1, 1),
    CHRISTMAS(25, 12);

    private final int day;

    private final int month;

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    HoliDay(int day, int month) {
        this.day = day;
        this.month = month;
    }
}

public class Test {

    public static void main(String[] args) {
        System.out.println(HoliDay.CHRISTMAS.getDay());
        System.out.println(HoliDay.CHRISTMAS.getMonth());
        for (HoliDay h : HoliDay.values()) {
            System.out.println(h.getDay());
            System.out.println(h.getMonth());
        }
    }
}
