package com.inqube.aamarmedic.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.adapter.SelectPatientBookingListDialogBoxAdapter;
import com.inqube.aamarmedic.model.agentbookinglist.Result;
import com.inqube.aamarmedic.util.AllInterfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentBookingListDetailsDialogBox extends Dialog implements
        AllInterfaces.AdapterCallback, View.OnClickListener {

    private Activity activity;
    private AllInterfaces.DialogCallback dialogCallback;
    private List<Result> list_items = new ArrayList<Result>();
    private String type;
    private TextView tv_patient_name, tv_patient_address,tv_patient_Mbno,tv_doc_name,tv_doctors_spec,tv_clinic_name,
            tv_doctor_time,tv_date_of_appointment,tv_registered_on,tv_remark;
    private Button btn_view_details_ok;
    private int position;

    public AgentBookingListDetailsDialogBox(Activity activity, List<Result> list_items, AllInterfaces.DialogCallback dialogCallback, String type, int position){
        super(activity);
        this.activity=activity;
        this.list_items =list_items;
        this.dialogCallback=dialogCallback;
        this.type = type;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.agentbooking_list_cell_dtls);

        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;
        window.setAttributes(attributesParams);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        tv_patient_name = findViewById(R.id.tv_patient_name);
        tv_patient_address = findViewById(R.id.tv_patient_address);
        tv_patient_Mbno = findViewById(R.id.tv_patient_Mbno);
        tv_doc_name = findViewById(R.id.tv_doc_name);
        tv_doctors_spec = findViewById(R.id.tv_doctors_spec);
        tv_clinic_name = findViewById(R.id.tv_clinic_name);
        tv_doctor_time = findViewById(R.id.tv_doctor_time);
        tv_date_of_appointment = findViewById(R.id.tv_date_of_appointment);
        tv_registered_on = findViewById(R.id.tv_registered_on);
        tv_remark = findViewById(R.id.tv_remark);

        btn_view_details_ok =findViewById(R.id.btn_view_details_ok);
        btn_view_details_ok.setOnClickListener(this);

        if (list_items.size()>0)
        {
            tv_patient_name.setText(list_items.get(position).getPatientName());
            tv_patient_address.setText(list_items.get(position).getAddress());

            if (list_items.get(position).getMobile()!=null)
            {
                tv_patient_Mbno.setText(list_items.get(position).getMobile());
            }

            if (list_items.get(position).getDoctorSchedule().getDoctorId().getFirstName()!=null)
            {
                tv_doc_name.setText(list_items.get(position).getDoctorSchedule().getDoctorId().getFirstName());
                if (list_items.get(position).getDoctorSchedule().getDoctorId().getLastName()!=null) {
                    tv_doc_name.setText(list_items.get(position).getDoctorSchedule().getDoctorId().getFirstName()+" "
                            + list_items.get(position).getDoctorSchedule().getDoctorId().getLastName());
                }
            }

            if (list_items.get(position).getDoctorSchedule().getDoctorId().getSpecializationId()!=null)
            {
                tv_doctors_spec.setText(list_items.get(position).getDoctorSchedule().getDoctorId().getSpecializationId().getSpecializationName());
            }

            if (list_items.get(position).getDoctorSchedule().getClinicId().getClinicName()!=null)
            {
                tv_clinic_name.setText(list_items.get(position).getDoctorSchedule().getClinicId().getClinicName());
            }

            if (list_items.get(position).getDoctorSchedule().getStartTime()!=null && list_items.get(position).getDoctorSchedule().getEndTime()!=null)
            {
                tv_doctor_time.setText(list_items.get(position).getDoctorSchedule().getStartTime() + " - " + list_items.get(position).getDoctorSchedule().getEndTime());
            }

            if (list_items.get(position).getDoctorSchedule().getDay()!=null)
            {
                tv_date_of_appointment.setText(list_items.get(position).getDoctorSchedule().getDay());
            }

            try
            {
                String time = list_items.get(position).getBookedBy().getCreatedAt();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");
                Date date = sdf.parse(time);
                sdf=new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
                String registeredOn=sdf.format(date.getTime());
                tv_registered_on.setText(registeredOn);
            }
            catch (ParseException ex)
            {
                ex.printStackTrace();
            }

            if (list_items.get(position).getRemark()!=null)
            {
                tv_remark.setText(list_items.get(position).getRemark());
            }
        }
    }

    @Override
    public void onReturn(String position) {
        dialogCallback.onDialogReturn(position+"-"+type);
        dismiss();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_view_details_ok:
                AgentBookingListDetailsDialogBox.this.dismiss();
                break;
        }
    }
}
