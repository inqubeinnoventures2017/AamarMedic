package com.inqube.aamarmedic.telehealthfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

import com.inqube.aamarmedic.AgentDashboardMainFragmentActivity;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseFragment;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;
import com.inqube.aamarmedic.util.UtilClass;

public class Medical_II_Fragment extends BaseFragment implements AllInterfaces.RetrofitResponseToActivityOrFrament,
        AllInterfaces.AdapterCallback, View.OnClickListener  {

    private OnAamarMedicTeleHealthMedicalIIFragmentInteractionListener mListener;

    private CheckBox chk_blood_pressure,chk_blood_sugar, chk_allergic;
    private EditText edt_last_blood_pressure, edt_fasting, edt_pp;
    private Button btnSubmit;
    private View view;
    LinearLayout ll_blood_pressure, ll_blood_sugar;
    private boolean resume;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_tele_health_medical_ii, container, false);
        return view;
        //return inflater.inflate(R.layout.fragment_tele_health_medical_ii, container, false);
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

    private void setUI(View view)
    {
        ll_blood_pressure = (LinearLayout)view.findViewById(R.id.ll_blood_pressure);
        ll_blood_pressure.setVisibility(view.GONE);

        chk_blood_pressure = (CheckBox)view.findViewById(R.id.chk_blood_pressure);
        edt_last_blood_pressure = (EditText)view.findViewById(R.id.edt_last_blood_pressure);
        chk_blood_sugar = (CheckBox)view.findViewById(R.id.chk_blood_sugar);

        ll_blood_sugar = (LinearLayout)view.findViewById(R.id.ll_blood_sugar);
        ll_blood_sugar.setVisibility(view.GONE);

        edt_fasting = (EditText)view.findViewById(R.id.edt_fasting);
        edt_pp = (EditText)view.findViewById(R.id.edt_pp);

        chk_allergic = (CheckBox)view.findViewById(R.id.chk_allergic);

        btnSubmit = (Button)view.findViewById(R.id.btnSubmit);

        //System.out.println("chk_blood_pressure:"+chk_blood_pressure.isChecked());

        chk_blood_pressure.setOnClickListener(this);
        chk_blood_sugar.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.chk_blood_pressure:
                ll_blood_pressure.setVisibility(View.GONE);
                if(chk_blood_pressure.isChecked())
                {
                    ll_blood_pressure.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.chk_blood_sugar:
                ll_blood_sugar.setVisibility(View.GONE);
                if(chk_blood_sugar.isChecked())
                {
                    ll_blood_sugar.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btnSubmit:

                if (!emptyCheckValidation()) {

                    if (((BaseActivity) getActivity()).isDeviceOnline()) {
                        ((BaseActivity) getActivity()).pb_loader = view.findViewById(R.id.pb_loader);

                        SharedPreferences pref = getActivity().getSharedPreferences(Config.MEDICAL_I, 0); // 0 - for private mode

                        /*System.out.println("Patient Id:"+ ((BaseActivity) getActivity()).getDataPreference(Config.PATIENT_ID,""));
                        System.out.println("Body Temperature:"+ pref.getString(Config.BODY_TEMP,""));
                        System.out.println("Cough:"+ pref.getBoolean(Config.HAVING_COUGH,false));
                        System.out.println("Cough Pattern:"+ pref.getString(Config.COUGH_PATTERN,""));
                        System.out.println("COUGH_NO_OF_DAYS:"+pref.getBoolean(Config.COUGH_MORE_THAN_15DAYS,false));
                        System.out.println("SHORTNESS OF BREATH:"+pref.getBoolean(Config.HAVING_SHORTNESS_BREATH,false));
                        System.out.println("BREATH TYPE:"+ pref.getString(Config.SHORTNESS_BREATH,""));

                        System.out.println("BLOOD PRESSURE:"+chk_blood_pressure.isChecked());
                        System.out.println("BLOOD SUGAR:"+chk_blood_sugar.isChecked());
                        System.out.println("BLOOD SUGAR FASTING:"+edt_fasting.getText().toString());
                        System.out.println("BLOOD SUGAR PP:"+edt_pp.getText().toString());
                        System.out.println("Allergic:"+chk_allergic.isChecked());*/

                        /*//Creating the LayoutInflater instance
                        LayoutInflater li = getLayoutInflater();
                        //Getting the View object as defined in the customtoast.xml file
                        View layout = li.inflate(R.layout.toast_patient_info, view.findViewById(R.id.custom_toast_layout));

                        //Creating the Toast object
                        Toast toast = new Toast(getActivity().getApplicationContext());
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.setView(layout);//setting the view of custom toast layout
                        toast.show();*/

                        /*AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        //builder.setTitle("Name");        // set the custom layout
                        final View customLayout = getLayoutInflater().inflate(R.layout.toast_patient_info, null);
                        builder.setView(customLayout);        // add a button
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });        // create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();*/

                        UtilClass.getInstance().submitTeleHealthMedicalData(((BaseActivity) getActivity()), Medical_II_Fragment.this,
                                ((BaseActivity) getActivity()).getDataPreference(Config.PATIENT_ID, ""), pref.getString(Config.BODY_TEMP,""),
                                pref.getBoolean(Config.HAVING_COUGH,false), pref.getString(Config.COUGH_PATTERN,""), pref.getBoolean(Config.COUGH_MORE_THAN_15DAYS,false),
                                pref.getBoolean(Config.HAVING_SHORTNESS_BREATH,false),  pref.getString(Config.SHORTNESS_BREATH,""),
                                chk_blood_pressure.isChecked(), edt_last_blood_pressure.getText().toString(),
                                chk_blood_sugar.isChecked(), edt_fasting.getText().toString(), edt_pp.getText().toString(), chk_allergic.isChecked(),
                                ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                    } else {
                        mListener.onAamarMedicTeleHealthMedicalIIFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                    }
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAamarMedicTeleHealthMedicalIIFragmentInteractionListener) {
            mListener = (OnAamarMedicTeleHealthMedicalIIFragmentInteractionListener) context;
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
        //System.out.println("submitTeleHealthMedicalData process called");

        if (which_method.equalsIgnoreCase("submitTeleHealthMedicalData")) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //builder.setTitle("Name");        // set the custom layout
            final View customLayout = getLayoutInflater().inflate(R.layout.toast_patient_info, null);
            builder.setView(customLayout);        // add a button

            /*Button btn_ok_patient_registration;
            btn_ok_patient_registration = view.findViewById(R.id.btn_ok_patient_registration);
            btn_ok_patient_registration.setOnClickListener(this);*/

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent( ((BaseActivity)getActivity()), AgentDashboardMainFragmentActivity.class));
                    ((BaseActivity)getActivity()).finish();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private boolean emptyCheckValidation() {
        if(chk_blood_pressure.isChecked() && edt_last_blood_pressure.getText().length() < 1)
        {
            mListener.onAamarMedicTeleHealthMedicalIIFragmentInteractionListener(getString(R.string.Please_enter_last_blood_pressure));
            return true;
        }

        if(chk_blood_sugar.isChecked())
        {
            if (edt_fasting.getText().length() < 1)
            {
                mListener.onAamarMedicTeleHealthMedicalIIFragmentInteractionListener(getString(R.string.Please_enter_blood_sugar_fasting));
                return true;
            }

            if (edt_fasting.getText().length() < 1)
            {
                mListener.onAamarMedicTeleHealthMedicalIIFragmentInteractionListener(getString(R.string.Please_enter_blood_sugar_pp));
                return true;
            }
        }
        return false;
    }

    @Override
    public void onFailure(ReturnResponse response) {
        btnSubmit.setEnabled(true);
        mListener.onAamarMedicTeleHealthMedicalIIFragmentInteractionListener(response.getMsg());
    }

    @Override
    public void onResponseFailure() {
        btnSubmit.setEnabled(true);
        mListener.onAamarMedicTeleHealthMedicalIIFragmentInteractionListener(getString(R.string.please_try_again));
    }

    @Override
    public void onResponseFailure(String msg) {

    }

    @Override
    public void onReturn(String position) {

    }

    public interface OnAamarMedicTeleHealthMedicalIIFragmentInteractionListener
    {
        //void onAamarMedicTeleHealthMedicalIIFragmentInteractionListener();
        void onAamarMedicTeleHealthMedicalIIFragmentInteractionListener(String msg);
    }
}
