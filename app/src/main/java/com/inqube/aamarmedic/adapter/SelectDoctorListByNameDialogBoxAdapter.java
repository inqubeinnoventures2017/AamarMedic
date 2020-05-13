package com.inqube.aamarmedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.model.doctorlistbyname.Doc;
import com.inqube.aamarmedic.model.doctorlistbyname.Result;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.inqube.aamarmedic.app.AamarMedicApplication.context;

public class SelectDoctorListByNameDialogBoxAdapter extends RecyclerView.Adapter<SelectDoctorListByNameDialogBoxAdapter.RecyclerViewHolder>{
    private Activity activity;
    private List<com.inqube.aamarmedic.model.doctorlistbyname.Doc> list_doctor;
    private AllInterfaces.AdapterCallback ad_callback;

    public SelectDoctorListByNameDialogBoxAdapter(Activity activity, List<Doc> list_doctor, AllInterfaces.AdapterCallback ad_callback) {
        this.activity = activity;
        this.list_doctor = list_doctor;
        this.ad_callback = ad_callback;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull int viewType) {

        View view;
        RecyclerViewHolder recyclerViewHolder;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_cell, parent, false);
        recyclerViewHolder = new RecyclerViewHolder(view, activity);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        if (list_doctor.get(position).getDoctorInfo()!=null)
        {
            if (list_doctor.get(position).getDoctorInfo().getFirstName().length() > 0 && list_doctor.get(position).getDoctorInfo().getLastName().length() > 0 )
            {
                String doctorName = ""+list_doctor.get(position).getDoctorInfo().getFirstName() + " "
                        + list_doctor.get(position).getDoctorInfo().getLastName();
                holder.tv_doctor_name.setText(doctorName);
            }else
            {
                holder.tv_doctor_name.setText(list_doctor.get(position).getDoctorInfo().getFirstName());
            }

            if (list_doctor.get(position).getDoctorInfo().getDegree().trim().length()>0)
            {
                holder.tv_doctor_degree.setText(list_doctor.get(position).getDoctorInfo().getDegree());
            }

            holder.tv_doctor_specialist.setText("");
            if (list_doctor.get(position).getDoctorInfo().getSpecializationInfo().getSpecializationDetails().get(0).getSpecializationName()!=null)
            {
                holder.tv_doctor_specialist.setText(list_doctor.get(position).getDoctorInfo().getSpecializationInfo().getSpecializationDetails().get(0).getSpecializationName());
            }

            if (list_doctor.get(position).getDoctorInfo().getImageUrl().trim().length() > 0)
            {
                Picasso.get().load(list_doctor.get(position).getDoctorInfo().getImageUrl()).into(holder.imv_doctor);
            }
            else {
                Picasso.get().load(R.drawable.dr_placeholder).into(holder.imv_doctor);
            }
        }

        if (list_doctor.get(position).getClinicInfo()!=null)
        {
            if (list_doctor.get(position).getClinicInfo().getClinicName().trim().length()>0)
            {
                holder.tv_hospital.setText(list_doctor.get(position).getClinicInfo().getClinicName());
            }

            if (list_doctor.get(position).getClinicInfo().getAddress().trim().length()>0)
            {
                holder.tv_hospital_addr.setText(list_doctor.get(position).getClinicInfo().getAddress());
            }

        }

        if (list_doctor.get(position).getDay().length()>0)
        {
            String doctorSchedule = ""+list_doctor.get(position).getDay() + " " + list_doctor.get(position).getStartTime() + " - "
                    +list_doctor.get(position).getEndTime();
            holder.tv_doctor_time.setText(doctorSchedule);
        }

        holder.ll_main_container_doc_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad_callback.onReturn(""+position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_doctor.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_doctor_name,tv_doctor_degree, tv_doctor_specialist, tv_hospital, tv_hospital_addr, tv_doctor_time;
        private ImageView imv_doctor;
        private LinearLayout ll_main,ll_hide_show;
        private int showHide = 0;
        private LinearLayout ll_main_container_doc_list;

        public RecyclerViewHolder(View itemView, Activity activity) {
            super(itemView);

            tv_doctor_name =  itemView.findViewById(R.id.tv_doctor_name);
            tv_doctor_degree =  itemView.findViewById(R.id.tv_doctor_degree);
            tv_doctor_specialist =  itemView.findViewById(R.id.tv_doctor_specialist);
            tv_hospital = itemView.findViewById(R.id.tv_doctor_hospitalname);
            tv_hospital_addr = itemView.findViewById(R.id.tv_hospital_addr);
            tv_doctor_time = itemView.findViewById(R.id.tv_doctor_time);
            imv_doctor =  itemView.findViewById(R.id.imv_doctor);

            ll_main_container_doc_list = itemView.findViewById(R.id.ll_main_container_doc_list);

        }
    }
}
