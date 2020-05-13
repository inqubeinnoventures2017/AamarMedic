package com.inqube.aamarmedic.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.inqube.aamarmedic.model.baseModel.BaseModelClass;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.R;

import java.util.ArrayList;
import java.util.List;

public class SelectRoleDialogBoxAdapter extends RecyclerView.Adapter<SelectRoleDialogBoxAdapter.RecyclerViewHolder> {

    private Activity activity;
    private AllInterfaces.AdapterCallback ad_callback;
    private List<BaseModelClass> list_items = new ArrayList<BaseModelClass>();

    public SelectRoleDialogBoxAdapter(List<BaseModelClass> list_items, Activity activity, AllInterfaces.AdapterCallback ad_call) {

        this.list_items=list_items;
        this.activity = activity;
        this.ad_callback = ad_call;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        RecyclerViewHolder recyclerViewHolder;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_box_list_cell, parent, false);
        recyclerViewHolder = new RecyclerViewHolder(view, activity);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        holder.tv_select_role.setText(""+list_items.get(position).getRole());
        holder.tv_select_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad_callback.onReturn(""+position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_items.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_select_role;
//        private LinearLayout ll_select_role;

        public RecyclerViewHolder(View itemView, Activity activity) {
            super(itemView);
            tv_select_role = (TextView) itemView.findViewById(R.id.tv_select_role);
//            ll_select_role = (LinearLayout) itemView.findViewById(R.id.ll_select_role);
        }
    }
}

