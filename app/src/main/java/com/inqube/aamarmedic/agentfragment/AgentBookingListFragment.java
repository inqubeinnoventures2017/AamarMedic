package com.inqube.aamarmedic.agentfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.adapter.SelectPatientBookingListDialogBoxAdapter;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseFragment;
import com.inqube.aamarmedic.dialog.AgentBookingListDialogBox;
import com.inqube.aamarmedic.model.agentbookinglist.MSG;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;
import com.inqube.aamarmedic.util.UtilClass;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Response;

public class AgentBookingListFragment extends BaseFragment implements AllInterfaces.RetrofitResponseToActivityOrFrament,
        AllInterfaces.AdapterCallback, View.OnClickListener, AllInterfaces.DialogCallback,
        AllInterfaces.StateCallback {

    private View view;
    private  boolean resume;
    private ImageView imv_home, imv_menu;
    private OnAamarMedicAgentBookingListFragmentInteractionListener mListener;
    private List<com.inqube.aamarmedic.model.agentbookinglist.Result> listBooking;
    private ProgressBar pb_loader;
    private RecyclerView rv_AgentBookinglist;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private AllInterfaces.DialogCallback dialogCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.agentbooking_list, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!resume) {
            setUI(view);
        }
        resume = true;
    }

    private void setUI(View view) {

        ((BaseActivity)getActivity()).pb_loader = view.findViewById(R.id.pb_loader);

        imv_home = getActivity().findViewById(R.id.imv_home);
        imv_home.setVisibility(View.VISIBLE);

        imv_menu = getActivity().findViewById(R.id.imv_menu);
        imv_menu.setVisibility(View.GONE);

        rv_AgentBookinglist = view.findViewById(R.id.rv_agentbookinglist);

        imv_home.setOnClickListener(this);

        //System.out.println("Agent_id: "+((BaseActivity)getActivity()).getUserPreference(Config.AGENT_ID, ""));
        //System.out.println("Token: " +((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));

        if (((BaseActivity)getActivity()).isDeviceOnline()) {
            ((BaseActivity)getActivity()).pb_loader.setVisibility(View.VISIBLE);
            UtilClass.getInstance().getAgentBookingListData(((BaseActivity)getActivity()),AgentBookingListFragment.this,
                    ((BaseActivity)getActivity()).getUserPreference(Config.AGENT_ID, ""),((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
        }
        else{
            mListener.onAamarMedicAgentBookingListFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAamarMedicAgentBookingListFragmentInteractionListener) {
            mListener = (OnAamarMedicAgentBookingListFragmentInteractionListener) context;
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
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.imv_home:
                mListener.onAamarMedicAgentBookingListFragmentInteractionListener();
                break;

            default:
                break;
        }
    }

    @Override
    public void onSuccess(Object response, String which_method) {
        if (which_method.equalsIgnoreCase("getAgentBookingListData")) {
            Response<com.inqube.aamarmedic.model.agentbookinglist.MSG> res = (Response<com.inqube.aamarmedic.model.agentbookinglist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            if (res.body()!=null) {
                listBooking = res.body().getResult();

                if (listBooking.size() > 0) {
                    layoutManager = new LinearLayoutManager(getActivity());
                    rv_AgentBookinglist.setLayoutManager(layoutManager);
                    rv_AgentBookinglist.setHasFixedSize(true);

                    adapter = new SelectPatientBookingListDialogBoxAdapter(getActivity(), listBooking, this, dialogCallback);
                    rv_AgentBookinglist.setAdapter(adapter);
                } else {
                    mListener.onAamarMedicAgentBookingListFragmentInteractionListener(getString(R.string.please_try_again));
                }
            }
        }
    }

    @Override
    public void onFailure(ReturnResponse response) {
        mListener.onAamarMedicAgentBookingListFragmentInteractionListener(response.getMsg());
    }

    @Override
    public void onResponseFailure() {
        mListener.onAamarMedicAgentBookingListFragmentInteractionListener(getString(R.string.please_try_again));
    }

    @Override
    public void onResponseFailure(String msg) {

    }

    @Override
    public void onReturn(String position) {

    }

    @Override
    public void onDialogReturn(String position) {

    }

    @Override
    public void onStateReturn(String position) {

    }

    public interface OnAamarMedicAgentBookingListFragmentInteractionListener
    {
        void onAamarMedicAgentBookingListFragmentInteractionListener();
        void onAamarMedicAgentBookingListFragmentInteractionListener(String msg);
    }
}
