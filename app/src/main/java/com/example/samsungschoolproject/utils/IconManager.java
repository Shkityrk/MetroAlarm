package com.example.samsungschoolproject.utils;

import com.example.samsungschoolproject.R;

import java.util.HashMap;

public class IconManager {
    private static final HashMap<String, Integer> iconMap = new HashMap<>();

    static {
        // Здесь добавьте соответствия номера линии и идентификатора ресурса изображения
        iconMap.put("1", R.drawable.img_1);
        iconMap.put("2", R.drawable.img_2);
        iconMap.put("3", R.drawable.img_3);
        iconMap.put("4", R.drawable.img_4);
        iconMap.put("4a", R.drawable.img_4a);

        iconMap.put("5", R.drawable.img_5);
        iconMap.put("6", R.drawable.img_6);
        iconMap.put("7", R.drawable.img_7);
        iconMap.put("8", R.drawable.img_8);
        iconMap.put("8a", R.drawable.img_8a);

        iconMap.put("9", R.drawable.img_9);
        iconMap.put("10", R.drawable.img_10);
        iconMap.put("11", R.drawable.img_11);
        iconMap.put("11a", R.drawable.img_11a);

        iconMap.put("12", R.drawable.img_12);
        iconMap.put("13", R.drawable.img_13);

        iconMap.put("МЦК", R.drawable.img_14);
        iconMap.put("MCC", R.drawable.img_14);

        iconMap.put("15", R.drawable.img_15);
//        iconMap.put("МЦД", R.drawable.img_d5);

        iconMap.put("D1", R.drawable.img_d1);
        iconMap.put("D2", R.drawable.img_d2);
        iconMap.put("D3", R.drawable.img_d3);
        iconMap.put("D4", R.drawable.img_d4);
        iconMap.put("D5", R.drawable.img_d5);

        iconMap.put("1Spb", R.drawable.img_1spb);
        iconMap.put("2Spb", R.drawable.img_2spb);
        iconMap.put("3Spb", R.drawable.img_3spb);
        iconMap.put("4Spb", R.drawable.img_4spb);
        iconMap.put("5Spb", R.drawable.img_5spb);
        iconMap.put("6Spb", R.drawable.img_6spb);

    }

    public static int getIconResource(String numLine) {
        Integer iconResId = iconMap.get(numLine);
        if (iconResId != null) {
            return iconResId;
        } else {
            return R.drawable.img;
        }
    }
}
