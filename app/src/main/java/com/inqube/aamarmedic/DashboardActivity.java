package com.inqube.aamarmedic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.inqube.aamarmedic.base.BaseActivityWithoutMenu;
import com.inqube.aamarmedic.coronavirusfragment.AboutCoronaVirusFragment;

public class DashboardActivity extends BaseActivityWithoutMenu
        implements View.OnClickListener
{
    private TextView tv_covid, tv_health,tv_doctor, tv_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.activity_dashboard);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUI() {
        super.setUI();

       /* pb_loader = (ProgressBar) findViewById(R.id.pb_loader);
        col_holder = (CoordinatorLayout) findViewById(R.id.col_holder);*/

        tv_covid = (TextView)findViewById(R.id.tv_covid);
        tv_health = (TextView)findViewById(R.id.tv_health);
        tv_doctor = (TextView)findViewById(R.id.tv_doctor);
        tv_order = (TextView)findViewById(R.id.tv_order);

        tv_covid.setOnClickListener(this);
        tv_health.setOnClickListener(this);
        tv_doctor.setOnClickListener(this);
        tv_order.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_covid:
                    startActivity(new Intent( DashboardActivity.this, AboutCoronaVirusFragment.class));
                break;

            case R.id.tv_health:
                    startActivity(new Intent( DashboardActivity.this, TeleHealthActivity.class));
                break;

            case R.id.tv_doctor:
                    startActivity(new Intent( DashboardActivity.this, DoctorAppoinmentActivity.class));
                    finish();
                break;

            case R.id.tv_order:
                break;
        }
    }

}
