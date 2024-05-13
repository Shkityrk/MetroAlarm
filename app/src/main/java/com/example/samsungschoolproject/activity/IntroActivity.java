package com.example.samsungschoolproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.samsungschoolproject.R;
import com.example.samsungschoolproject.fragment.viewmodel.IntroViewPagerAdapter;
import com.example.samsungschoolproject.utils.SharedPreferencesUtils;
import com.example.samsungschoolproject.utils.ThemeUtils;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private ImageView[] dots;
    private List<String> dataList;

    private int[] layouts = {R.layout.slide1, R.layout.slide2, R.layout.slide3, R.layout.slidedownload};

    public void showToast(View view) {
        Toast.makeText(this, "Привет! Это тост!", Toast.LENGTH_SHORT).show();
    }


    public void closeApplication(View view) {
        finish(); // Закрыть текущую активити
    }

    private void introClosed(View view) {
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
        sharedPreferencesUtils.setFirstStart(false);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        viewPager = findViewById(R.id.viewPager);
        dotsLayout = findViewById(R.id.dotsLayout);

        dataList = new ArrayList<>();
        dataList.add("Слайд 1");
        dataList.add("Слайд 2");
        dataList.add("Слайд 3");
        dataList.add("Слайд 4");


        IntroViewPagerAdapter adapter = new IntroViewPagerAdapter(this, layouts);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        addDots(0);
        applyScaleAnimationToSlide1();
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            addDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    };

    private void addDots(int position) {
        TextView[] dots = new TextView[dataList.size()];
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorInactiveDot));
            dots[i].setText("\u2022");
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorActiveDot));
        }
    }

    private void applyScaleAnimationToSlide1() {
        // Получим первый слайд (slide1)
        View slide1 = viewPager.getChildAt(0);

        // Если слайд существует и это slide1
        if (slide1 != null && viewPager.getCurrentItem() == 0) {
            // Найдем изображение на slide1 по его идентификатору
            ImageView imageView = slide1.findViewById(R.id.imageView);

            // Загрузим анимацию масштабирования из ресурсов
            Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);

            // Применим анимацию к изображению на slide1
            imageView.startAnimation(scaleAnimation);
        }
    }

    public void downloadDatabase(View view) {
        Intent intent = new Intent(this, ChoseUploadingDatabaseActivity.class);
        startActivity(intent);
        introClosed(view);
        finish(); // Закрыть текущую активити
    }
}