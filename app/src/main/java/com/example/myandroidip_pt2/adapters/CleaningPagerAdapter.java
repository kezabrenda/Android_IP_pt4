package com.example.myandroidip_pt2.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myandroidip_pt2.Cleaning;
import com.example.myandroidip_pt2.ui.CleaningDetailFragment;

import java.util.ArrayList;

public class CleaningPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Cleaning> mDryCleaning;

    public CleaningPagerAdapter(FragmentManager fm, ArrayList<Cleaning> cleaning){
        super(fm);
        mDryCleaning = cleaning;
    }

    @Override
    public Fragment getItem(int position) {
        return CleaningDetailFragment.newInstance(mDryCleaning, position);
    }

    @Override
    public int getCount(){
        return mDryCleaning.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mDryCleaning.get(position).getName();
    }
}
