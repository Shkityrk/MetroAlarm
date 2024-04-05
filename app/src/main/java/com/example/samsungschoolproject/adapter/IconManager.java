package com.example.samsungschoolproject.adapter;

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
        iconMap.put("5", R.drawable.img_5);
        iconMap.put("6", R.drawable.img_6);
        iconMap.put("7", R.drawable.img_7);
        iconMap.put("8", R.drawable.img_8);
        iconMap.put("9", R.drawable.img_9);
        iconMap.put("10", R.drawable.img_10);
        iconMap.put("11", R.drawable.img_11);
        iconMap.put("12", R.drawable.img_12);
        iconMap.put("13", R.drawable.img_13);
        iconMap.put("МЦК", R.drawable.img_14);
        iconMap.put("15", R.drawable.img_15);
        iconMap.put("МЦД", R.drawable.img_d5);

        // Добавьте другие соответствия, если необходимо
    }

    public static int getIconResource(String numLine) {
        Integer iconResId = iconMap.get(numLine);
        if (iconResId != null) {
            return iconResId;
        } else {
            // Если нет соответствующего изображения, возвращаем значение по умолчанию
            return R.drawable.img;
        }
    }
}
