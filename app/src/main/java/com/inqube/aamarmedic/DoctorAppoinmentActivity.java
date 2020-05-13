package com.inqube.aamarmedic;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.gson.Gson;
import com.inqube.aamarmedic.adapter.SelectDoctorListDialogBoxAdapter;
import com.inqube.aamarmedic.adapter.SelectStateDailogBoxAdapter;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivityWithoutMenu;
import com.inqube.aamarmedic.dialog.DoctorDialogBox;
import com.inqube.aamarmedic.dialog.DoctorListByNameDialogBox;
import com.inqube.aamarmedic.dialog.StateDailog;
import com.inqube.aamarmedic.model.baseModel.BaseModelClass;
import com.inqube.aamarmedic.model.districtlist.Result;
import com.inqube.aamarmedic.model.specializationlist.MSG;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;
import com.inqube.aamarmedic.util.UtilClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class DoctorAppoinmentActivity extends BaseActivityWithoutMenu
    implements View.OnClickListener, AllInterfaces.DialogCallback, AllInterfaces.StateCallback,
        AllInterfaces.AdapterCallback
{
    ArrayList<BaseModelClass> all_doctor_list;
    ArrayList<BaseModelClass> al_dialog_list;
    private List<com.inqube.aamarmedic.model.specializationlist.Spec> listSpecialization;
    private List<com.inqube.aamarmedic.model.clinic.Result> listClinic;
    private List<com.inqube.aamarmedic.model.daylist.Result> listDay;
    private List<com.inqube.aamarmedic.model.doctorlist.Result> listDoctor;
    private List<com.inqube.aamarmedic.model.doctorlistbyname.Doc> listDoctorbyname;
    private List<com.inqube.aamarmedic.model.districtlist.District> listDistrict;
    private List<com.inqube.aamarmedic.model.citylist.City> listCity;
    private List<com.inqube.aamarmedic.model.appointment.Result> listAppointment;

    private TextView tv_specialization, tv_clinic, tv_day, tv_doctor_name,tv_doctor_degree, tv_doctor_specialist, tv_hospital, tv_hospital_addr, tv_doctor_time,
                        tv_district,tv_city;
    private Button btnShowDoctor,btnSubmit;
    private EditText edt_search,edt_patientname,edt_mobileno,edt_address,edt_pinno,edt_remarks;
    private LinearLayout ll_doctor_info;

    String selectedSpecializationId, selectedClinicId,selectedDayId,selectedDay, selectDay, selectedDistrict, selectedDistrictId,
            selectedCity, selectedCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_doctor);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUI() {
        super.setUI();

        pb_loader =  findViewById(R.id.pb_loader);
        col_holder =  findViewById(R.id.col_holder);

        edt_search = findViewById(R.id.edt_search);

        tv_specialization =findViewById(R.id.tv_specialization);
        tv_clinic = findViewById(R.id.tv_clinic);
        tv_day = findViewById(R.id.tv_day);

        tv_doctor_name = findViewById(R.id.tv_doctor_name);
        tv_doctor_degree = findViewById(R.id.tv_doctor_degree);
        tv_doctor_specialist = findViewById(R.id.tv_doctor_specialist);
        tv_hospital = findViewById(R.id.tv_doctor_hospitalname);
        tv_hospital_addr = findViewById(R.id.tv_hospital_addr);
        tv_doctor_time = findViewById(R.id.tv_doctor_time);

        edt_patientname =findViewById(R.id.edt_patientname);
        edt_mobileno =findViewById(R.id.edt_mobileno);
        edt_address =findViewById(R.id.edt_address);
        edt_pinno =findViewById(R.id.edt_pinno);
        tv_district =findViewById(R.id.tv_district);
        tv_city =findViewById(R.id.tv_city);
        edt_remarks =findViewById(R.id.edt_remarks);

        btnShowDoctor=findViewById(R.id.btnShowDoctors);
        btnSubmit=findViewById(R.id.btnSubmit);

        ll_doctor_info = findViewById(R.id.ll_doctor_info);
        ll_doctor_info.setVisibility(View.GONE);

        edt_search.setOnClickListener(this);

        tv_specialization.setOnClickListener(this);
        tv_clinic.setOnClickListener(this);
        tv_day.setOnClickListener(this);
        tv_district.setOnClickListener(this);
        tv_city.setOnClickListener(this);

        btnShowDoctor.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        edt_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(event.getRawX() >= (edt_search.getRight() - edt_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        if (isDeviceOnline()) {
                            pb_loader.setVisibility(View.VISIBLE);

                            //System.out.println("token:" + getUserPreference(Config.AUTH_TOKEN,""));
                            tv_specialization.setText("");
                            tv_clinic.setText("");
                            tv_day.setText("");

                            /*UtilClass.getInstance().getDoctorListbByNameData(DoctorAppoinmentActivity.this, DoctorAppoinmentActivity.this,
                            edt_search.getText().toString(), getUserPreference(Config.AUTH_TOKEN,""));*/
                        }
                        else {
                            createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg));
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        if (isDeviceOnline()==false) {
            createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg), new DoctorAppoinmentActivity());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_specialization:
                if (isDeviceOnline()) {
                    pb_loader.setVisibility(View.VISIBLE);

                    System.out.println("token:" + getUserPreference(Config.AUTH_TOKEN,""));

                    /*UtilClass.getInstance().getSpecializationData(DoctorAppoinmentActivity.this, DoctorAppoinmentActivity.this,
                            getUserPreference(Config.AUTH_TOKEN,""));*/
                }
                else {
                    createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg));
                }
                break;
            case R.id.tv_clinic:

                    pb_loader.setVisibility(View.VISIBLE);

                    // Checking validity whether user provided all required parameter or not
                    /*if( tv_specialization.getText().length() < 1 ) {
                        createSnackBar(col_holder, getString(R.string.please_select_specialisation), new DoctorAppoinmentActivity());
                    }
                    else
                    {*/
                        System.out.println("token:" + getUserPreference(Config.AUTH_TOKEN,""));
                        System.out.println("Specialization:" + selectedSpecializationId);

                        if (!isDeviceOnline()) {
                            createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg));
                            /*UtilClass.getInstance().getClinicData(DoctorAppoinmentActivity.this, DoctorAppoinmentActivity.this,
                                    selectedSpecializationId, getUserPreference(Config.AUTH_TOKEN, ""));*/
                        }
                    //}
                break;
            case R.id.tv_day:

                    /*pb_loader.setVisibility(View.VISIBLE);

                    // Checking validity whether user provided all required parameter or not
                    *//*if( tv_specialization.getText().length() < 1 ){
                        createSnackBar(col_holder, getString(R.string.please_select_specialisation), new DoctorAppoinmentActivity());
                    }
                    else if( tv_clinic.getText().toString().length() < 1 ){
                        createSnackBar(col_holder, getString(R.string.please_select_clinic), new DoctorAppoinmentActivity());
                    }
                    else {*//*

                        System.out.println("token:" + getUserPreference(Config.AUTH_TOKEN,""));
                        System.out.println("Specialization:" + selectedSpecializationId);

                        if (isDeviceOnline()) {
                            //API Calling
                            *//*UtilClass.getInstance().getDayListData(DoctorAppoinmentActivity.this, DoctorAppoinmentActivity.this,
                                    selectedSpecializationId, selectedClinicId, getUserPreference(Config.AUTH_TOKEN, ""));*//*
                        }
                        else {
                            createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg));
                        }
                    //}*/
                break;
            case R.id.btnShowDoctors:

                    pb_loader.setVisibility(View.VISIBLE);
                    edt_search.setText("");

                    // Checking validity whether user provided all required parameter or not
                    if (( tv_specialization.getText().length() < 1 ) ||
                            ( tv_clinic.getText().length() < 1 ) ||
                            ( tv_day.getText().length() < 1 ))
                    {
                        createSnackBar(col_holder, getString(R.string.Please_select_atleast_one), new DoctorAppoinmentActivity());
                    }else {
                        //API Calling
                        selectDay = "";
                        if (selectedDay!=null)
                        {
                            selectDay = selectedDay.toLowerCase();
                        }

                        //System.out.println("spec_id:" + selectedSpecializationId);
                        //System.out.println("clinic_id:" + selectedClinicId);
                        //System.out.println("day:" + selectDay);
                        //System.out.println("token:" + getUserPreference(Config.AUTH_TOKEN,""));

                        if (!isDeviceOnline()) {
                            createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg));
                        }
                    }
                break;
            case R.id.tv_district:
                pb_loader.setVisibility(View.VISIBLE);
                if (!isDeviceOnline()) {
                    createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg));
                }
                break;
            case R.id.tv_city:
               /* pb_loader.setVisibility(View.VISIBLE);
                if (isDeviceOnline()) {
                    *//*UtilClass.getInstance().getCityData(DoctorAppoinmentActivity.this, DoctorAppoinmentActivity.this,
                            selectedDistrictId, getUserPreference(Config.AUTH_TOKEN, ""));*//*
                }
                else {
                    createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg));
                }*/
                break;
            case R.id.btnSubmit:
                if (!emptyCheckValidation()) {
                    if (!isDeviceOnline()) {
                        createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg));
                    }
                }
                break;
        }
    }

    private boolean emptyCheckValidation()
    {
        if (edt_patientname.getText().length()<1)
        {
            createSnackBar(col_holder, getString(R.string.Please_enter_the_patient_name));
            return true;
        }else if (edt_mobileno.getText().length() < 10)
        {
            createSnackBar(col_holder, getString(R.string.Please_enter_tendigit_mb));
            return true;
        }else if (edt_address.getText().length() < 1)
        {
            createSnackBar(col_holder, getString(R.string.Please_enter_address));
            return true;
        }
        else if (edt_pinno.getText().length() < 1)
        {
            createSnackBar(col_holder, getString(R.string.Please_enter_PINCode));
            return true;
        }else if (tv_district.getText().length() < 1)
        {
            createSnackBar(col_holder, getString(R.string.Please_enter_district));
            return true;
        }else if (tv_city.getText().length() < 1)
        {
            createSnackBar(col_holder, getString(R.string.Please_enter_city));
            return true;
        }else if (edt_remarks.getText().length() < 1)
        {
            createSnackBar(col_holder, getString(R.string.Please_enter_remarks));
            return true;
        }
        return false;
    }

    @Override
    public void onDialogReturn(String position) {
        String str[] = position.split("-");
        ll_doctor_info.setVisibility(View.VISIBLE);

        if (str[1].equalsIgnoreCase(Config.DOCTOR_LIST)) {
            saveDataPreference(Config.DOCTOR_ID,listDoctor.get(Integer.parseInt(str[0])).get_id());
            String doctorName = listDoctor.get(Integer.parseInt(str[0])).getDoctorInfo().getFirstName() + " " + listDoctor.get(Integer.parseInt(str[0])).getDoctorInfo().getLastName();
            tv_doctor_name.setText(doctorName);
            tv_doctor_degree.setText(listDoctor.get(Integer.parseInt(str[0])).getDoctorInfo().getDegree());
            tv_doctor_specialist.setText(listDoctor.get(Integer.parseInt(str[0])).getDoctorInfo().getSpecializationInfo().getSpecializationDetails().get(0).getSpecializationName());
            tv_hospital.setText(listDoctor.get(Integer.parseInt(str[0])).getClinicInfo().getClinicName());
            tv_hospital_addr.setText(listDoctor.get(Integer.parseInt(str[0])).getClinicInfo().getAddress());
            String doctorTime = listDoctor.get(Integer.parseInt(str[0])).getDay() + " " + listDoctor.get(Integer.parseInt(str[0])).getStartTime() + " - "
                    +listDoctor.get(Integer.parseInt(str[0])).getEndTime();
            tv_doctor_time.setText(doctorName);

            //System.out.println("DoctorId:"+listDoctor.get(Integer.parseInt(str[0])).get_id());
        }else if (str[1].equalsIgnoreCase(Config.DOCTOR_LIST_BY_NAME)) {
            saveDataPreference(Config.DOCTOR_ID,listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorInfo().get_id());
            String doctorName = listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorInfo().getFirstName() + " " +
                    listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorInfo().getLastName();
            tv_doctor_name.setText(doctorName);
            tv_doctor_degree.setText(listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorInfo().getDegree());
            tv_doctor_specialist.setText(listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorInfo().getSpecializationInfo().getSpecializationDetails().get(0).getSpecializationName());
            tv_hospital.setText(listDoctorbyname.get(Integer.parseInt(str[0])).getClinicInfo().getClinicName());
            tv_hospital_addr.setText(listDoctorbyname.get(Integer.parseInt(str[0])).getClinicInfo().getAddress());
            String doctorTime = listDoctorbyname.get(Integer.parseInt(str[0])).getDay() + " "
                    + listDoctorbyname.get(Integer.parseInt(str[0])).getStartTime() + " - "
                    +listDoctorbyname.get(Integer.parseInt(str[0])).getEndTime();
            tv_doctor_time.setText(doctorTime);
            //System.out.println("DoctorId"+listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorInfo().get_id());
        }
    }

    @Override
    public void onStateReturn(String position) {
        String str[] = position.split("-");
        System.out.println("str[2]:"+str[2]);
        if (str[2].equalsIgnoreCase(Config.SPECIALIZATION)) {
            String selectedSpecialization =" " + listSpecialization.get(Integer.parseInt(str[1])).getSpecializationInfo().get(Integer.parseInt(str[1])).getSpecializationName();
            selectedSpecializationId = ""+listSpecialization.get(Integer.parseInt(str[1])).getSpecializationInfo().get(Integer.parseInt(str[1])).get_id();
            tv_specialization.setText(selectedSpecialization);
            //saveUserPreference(Config.SPECIALIZATION,selectedSpecializationId);
            //information = stateDB[0]+","+selectedLanguageId;
            //tv_zila.setText(getString(R.string.select_zila));
            //tv_upazila.setText(getString(R.string.select_upazila));
            //tv_union.setText(getString(R.string.select_union));
        }
        if (str[2].equalsIgnoreCase(Config.CLINIC)) {
            String selectedClinic = " " + listClinic.get(Integer.parseInt(str[1])).getClinicId().getClinicName();
            selectedClinicId = ""+listClinic.get(Integer.parseInt(str[1])).getClinicId().get_id();
            tv_clinic.setText(selectedClinic);
        }
        if (str[2].equalsIgnoreCase(Config.DAYLIST)) {
            selectedDay = " " + listDay.get(Integer.parseInt(str[1])).getDay();
            selectedDayId = ""+listDay.get(Integer.parseInt(str[1])).get_id();
            tv_day.setText(selectedDay);
        }
        if (str[2].equalsIgnoreCase(Config.DISTRICTLIST)) {
            selectedDistrict =" " + listDistrict.get(Integer.parseInt(str[1])).getDistrictName();
            selectedDistrictId = ""+listDistrict.get(Integer.parseInt(str[1])).get_id();
            tv_district.setText(selectedDistrict);
            //System.out.println("selectedDistrictId:"+selectedDistrictId);
        }
        if (str[2].equalsIgnoreCase(Config.CITYLIST)) {
            selectedCity =" " + listCity.get(Integer.parseInt(str[1])).getCityName();
            selectedCityId = ""+listCity.get(Integer.parseInt(str[1])).get_id();
            tv_city.setText(selectedCity);
            //System.out.println("selectedCityId:"+selectedCityId);
        }
    }

    @Override
    public void onResponseFailure() {
        super.onResponseFailure();
        pb_loader.setVisibility(View.GONE);
        createSnackBar(col_holder, getString(R.string.please_try_again));
    }

    @Override
    public void onFailure(ReturnResponse response) {
        super.onFailure(response);
        pb_loader.setVisibility(View.GONE);
        createSnackBar(col_holder, response.getMsg());
    }

    @Override
    public void onSuccess(Object response, String which_method) {
        super.onSuccess(response, which_method);

        if (which_method.equalsIgnoreCase("getDoctorListbByNameData")) {
            pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.doctorlistbyname.MSG> res = (Response<com.inqube.aamarmedic.model.doctorlistbyname.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            if (res.body()!=null)
            {
                listDoctorbyname = res.body().getResult().getDoc();

                DoctorListByNameDialogBox d = new DoctorListByNameDialogBox(DoctorAppoinmentActivity.this,listDoctorbyname,this,Config.DOCTOR_LIST_BY_NAME);
                d.show();
            }
        }

        if (which_method.equalsIgnoreCase("getSpecializationData")) {
            pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.specializationlist.MSG> res = (Response<MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            if (res.body()!=null)
            {
                listSpecialization = res.body().getResult().getSpec();

                tv_specialization.setEnabled(true);

                al_dialog_list = new ArrayList<>();
                for (int i = 0; i < listSpecialization.size(); i++) {
                    BaseModelClass bm = new BaseModelClass("" + listSpecialization.get(i).get_id(), listSpecialization.get(i).getSpecializationInfo().get(i).getSpecializationName(), "0");
                    al_dialog_list.add(bm);
                }

                if (al_dialog_list.size() > 0) {
                    StateDailog stateDailog = new StateDailog(DoctorAppoinmentActivity.this, al_dialog_list, this, Config.SPECIALIZATION);
                    stateDailog.show();
                }
            }
        }
        if (which_method.equalsIgnoreCase("getClinicData")) {
            pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.clinic.MSG> res = (Response<com.inqube.aamarmedic.model.clinic.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            if (res.body()!=null)
            {
                listClinic = res.body().getResult();

                tv_clinic.setEnabled(true);

                al_dialog_list = new ArrayList<>();
                for (int i = 0; i < listClinic.size(); i++) {
                    BaseModelClass bm = new BaseModelClass("" + listClinic.get(i).getClinicId().get_id(), listClinic.get(i).getClinicId().getClinicName(), "0");
                    al_dialog_list.add(bm);
                }

                if (al_dialog_list.size() > 0) {
                    StateDailog stateDailog = new StateDailog(DoctorAppoinmentActivity.this, al_dialog_list, this, Config.CLINIC);
                    stateDailog.show();
                }
            }
        }
        if (which_method.equalsIgnoreCase("getDayListData")) {
            pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.daylist.MSG> res = (Response<com.inqube.aamarmedic.model.daylist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            if (res.body()!=null)
            {
                listDay = res.body().getResult();

                tv_day.setEnabled(true);

                al_dialog_list = new ArrayList<>();
                for (int i = 0; i < listDay.size(); i++) {
                    BaseModelClass bm = new BaseModelClass("" + listDay.get(i).get_id(), listDay.get(i).getDay(), "0");
                    al_dialog_list.add(bm);
                }

                if (al_dialog_list.size() > 0) {
                    StateDailog stateDailog = new StateDailog(DoctorAppoinmentActivity.this, al_dialog_list, this, Config.DAYLIST);
                    stateDailog.show();
                }
            }
        }

        if (which_method.equalsIgnoreCase("getDoctorListData")) {
            pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.doctorlist.MSG> res = (Response<com.inqube.aamarmedic.model.doctorlist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            if (res.body()!=null)
            {
                listDoctor = res.body().getResult();
           /* System.out.println("listsize:"+listDoctor.size());
            System.out.println(""+listDoctor.get(0).getDoctorId().getFirstName() + " " + listDoctor.get(0).getDoctorId().getLastName());
            System.out.println(""+listDoctor.get(0).getDoctorId().getDegree());
            System.out.println(""+listDoctor.get(0).getDoctorId().getSpecializationId().getSpecializationName());
            System.out.println(""+listDoctor.get(0).getDay() + " " + listDoctor.get(0).getStartTime() + " "
                    +listDoctor.get(0).getEndTime());*/

                DoctorDialogBox d = new DoctorDialogBox(DoctorAppoinmentActivity.this,listDoctor,this, Config.DOCTOR_LIST);
                d.show();
            }
        }

        if (which_method.equalsIgnoreCase("getDistrictData")) {
            pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.districtlist.MSG> res = (Response<com.inqube.aamarmedic.model.districtlist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            if (res.body()!=null)
            {
                listDistrict = res.body().getResult().getDistrict();

                tv_district.setEnabled(true);

                al_dialog_list = new ArrayList<>();
                for (int i = 0; i < listDistrict.size(); i++) {
                    BaseModelClass bm = new BaseModelClass("" + listDistrict.get(i).get_id(), listDistrict.get(i).getDistrictName(), "0");
                    al_dialog_list.add(bm);
                }

                if (al_dialog_list.size() > 0) {
                    StateDailog stateDailog = new StateDailog(DoctorAppoinmentActivity.this, al_dialog_list, this, Config.DISTRICTLIST);
                    stateDailog.show();
                }
            }
        }
        if (which_method.equalsIgnoreCase("getCityData")) {
            pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.citylist.MSG> res = (Response<com.inqube.aamarmedic.model.citylist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            if (res.body()!=null)
            {
                listCity = res.body().getResult().getCity();

                tv_city.setEnabled(true);

                al_dialog_list = new ArrayList<>();
                for (int i = 0; i < listCity.size(); i++) {
                    BaseModelClass bm = new BaseModelClass("" + listCity.get(i).get_id(), listCity.get(i).getCityName(), "0");
                    al_dialog_list.add(bm);
                }

                if (al_dialog_list.size() > 0) {
                    StateDailog stateDailog = new StateDailog(DoctorAppoinmentActivity.this, al_dialog_list, this, Config.CITYLIST);
                    stateDailog.show();
                }
            }
        }

        if (which_method.equalsIgnoreCase("getDoctorAppointmentData")) {
            pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.appointment.MSG> res = (Response<com.inqube.aamarmedic.model.appointment.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());

            //createSnackBar(col_holder, res.body().getMessage());

            //Creating the LayoutInflater instance
            LayoutInflater li = getLayoutInflater();
            //Getting the View object as defined in the customtoast.xml file
            View layout = li.inflate(R.layout.activity_appointment_book,(ViewGroup) findViewById(R.id.custom_toast_layout));

            //Creating the Toast object
            Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setView(layout);//setting the view of custom toast layout
            toast.show();

            startActivity(new Intent(DoctorAppoinmentActivity.this, DashboardActivity.class));
            finish();
        }
    }

    @Override
    public void onReturn(String position) {

    }


}
