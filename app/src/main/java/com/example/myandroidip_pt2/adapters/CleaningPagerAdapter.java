package com.example.myandroidip_pt2.adapters;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myandroidip_pt2.Cleaning;
import com.example.myandroidip_pt2.ui.CleaningDetailFragment;

import java.util.ArrayList;

public class CleaningPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Cleaning> mCleaning;

    public CleaningPagerAdapter(FragmentManager fm, ArrayList<Cleaning> cleaning){
        super(fm);
        mCleaning = cleaning;
    }

    @Override
    public CleaningDetailFragment getItem(int position){
        return CleaningDetailFragment.newInstance(mCleaning.get(position));
    }

    @Override
    public int getCount(){
        return mCleaning.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return mCleaning.get(position).getName();
    }
}
