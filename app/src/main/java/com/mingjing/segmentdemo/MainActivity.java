package com.mingjing.segmentdemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mingjing.widget.indicator.AbsPageIndicatorAdapter;
import com.mingjing.widget.indicator.PageIndicatorView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PageIndicatorView indicatorView = (PageIndicatorView) findViewById(R.id.piv_test);
        final Drawable drawable = getResources().getDrawable(R.drawable.indicator);
        final Drawable selectDrawable = getResources().getDrawable(R.drawable.select);

        indicatorView.setAdapter(new AbsPageIndicatorAdapter() {
            @Override
            public int getItemCount() {
                return 3;
            }

            @Override
            public Drawable getItemView(int position, Drawable convertDrawable, View parent) {
                return drawable;
            }

            @Override
            public Drawable getSelectView(int position, Drawable convertDrawable, View parent) {
                return selectDrawable;
            }
        });

        indicatorView.setOnItemClickListener(new PageIndicatorView.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(MainActivity.this, "" + pos, Toast.LENGTH_LONG).show();
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_test);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TestFragment.create("position:" + position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        viewPager.addOnPageChangeListener(indicatorView);
    }
}
