package com.inqube.aamarmedic.telehealthfragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.agentfragment.AgentDashboardFragment;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseFragment;
import com.inqube.aamarmedic.model.telehealthpersonal.MSG;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class Medical_I_Fragment extends BaseFragment implements AllInterfaces.RetrofitResponseToActivityOrFrament,
        AllInterfaces.AdapterCallback, View.OnClickListener {

    private OnAamarMedicTeleHealthMedicalIFragmentInteractionListener mListener;

    private View view;
    private EditText edt_body_temp;
    private RadioButton rb_wet, rb_dry, rb_acute, rb_under_control;
    private CheckBox chk_having_cough, chk_cough_more_than_15days, chk_having_shortness_breath;
    private Button btn_medical;
    private RadioGroup rg_cough_pattern, rg_shortness_breath;
    private LinearLayout ll_shortness_breath, ll_cough_pattern;

    private String cough_pattern, shortness_breath;
    private boolean resume;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tele_health_medical_i, container, false);
        return view;
        //return inflater.inflate(R.layout.fragment_tele_health_medical_i, container, false);
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

        edt_body_temp = (EditText) view.findViewById(R.id.edt_body_temp);

        chk_having_cough = (CheckBox) view.findViewById(R.id.chk_having_cough);

        rg_cough_pattern = (RadioGroup) view.findViewById(R.id.rg_cough_pattern);
        rb_dry = (RadioButton) view.findViewById(R.id.rb_dry);
        rb_wet = (RadioButton) view.findViewById(R.id.rb_wet);

        chk_cough_more_than_15days = (CheckBox) view.findViewById(R.id.chk_cough_more_than_15days);
        chk_having_shortness_breath = (CheckBox) view.findViewById(R.id.chk_having_shortness_breath);

        rg_shortness_breath = (RadioGroup) view.findViewById(R.id.rg_shortness_breath);
        rb_acute = (RadioButton) view.findViewById(R.id.rb_acute);
        rb_under_control = (RadioButton) view.findViewById(R.id.rb_under_control);

        btn_medical = (Button) view.findViewById(R.id.btn_medical_i);

        ll_cough_pattern = (LinearLayout)view.findViewById(R.id.ll_cough_pattern);
        ll_shortness_breath = (LinearLayout)view.findViewById(R.id.ll_shortness_breath);

        ll_cough_pattern.setVisibility(View.GONE);
        ll_shortness_breath.setVisibility(View.GONE);

        chk_having_cough.setOnClickListener(this);
        chk_having_shortness_breath.setOnClickListener(this);
        btn_medical.setOnClickListener(this);

        rg_cough_pattern.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_wet:
                        cough_pattern = Config.WET;
                        break;
                    case R.id.rb_dry:
                        cough_pattern = Config.DRY;
                        break;
                }
            }
        });

        rg_shortness_breath.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_acute:
                        shortness_breath = Config.ACUTE;
                        break;
                    case R.id.rb_under_control:
                        shortness_breath = Config.UNDERCONTROL;
                        break;
                }
            }
        });

        btn_medical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emptyCheckValidation()){
                    SharedPreferences pref = ((BaseActivity)getActivity()).getSharedPreferences(Config.MEDICAL_I,Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Config.BODY_TEMP,edt_body_temp.getText().toString());
                    editor.putBoolean(Config.HAVING_COUGH,chk_having_cough.isChecked());
                    editor.putString(Config.COUGH_PATTERN,cough_pattern);
                    editor.putBoolean(Config.COUGH_MORE_THAN_15DAYS,chk_cough_more_than_15days.isChecked());
                    editor.putBoolean(Config.HAVING_SHORTNESS_BREATH,chk_having_shortness_breath.isChecked());
                    editor.putString(Config.SHORTNESS_BREATH,shortness_breath);
                    editor.commit();

                    mListener.onAamarMedicTeleHealthMedicalIFragmentInteractionListener();
                }
            }
        });

        if (!((BaseActivity) getActivity()).isDeviceOnline()) {
            mListener.onAamarMedicTeleHealthMedicalIFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAamarMedicTeleHealthMedicalIFragmentInteractionListener) {
            mListener = (OnAamarMedicTeleHealthMedicalIFragmentInteractionListener) context;
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
    public void onReturn(String position) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chk_having_cough:
                ll_cough_pattern.setVisibility(View.GONE);
                if(chk_having_cough.isChecked())
                {
                    ll_cough_pattern.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.chk_having_shortness_breath:
                ll_shortness_breath.setVisibility(View.GONE);
                if (chk_having_shortness_breath.isChecked())
                {
                    ll_shortness_breath.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_medical_i:
                System.out.println("emptyCheckValidation:"+emptyCheckValidation());
                if (!emptyCheckValidation()){

                    mListener.onAamarMedicTeleHealthMedicalIFragmentInteractionListener();
                }


                break;
        }
    }

    private boolean emptyCheckValidation() {
        if (edt_body_temp.getText().length() < 1) {
            mListener.onAamarMedicTeleHealthMedicalIFragmentInteractionListener(getString(R.string.Please_enter_body_temp));
            return true;
        }

        if (chk_having_cough.isChecked() && (!rb_wet.isChecked() && !rb_dry.isChecked()) ) {
            mListener.onAamarMedicTeleHealthMedicalIFragmentInteractionListener(getString(R.string.Please_select_cough_pattern));
            return true;
        }

        if (chk_having_shortness_breath.isChecked() && (!rb_acute.isChecked() && !rb_under_control.isChecked()) ) {
            mListener.onAamarMedicTeleHealthMedicalIFragmentInteractionListener(getString(R.string.Please_select_shortness_breath));
            return true;
        }
        return false;
    }


    @Override
    public void onSuccess(Object response, String which_method) {

    }

    @Override
    public void onFailure(ReturnResponse response) {
        btn_medical.setEnabled(true);
        mListener.onAamarMedicTeleHealthMedicalIFragmentInteractionListener(response.getMsg());
    }

    @Override
    public void onResponseFailure() {
        btn_medical.setEnabled(true);
        mListener.onAamarMedicTeleHealthMedicalIFragmentInteractionListener(getString(R.string.please_try_again));
    }

    @Override
    public void onResponseFailure(String msg) {

    }

    public interface OnAamarMedicTeleHealthMedicalIFragmentInteractionListener {
        void onAamarMedicTeleHealthMedicalIFragmentInteractionListener();
        void onAamarMedicTeleHealthMedicalIFragmentInteractionListener(String msg);

    }


}