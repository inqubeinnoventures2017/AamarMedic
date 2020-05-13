package com.inqube.aamarmedic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.inqube.aamarmedic.base.BaseActivityWithoutMenu;

public class SplashActivity extends BaseActivityWithoutMenu {
    private Handler mWaitHandler= new Handler(); ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mWaitHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                //The following code will execute after the 5 seconds.

                try {
                    //Go to next page i.e, start the next activity.
                    Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                    startActivity(intent);
                    //Let's Finish Splash Activity since we don't want to show this when user press back button.
                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 5000);
    }
}
