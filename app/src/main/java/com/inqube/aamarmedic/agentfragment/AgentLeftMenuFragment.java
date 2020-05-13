package com.inqube.aamarmedic.agentfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.inqube.aamarmedic.AgentBookingListMainFragmentActivity;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseFragment;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;
import com.inqube.aamarmedic.util.UtilClass;

import retrofit2.Response;

public class AgentLeftMenuFragment extends BaseFragment implements
        AllInterfaces.RetrofitResponseToActivityOrFrament,AllInterfaces.AdapterCallback,
        View.OnClickListener
{
    private OnAamarMedicAgentLeftMenuInteractionListener mListener;
    private boolean resume;
    private View view;
    private TextView tv_wallet_balance,tv_my_booking,
            tv_otc_medicine,tv_wellness_items,tv_consumable_items,tv_logout, tv_username;
    private ProgressBar pb_loader;
    private ImageView imv_user;
    private DrawerLayout drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = onCreateView(inflater, container, savedInstanceState, R.layout.left_panel_menu_fragment);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!resume) {
            ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);
            setUI(view);
        }
        resume = true;
    }

    private void setUI(View view) {
        imv_user = (ImageView)view.findViewById(R.id.imv_user);
        tv_wallet_balance = (TextView) view.findViewById(R.id.tv_wallet_balance);
        tv_otc_medicine = (TextView) view.findViewById(R.id.tv_otc_medicine);
        tv_wellness_items = (TextView) view.findViewById(R.id.tv_wellness_items);
        tv_consumable_items = (TextView) view.findViewById(R.id.tv_consumable_items);
        tv_logout = (TextView) view.findViewById(R.id.tv_logout);
        pb_loader=(ProgressBar)view.findViewById(R.id.pb_loader);
        tv_my_booking = (TextView)view.findViewById(R.id.tv_my_booking);
        tv_username = (TextView)view.findViewById(R.id.tv_username);

        imv_user.setOnClickListener(this);
        tv_wallet_balance.setOnClickListener(this);

        tv_my_booking.setOnClickListener(this);

        tv_otc_medicine.setOnClickListener(this);
        tv_wellness_items.setOnClickListener(this);
        tv_consumable_items.setOnClickListener(this);

        tv_logout.setOnClickListener(this);
        tv_username.setText(""+ ((BaseActivity)getActivity()).getUserPreference(Config.USER_NAME,""));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAamarMedicAgentLeftMenuInteractionListener) {
            mListener = (OnAamarMedicAgentLeftMenuInteractionListener) context;
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
            case R.id.imv_home:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fm_container, new AgentDashboardFragment(), "AgentDashboardFragment")
                        .addToBackStack("AgentDashboardFragment")
                        .commit();
                break;
            case R.id.imv_user:
                if (((BaseActivity)getActivity()).isDeviceOnline()) {
                    ((BaseActivity) getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);

                    mListener.onAamarMedicUserInfoInteractionListener();
                }
                    break;
            /*case R.id.tv_wallet_balance:

                break;
            case R.id.tv_otc_medicine:

                break;
            case R.id.tv_wellness_items:

                break;
            case R.id.tv_consumable_items:

                break;*/
            case R.id.tv_my_booking:
                if (((BaseActivity)getActivity()).isDeviceOnline()) {
                    ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);

                    /*getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fm_container, new AgentBookingListFragment(), "AgentBookingListFragment")
                            .addToBackStack("AgentBookingListFragment")
                            .commit();*/

                    startActivity(new Intent(((BaseActivity)getActivity()), AgentBookingListMainFragmentActivity.class));
                    //getActivity().finish();
                }
                break;
            case R.id.tv_logout:
                if (((BaseActivity)getActivity()).isDeviceOnline()) {
                    ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);

                    UtilClass.getInstance().logoutFromServer(((BaseActivity)getActivity()),AgentLeftMenuFragment.this,((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                }
                break;
        }
    }

    @Override
    public void onSuccess(Object response, String which_method) {
        if (which_method.equalsIgnoreCase("logoutFromServer")) {

            Response<com.inqube.aamarmedic.model.logout.MSG> res = (Response<com.inqube.aamarmedic.model.logout.MSG>) response;

            if (true == res.body().getSuccess()) {
                mListener.onAamarMedicAgentLeftMenuSignOutListener();
            }else{
                mListener.onAamarMedicAgentLeftMenuResponeceListener(getString(R.string.please_try_again));
            }
        }
    }

    @Override
    public void onFailure(ReturnResponse response) {

        mListener.onAamarMedicAgentLeftMenuResponeceListener(response.getMsg());
    }

    @Override
    public void onResponseFailure() {

        mListener.onAamarMedicAgentLeftMenuResponeceListener(getString(R.string.please_try_again));
    }

    @Override
    public void onResponseFailure(String msg) {

    }

    @Override
    public void onReturn(String position) {

    }

    public interface OnAamarMedicAgentLeftMenuInteractionListener {
        void onAamarMedicAgentLeftMenuResponeceListener(String msg);
        void onAamarMedicAgentLeftMenuSignOutListener();
        void onAamarMedicUserInfoInteractionListener();
    }
}
