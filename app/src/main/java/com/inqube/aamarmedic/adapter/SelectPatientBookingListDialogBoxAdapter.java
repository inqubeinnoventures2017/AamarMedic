package com.inqube.aamarmedic.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.dialog.AgentBookingListDetailsDialogBox;
import com.inqube.aamarmedic.dialog.AgentBookingListDialogBox;
import com.inqube.aamarmedic.model.agentbookinglist.Result;
import com.inqube.aamarmedic.util.AllInterfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SelectPatientBookingListDialogBoxAdapter extends RecyclerView.Adapter<SelectPatientBookingListDialogBoxAdapter.RecyclerViewHolder> {
    private Activity activity;
    private List<com.inqube.aamarmedic.model.agentbookinglist.Result> list_booking;
    private AllInterfaces.AdapterCallback ad_callback;
    private AllInterfaces.DialogCallback dialogCallback;
    private AllInterfaces.DialogCallback dialogCallback_dtls;

    public SelectPatientBookingListDialogBoxAdapter(Activity activity, List<Result> list_booking, AllInterfaces.AdapterCallback ad_callback,
                                                    AllInterfaces.DialogCallback dialogCallback) {
        this.activity = activity;
        this.list_booking = list_booking;
        this.ad_callback = ad_callback;
        this.dialogCallback = dialogCallback;
    }

    @Override
    public SelectPatientBookingListDialogBoxAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        SelectPatientBookingListDialogBoxAdapter.RecyclerViewHolder recyclerViewHolder;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agentbooking_list_cell, parent, false);
        recyclerViewHolder = new SelectPatientBookingListDialogBoxAdapter.RecyclerViewHolder(view, activity);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectPatientBookingListDialogBoxAdapter.RecyclerViewHolder holder, int position) {
        if (list_booking.get(position).get_id() != null) {
            holder.tv_patient_name.setText("" + list_booking.get(position).getPatientName());
            holder.tv_doctors_name.setText("" + list_booking.get(position).getDoctorSchedule().getDoctorId().getFirstName() + " " +
                    list_booking.get(position).getDoctorSchedule().getDoctorId().getLastName());
            holder.tv_date_of_appointment.setText("" + list_booking.get(position).getDoctorSchedule().getDay() + " " +
                    list_booking.get(position).getDoctorSchedule().getEndTime());

            try {
                String time = list_booking.get(position).getCreatedAt();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss zzz");
                Date date = sdf.parse(time);
                sdf = new SimpleDateFormat("EEE dd/MM/yyyy HH:mm");
                String registeredOn = sdf.format(date.getTime());
                holder.tv_registered_on.setText(registeredOn);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        holder.ll_main_container_booking_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad_callback.onReturn("" + position + "-" + "maincontainer");
            }
        });

        holder.btn_view_dtls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgentBookingListDetailsDialogBox d = new AgentBookingListDetailsDialogBox(activity, list_booking, dialogCallback_dtls, "", position);
                d.show();

                ad_callback.onReturn("" + position + "-" + "viewdtls");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_booking.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_patient_name, tv_doctors_name, tv_date_of_appointment, tv_registered_on, tv_status;
        private LinearLayout ll_main_container_booking_list;
        private Button btn_view_dtls;

        public RecyclerViewHolder(View itemView, Activity activity) {
            super(itemView);

            tv_patient_name = (TextView) itemView.findViewById(R.id.tv_patient_name);
            tv_doctors_name = (TextView) itemView.findViewById(R.id.tv_doctors_name);
            tv_date_of_appointment = (TextView) itemView.findViewById(R.id.tv_date_of_appointment);
            tv_registered_on = (TextView) itemView.findViewById(R.id.tv_registered_on);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);

            btn_view_dtls = (Button) itemView.findViewById(R.id.btn_view_details);

            ll_main_container_booking_list = (LinearLayout) itemView.findViewById(R.id.ll_main_container_booking_list);
        }
    }
}
