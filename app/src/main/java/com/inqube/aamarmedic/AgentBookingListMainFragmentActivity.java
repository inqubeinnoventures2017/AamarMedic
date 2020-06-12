package com.inqube.aamarmedic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.inqube.aamarmedic.agentfragment.AgentBookingListFragment;
import com.inqube.aamarmedic.base.BaseActivity;

public class AgentBookingListMainFragmentActivity extends BaseActivity implements AgentBookingListFragment.OnAamarMedicAgentBookingListFragmentInteractionListener
 {

    private FrameLayout fm_container,fm_container_left;
    private ImageView imv_back, imv_menu, imv_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.agent_main_fragment);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUI() {
        pb_loader = (ProgressBar) findViewById(R.id.pb_loader);
        fm_container = (FrameLayout) findViewById(R.id.fm_container);
        fm_container_left = (FrameLayout) findViewById(R.id.fm_container_left);
        col_holder = (CoordinatorLayout) findViewById(R.id.col_holder);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        imv_menu = (ImageView) toolbar.findViewById(R.id.imv_menu);
        imv_menu.setVisibility(View.VISIBLE);

        imv_back =  (ImageView) toolbar.findViewById(R.id.imv_back);
        imv_back.setVisibility(View.GONE);

        /*imv_home = (ImageView) toolbar.findViewById(R.id.imv_home);
        imv_home.setVisibility(View.GONE);*/

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fm_container, new AgentBookingListFragment(), "AgentBookingListFragment")
                .addToBackStack("AgentBookingListFragment")
                .commit();

        /*getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fm_container_left, new AgentLeftMenuFragment(), "AgentLeftMenuFragment")
                //.addToBackStack("AgentLeftMenuFragment")
                .commit();*/
    }

    @Override
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
        }

        imv_menu = (ImageView) toolbar.findViewById(R.id.imv_menu);
        imv_menu.setVisibility(View.VISIBLE);
    }

    /*public void clearBackStack (){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }*/

     //---------------------------->>>>>>>>>>>>>>>> AGENT BOOKING LIST INTERFACE <<<<<<<<<<<<<<<<<<<<<<<<<<<--------------------------------
     @Override
     public void onAamarMedicAgentBookingListFragmentInteractionListener() {
         startActivity(new Intent(AgentBookingListMainFragmentActivity.this, AgentDashboardMainFragmentActivity.class));
     }

     @Override
     public void onAamarMedicAgentBookingListFragmentInteractionListener(String msg) {
         createSnackBar(col_holder,msg);
     }
 }
