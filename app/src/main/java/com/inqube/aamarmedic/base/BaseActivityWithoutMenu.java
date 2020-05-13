package com.inqube.aamarmedic.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.inqube.aamarmedic.app.AamarMedicApplication;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.UtilClass;
import com.inqube.aamarmedic.app.AamarMedicApplication;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;
import com.inqube.aamarmedic.util.UtilClass;
import com.inqube.aamarmedic.R;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class
BaseActivityWithoutMenu extends AppCompatActivity implements
        AllInterfaces.RetrofitResponseToActivityOrFrament {

    protected boolean resume;
    public static boolean shouldIGoBack=true;
    ///////////////////////////////////////////////////////////
    ////////////////UI VARIABLES//////////////////////////////
    protected CoordinatorLayout col_holder;
    protected ProgressBar pb_loader;

    ////////////////////////////////////////////////////////////
    protected void onCreate(@Nullable Bundle savedInstanceState, int layout) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(SoilBaseActivity.this));
        setContentView(layout);
        setupWindowAnimations();
        //StatusBarUtil.setTransparent(this);


    }

    @Override
    protected void attachBaseContext(Context newBase) {
           super.attachBaseContext(UtilClass.getInstance().updateResources(newBase, AamarMedicApplication.locale));
    }


    public void disableScreen() {
        pb_loader.setVisibility(View.VISIBLE);
        shouldIGoBack = false;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void enableScreen() {
        pb_loader.setVisibility(View.GONE);
        shouldIGoBack = true;
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }

    protected void setUI() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!resume) {
            try{
                setUI();
            }catch(Exception e){
                System.out.println("EXCEPTION "+e);
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                finish();
            }
        }
        resume = true;
    }


//    @Override
//    public void onBackPressed() {
//        if (shouldIGoBack) {
//            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
//                getSupportFragmentManager().popBackStackImmediate();
//                getSupportFragmentManager().beginTransaction().commit();
//
//            } else {
//                System.out.println(" NO FRAGMENT ");
//                appCloseDialog(getString(R.string.close_app), BaseActivityWithoutMenu.this);
//            }
//        }
//    }

    @SuppressLint("WrongConstant")
    protected void createSnackBar(View v, String str) {
        Snackbar snackbar = Snackbar
                .make(v, str, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        TextView tv = view.findViewById(R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @SuppressLint("WrongConstant")
    protected void createSnackBar(View v, String str , View.OnClickListener action) {
        Snackbar snackbar = Snackbar
                .make(v, str, Snackbar.LENGTH_INDEFINITE);
        /*snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(Snackbar.LENGTH_SHORT);*/
        snackbar.setAction(R.string.reload,action);
        View view = snackbar.getView();
        TextView tv = view.findViewById(R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }


    public void appCloseDialog(String msg, final BaseActivityWithoutMenu ctx) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ctx);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder
                .setMessage("" + msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(startMain);
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void appUpdateDialog(String msg, final BaseActivityWithoutMenu ctx) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ctx);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder
                .setMessage(R.string.update_app)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        deleteDataPreference();

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(startMain);
                        dialog.dismiss();
                        finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void hideKeyboard(BaseActivityWithoutMenu activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }

    public boolean  isEmailValid(String email) {
        String EMAIL_STRING = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(EMAIL_STRING, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

//    public boolean  isEmailValid(String email) {
//        boolean check;
//        Pattern p;
//        Matcher m;
//        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//        p = Pattern.compile(EMAIL_STRING);
//        m = p.matcher(email);
//        check = m.matches();
//        return check;
//    }

    public boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            if(phone.length() != 10) {
                check = false;
            } else {
                check = true;
            }
        } else {
            check=false;
        }
        return check;
    }

    protected void getImage(Context ctx, int int_drawable, String img_url, ImageView imv, int width, int height) {
        try {
            URL url = new URL(img_url.trim());
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            Picasso.get()
                    .load("" + uri)
                    .error(int_drawable)
                    .resize(width, height)
                    .into(imv);
        } catch (Exception e) {
            Picasso.get()
                    .load(int_drawable)
                    .into(imv);
        }
    }

    protected void getImage(Context ctx, int int_drawable, String img_url, ImageView imv) {
        try {
            URL url = new URL(img_url.trim());
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            Picasso.get()
                    .load("" + uri)
                    .error(int_drawable)
                    .into(imv);
        } catch (Exception e) {
            Picasso.get()
                    .load(int_drawable)
                    .into(imv);
        }
    }

    /////////////////////FOR ACTIVITY EXCEPTION CASES //////////////////////////
    protected void exceptionDialog(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                BaseActivityWithoutMenu.this);
        alertDialogBuilder.setTitle(R.string.app_name);
        alertDialogBuilder
                .setMessage("" + msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        //openSettings();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    ////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////// TO OPEN SETTINGS ///////////////////////////////////////
    protected void openSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public void showToastMessage(Context context, String message) {
        View layout = getLayoutInflater().inflate(R.layout.custom_toast, null);
        // set a message
        TextView text = (TextView) layout.findViewById(R.id.htv_custom_toast);
        text.setText(message);
        // Toast...
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////// FOR ONLINE CHECKING /////////////////////////////////////////
    public boolean isDeviceOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

        } else {
            exceptionDialog(getString(R.string.sorry_you_not_online_msg));
            return false;
        }
        return true;
    }

    /////////////////////SHARED PREFERENCE ///////////////////////////////////
    public void saveUserPreference(String key, String value) {
        getSharedPreferences(Config.USER, 0).edit().putString(key, value).commit();
    }

    public void saveDataPreference(String key, String value) {
        getSharedPreferences(Config.DATA, 0).edit().putString(key, value).commit();
    }

    public String getUserPreference(String key, String value) {
        return getSharedPreferences(Config.USER, 0).getString(key, value);
    }

    public String getDataPreference(String key, String value) {
        return getSharedPreferences(Config.DATA, 0).getString(key, value);
    }

    public void deleteUserPreference() {
        getSharedPreferences(Config.USER, 0).edit().clear().commit();
    }

    public void deleteDataPreference() {
        getSharedPreferences(Config.DATA, 0).edit().clear().commit();
    }


    public void deleteSpecificValueFromData(String key) {
        getSharedPreferences(Config.DATA, 0).edit().remove(key).apply();
    }

    public void deleteSpecificValueFromUser(String key) {
        getSharedPreferences(Config.USER, 0).edit().remove(key).apply();
    }

    ///////////////////////// RetroFit RESPONSE //////////////////////////////////

    @Override
    public void onSuccess(Object response, String which_method) {

    }

    @Override
    public void onFailure(ReturnResponse response) {

    }

    @Override
    public void onResponseFailure() {

    }

    @Override
    public void onResponseFailure(String msg) {

    }
}
