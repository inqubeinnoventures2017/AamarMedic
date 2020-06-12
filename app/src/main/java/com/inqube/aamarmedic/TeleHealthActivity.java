package com.inqube.aamarmedic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.inqube.aamarmedic.adapter.SelectTeleHealthTabLayoutAdapter;
import com.inqube.aamarmedic.agentfragment.AgentDashboardFragment;
import com.inqube.aamarmedic.agentfragment.AgentLeftMenuFragment;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseActivityWithoutMenu;
import com.inqube.aamarmedic.telehealthfragment.Medical_II_Fragment;
import com.inqube.aamarmedic.telehealthfragment.Medical_I_Fragment;
import com.inqube.aamarmedic.telehealthfragment.PersonalFragment;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.CustomViewPager;

public class TeleHealthActivity extends BaseActivity
        implements View.OnClickListener, AllInterfaces.DialogCallback, AllInterfaces.StateCallback,
        PersonalFragment.OnAamarMedicTeleHealthPersonalFragmentInteractionListener,
        Medical_I_Fragment.OnAamarMedicTeleHealthMedicalIFragmentInteractionListener,
        Medical_II_Fragment.OnAamarMedicTeleHealthMedicalIIFragmentInteractionListener
{

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private CoordinatorLayout col_holder;
    private ImageView imv_home, imv_back, imv_menu;
    //private FrameLayout fm_container_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_tele_health);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUI() {
        super.setUI();

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(CustomViewPager) findViewById(R.id.viewPager);
        col_holder = (CoordinatorLayout) findViewById(R.id.col_holder);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Personal)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Medical_i)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.Medical_ii)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        imv_menu = (ImageView) toolbar.findViewById(R.id.imv_menu);
        imv_menu.setVisibility(View.GONE);

        imv_home =(ImageView) toolbar.findViewById(R.id.imv_home);
        imv_home.setVisibility(View.VISIBLE);

        imv_back =(ImageView) toolbar.findViewById(R.id.imv_back);
        imv_back.setVisibility(View.GONE);

        imv_home.setOnClickListener(this);
       /* fm_container_left = (FrameLayout) findViewById(R.id.fm_container_left);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);*/

        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        tabStrip.setEnabled(true);
        for(int i = 1; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(false);
        }

        final SelectTeleHealthTabLayoutAdapter adapter = new SelectTeleHealthTabLayoutAdapter(this,getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setPagingEnabled(false);
        //viewPager.beginFakeDrag();
        viewPager.setOffscreenPageLimit(tabStrip.getChildCount());

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /*@Override
    public void onBackPressed() {
        System.out.println(" BACK PRESSED " + getSupportFragmentManager().getBackStackEntryCount() + " " + shouldIGoBack);
        if (shouldIGoBack) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStackImmediate();
                getSupportFragmentManager().beginTransaction().commit();
            } else {
                System.out.println(" R NEI ");
                //appCloseDialog(getString(R.string.close_app), this);
            }
            //finish();
        }
    }*/


    @Override
    public void onBackPressed() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem != 0) {
            viewPager.setCurrentItem(currentItem - 1, true);
        } else {
            //super.onBackPressed();
        }
    }

    public void clearBackStack (){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.imv_home:
                startActivity(new Intent(TeleHealthActivity.this, AgentDashboardMainFragmentActivity.class));
                break;
        }
    }

    @Override
    public void onDialogReturn(String position) {

    }

    @Override
    public void onStateReturn(String position) {

    }
    //-------------------------->>> Tele Health Personal Fragment <<<------------------------------------------------

    @Override
    public void onAamarMedicTeleHealthPersonalFragmentInteractionListener(boolean dataExists) {
        LinearLayout tabStrip = ((LinearLayout)tabLayout.getChildAt(0));
        for(int i = 1; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setClickable(true);
        }

        /*getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);*/

        viewPager.setOffscreenPageLimit(tabStrip.getChildCount());
        if (!dataExists) {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        }
    }

    @Override
    public void onAamarMedicTeleHealthPersonalFragmentInteractionListener(String msg) {
        createSnackBar(col_holder, msg);
    }

    //-------------------------->>> Tele Health Medical - I Fragment <<<------------------------------------------------
    @Override
    public void onAamarMedicTeleHealthMedicalIFragmentInteractionListener() {
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }

    @Override
    public void onAamarMedicTeleHealthMedicalIFragmentInteractionListener(String msg) {
        createSnackBar(col_holder, msg);
    }

    //-------------------------->>> Tele Health Medical - II Fragment <<<------------------------------------------------
    /*@Override
    public void onAamarMedicTeleHealthMedicalIIFragmentInteractionListener() {

    }*/

    @Override
    public void onAamarMedicTeleHealthMedicalIIFragmentInteractionListener(String msg) {
        createSnackBar(col_holder, msg);
    }
}
