package com.inqube.aamarmedic.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseActivityWithoutMenu;
//import com.inqube.aamarmedic.model.getStateList.MSG;
//import com.inqube.aamarmedic.model.login.LoginJSONParameter;
//import com.inqube.aamarmedic.model.resetPassword.ResetPassword;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.model.clinic.MSG;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilClass {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static UtilClass utils;
    private AlertDialog dialog;
    private AlertDialog.Builder dialogBuilder;
    private DownloadManager.Request request;
    private DownloadManager downloadManager;

    public static UtilClass getInstance() {
        if (utils == null) {
            utils = new UtilClass();
        }
        return utils;
    }

    public void clearBackStack(FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }


    public void hasFragment(FragmentManager fragmentManager, String tag) {
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            FragmentManager.BackStackEntry frag_manager = fragmentManager.getBackStackEntryAt(i);
            String fragment_tag = frag_manager.getName();
            if (fragment_tag.equalsIgnoreCase(tag)) {
                System.out.println("INSIDE IF");
                fragmentManager.popBackStack(fragment_tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            }
        }
    }

//    public String getStringImage(Bitmap bmp) {
//        String encodedImage = "";
//        if (bmp != null) {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] imageBytes = baos.toByteArray();
//            encodedImage = "data:image/jpeg;base64,"+Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        }
//
//        return encodedImage;
//    }

    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] array = stream.toByteArray();
        return Base64.encodeToString(array, Base64.NO_WRAP);
    }

    public Context updateResources(Context context, String language) {
        System.out.println("  LOCALE "+language);
        Locale locale;
        if (language.equalsIgnoreCase(Config.SPANISH)) {
            locale = new Locale("es");
        }else{
            locale = new Locale("en");
        }
        Locale.setDefault(locale);
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale); return context.createConfigurationContext(configuration); }

    //localization for languages
    public void setLocale(Activity activity, String lang) {
        System.out.println( "LOCALE  "+lang);
        Locale myLocale = new Locale(lang);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }


    public void downloadFile(Activity activity, Uri uri) {
//        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//        // set title and description
//        request.setTitle(activity.getString(R.string.file));
//        request.allowScanningByMediaScanner();
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        //set the local destination for download file to a path within the application's external files directory
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "downloadfileName");
////        request.setMimeType("*/*");
//        downloadManager.enqueue(request);
        String path= String.valueOf(uri);
        String filename=path.substring(path.lastIndexOf("/")+1);

        System.out.println(  " DOWNLOAD FILE NAME "+filename);

        request = new DownloadManager.Request(uri);
        request.setDestinationInExternalPublicDir("/aamarmedic", filename);
        request.setTitle(""+activity.getString(R.string.file));
        request.setNotificationVisibility(request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    /////////////////////////////Image Capture Permission////////////////////
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle(R.string.permission_neccessary);
                    alertBuilder.setMessage(R.string.ext_storage_reqd);
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    //////////////////////// IMAGE RESIZE//////////////////////////
    public Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                            boolean filter) {
        float ratio = Math.min(
                maxImageSize / realImage.getWidth(),
                maxImageSize / realImage.getHeight());
        int width = Math.round(ratio * realImage.getWidth());
        int height = Math.round(ratio * realImage.getHeight());
        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public String timeFormat(String time) {
        SimpleDateFormat _12HourSDF = null;
        Date _24HourDt = null;
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            _12HourSDF = new SimpleDateFormat("hh:mm a");
            _24HourDt = _24HourSDF.parse(time);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "" + _12HourSDF.format(_24HourDt);
    }

    public Bitmap scaleBitmap(Bitmap bitmap, int wantedWidth, int wantedHeight) {
        Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float) wantedWidth / bitmap.getWidth(), (float) wantedHeight / bitmap.getHeight());
        canvas.drawBitmap(bitmap, m, new Paint());
        return output;
    }

    public String getCurrentDate(String input, Activity activity) {
        //2020-02-20 07:33:24
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(input);
        } catch (ParseException e) {
            System.out.println(" CURRENT DATE EXP "+e);
        }
        DateFormat dateFormat = new SimpleDateFormat(activity.getString(R.string.date_format));
        String current_date = dateFormat.format(date);
        return current_date;
    }

    public String getConvertedDate(String input, Activity activity) {


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = null;
        try {
            date = formatter.parse(input);
        } catch (ParseException e) {
            System.out.println(" CURRENT DATE EXP "+e);
        }
        DateFormat dateFormat = new SimpleDateFormat(activity.getString(R.string.date_format));
        String current_date = dateFormat.format(date);
        return current_date;
    }

    public Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    ////////////////// SEND OTP PASSWORD/////////////////////////////////////////////

    public Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public void dateDialog(final TextView tv , final Activity activty){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                String newMonth;
                if (monthOfYear < 9
                ){
                    newMonth = "0"+(monthOfYear + 1);

                }else {
                    newMonth = "" + (monthOfYear + 1);
                }
                tv.setText(dayOfMonth + "-" + newMonth + "-" + year );
            }};
        DatePickerDialog dpDialog=new DatePickerDialog(activty, listener, mYear, mMonth, mDay);
        dpDialog.show();
    }

    public void dateDialogyymmdd(final TextView tv , final Activity activty){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                String newMonth;
                if (monthOfYear < 9
                ){
                    newMonth = "0"+(monthOfYear + 1);

                }else {
                    newMonth = "" + (monthOfYear + 1);
                }
                tv.setText(""+year + "-" + newMonth + "-" + dayOfMonth  );
            }};
        DatePickerDialog dpDialog=new DatePickerDialog(activty, listener, mYear, mMonth, mDay);
        dpDialog.show();
    }


    ////////////////// DatePicker with Selectable Days /////////////////////////
    public void dateDialogwithSelectableDays(final TextView tv , final Activity activty){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                String newMonth;
                if (monthOfYear < 9
                ){
                    newMonth = "0"+(monthOfYear + 1);

                }else {
                    newMonth = "" + (monthOfYear + 1);
                }
                tv.setText(dayOfMonth + "-" + newMonth + "-" + year );
            }
        };

        //datePickerDialog.setDisabledDays(days)

        DatePickerDialog dpDialog=new DatePickerDialog(activty, listener, mYear, mMonth, mDay);
        dpDialog.show();

    }
    //////////////////

    ///////////////////--->>> API Services<<<---/////////////////////////////
    //>>>>>>>>>>> Login Data <<<<<<<<<<<<
    public void loginToServer(final BaseActivityWithoutMenu activity,
                              final AllInterfaces.RetrofitResponseToActivityOrFrament retRes,
                              String username, String password, String fcm_token) {
        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.login.MSG> userCall = service.loginData(new LoginJSONParameter(username, password, fcm_token));
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.login.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.login.MSG> call, Response<com.inqube.aamarmedic.model.login.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "loginToServer");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.login.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> Logout Data <<<<<<<<<<<<
    public void logoutFromServer(final BaseActivity activity,
                              final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String token) {
        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.logout.MSG> userCall = service.logoutData(token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.logout.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.logout.MSG> call, Response<com.inqube.aamarmedic.model.logout.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "logoutFromServer");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.logout.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> Specialization List <<<<<<<<<<<<
    public void getSpecializationData(final BaseActivity activity,
                             final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String token, String language_id) {

        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.specializationlist.MSG> userCall = service.getSpecializationList(token,language_id);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.specializationlist.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.specializationlist.MSG> call, Response<com.inqube.aamarmedic.model.specializationlist.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getSpecializationData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.specializationlist.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> Clinic List <<<<<<<<<<<<
    public void getClinicData(final BaseActivity activity,
                                      final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String spec_id, String token) {

        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.clinic.MSG> userCall = service.getClinicList(spec_id, token);
        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.clinic.MSG> call, Response<com.inqube.aamarmedic.model.clinic.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getClinicData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.clinic.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> Day List <<<<<<<<<<<<
    public void getDayListData(final BaseActivity activity,
                              final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String spec_id, String clinic_id, String token) {

        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.daylist.MSG> userCall = service.getDayList(spec_id,clinic_id, token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.daylist.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.daylist.MSG> call, Response<com.inqube.aamarmedic.model.daylist.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getDayListData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.daylist.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> Doctor List <<<<<<<<<<<<
    public void getDoctorListData(final BaseActivity activity,
                               final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String spec_id, String clinic_id, String day_id,
                                  String token, String lang_id) {

        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.doctorlist.MSG> userCall = service.getDoctorlist(spec_id,clinic_id,day_id, token, lang_id);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.doctorlist.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.doctorlist.MSG> call, Response<com.inqube.aamarmedic.model.doctorlist.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getDoctorListData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.doctorlist.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> Doctor List by Name <<<<<<<<<<<<
    public void getDoctorListbByNameData(final BaseActivity activity,
                                  final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String doc, String token) {

        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.doctorlistbyname.MSG> userCall = service.getDoctorlistbyname(doc, token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.doctorlistbyname.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.doctorlistbyname.MSG> call, Response<com.inqube.aamarmedic.model.doctorlistbyname.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getDoctorListbByNameData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.doctorlistbyname.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> District List <<<<<<<<<<<<
    public void getDistrictData(final BaseActivity activity,
                                      final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String token) {

        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.districtlist.MSG> userCall = service.getDistrictList(token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.districtlist.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.districtlist.MSG> call, Response<com.inqube.aamarmedic.model.districtlist.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getDistrictData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.districtlist.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> City List <<<<<<<<<<<<
    public void getCityData(final BaseActivity activity,
                                final AllInterfaces.RetrofitResponseToActivityOrFrament retRes,String dist_id, String token) {

        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.citylist.MSG> userCall = service.getCityList(dist_id,token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.citylist.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.citylist.MSG> call, Response<com.inqube.aamarmedic.model.citylist.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getCityData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.citylist.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> Doctor Appointment <<<<<<<<<<<<
    public void getDoctorAppointmentData(final BaseActivity activity,
                                         final AllInterfaces.RetrofitResponseToActivityOrFrament retRes,
                                         String doctor_schedule, String patient_name, String mobile, String address,
                                         String pin_code, String district, String city, String remark, String token) {

        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.appointment.MSG> userCall = service.getDoctorAppointment(doctor_schedule,
                patient_name, mobile, address, pin_code, district, city,remark,token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.appointment.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.appointment.MSG> call, Response<com.inqube.aamarmedic.model.appointment.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getDoctorAppointmentData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.appointment.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> TeleHealth Personal <<<<<<<<<<<<
    public void submitTeleHealthPersonalData(final BaseActivity activity,
                                             final AllInterfaces.RetrofitResponseToActivityOrFrament retRes,
                                             String patient_name, String mobile_no, String aadhar_no,  String email_id,  String address,
                                             String city, String area, String pin_code, String gender, String date_of_birth, String token) {

        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.telehealthpersonal.MSG> userCall = service.submitTeleHealthPersonal(patient_name,
                mobile_no, aadhar_no, email_id, address, city, area, pin_code, gender, date_of_birth,token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.telehealthpersonal.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.telehealthpersonal.MSG> call, Response<com.inqube.aamarmedic.model.telehealthpersonal.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "submitTeleHealthPersonalData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.telehealthpersonal.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    public void getPatientPersonalDetailsData(final BaseActivity activity,
                                              final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String mobile, String token) {
        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.patientpersonaldetails.MSG> userCall = service.getPatientPersonalDetails(mobile, token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.patientpersonaldetails.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.patientpersonaldetails.MSG> call, Response<com.inqube.aamarmedic.model.patientpersonaldetails.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getPatientPersonalDetailsData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.patientpersonaldetails.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //>>>>>>>>>>> Submit info about TeleHealth Medical <<<<<<<<<<<<
    public void submitTeleHealthMedicalData(final BaseActivity activity,
                                              final AllInterfaces.RetrofitResponseToActivityOrFrament retRes,
                                            String patient_id, String body_temperature, boolean is_cough,
                                            String cough_pattern, boolean cough_no_of_days, boolean is_shortness_of_breath,
                                            String shortness_of_breath_type, boolean is_blood_pressure, String last_blood_pressure,
                                            boolean is_blood_sugar, String blood_sugar_fasting, String blood_sugar_pp,
                                            boolean is_allergic, String token) {
        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.telehealthmedical.MSG> userCall = service.submitTeleHealthMedical(patient_id, body_temperature, is_cough,
            cough_pattern, cough_no_of_days, is_shortness_of_breath, shortness_of_breath_type, is_blood_pressure, last_blood_pressure,
            is_blood_sugar, blood_sugar_fasting, blood_sugar_pp, is_allergic, token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.telehealthmedical.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.telehealthmedical.MSG> call, Response<com.inqube.aamarmedic.model.telehealthmedical.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "submitTeleHealthMedicalData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.telehealthmedical.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //----------------------------->>>>>>>>>>> Checking Duplicate AADHAR No <<<<<<<<<<<<--------------------------------------
    public void checkDuplicateAadharNoData(final BaseActivity activity,
                                              final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String aadhar_no, String token) {
        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.checkduplicateaadhar.MSG> userCall = service.checkDuplicateAadharNo(aadhar_no, token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.checkduplicateaadhar.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.checkduplicateaadhar.MSG> call, Response<com.inqube.aamarmedic.model.checkduplicateaadhar.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "checkDuplicateAadharNoData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.checkduplicateaadhar.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //----------------------------->>>>>>>>>>> Agent Booking List <<<<<<<<<<<<--------------------------------------
    public void getAgentBookingListData(final BaseActivity activity,
                                              final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String agent_id, String token) {
        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.agentbookinglist.MSG> userCall = service.getAgentBookingList(agent_id, token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.agentbookinglist.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.agentbookinglist.MSG> call, Response<com.inqube.aamarmedic.model.agentbookinglist.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getAgentBookingListData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.agentbookinglist.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //----------------------------->>>>>>>>>>> Language List <<<<<<<<<<<<--------------------------------------
    public void getLanguageListData(final BaseActivity activity,
                                        final AllInterfaces.RetrofitResponseToActivityOrFrament retRes, String token) {
        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.languagelist.MSG> userCall = service.getLanguageList(token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.languagelist.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.languagelist.MSG> call, Response<com.inqube.aamarmedic.model.languagelist.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getLanguageListData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.languagelist.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //----------------------------->>>>>>>>>>> Agent Profile Details <<<<<<<<<<<<--------------------------------------
    public void getAgentProfileDetailsData(final BaseActivity activity,
                                    final AllInterfaces.RetrofitResponseToActivityOrFrament retRes,
                                           String id, String token) {
        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.agentprofiledetails.MSG> userCall = service.getAgentProfileDetails(id, token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.agentprofiledetails.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.agentprofiledetails.MSG> call, Response<com.inqube.aamarmedic.model.agentprofiledetails.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "getAgentProfileDetailsData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.agentprofiledetails.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }

    //----------------------------->>>>>>>>>>> Agent Profile Update <<<<<<<<<<<<--------------------------------------
    public void updateAgentProfileData(final BaseActivity activity,
                                           final AllInterfaces.RetrofitResponseToActivityOrFrament retRes,
                                           String id, String name, String email, String mobile, String language_id, String token) {
        activity.disableScreen();
        System.out.println("SERVICE Response Code::");
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<com.inqube.aamarmedic.model.agentprofileupdate.MSG> userCall = service.updateAgentProfile(id, name, email, mobile, language_id, token);
        userCall.enqueue(new Callback<com.inqube.aamarmedic.model.agentprofileupdate.MSG>() {
            @Override
            public void onResponse(Call<com.inqube.aamarmedic.model.agentprofileupdate.MSG> call, Response<com.inqube.aamarmedic.model.agentprofileupdate.MSG> response) {
                System.out.println("SERVICE Response Code::" + response.code());
                //Log.w(" Full json ", new Gson().toJson(response));
                ReturnResponse rc = new ResponseCategory(activity).checkErrorCode(response.code());
                if (rc.isRet_status()) {
                    retRes.onSuccess(response, "updateAgentProfileData");
                } else {
                    retRes.onFailure(rc);
                }
                activity.enableScreen();
            }

            @Override
            public void onFailure(Call<com.inqube.aamarmedic.model.agentprofileupdate.MSG> call, Throwable t) {
                retRes.onResponseFailure();
                activity.enableScreen();
                Log.d("onFailure", t.toString());
            }
        });
    }
}