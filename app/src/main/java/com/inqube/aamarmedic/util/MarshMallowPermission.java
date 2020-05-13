package com.inqube.aamarmedic.util;//package com.inqube_it.kunacredit.util;//package com.inqube.geneus.util;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.pm.PackageManager;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.widget.Toast;
//
///**
// * Created by root on 11/11/16.
// */
//public class MarshMallowPermission {
//
//
//    public static final int RECORD_PERMISSION_REQUEST_CODE = 1;
//    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
//    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
//    private static final int PERMISSION_REQUEST_CODE_LOCATION = 4;
//    private static final int PERMISSION_REQUEST_CODE_PHONE_STATE = 5;
//    Activity activity;
//
//    public MarshMallowPermission(Activity activity) {
//        this.activity = activity;
//    }
//
//    public boolean checkPermissionForRecord() {
//        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    public boolean checkPermissionForExternalStorage() {
//        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    public boolean checkPermissionForCamera() {
//        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    public boolean checkPermissionForGPS() {
//        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    public boolean checkPermissionForReadPhoneState() {
//        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    public void requestPermissionForRecord() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)) {
//            Toast.makeText(activity, "Microphone permission needed for recording. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    public void requestPermissionForExternalStorage() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Toast.makeText(activity, "External Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    public void requestPermissionForCamera() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
//            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    public void requestPermissionForGPS() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE_LOCATION);
//        }
//    }
//
//    public void requestPermissionForReadPhoneState() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_PHONE_STATE)) {
//            Toast.makeText(activity, "Camera permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
//        } else {
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE_PHONE_STATE);
//        }
//    }
//
//    public boolean checkPermissionForSms() {
//        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECEIVE_SMS);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    public boolean checkPermissionForReadSms() {
//        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS);
//        return result == PackageManager.PERMISSION_GRANTED;
//    }
//
//    public void requestSmsPermission() {
//        String permission = Manifest.permission.RECEIVE_SMS;
//        int grant = ContextCompat.checkSelfPermission(activity, permission);
//        if (grant != PackageManager.PERMISSION_GRANTED) {
//            String[] permission_list = new String[1];
//            permission_list[0] = permission;
//            ActivityCompat.requestPermissions(activity, permission_list, 1);
//        }
//    }
//
//    public void requestSmsReadPermission() {
//        String permission = Manifest.permission.READ_SMS;
//        int grant = ContextCompat.checkSelfPermission(activity, permission);
//        if (grant != PackageManager.PERMISSION_GRANTED) {
//            String[] permission_list = new String[1];
//            permission_list[0] = permission;
//            ActivityCompat.requestPermissions(activity, permission_list, 1);
//        }
//    }
//}
