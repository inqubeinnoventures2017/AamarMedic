package com.inqube.aamarmedic.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.adapter.SelectRoleDialogBoxAdapter;
import com.inqube.aamarmedic.model.baseModel.BaseModelClass;
import com.inqube.aamarmedic.util.AllInterfaces;

import java.util.ArrayList;
import java.util.List;

public class DialogBox extends Dialog implements
        AllInterfaces.AdapterCallback {

    private Activity activity;
    private RecyclerView rv_select_role;
    private RecyclerView.Adapter adapter;
    private AllInterfaces.DialogCallback dialogCallback;
    private List<BaseModelClass> list_items = new ArrayList<BaseModelClass>();
    private RecyclerView.LayoutManager layoutManager;

    public DialogBox(Activity activity, List<BaseModelClass> list_items, AllInterfaces.DialogCallback dialogCallback){
        super(activity);
        this.activity=activity;
        this.list_items =list_items;
        this.dialogCallback=dialogCallback;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_box);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        rv_select_role = (RecyclerView) findViewById(R.id.rv_select_role);
        layoutManager = new LinearLayoutManager(activity);
        rv_select_role.setLayoutManager(layoutManager);
        rv_select_role.setHasFixedSize(true);
        adapter = new SelectRoleDialogBoxAdapter(list_items, activity, this);
        rv_select_role.setAdapter(adapter);
    }

    @Override
    public void onReturn(String position) {
        dialogCallback.onDialogReturn(position);
        dismiss();
    }
}
