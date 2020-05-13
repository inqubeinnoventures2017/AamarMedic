package com.inqube.aamarmedic.agentfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.inqube.aamarmedic.coronavirusfragment.AboutCoronaVirusFragment;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.TeleHealthActivity;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseFragment;
import com.inqube.aamarmedic.doctorfragment.DoctorAppointmentFragment;
import com.inqube.aamarmedic.telehealthfragment.PersonalFragment;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;

public class AgentDashboardFragment extends BaseFragment implements
        AllInterfaces.RetrofitResponseToActivityOrFrament, AllInterfaces.AdapterCallback,
        View.OnClickListener {

    private View view;
    private TextView tv_covid, tv_health,tv_doctor, tv_order, tv_menu_title;
    private OnAamarMedicAgentDashboardFragmentInteractionListener mListener;
    private boolean resume;
    private LinearLayout ll_main_container;
    private ImageView imv_home, imv_menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = onCreateView(inflater, container, savedInstanceState, R.layout.activity_dashboard);
        return view;
    }

    private void setUI(View view) {

        tv_menu_title = (TextView)((BaseActivity)getActivity()).findViewById(R.id.tv_menu_title);
        tv_menu_title.setText(getString(R.string.dash_board));

        tv_covid = (TextView)view.findViewById(R.id.tv_covid);
        tv_health = (TextView)view.findViewById(R.id.tv_health);
        tv_doctor = (TextView)view.findViewById(R.id.tv_doctor);
        tv_order = (TextView)view.findViewById(R.id.tv_order);

        tv_covid.setOnClickListener(this);
        tv_health.setOnClickListener(this);
        tv_doctor.setOnClickListener(this);
        tv_order.setOnClickListener(this);

        /*TextView toolbarTextView  = (TextView) ((MainActivity) this.getActivity()).findViewById(R.id.toolbarTitle);
        toolbarTextView.setText("Hello");*/

        imv_menu = (ImageView) ((BaseActivity)getActivity()).findViewById(R.id.imv_menu);
        imv_menu.setVisibility(View.VISIBLE);

        imv_home = (ImageView)((BaseActivity)getActivity()).findViewById(R.id.imv_home);
        imv_home.setVisibility(View.GONE);

        if (!((BaseActivity) getActivity()).isDeviceOnline()) {
            mListener.onAamarMedicAgentDashboardFragmentResponeceListener(getString(R.string.sorry_you_not_online_msg));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!resume) {
            ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);
            setUI(view);
            /*ll_main_container = (LinearLayout) view.findViewById(R.id.ll_main_container);
            ll_main_container.setVisibility(View.GONE);*/
        }
        resume = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAamarMedicAgentDashboardFragmentInteractionListener) {
            mListener = (OnAamarMedicAgentDashboardFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_covid:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fm_container, new AboutCoronaVirusFragment(), "AboutCoronaVirusFragment")
                        .addToBackStack("AboutCoronaVirusFragment")
                        .commit();
                break;

            case R.id.tv_health:
                startActivity(new Intent( ((BaseActivity)getActivity()), TeleHealthActivity.class));
                break;

            case R.id.tv_doctor:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fm_container, new DoctorAppointmentFragment(), "DoctorAppointmentFragment")
                        .addToBackStack("DoctorAppointmentFragment")
                        .commit();
                break;

            case R.id.tv_order:
                break;
        }
    }


    @Override
    public void onSuccess(Object response, String which_method) {

    }

    @Override
    public void onFailure(ReturnResponse response) {
        mListener.onAamarMedicAgentDashboardFragmentResponeceListener(response.getMsg());
    }

    @Override
    public void onResponseFailure() {
        mListener.onAamarMedicAgentDashboardFragmentResponeceListener(getString(R.string.please_try_again));
    }

    @Override
    public void onResponseFailure(String msg) {

    }

    @Override
    public void onReturn(String position) {

    }

    public interface OnAamarMedicAgentDashboardFragmentInteractionListener {
        //void onAamarMedicAgentDashboardFragmentResponeceListener();
        void onAamarMedicAgentDashboardFragmentResponeceListener(String msg);
    }
}
