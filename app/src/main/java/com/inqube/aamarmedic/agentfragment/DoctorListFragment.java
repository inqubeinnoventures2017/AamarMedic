package com.inqube.aamarmedic.agentfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.adapter.SelectDoctorListDialogBoxAdapter;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseFragment;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;

import java.util.List;

import retrofit2.Response;

public class DoctorListFragment extends BaseFragment implements
        AllInterfaces.RetrofitResponseToActivityOrFrament,
        AllInterfaces.AdapterCallback,
        View.OnClickListener {

    private View view;
    private boolean resume;
    private LinearLayout ll_main_container;
    private RecyclerView rv_doctorlist;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private List<com.inqube.aamarmedic.model.doctorlist.Result> list_doctor;
    private OnAamarMedicDoctorListFragmentInteractionListener mListener;
    private  com.inqube.aamarmedic.model.doctorlist.Result doctor_data;
    private com.inqube.aamarmedic.model.doctorlist.MSG data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = onCreateView(inflater, container, savedInstanceState, R.layout.doctor_list);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!resume) {

            ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);
            ll_main_container = (LinearLayout) view.findViewById(R.id.ll_main_container);
            ll_main_container.setVisibility(View.GONE);

            if (((BaseActivity) getActivity()).isDeviceOnline()) {
                /*UtilClass.getInstance().getFarmerListByAgent(((BaseActivity)getActivity()),
                        this,""+((BaseActivity)getActivity()).getUserPreference(Config.AGENT_ID,""));*/

                System.out.println("spec_id:"+((BaseActivity)getActivity()).getUserPreference(Config.SPECIALIZATION,""));
                System.out.println("clinic_id:"+((BaseActivity)getActivity()).getUserPreference(Config.CLINIC,""));
                System.out.println("day:"+((BaseActivity)getActivity()).getUserPreference(Config.DAYLIST,""));
                System.out.println("token:"+((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN,""));

                /*UtilClass.getInstance().getDoctorListData(((BaseActivity)getActivity()),this,""+((BaseActivity)getActivity()).getUserPreference(Config.SPECIALIZATION,""),
                        ""+((BaseActivity)getActivity()).getUserPreference(Config.CLINIC,""),""+((BaseActivity)getActivity()).getUserPreference(Config.DAYLIST,""),
                        ""+((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN,""));*/
            }
        }
        resume = true;
    }

    private void setUI(View view) {
        rv_doctorlist = (RecyclerView)view.findViewById(R.id.rv_doctorlist);

        //list_farmer = data.getRows();
        list_doctor = data.getResult();

        layoutManager = new LinearLayoutManager(getActivity());
        rv_doctorlist.setLayoutManager(layoutManager);
        rv_doctorlist.setHasFixedSize(true);

        adapter = new SelectDoctorListDialogBoxAdapter(getActivity(), list_doctor,this);
        rv_doctorlist.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAamarMedicDoctorListFragmentInteractionListener) {
            mListener = (OnAamarMedicDoctorListFragmentInteractionListener) context;
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
    public void onSuccess(Object response, String which_method) {
        if(which_method.equalsIgnoreCase("getDoctorListData")){
            ll_main_container.setVisibility(View.VISIBLE);
            Response<com.inqube.aamarmedic.model.doctorlist.MSG> res = (Response<com.inqube.aamarmedic.model.doctorlist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            data = res.body();
            setUI(view);
        }
    }

    @Override
    public void onFailure(ReturnResponse response) {
        mListener.onAamarMedicDoctorListFragmentInteractionListener(response.getMsg());
    }

    @Override
    public void onResponseFailure() {
        mListener.onAamarMedicDoctorListFragmentInteractionListener(getString(R.string.please_try_again));
    }

    @Override
    public void onResponseFailure(String msg) {

    }

    @Override
    public void onReturn(String position) {
        String str[] = position.split("-");
        doctor_data = data.getResult().get(Integer.parseInt(str[0]));
        System.out.println(" FARMER  "+doctor_data.get_id());
        Gson gson = new Gson();
        String farmer_json = gson.toJson(doctor_data);
        ((BaseActivity)getActivity()).saveDataPreference(Config.DOCTOR_DATA,farmer_json);

    }

    public interface OnAamarMedicDoctorListFragmentInteractionListener {
        void onAamarMedicDoctorListFragmentInteractionListener();
        void onAamarMedicDoctorListFragmentInteractionListener(String msg);
    }
}
