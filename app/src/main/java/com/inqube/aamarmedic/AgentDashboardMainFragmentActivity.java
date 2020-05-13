package com.inqube.aamarmedic;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.inqube.aamarmedic.agentfragment.AgentDashboardFragment;
import com.inqube.aamarmedic.agentfragment.AgentLeftMenuFragment;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.coronavirusfragment.AboutCoronaVirusFragment;
import com.inqube.aamarmedic.doctorfragment.DoctorAppointmentFragment;
import com.inqube.aamarmedic.agentfragment.AgentBookingListFragment;
import com.inqube.aamarmedic.agentfragment.AgentProfileDetailsFragment;


public class AgentDashboardMainFragmentActivity extends BaseActivity
        implements AgentDashboardFragment.OnAamarMedicAgentDashboardFragmentInteractionListener,
        AgentLeftMenuFragment.OnAamarMedicAgentLeftMenuInteractionListener,
        DoctorAppointmentFragment.OnAamarMedicDoctorAppointmentFragmentInteractionListener,
        AboutCoronaVirusFragment.OnAamarMedicCoronaVirusFragmentInteractionListener,
        AgentProfileDetailsFragment.OnAamarMedicAgentProfileDetailsFragmentInteractionListener
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

        imv_home = (ImageView) toolbar.findViewById(R.id.imv_home);
        imv_home.setVisibility(View.GONE);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fm_container, new AgentDashboardFragment(), "AgentDashboardFragment")
                .addToBackStack("AgentDashboardFragment")
                .commit();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fm_container_left, new AgentLeftMenuFragment(), "AgentLeftMenuFragment")
                //.addToBackStack("AgentLeftMenuFragment")
                .commit();
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
        imv_home = (ImageView) toolbar.findViewById(R.id.imv_home);
        imv_home.setVisibility(View.GONE);

        imv_menu = (ImageView) toolbar.findViewById(R.id.imv_menu);
        imv_menu.setVisibility(View.VISIBLE);
    }

    public void clearBackStack (){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    //---------------------------->>> DASHBOARD FRAGMENT INTERFACE  <<<--------------------------------------
    /*@Override
    public void onAamarMedicAgentDashboardFragmentResponeceListener() {

    }*/

    /*@Override
    public void onAamarMedicAgentDashboardFragmentResponeceListener() {

    }*/

    @Override
    public void onAamarMedicAgentDashboardFragmentResponeceListener(String msg) {
        createSnackBar(col_holder, msg);
    }

    //----------------------------------->>> LEFT PANEL FRAGMENT INTERFACE  <<<---------------------------------
    @Override
    public void onAamarMedicAgentLeftMenuResponeceListener(String msg) {
        createSnackBar(col_holder, msg);
    }

    @Override
    public void onAamarMedicAgentLeftMenuSignOutListener() {
        Intent startMain = new Intent(AgentDashboardMainFragmentActivity.this, SignInActivity.class);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startMain);
        finish();
    }

    @Override
    public void onAamarMedicUserInfoInteractionListener() {
        drawer.closeDrawer(Gravity.LEFT);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fm_container, new AgentProfileDetailsFragment(), "UserInfoFragment")
                .addToBackStack("UserInfoFragment")
                .commit();
    }

    //----------------------------- >>> DOCTOR APPOINTMENT INTERFACE <<< -----------------------------------

    @Override
    public void onAamarMedicDoctorAppointmentFragmentInteractionListener() {

    }

    @Override
    public void onAamarMedicDoctorAppointmentFragmentInteractionListener(String msg) {
        createSnackBar(col_holder,msg);
    }

    //----------------------------- >>> ABOUT CORONA VIRUS INTERFACE <<< -----------------------------------
    @Override
    public void onAamarMedicCoronaVirusFragmentInteractionListener() {

    }

    @Override
    public void onAamarMedicCoronaVirusFragmentInteractionListener(String msg) {
        createSnackBar(col_holder,msg);
    }

    //----------------------------- >>> AGENT PROFILE DETAILS INTERFACE <<< -----------------------------------
    @Override
    public void onAamarMedicAgentProfileDetailsFragmentInteractionListener() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fm_container, new AgentDashboardFragment(), "AgentDashboardFragment")
                .addToBackStack("AgentDashboardFragment")
                .commit();
    }

    @Override
    public void onAamarMedicAgentProfileDetailsFragmentInteractionListener(String msg) {
        createSnackBar(col_holder,msg);
    }


    @Override
    public void onAamarMedicUserInfoHomeFragmentInteractionListener() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fm_container, new AgentDashboardFragment(), "AgentDashboardFragment")
                .addToBackStack("AgentDashboardFragment")
                .commit();
    }


}
