package com.inqube.aamarmedic.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.inqube.aamarmedic.telehealthfragment.Medical_II_Fragment;
import com.inqube.aamarmedic.telehealthfragment.Medical_I_Fragment;
import com.inqube.aamarmedic.telehealthfragment.PersonalFragment;

public class SelectTeleHealthTabLayoutAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;

    public SelectTeleHealthTabLayoutAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PersonalFragment personalFragment = new PersonalFragment();
                return personalFragment;
            case 1:
                Medical_I_Fragment medical_i_fragment = new Medical_I_Fragment();
                return medical_i_fragment;
            case 2:
                Medical_II_Fragment medical_ii_fragment = new Medical_II_Fragment();
                return medical_ii_fragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
