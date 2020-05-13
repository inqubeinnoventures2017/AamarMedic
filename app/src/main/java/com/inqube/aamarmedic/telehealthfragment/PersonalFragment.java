package com.inqube.aamarmedic.telehealthfragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseFragment;
import com.inqube.aamarmedic.model.patientpersonaldetails.Result;
import com.inqube.aamarmedic.model.telehealthpersonal.MSG;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;
import com.inqube.aamarmedic.util.UtilClass;

import java.util.List;

import retrofit2.Response;

public class PersonalFragment extends BaseFragment implements AllInterfaces.RetrofitResponseToActivityOrFrament,
        AllInterfaces.AdapterCallback, View.OnClickListener
{
    private boolean resume;
    private View view;
    private EditText edt_mbno,edt_patientname,edt_aadhar_no,edt_emailid,edt_address,edt_city,edt_area,edt_pincode,edt_date_of_birth;
    private RadioButton rd_male, rd_female, rd_other;
    private Button btn_personal;
    private String dbmsg="", gender = "", emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private RadioGroup rg_gender;
    private TextView tv_dob;
    final static int aadharnoMaxLen = 12, mobilenoMaxLen = 10,pincodeMaxLen=6;

    private OnAamarMedicTeleHealthPersonalFragmentInteractionListener mListener;

    //private List<Result> list_Tele_H_personal;
    private List<Result> list_Patient_Dtls;
    boolean skipOnChange = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_tele_health_personal, container, false);
        view = inflater.inflate(R.layout.fragment_tele_health_personal, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!resume) {
            ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);
            setUI(view);
            /*ll_main_container = (LinearLayout) view.findViewById(R.id.ll_main_container);
            ll_main_container.setVisibility(View.GONE);*/

            /*if (((BaseActivity) getActivity()).isDeviceOnline()) {
            }*/
        }
        resume = true;
    }

    private void setUI(View view) {
        //((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);
        //((BaseActivity)getActivity()).col_holder = (CoordinatorLayout) view.findViewById(R.id.col_holder);

        edt_mbno = (EditText)view.findViewById(R.id.edt_mbno);
        edt_patientname= (EditText)view.findViewById(R.id.edt_patientname);
        edt_aadhar_no= (EditText)view.findViewById(R.id.edt_aadhar_no);
        edt_emailid= (EditText)view.findViewById(R.id.edt_emailid);
        edt_address= (EditText)view.findViewById(R.id.edt_address);
        edt_city= (EditText)view.findViewById(R.id.edt_city);
        edt_area= (EditText)view.findViewById(R.id.edt_area);
        edt_pincode= (EditText)view.findViewById(R.id.edt_pincode);
        tv_dob= (TextView)view.findViewById(R.id.tv_date_of_birth);
        rg_gender=(RadioGroup)view.findViewById(R.id.rg_gender);
        rd_male=(RadioButton)view.findViewById(R.id.rb_male);
        rd_female=(RadioButton)view.findViewById(R.id.rb_female);
        rd_other=(RadioButton)view.findViewById(R.id.rb_other);

        btn_personal= (Button)view.findViewById(R.id.btn_personal);

        btn_personal.setOnClickListener(this);
        tv_dob.setOnClickListener(this);

        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_male:
                        gender= Config.MALE;
                        break;
                    case R.id.rb_female:
                        gender=Config.FEMALE;
                        break;
                    case R.id.rb_other:
                        gender=Config.OTHER;
                        break;
                }
            }
        });

        edt_mbno.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    if (edt_mbno.getText().length()==mobilenoMaxLen) {
                        if (((BaseActivity)getActivity()).isDeviceOnline()) {
                            ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);

                            UtilClass.getInstance().getPatientPersonalDetailsData(((BaseActivity)getActivity()),
                                    PersonalFragment.this, edt_mbno.getText().toString(),
                                    ((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN,""));
                        }
                        else {
                            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                            return false;
                        }
                    }else
                    {
                        mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_tendigit_mb));
                        return false;
                    }
                    return true;
                }
                return false;
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edt_mbno.getText().length()<mobilenoMaxLen)
                {
                    if (edt_patientname.getText().length()>0)
                    {
                        controlEnableDisable(true);
                        edt_mbno.setText("");
                        edt_mbno.setFocusable(true);
                    }
                }else if (edt_mbno.getText().length()==mobilenoMaxLen) {
                    if (((BaseActivity)getActivity()).isDeviceOnline()) {
                        ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);

                        UtilClass.getInstance().getPatientPersonalDetailsData(((BaseActivity)getActivity()),
                                PersonalFragment.this, edt_mbno.getText().toString(),
                                ((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN,""));
                    }
                    else {
                        mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                    }
                }else
                {
                    controlEnableDisable(true);
                }
            }
        };

        edt_mbno.addTextChangedListener(textWatcher);

        /*edt_mbno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (edt_mbno.getText().length()==mobilenoMaxLen) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN) {
                        //if(event.getRawX() >= (edt_mbno.getRight() - edt_mbno.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (((BaseActivity)getActivity()).isDeviceOnline()) {
                            ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);

                            UtilClass.getInstance().getPatientPersonalDetailsData(((BaseActivity)getActivity()),
                                    PersonalFragment.this, edt_mbno.getText().toString(),
                                    ((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN,""));
                        }
                        else {
                            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                            return false;
                        }
                    }
                    //}
                }else
                {
                    mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_tendigit_mb));
                    return false;
                }
                return false;
            }
        });*/

        edt_aadhar_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (edt_aadhar_no.getText().length() < aadharnoMaxLen) {
                    mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_aadhar_no));
                }
                else {
                    // When focus is lost check that the text field has valid values.
                    if (!hasFocus) {
                        // Validate youredittext
                        System.out.println("aadhar no.:" + edt_aadhar_no.getText().toString());
                        UtilClass.getInstance().checkDuplicateAadharNoData(((BaseActivity) getActivity()), PersonalFragment.this,
                                edt_aadhar_no.getText().toString(), ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                    }
                }
            }
        });

        if (!((BaseActivity)getActivity()).isDeviceOnline()) {
            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
        }
        else {
            controlEnableDisable(false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAamarMedicTeleHealthPersonalFragmentInteractionListener) {
            mListener = (OnAamarMedicTeleHealthPersonalFragmentInteractionListener) context;
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
            case R.id.btn_personal:
                if (!emptyCheckValidation()) {
                    //System.out.println("list_Patient_Dtls:"+list_Patient_Dtls);
                    //System.out.println("list_Patient_Dtls.size():"+list_Patient_Dtls.size());
                    if (list_Patient_Dtls!=null)
                    {
                        if (list_Patient_Dtls.size()>0){
                            if (!(edt_mbno.getText().toString().equalsIgnoreCase(list_Patient_Dtls.get(0).getMobileNo()) &&
                                    edt_aadhar_no.getText().toString().equalsIgnoreCase(list_Patient_Dtls.get(0).getAadharNo()))) {
                                if (((BaseActivity)getActivity()).isDeviceOnline()) {
                                    ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);

                                    UtilClass.getInstance().submitTeleHealthPersonalData(((BaseActivity)getActivity()),PersonalFragment.this,
                                            edt_patientname.getText().toString(), edt_mbno.getText().toString(), edt_aadhar_no.getText().toString(),
                                            edt_emailid.getText().toString(), edt_address.getText().toString(), edt_city.getText().toString(),
                                            edt_area.getText().toString(), edt_pincode.getText().toString(), gender, tv_dob.getText().toString(), ((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN, "")
                                    );
                                } else {
                                    mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                                }
                            }else {
                                //startActivity(new Intent(TeleHealth_Personal_Activity.this, TeleHealth_Medical_I_Activity.class));
                                mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(false);
                            }
                        }
                        else
                        {
                            insertPatientDetailsInfo();
                        }
                    }
                    else
                    {
                        insertPatientDetailsInfo();
                    }
                }
                break;
            case R.id.tv_date_of_birth:
                UtilClass.getInstance().dateDialog(tv_dob, getActivity());
                break;
        }
    }

    private void insertPatientDetailsInfo()
    {
        if (((BaseActivity)getActivity()).isDeviceOnline()) {
            ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);

            UtilClass.getInstance().submitTeleHealthPersonalData(((BaseActivity)getActivity()),PersonalFragment.this,
                    edt_patientname.getText().toString(), edt_mbno.getText().toString(), edt_aadhar_no.getText().toString(),
                    edt_emailid.getText().toString(), edt_address.getText().toString(), edt_city.getText().toString(),
                    edt_area.getText().toString(), edt_pincode.getText().toString(), gender, tv_dob.getText().toString(), ((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN, "")
            );
        } else {
            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
        }
    }

    private boolean emptyCheckValidation() {
        if (edt_mbno.getText().length() < mobilenoMaxLen) {
            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_tendigit_mb));
            return true;
        }else if (edt_patientname.getText().length() < 1) {
            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_the_patient_name));
            return true;
        }else if (edt_aadhar_no.getText().length() < aadharnoMaxLen) {
            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_aadhar_no));
            return true;
        }else if (edt_emailid.getText().length() < 1) {
            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_emailid));
            return true;
        }else if (!edt_emailid.getText().toString().trim().matches(emailPattern))
        {   mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_valid_email));
            return true;
        }else if (edt_address.getText().length() < 1) {
            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_address));
            return true;
        }else if (edt_city.getText().length() < 1) {
            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_city));
            return true;
        }else if (edt_pincode.getText().length() < pincodeMaxLen) {
            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_PINCode));
            return true;
        }else if (tv_dob.getText().length() < 1) {
            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.Please_enter_date_of_birth));
            return true;
        }
        return false;
    }

    @Override
    public void onSuccess(Object response, String which_method) {
        if (which_method.equalsIgnoreCase("checkDuplicateAadharNoData")) {
            Response<com.inqube.aamarmedic.model.checkduplicateaadhar.MSG> res = (Response<com.inqube.aamarmedic.model.checkduplicateaadhar.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            if(res.body().getStatus()==200)
            {
                mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(res.body().getMessage());
                edt_aadhar_no.setText("");
                edt_aadhar_no.setFocusable(true);
            }
        }

        if (which_method.equalsIgnoreCase("submitTeleHealthPersonalData")) {
            //((BaseActivity)getActivity()).pb_loader.setVisibility(View.GONE);
            Response<MSG> res = (Response<MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            dbmsg = res.body().getMessage();

            mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(false);
            //mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(dbmsg);
            //startActivity(new Intent(TeleHealth_Personal_Activity.this, TeleHealth_Medical_I_Activity.class));
        }

        if (which_method.equalsIgnoreCase("getPatientPersonalDetailsData")) {

            Response<com.inqube.aamarmedic.model.patientpersonaldetails.MSG> res = (Response<com.inqube.aamarmedic.model.patientpersonaldetails.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            list_Patient_Dtls = res.body().getResult();

            controlEnableDisable(true);
            //InputMethodManager.HIDE_IMPLICIT_ONLY;


            if (list_Patient_Dtls!=null) {
                if (list_Patient_Dtls.size() > 0)
                {
                    /*Toast toast = Toast.makeText(getActivity(), R.string.this_mbno_already_registered, Toast.LENGTH_LONG);
                    toast.show();*/

                    //Creating the LayoutInflater instance
                    LayoutInflater li = getLayoutInflater();
                    //Getting the View object as defined in the customtoast.xml file
                    View layout = li.inflate(R.layout.activity_medical_data_exists, view.findViewById(R.id.medical_data_exists));

                    //Creating the Toast object
                    TextView tv_toast_msg = (TextView) layout.findViewById(R.id.tv_toast_msg);
                    tv_toast_msg.setText(getString(R.string.this_mbno_already_registered));
                    Toast toast = new Toast(getActivity().getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setView(layout);//setting the view of custom toast layout
                    toast.show();

                    ((BaseActivity) getActivity()).saveDataPreference(Config.PATIENT_ID, list_Patient_Dtls.get(0).get_id());
                    edt_patientname.setText(list_Patient_Dtls.get(0).getPatientName());
                    edt_aadhar_no.setText(list_Patient_Dtls.get(0).getAadharNo());
                    edt_emailid.setText(list_Patient_Dtls.get(0).getEmailId());
                    edt_address.setText(list_Patient_Dtls.get(0).getAddress());
                    edt_city.setText(list_Patient_Dtls.get(0).getCity());
                    edt_area.setText(list_Patient_Dtls.get(0).getArea());
                    edt_pincode.setText(list_Patient_Dtls.get(0).getPinCode());
                    tv_dob.setText(list_Patient_Dtls.get(0).getDateOfBirth());

                    gender = list_Patient_Dtls.get(0).getGender();
                    if (gender != null)
                    {
                        switch (gender)
                        {
                            case Config.MALE:
                                rd_male.setChecked(true);
                                break;
                            case Config.FEMALE:
                                rd_female.setChecked(true);
                                break;
                            case Config.OTHER:
                                rd_other.setChecked(true);
                                break;
                        }
                    } else {
                        rd_other.setChecked(true);
                    }
                    controlEnableDisable(false);
                    mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(true);
                }
            }
            edt_patientname.requestFocus();
        }
    }

    private void controlEnableDisable(boolean flag)
    {
        System.out.println("flag:"+flag);
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        edt_patientname.setEnabled(flag);
        edt_aadhar_no.setEnabled(flag);
        edt_emailid.setEnabled(flag);
        edt_address.setEnabled(flag);
        edt_city.setEnabled(flag);
        edt_area.setEnabled(flag);
        edt_pincode.setEnabled(flag);
        rd_male.setEnabled(flag);
        rd_female.setEnabled(flag);
        rd_other.setEnabled(flag);
        tv_dob.setEnabled(flag);

        if (flag==true)
        {
            edt_patientname.setText("");
            edt_aadhar_no.setText("");
            edt_emailid.setText("");
            edt_address.setText("");
            edt_city.setText("");
            edt_area.setText("");
            edt_pincode.setText("");
            tv_dob.setText("");
        }
    }

    @Override
    public void onFailure(ReturnResponse response) {
        btn_personal.setEnabled(true);
        mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(response.getMsg());
    }

    @Override
    public void onResponseFailure() {
        btn_personal.setEnabled(true);
        mListener.onAamarMedicTeleHealthPersonalFragmentInteractionListener(getString(R.string.please_try_again));
    }

    @Override
    public void onResponseFailure(String msg) {

    }

    @Override
    public void onReturn(String position) {

    }

    public interface OnAamarMedicTeleHealthPersonalFragmentInteractionListener
    {
        void onAamarMedicTeleHealthPersonalFragmentInteractionListener(boolean dataExists);
        void onAamarMedicTeleHealthPersonalFragmentInteractionListener(String msg);
    }
}
