package com.inqube.aamarmedic.app;

import android.content.Context;

import androidx.multidex.MultiDexApplication;


public class AamarMedicApplication extends MultiDexApplication {
    public static Context context;
    public static String locale;

    @Override
    public void onCreate() {
        super.onCreate();
        //Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExHandler());
        context = this;
        System.out.println(" APP "+getSharedPreferences(Config.USER,0).getString(Config.PROF_LANGUAGE_ID,"en"));
        locale = getSharedPreferences(Config.USER,0).getString(Config.PROF_LANGUAGE_ID,"en");
    }

    public static Context getContext() {
        return context;
    }
}
