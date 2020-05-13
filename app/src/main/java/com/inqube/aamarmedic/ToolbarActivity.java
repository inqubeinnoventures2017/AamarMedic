package com.inqube.aamarmedic;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseActivityWithoutMenu;
import com.inqube.aamarmedic.util.AllInterfaces;

public class ToolbarActivity extends BaseActivityWithoutMenu
        implements View.OnClickListener{

    private ImageView imv_home;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.toolbar);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUI() {
        super.setUI();

    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public void onClick(View view) {

    }
}
