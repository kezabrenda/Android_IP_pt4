package com.example.myandroidip_pt2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myandroidip_pt2.Cleaning;
import com.example.myandroidip_pt2.R;
import com.example.myandroidip_pt2.adapters.CleaningPagerAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CleaningDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager) ViewPager mViewPager;
    private CleaningPagerAdapter adapterViewPager;
    ArrayList<Cleaning> mCleaning = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaning_detail);

        ButterKnife.bind(this);

        mCleaning = Parcels.unwrap(getIntent().getParcelableExtra("dry cleaning"));
        int startingPosition = getIntent().getIntExtra("position", 0);

        adapterViewPager = new CleaningPagerAdapter(getSupportFragmentManager(), mCleaning);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);
    }
}