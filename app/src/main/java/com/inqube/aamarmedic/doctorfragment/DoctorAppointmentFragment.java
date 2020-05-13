package com.inqube.aamarmedic.doctorfragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.gson.Gson;
import com.inqube.aamarmedic.DashboardActivity;
import com.inqube.aamarmedic.DoctorAppoinmentActivity;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.agentfragment.AgentDashboardFragment;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseFragment;
import com.inqube.aamarmedic.dialog.DialogBox;
import com.inqube.aamarmedic.dialog.DoctorDialogBox;
import com.inqube.aamarmedic.dialog.DoctorListByNameDialogBox;
import com.inqube.aamarmedic.dialog.StateDailog;
import com.inqube.aamarmedic.model.baseModel.BaseModelClass;
import com.inqube.aamarmedic.model.doctorlistbyname.MSG;
import com.inqube.aamarmedic.model.specializationlist.Spec;
import com.inqube.aamarmedic.telehealthfragment.PersonalFragment;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;
import com.inqube.aamarmedic.util.UtilClass;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Response;

public class DoctorAppointmentFragment extends BaseFragment implements AllInterfaces.RetrofitResponseToActivityOrFrament,
        AllInterfaces.AdapterCallback, View.OnClickListener, AllInterfaces.DialogCallback,
        AllInterfaces.StateCallback {

    private View view;
    ArrayList<BaseModelClass> all_doctor_list;
    ArrayList<BaseModelClass> al_dialog_list;
    private List<com.inqube.aamarmedic.model.specializationlist.Spec> listSpecialization;
    private List<com.inqube.aamarmedic.model.clinic.Result> listClinic;
    private List<com.inqube.aamarmedic.model.daylist.Result> listDay;
    private List<com.inqube.aamarmedic.model.doctorlist.Result> listDoctor;
    private List<com.inqube.aamarmedic.model.doctorlistbyname.Result> listDoctorbyname;
    private List<com.inqube.aamarmedic.model.districtlist.District> listDistrict;
    private List<com.inqube.aamarmedic.model.citylist.City> listCity;
    private List<com.inqube.aamarmedic.model.appointment.Result> listAppointment;

    private TextView tv_specialization, tv_clinic, tv_day, tv_doctor_name, tv_doctor_degree, tv_doctor_specialist, tv_hospital, tv_hospital_addr, tv_doctor_time,
            tv_district, tv_city, tv_menu_title,tvAppointmentDate;

    private Button btnShowDoctor, btnSubmit;
    private EditText edt_search, edt_patientname, edt_mobileno, edt_address, edt_pinno, edt_remarks;
    private LinearLayout ll_doctor_info;
    private ImageView imv_home, imv_doc, imv_menu;
    final static int mobilenoMaxLen = 10, pincodeMaxLen = 6;

    private String selectedSpecializationId, selectedClinicId, selectedDayId, selectedDay, selectDay, selectedDistrict, selectedDistrictId,
            selectedCity, selectedCityId,selectAnyClinic="Any";

    private boolean resume;

    private OnAamarMedicDoctorAppointmentFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_doctor, container, false);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        super.onResume();
        if (!resume) {
            ((BaseActivity) getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);
            try {
                setUI(view);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        resume = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUI(View view) throws ParseException {

        tv_menu_title = (TextView)((BaseActivity)getActivity()).findViewById(R.id.tv_menu_title);
        tv_menu_title.setText(getString(R.string.find_doctor));

        edt_search = (EditText) view.findViewById(R.id.edt_search);

        imv_doc = (ImageView) view.findViewById(R.id.imv_doc);

        tv_specialization = (TextView) view.findViewById(R.id.tv_specialization);
        tv_clinic = (TextView) view.findViewById(R.id.tv_clinic);
        tv_day = (TextView) view.findViewById(R.id.tv_day);

        tv_doctor_name = (TextView) view.findViewById(R.id.tv_doctor_name);
        tv_doctor_degree = (TextView) view.findViewById(R.id.tv_doctor_degree);
        tv_doctor_specialist = (TextView) view.findViewById(R.id.tv_doctor_specialist);
        tv_hospital = (TextView) view.findViewById(R.id.tv_doctor_hospitalname);
        tv_hospital_addr = (TextView) view.findViewById(R.id.tv_hospital_addr);
        tv_doctor_time = (TextView) view.findViewById(R.id.tv_doctor_time);

        edt_patientname = (EditText) view.findViewById(R.id.edt_patientname);
        edt_mobileno = (EditText) view.findViewById(R.id.edt_mobileno);
        edt_address = (EditText) view.findViewById(R.id.edt_address);
        edt_pinno = (EditText) view.findViewById(R.id.edt_pinno);
        tv_district = (TextView) view.findViewById(R.id.tv_district);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        edt_remarks = (EditText) view.findViewById(R.id.edt_remarks);
        //tvAppointmentDate= (TextView)view.findViewById(R.id.tvAppointmentDate);

        btnShowDoctor = (Button) view.findViewById(R.id.btnShowDoctors);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        ll_doctor_info = (LinearLayout) view.findViewById(R.id.ll_doctor_info);
        ll_doctor_info.setVisibility(View.GONE);

        imv_home = (ImageView) ((BaseActivity) getActivity()).findViewById(R.id.imv_home);
        imv_home.setVisibility(View.VISIBLE);

        imv_menu = (ImageView) ((BaseActivity) getActivity()).findViewById(R.id.imv_menu);
        imv_menu.setVisibility(View.GONE);

        edt_search.setOnClickListener(this);

        tv_specialization.setOnClickListener(this);
        tv_clinic.setOnClickListener(this);
        tv_day.setOnClickListener(this);
        tv_district.setOnClickListener(this);
        tv_city.setOnClickListener(this);

        btnShowDoctor.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        imv_home.setOnClickListener(this);
        //tvAppointmentDate.setOnClickListener(this);

        edt_search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if (edt_search.getText().toString().trim().length() < 1) {
                    mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.please_provide_doctor_name));
                    return false;
                } else {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        if (((BaseActivity) getActivity()).isDeviceOnline()) {
                            ((BaseActivity) getActivity()).pb_loader.setVisibility(View.VISIBLE);
                            //System.out.println("token:" + getUserPreference(Config.AUTH_TOKEN,""));
                            tv_specialization.setText("");
                            tv_clinic.setText("");
                            tv_day.setText("");

                            UtilClass.getInstance().getDoctorListbByNameData(((BaseActivity) getActivity()), DoctorAppointmentFragment.this,
                                    edt_search.getText().toString(), ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                        } else {
                            mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                            return false;
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        edt_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (edt_search.getText().toString().trim().length() < 1) {
                        mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.please_provide_doctor_name));
                        return false;
                    } else {
                        if (event.getRawX() >= (edt_search.getRight() - edt_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                            if (((BaseActivity) getActivity()).isDeviceOnline()) {
                                ((BaseActivity) getActivity()).pb_loader.setVisibility(View.VISIBLE);

                                //System.out.println("token:" + getUserPreference(Config.AUTH_TOKEN,""));
                                tv_specialization.setText("");
                                tv_clinic.setText("");
                                tv_day.setText("");

                                UtilClass.getInstance().getDoctorListbByNameData(((BaseActivity) getActivity()), DoctorAppointmentFragment.this,
                                        edt_search.getText().toString(), ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                            } else {
                                mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                            }
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        if (!((BaseActivity) getActivity()).isDeviceOnline()) {
            mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
        }

        /*String inputStart = "01/01/2020";
        String inputStop = "12/31/2020";  // 258 Fridays.
        // String inputStop = "01/01/2009";  // 0 Friday.
        // String inputStop = "01/02/2009";  // 1 Friday.

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "MM/dd/yyyy" );

        LocalDate start = LocalDate.parse ( inputStart , formatter );
        LocalDate stop = LocalDate.parse ( inputStop , formatter );

        List<LocalDate> fridays = new ArrayList<> ();  // Collect each Friday found.
        LocalDate nextOrSameFriday = start.with ( TemporalAdjusters.nextOrSame ( DayOfWeek.FRIDAY ) );
        // Loop while we have a friday in hand (non-null) AND that friday is not after our stop date (isBefore or isEqual the stop date).
        while ( ( null != nextOrSameFriday ) & (  ! nextOrSameFriday.isAfter ( stop ) ) ) {
            fridays.add ( nextOrSameFriday );  //  Remember this friday.
            nextOrSameFriday = nextOrSameFriday.plusWeeks ( 1 );  // Move to the next Friday, setting up for next iteration of this loop.
        }
        System.out.println("nextOrSameFriday"+nextOrSameFriday);*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAamarMedicDoctorAppointmentFragmentInteractionListener) {
            mListener = (OnAamarMedicDoctorAppointmentFragmentInteractionListener) context;
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
            case R.id.tv_specialization:
                if (((BaseActivity) getActivity()).isDeviceOnline()) {
                    ((BaseActivity) getActivity()).pb_loader.setVisibility(View.VISIBLE);

                    //System.out.println("token:" + ((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN,""));

                    UtilClass.getInstance().getSpecializationData(((BaseActivity) getActivity()), DoctorAppointmentFragment.this,
                            ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""),
                            ((BaseActivity) getActivity()).getUserPreference(Config.LANGUAGE_ID, ""));
                } else {
                    mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                }
                break;
            case R.id.tv_clinic:
                ((BaseActivity) getActivity()).pb_loader.setVisibility(View.VISIBLE);

                // Checking validity whether user provided all required parameter or not
                if (tv_specialization.getText().length() < 1) {
                    mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.please_select_specialisation));
                    ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
                    break;
                }

                if (((BaseActivity) getActivity()).isDeviceOnline()) {
                    //API Calling
                    UtilClass.getInstance().getClinicData(((BaseActivity) getActivity()), DoctorAppointmentFragment.this,
                            selectedSpecializationId, ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                } else {
                    mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                }
                //}
                break;
            case R.id.tv_day:
                ((BaseActivity) getActivity()).pb_loader.setVisibility(View.VISIBLE);
                // Checking validity whether user provided all required parameter or not
                if (tv_specialization.getText().length() < 1) {
                    mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.please_select_specialisation));
                    ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
                    break;
                } else if (tv_clinic.getText().toString().length() < 1) {
                    mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.please_select_clinic));
                    ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
                    break;
                }

                //System.out.println("token:" + ((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN,""));
                //System.out.println("Specialization:" + selectedSpecializationId);

                if (((BaseActivity) getActivity()).isDeviceOnline()) {
                    //API Calling
                    UtilClass.getInstance().getDayListData(((BaseActivity) getActivity()), DoctorAppointmentFragment.this,
                            selectedSpecializationId, selectedClinicId, ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                } else {
                    mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                }
                //}
                break;
            case R.id.btnShowDoctors:
                //((BaseActivity)getActivity()).pb_loader.setVisibility(View.VISIBLE);
                edt_search.setText("");

                /*//Creating the LayoutInflater instance
                LayoutInflater li = getLayoutInflater();
                //Getting the View object as defined in the customtoast.xml file
                View layout = li.inflate(R.layout.activity_appointment_book, view.findViewById(R.id.ctl_appointment_book));

                //Creating the Toast object
                TextView tv_toast_msg = (TextView) layout.findViewById(R.id.tv_toast_msg);
                tv_toast_msg.setText(getString(R.string.appointment_book));
                Toast toast = new Toast(getActivity().getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setView(layout);//setting the view of custom toast layout
                toast.show();*/

                // Checking validity whether user provided all required parameter or not
                if ((tv_specialization.getText().length() < 1) &&
                        (tv_clinic.getText().length() < 1) &&
                        (tv_day.getText().length() < 1)) {
                    ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
                    mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.Please_select_atleast_one));
                    break;
                } else {

                    if (selectedClinicId==null)
                    {
                        selectedClinicId="";
                    }

                    selectDay = "";
                    if (selectedDay != null) {
                        selectDay = selectedDay.toLowerCase();
                    }

                    System.out.println("spec_id:" + selectedSpecializationId);
                    System.out.println("clinic_id:" + selectedClinicId);
                    System.out.println("day:" + selectDay);
                    System.out.println("token:" + ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                    System.out.println("LANGUAGE_ID:" + ((BaseActivity) getActivity()).getUserPreference(Config.LANGUAGE_ID, ""));

                    if (((BaseActivity) getActivity()).isDeviceOnline()) {
                        UtilClass.getInstance().getDoctorListData(((BaseActivity) getActivity()), DoctorAppointmentFragment.this,
                                selectedSpecializationId, selectedClinicId, selectDay, ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""),
                                ((BaseActivity) getActivity()).getUserPreference(Config.LANGUAGE_ID, ""));
                    } else {
                        mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                    }
                }
                break;
            case R.id.tv_district:
                ((BaseActivity) getActivity()).pb_loader.setVisibility(View.VISIBLE);
                if (((BaseActivity) getActivity()).isDeviceOnline()) {
                    UtilClass.getInstance().getDistrictData(((BaseActivity) getActivity()), DoctorAppointmentFragment.this,
                            ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                } else {
                    mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                }
                break;
            case R.id.tv_city:
                ((BaseActivity) getActivity()).pb_loader.setVisibility(View.VISIBLE);
                if (((BaseActivity) getActivity()).isDeviceOnline()) {
                    UtilClass.getInstance().getCityData(((BaseActivity) getActivity()), DoctorAppointmentFragment.this,
                            selectedDistrictId, ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                } else {
                    mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                }
                break;
            case R.id.btnSubmit:
                if (emptyCheckValidation() == false) {
                    if (((BaseActivity) getActivity()).isDeviceOnline()) {
                        //API Calling
                        UtilClass.getInstance().getDoctorAppointmentData(((BaseActivity) getActivity()), DoctorAppointmentFragment.this,
                                ((BaseActivity) getActivity()).getDataPreference(Config.DOCTOR_ID, ""), edt_patientname.getText().toString(), edt_mobileno.getText().toString(),
                                edt_address.getText().toString(), edt_pinno.getText().toString(), selectedDistrictId, selectedCityId, edt_remarks.getText().toString(),
                                ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                    } else {
                        mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                    }
                }
                break;
            case R.id.imv_home:
                if (getActivity().getSupportFragmentManager() != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fm_container, new AgentDashboardFragment(), "AgentDashboardFragment")
                            .addToBackStack("AgentDashboardFragment")
                            .commit();
                }
                break;
            /*case R.id.tvAppointmentDate:
                String start = "01/01/2020";
                String end = "12/01/2021";
                SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                Calendar scal=Calendar.getInstance();
                try {
                    scal.setTime(dateFormat.parse(start));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar ecal=Calendar.getInstance();
                try {
                    ecal.setTime(dateFormat.parse(end));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ArrayList<Date> fridayDates=new ArrayList<>();

                while(!scal.equals(ecal)){
                    scal.add(Calendar.DATE, 1);
                    if(scal.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY){
                        fridayDates.add(scal.getTime());
                    }
                }



                //datePicker.setSelectableDays(Calendar[] days)
                *//*DatePickerDialog dpDialog=new DatePickerDialog(activty, listener, mYear, mMonth, mDay);
                dpDialog.show();*//*
                UtilClass.getInstance().dateDialog(tvAppointmentDate, getActivity());
                break;*/
        }
    }

    private boolean emptyCheckValidation() {
        if (edt_patientname.getText().length() < 1) {
            mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.Please_enter_the_patient_name));
            return true;
        } else if ((edt_mobileno.getText().length() < mobilenoMaxLen)||(edt_mobileno.getText().length() > mobilenoMaxLen)) {
            mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.Please_enter_tendigit_mb));
            return true;
        } else if (edt_address.getText().length() < 1) {
            mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.Please_enter_address));
            return true;
        } else if ((edt_pinno.getText().length() < pincodeMaxLen)|| (edt_pinno.getText().length() > pincodeMaxLen)) {
            mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.Please_enter_PINCode));
            return true;
        } else if (tv_district.getText().length() < 1) {
            mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.Please_enter_district));
            return true;
        } else if (tv_city.getText().length() < 1) {
            mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.Please_enter_city));
            return true;
        } else if (edt_remarks.getText().length() < 1) {
            mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.Please_enter_remarks));
            return true;
        }
        return false;
    }

    @Override
    public void onDialogReturn(String position) {
        String str[] = position.split("-");
        ll_doctor_info.setVisibility(View.VISIBLE);

        if (str[1].equalsIgnoreCase(Config.DOCTOR_LIST)) {
            ((BaseActivity) getActivity()).saveDataPreference(Config.DOCTOR_ID, listDoctor.get(Integer.parseInt(str[0])).get_id());
            tv_doctor_name.setText("" + listDoctor.get(Integer.parseInt(str[0])).getDoctorInfo().getFirstName() + " " +
                    listDoctor.get(Integer.parseInt(str[0])).getDoctorInfo().getLastName());
            tv_doctor_degree.setText("" + listDoctor.get(Integer.parseInt(str[0])).getDoctorInfo().getDegree());
            tv_doctor_specialist.setText(" " + listDoctor.get(Integer.parseInt(str[0])).getDoctorInfo().getSpecializationInfo().getSpecializationDetails().get(0).getSpecializationName());
            tv_hospital.setText("" + listDoctor.get(Integer.parseInt(str[0])).getClinicInfo().getClinicName());
            tv_hospital_addr.setText("" + listDoctor.get(Integer.parseInt(str[0])).getClinicInfo().getAddress());
            tv_doctor_time.setText("" + listDoctor.get(Integer.parseInt(str[0])).getDay() + " " + listDoctor.get(Integer.parseInt(str[0])).getStartTime() + " - "
                    + listDoctor.get(Integer.parseInt(str[0])).getEndTime());
            if (listDoctor.get(Integer.parseInt(str[0])).getDoctorInfo().getImageUrl().length() > 0) {
                Picasso.get().load(listDoctor.get(Integer.parseInt(str[0])).getDoctorInfo().getImageUrl()).into(imv_doc);
            } else {
                imv_doc.setImageResource(R.drawable.dr_placeholder);
            }
            //System.out.println("DoctorId:"+listDoctor.get(Integer.parseInt(str[0])).get_id());
        } else if (str[1].equalsIgnoreCase(Config.DOCTOR_LIST_BY_NAME)) {
            ((BaseActivity) getActivity()).saveDataPreference(Config.DOCTOR_ID, listDoctorbyname.get(Integer.parseInt(str[0])).get_id());
            tv_doctor_name.setText("" + listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorId().getFirstName() + " " + listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorId().getLastName());
            tv_doctor_degree.setText("" + listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorId().getDegree());
            tv_doctor_specialist.setText(" " + listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorId().getSpecializationId().getSpecializationName());
            tv_hospital.setText("" + listDoctorbyname.get(Integer.parseInt(str[0])).getClinicId().getClinicName());
            tv_hospital_addr.setText("" + listDoctorbyname.get(Integer.parseInt(str[0])).getClinicId().getAddress());
            tv_doctor_time.setText("" + listDoctorbyname.get(Integer.parseInt(str[0])).getDay() + " " + listDoctorbyname.get(Integer.parseInt(str[0])).getStartTime() + " - "
                    + listDoctorbyname.get(Integer.parseInt(str[0])).getEndTime());
            if (listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorId().getImageUrl().length() > 0) {
                Picasso.get().load(listDoctorbyname.get(Integer.parseInt(str[0])).getDoctorId().getImageUrl()).into(imv_doc);
            } else {
                imv_doc.setImageResource(R.drawable.dr_placeholder);
            }
            //System.out.println("DoctorId"+listDoctorbyname.get(Integer.parseInt(str[0])).get_id());
        }
    }

    @Override
    public void onStateReturn(String position) {
        String str[] = position.split("-");
        //System.out.println("str[2]:"+str[2]);
        if (str[2].equalsIgnoreCase(Config.SPECIALIZATION)) {
            String selectedSpecialization = listSpecialization.get(Integer.parseInt(str[1])).getSpecializationInfo().get(0).getSpecializationName();
            selectedSpecializationId = "" + listSpecialization.get(Integer.parseInt(str[1])).getSpecializationInfo().get(0).getSpecializationId();
            tv_specialization.setText(" " + selectedSpecialization);
            //saveUserPreference(Config.SPECIALIZATION,selectedSpecializationId);
            //information = stateDB[0]+","+selectedLanguageId;
            //tv_zila.setText(getString(R.string.select_zila));
            //tv_upazila.setText(getString(R.string.select_upazila));
            //tv_union.setText(getString(R.string.select_union));
            tv_clinic.setText(" " + getString(R.string.Select_Clinic));
            tv_day.setText(" " + getString(R.string.Select_Day));
        }
        if (str[2].equalsIgnoreCase(Config.CLINIC)) {
            String selectedClinic = listClinic.get(Integer.parseInt(str[1])).getClinicId().getClinicName();
            selectedClinicId = "" + listClinic.get(Integer.parseInt(str[1])).getClinicId().get_id();
            if (selectedClinicId.equalsIgnoreCase("0") || selectedClinic.equalsIgnoreCase(selectAnyClinic))
            {
                selectedClinicId="";
                selectedDay="";
                selectedDayId="";
                selectDay="";
            }
            tv_clinic.setText(" " + selectedClinic);
            tv_day.setText(" " + getString(R.string.Select_Day));
        }
        if (str[2].equalsIgnoreCase(Config.DAYLIST)) {
            selectedDay = listDay.get(Integer.parseInt(str[1])).getDay();
            selectedDayId = "" + listDay.get(Integer.parseInt(str[1])).get_id();
            tv_day.setText(" " + selectedDay);
        }
        if (str[2].equalsIgnoreCase(Config.DISTRICTLIST)) {
            selectedDistrict = listDistrict.get(Integer.parseInt(str[1])).getDistrictName();
            selectedDistrictId = "" + listDistrict.get(Integer.parseInt(str[1])).get_id();
            tv_district.setText(" " + selectedDistrict);
            tv_city.setText(" " + getString(R.string.Select_City));
            //System.out.println("selectedDistrictId:"+selectedDistrictId);
        }
        if (str[2].equalsIgnoreCase(Config.CITYLIST)) {
            selectedCity = listCity.get(Integer.parseInt(str[1])).getCityName();
            selectedCityId = "" + listCity.get(Integer.parseInt(str[1])).get_id();
            tv_city.setText(" " + selectedCity);
            //System.out.println("selectedCityId:"+selectedCityId);
        }
    }

    @Override
    public void onSuccess(Object response, String which_method) {
        if (which_method.equalsIgnoreCase("getDoctorListbByNameData")) {
            ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
            Response<MSG> res = (Response<com.inqube.aamarmedic.model.doctorlistbyname.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            listDoctorbyname = res.body().getResult();
            if (listDoctorbyname.size() > 0) {
                DoctorListByNameDialogBox d = new DoctorListByNameDialogBox(((BaseActivity) getActivity()), listDoctorbyname, this, Config.DOCTOR_LIST_BY_NAME);
                d.show();
            } else {
                mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.please_try_again));
            }
        }

        if (which_method.equalsIgnoreCase("getSpecializationData")) {
            ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.specializationlist.MSG> res = (Response<com.inqube.aamarmedic.model.specializationlist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            listSpecialization = res.body().getResult().getSpec();

            tv_specialization.setEnabled(true);

            System.out.println("listSpecialization.size(): "+listSpecialization.size());
            al_dialog_list = new ArrayList<>();
            for (int i = 0; i < listSpecialization.size(); i++) {
                BaseModelClass bm = new BaseModelClass("" + listSpecialization.get(i).getSpecializationInfo().get(0).getSpecializationId(),
                        listSpecialization.get(i).getSpecializationInfo().get(0).getSpecializationName(), "0");
                al_dialog_list.add(bm);
            }

            if (al_dialog_list.size() > 0) {
                StateDailog stateDailog = new StateDailog(((BaseActivity) getActivity()), al_dialog_list, this, Config.SPECIALIZATION);
                stateDailog.show();
            }
        }
        if (which_method.equalsIgnoreCase("getClinicData")) {
            ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.clinic.MSG> res = (Response<com.inqube.aamarmedic.model.clinic.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            listClinic = res.body().getResult();

            tv_clinic.setEnabled(true);

            System.out.println("listClinic.size():"+listClinic.size());

            al_dialog_list = new ArrayList<>();

            com.inqube.aamarmedic.model.clinic.Result r = new com.inqube.aamarmedic.model.clinic.Result();
            com.inqube.aamarmedic.model.clinic.ClinicId c =new com.inqube.aamarmedic.model.clinic.ClinicId();
            c.set_id("0");
            c.setClinicName(selectAnyClinic);
            r.setClinicId(c);
            listClinic.add(0,r);

            for (int i = 0; i < listClinic.size(); i++) {
                BaseModelClass bm = new BaseModelClass("" + listClinic.get(i).getClinicId().get_id(), listClinic.get(i).getClinicId().getClinicName(), "0");
                al_dialog_list.add(bm);
            }

            if (al_dialog_list.size() > 0) {
                StateDailog stateDailog = new StateDailog(((BaseActivity) getActivity()), al_dialog_list, this, Config.CLINIC);
                stateDailog.show();
            }
        }
        if (which_method.equalsIgnoreCase("getDayListData")) {
            ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.daylist.MSG> res = (Response<com.inqube.aamarmedic.model.daylist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            listDay = res.body().getResult();

            tv_day.setEnabled(true);

            al_dialog_list = new ArrayList<>();
            for (int i = 0; i < listDay.size(); i++) {
                BaseModelClass bm = new BaseModelClass("" + listDay.get(i).get_id(), listDay.get(i).getDay(), "0");
                al_dialog_list.add(bm);
            }

            if (al_dialog_list.size() > 0) {
                StateDailog stateDailog = new StateDailog(((BaseActivity) getActivity()), al_dialog_list, this, Config.DAYLIST);
                stateDailog.show();
            }
        }

        if (which_method.equalsIgnoreCase("getDoctorListData")) {
            ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.doctorlist.MSG> res = (Response<com.inqube.aamarmedic.model.doctorlist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            listDoctor = res.body().getResult();

            if (listDoctor.size() > 0) {
                DoctorDialogBox d = new DoctorDialogBox(((BaseActivity) getActivity()), listDoctor, this, Config.DOCTOR_LIST);
                d.show();
            } else {
                mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.please_try_again));
            }
        }

        if (which_method.equalsIgnoreCase("getDistrictData")) {
            ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.districtlist.MSG> res = (Response<com.inqube.aamarmedic.model.districtlist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            listDistrict = res.body().getResult().getDistrict();

            tv_district.setEnabled(true);

            al_dialog_list = new ArrayList<>();
            for (int i = 0; i < listDistrict.size(); i++) {
                BaseModelClass bm = new BaseModelClass("" + listDistrict.get(i).get_id(), listDistrict.get(i).getDistrictName(), "0");
                al_dialog_list.add(bm);
            }

            if (al_dialog_list.size() > 0) {
                StateDailog stateDailog = new StateDailog(((BaseActivity) getActivity()), al_dialog_list, this, Config.DISTRICTLIST);
                stateDailog.show();
            }
        }
        if (which_method.equalsIgnoreCase("getCityData")) {
            ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.citylist.MSG> res = (Response<com.inqube.aamarmedic.model.citylist.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            listCity = res.body().getResult().getCity();

            tv_city.setEnabled(true);

            al_dialog_list = new ArrayList<>();
            for (int i = 0; i < listCity.size(); i++) {
                BaseModelClass bm = new BaseModelClass("" + listCity.get(i).get_id(), listCity.get(i).getCityName(), "0");
                al_dialog_list.add(bm);
            }

            if (al_dialog_list.size() > 0) {
                StateDailog stateDailog = new StateDailog(((BaseActivity) getActivity()), al_dialog_list, this, Config.CITYLIST);
                stateDailog.show();
            }
        }

        if (which_method.equalsIgnoreCase("getDoctorAppointmentData")) {
            ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
            Response<com.inqube.aamarmedic.model.appointment.MSG> res = (Response<com.inqube.aamarmedic.model.appointment.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());

            //createSnackBar(col_holder, res.body().getMessage());

            //Creating the LayoutInflater instance
            LayoutInflater li = getLayoutInflater();
            //Getting the View object as defined in the customtoast.xml file
            View layout = li.inflate(R.layout.activity_appointment_book, view.findViewById(R.id.ctl_appointment_book));

            //Creating the Toast object
            TextView tv_toast_msg = (TextView) layout.findViewById(R.id.tv_toast_msg);
            tv_toast_msg.setText(getString(R.string.appointment_book));
            Toast toast = new Toast(getActivity().getApplicationContext());
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setView(layout);//setting the view of custom toast layout
            toast.show();

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fm_container, new AgentDashboardFragment(), "AgentDashboardFragment")
                    .addToBackStack("AgentDashboardFragment")
                    .commit();
        }
    }

    @Override
    public void onFailure(ReturnResponse response) {
        btnShowDoctor.setEnabled(true);
        btnSubmit.setEnabled(true);
        mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(response.getMsg());
    }

    @Override
    public void onResponseFailure() {
        btnShowDoctor.setEnabled(true);
        btnSubmit.setEnabled(true);
        mListener.onAamarMedicDoctorAppointmentFragmentInteractionListener(getString(R.string.please_try_again));
    }

    @Override
    public void onResponseFailure(String msg) {

    }

    @Override
    public void onReturn(String position) {

    }

    public interface OnAamarMedicDoctorAppointmentFragmentInteractionListener {
        void onAamarMedicDoctorAppointmentFragmentInteractionListener();

        void onAamarMedicDoctorAppointmentFragmentInteractionListener(String msg);
    }
}
