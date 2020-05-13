package com.inqube.aamarmedic.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.app.AamarMedicApplication;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.CircleTransform;
import com.inqube.aamarmedic.util.ReturnResponse;
import com.inqube.aamarmedic.util.UtilClass;
import com.inqube.aamarmedic.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URL;

public class BaseActivity extends AppCompatActivity implements AllInterfaces.RetrofitResponseToActivityOrFrament
{

    /////////////////NORMAL PARAMS //////////////////////////
    protected boolean resume;
    public static boolean shouldIGoBack = true;
    protected Toolbar toolbar;
    protected ImageView imv_logo,imv_menu,imv_back;
    protected DrawerLayout drawer;

    ////////////////UI VARIABLES//////////////////////////////
    protected CoordinatorLayout col_holder;
    public ProgressBar pb_loader;

    ////////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(@Nullable Bundle savedInstanceState, int layout) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout);
        setupWindowAnimations();
        toolbar = findViewById(R.id.toolbar);

        imv_logo = (ImageView)toolbar.findViewById(R.id.imv_logo);
        imv_menu = (ImageView)toolbar.findViewById(R.id.imv_menu);
        imv_back = (ImageView)toolbar.findViewById(R.id.imv_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" BACK NOT WORKING ");
                System.out.println(" BACK PRESSED " + getSupportFragmentManager().getBackStackEntryCount());
                if (shouldIGoBack) {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                        getSupportFragmentManager().popBackStackImmediate();
                        getSupportFragmentManager().beginTransaction().commit();
                    } else {
                        System.out.println(" R NEI ");
                        appCloseDialog(getString(R.string.close_app), BaseActivity.this);
                    }
                }
            }
        });
        imv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" MENU CLICKED ");
                if (drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.closeDrawers();
                }else{
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });

        imv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shouldIGoBack) {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                        getSupportFragmentManager().popBackStackImmediate();
                        getSupportFragmentManager().beginTransaction().commit();
                    } else {
                        System.out.println(" R NEI ");
                        appCloseDialog(getString(R.string.close_app), BaseActivity.this);
                    }
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(UtilClass.getInstance().updateResources(newBase, AamarMedicApplication.locale));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }

    public void showToastMessage(Context context, String message) {
        View layout = getLayoutInflater().inflate(R.layout.custom_toast, null);
        // set a message
        @SuppressLint("WrongViewCast") TextView text = (TextView) layout.findViewById(R.id.htv_custom_toast);
        text.setText(message);
        // Toast...
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }



    protected void setUI() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!resume) {
            try {
                setUI();
            } catch (Exception e) {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                finish();
            }
        }
        resume = true;
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
        System.out.println(" BACK PRESSED " + getSupportFragmentManager().getBackStackEntryCount() + " " + shouldIGoBack);
        if (shouldIGoBack) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStackImmediate();
                getSupportFragmentManager().beginTransaction().commit();
            } else {
                System.out.println(" R NEI ");
                appCloseDialog(getString(R.string.close_app), this);
            }
        }
    }

    @SuppressLint("WrongConstant")
    public void createSnackBar(View v, String str) {
        final Snackbar snackbar = Snackbar
                .make(v, str, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setDuration(Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.dismiss, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
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

    public void appCloseDialog(String msg, final BaseActivity ctx) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ctx);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
        alertDialogBuilder
                .setMessage("" + msg)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(startMain);
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void hideKeyboard(BaseActivity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }

    public void getImage(Context ctx, int int_drawable, String img_url, ImageView imv, int width, int height) {
        try {
            URL url = new URL(img_url.trim());
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            Picasso.get()
                    .load("" + uri).resize(1024, 150).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .error(int_drawable)
                    .into(imv);
        } catch (Exception e) {
            System.out.println(" IMAGE EXCEPTION " + e);
            Picasso.get()
                    .load(int_drawable)
                    .into(imv);
        }
    }

    public void getImage(Context ctx, int int_drawable, String img_url, ImageView imv) {
        try {
            URL url = new URL(img_url.trim());
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            Picasso.get()
                    .load("" + uri).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .error(int_drawable)
                    .into(imv);
        } catch (Exception e) {
            System.out.println(" IMAGE EXCEPTION " + e);
            Picasso.get()
                    .load(int_drawable)
                    .into(imv);
        }
    }


    public void getImageFit(Context ctx, int int_drawable, String img_url, ImageView imv) {
        try {
            URL url = new URL(img_url.trim());
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            Picasso.get()
                    .load("" + uri).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .fit()
                    .error(int_drawable)
                    .into(imv);
        } catch (Exception e) {
            System.out.println(" IMAGE EXCEPTION " + e);
            Picasso.get()
                    .load(int_drawable)
                    .into(imv);
        }
    }


    public void getCircleImage(Context ctx, int int_drawable, String img_url, ImageView imv) {
        try {
            URL url = new URL(img_url.trim());
            URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            Picasso.get()
                    .load("" + uri).networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .fit()
                    .error(int_drawable)
                    .transform(new CircleTransform())
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(imv);
        } catch (Exception e) {
            System.out.println(" IMAGE EXCEPTION " + e);
            Picasso.get()
                    .load(int_drawable)
                    .into(imv);
        }
    }

    /////////////////////FOR ACTIVITY EXCEPTION CASES //////////////////////////
    protected void exceptionDialog(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                BaseActivity.this);
        alertDialogBuilder.setTitle(R.string.app_name);
        alertDialogBuilder
                .setMessage("" + msg)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
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
