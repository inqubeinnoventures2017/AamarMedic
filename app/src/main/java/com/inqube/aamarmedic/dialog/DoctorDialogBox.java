package com.inqube.aamarmedic.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inqube.aamarmedic.DoctorAppoinmentActivity;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.adapter.SelectDoctorListDialogBoxAdapter;
import com.inqube.aamarmedic.adapter.SelectRoleDialogBoxAdapter;
import com.inqube.aamarmedic.model.baseModel.BaseModelClass;
import com.inqube.aamarmedic.model.doctorlist.Result;
import com.inqube.aamarmedic.util.AllInterfaces;

import java.util.ArrayList;
import java.util.List;

public class DoctorDialogBox extends Dialog implements
        AllInterfaces.AdapterCallback {

    private Activity activity;
    private RecyclerView rv_doctorlist;
    private RecyclerView.Adapter adapter;
    private AllInterfaces.DialogCallback dialogCallback;
    private List<Result> list_items = new ArrayList<Result>();
    private RecyclerView.LayoutManager layoutManager;
    private String type;

    public DoctorDialogBox(Activity activity, List<Result> list_items, AllInterfaces.DialogCallback dialogCallback, String type){
        super(activity);
        this.activity=activity;
        this.list_items =list_items;
        this.dialogCallback=dialogCallback;
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.doctor_list);

        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.5f;
        window.setAttributes(attributesParams);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        rv_doctorlist = (RecyclerView) findViewById(R.id.rv_doctorlist);
        layoutManager = new LinearLayoutManager(activity);
        rv_doctorlist.setLayoutManager(layoutManager);
        rv_doctorlist.setHasFixedSize(true);
        adapter = new SelectDoctorListDialogBoxAdapter(activity,list_items,this);
        rv_doctorlist.setAdapter(adapter);
    }

    @Override
    public void onReturn(String position) {
        dialogCallback.onDialogReturn(position+"-"+type);
        dismiss();
    }
}
