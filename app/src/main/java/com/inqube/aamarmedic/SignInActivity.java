package com.inqube.aamarmedic;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.gson.Gson;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseActivityWithoutMenu;
import com.inqube.aamarmedic.model.login.MSG;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;
import com.inqube.aamarmedic.util.UtilClass;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Response;

public class SignInActivity extends BaseActivityWithoutMenu
        implements View.OnClickListener, AllInterfaces.DialogCallback, AllInterfaces.StateCallback {

    private TextView tv_forgotpsw;
    private EditText edt_user, edt_psw;
    private Button btn_login;
    private String state_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, R.layout.sign_in);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUI() {
        super.setUI();

        pb_loader =  findViewById(R.id.pb_loader);
        col_holder =  findViewById(R.id.col_holder);

        edt_user =(EditText) findViewById(R.id.edt_user);
        edt_psw =(EditText)findViewById(R.id.edt_psw);
        tv_forgotpsw = (TextView)findViewById(R.id.tv_forgotpsw);

        btn_login=(Button)findViewById(R.id.btnLogin);

        btn_login.setOnClickListener(this);
        tv_forgotpsw.setOnClickListener(this);

        if (!isDeviceOnline()) {
            createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg), new SnackBarClickAction());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                if (isDeviceOnline()) {
                    if (checkLoginValidity()) {
                        UtilClass.getInstance().loginToServer(SignInActivity.this,SignInActivity.this,
                                edt_user.getText().toString(),edt_psw.getText().toString(),"123456");
                    }
                } else {
                    createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg));
                }
                break;

            case R.id.tv_forgotpsw:
                //Creating the LayoutInflater instance
                /*LayoutInflater li = getLayoutInflater();
                //Getting the View object as defined in the customtoast.xml file
                View layout = li.inflate(R.layout.toast_patient_info, findViewById(R.id.custom_toast_layout));

                //Creating the Toast object
                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setView(layout);//setting the view of custom toast layout
                toast.show();*/
                //
                break;
        }
    }

    @Override
    public void onSuccess(Object response, String which_method) {
        super.onSuccess(response, which_method);

        if(which_method.equalsIgnoreCase("loginToServer")){
            btn_login.setEnabled(true);
            Response<com.inqube.aamarmedic.model.login.MSG> res = (Response<MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());

            saveUserPreference(Config.USER, json);
            saveUserPreference(Config.LANGUAGE_ID, res.body().getResult().getLanguageId());
            saveUserPreference(Config.AGENT_ID, res.body().getResult().get_id());
            saveUserPreference(Config.AUTH_TOKEN,res.body().getToken());
            saveUserPreference(Config.USER_NAME, res.body().getResult().getUsername());

           /* System.out.println("Language Id:"+res.body().getResult().getLanguageId());
            System.out.println("Agent Id:"+res.body().getResult().get_id());
            System.out.println("token:"+res.body().getToken());*/

            startActivity(new Intent(SignInActivity.this, AgentDashboardMainFragmentActivity.class));
            finish();
        }
    }

    @Override
    public void onResponseFailure() {
        super.onResponseFailure();
        createSnackBar(col_holder, getString(R.string.please_try_again), new SnackBarClickAction());
    }

    @Override
    public void onFailure(ReturnResponse response) {
        super.onFailure(response);
        createSnackBar(col_holder, response.getMsg(), new SnackBarClickAction());
    }

    @Override
    public void onDialogReturn(String position) {

    }

    @Override
    public void onStateReturn(String position) {

    }

    public class SnackBarClickAction implements View.OnClickListener {

        String msg;

        public SnackBarClickAction(String msg) {
            this.msg = msg;
        }

        public SnackBarClickAction() {

        }

        @Override
        public void onClick(View v) {
            if (isDeviceOnline()) {
                if (checkLoginValidity()) {
                    UtilClass.getInstance().loginToServer(SignInActivity.this,SignInActivity.this,
                            edt_user.getText().toString(),edt_psw.getText().toString(),"123456");
                }
            } else {
                createSnackBar(col_holder, getString(R.string.sorry_you_not_online_msg));
            }
        }
    }

    private boolean checkLoginValidity() {
        if (edt_user.getText().toString().length() < 1) {
            createSnackBar(col_holder, getString(R.string.plase_enter_user_name));
            return false;
        }

        if (TextUtils.isEmpty(edt_psw.getText())) {
            createSnackBar(col_holder, getString(R.string.please_enter_valid_password));
            return false;
        }
        return true;
    }
}
