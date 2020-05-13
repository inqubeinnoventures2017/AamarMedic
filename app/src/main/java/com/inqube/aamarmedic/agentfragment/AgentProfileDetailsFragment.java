package com.inqube.aamarmedic.agentfragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.inqube.aamarmedic.AgentDashboardMainFragmentActivity;
import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.SignInActivity;
import com.inqube.aamarmedic.app.Config;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseFragment;
import com.inqube.aamarmedic.dialog.StateDailog;
import com.inqube.aamarmedic.model.baseModel.BaseModelClass;
import com.inqube.aamarmedic.model.languagelist.MSG;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.CircleTransform;
import com.inqube.aamarmedic.util.FileUtils;
import com.inqube.aamarmedic.util.ReturnResponse;
import com.inqube.aamarmedic.util.UtilClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

public class AgentProfileDetailsFragment extends BaseFragment implements AllInterfaces.RetrofitResponseToActivityOrFrament,
        AllInterfaces.AdapterCallback, View.OnClickListener,AllInterfaces.StateCallback {

    private View view;
    private boolean resume;
    private ImageView imv_home, imv_user_pic, imv_menu, imv_user_img;
    private EditText edt_name, edt_mobileno, edt_emailid;
    private TextView tv_lang,tv_menu_title;
    private Button btnSubmit;
    private ProgressBar pb_loader;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", SelectedLanguageId;
    private Uri fileUri;
    private ArrayList<BaseModelClass> al_dialog_list;
    Locale myLocale;
    private List<com.inqube.aamarmedic.model.agentprofiledetails.Result> listProfile;
    private List<com.inqube.aamarmedic.model.languagelist.Result> listLang;

    private OnAamarMedicAgentProfileDetailsFragmentInteractionListener mListener;
    private JSONArray imageArray = new JSONArray();
    private List<Uri> imageUri = new ArrayList<>(1);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_user_info_edit, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!resume) {
            pb_loader = view.findViewById(R.id.pb_loader);
            requestPermission();
            setUI(view);
        }
        resume = true;
    }

    private void setUI(View view) {
        imv_user_pic = view.findViewById(R.id.imv_user_pic);
        imv_user_img= view.findViewById(R.id.imv_user_img);

        edt_name = view.findViewById(R.id.edt_name);
        edt_mobileno = view.findViewById(R.id.edt_mobileno);
        edt_emailid = view.findViewById(R.id.edt_emailid);
        tv_lang = view.findViewById(R.id.tv_language);

        btnSubmit = view.findViewById(R.id.btnSubmit);

        pb_loader = view.findViewById(R.id.pb_loader);

        imv_menu =  getActivity().findViewById(R.id.imv_menu);
        imv_menu.setVisibility(View.GONE);

        imv_home = getActivity().findViewById(R.id.imv_home);
        imv_home.setVisibility(View.VISIBLE);

        tv_menu_title = getActivity().findViewById(R.id.tv_menu_title);
        tv_menu_title.setText(getString(R.string.agent_profile));

        imv_home.setOnClickListener(this);
        imv_user_pic.setOnClickListener(this);
        tv_lang.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        if (((BaseActivity)getActivity()).isDeviceOnline()) {
            ((BaseActivity)getActivity()).pb_loader.setVisibility(View.VISIBLE);
            btnSubmit.setEnabled(false);
            UtilClass.getInstance().getAgentProfileDetailsData(((BaseActivity)getActivity()),AgentProfileDetailsFragment.this,
                    ((BaseActivity)getActivity()).getUserPreference(Config.AGENT_ID,""),((BaseActivity)getActivity()).getUserPreference(Config.AUTH_TOKEN,""));
        }else{
            mListener.onAamarMedicAgentProfileDetailsFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
        }

        /*if (((BaseActivity)getActivity()).getDataPreference(Config.SELECTED_LANGUAGE,"").equalsIgnoreCase(Config.ENGLISH)){
            rb_english.setChecked(true);
        }else if (((BaseActivity)getActivity()).getDataPreference(Config.SELECTED_LANGUAGE,"").equalsIgnoreCase(Config.SPANISH)){
            rb_spanish.setChecked(true);*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAamarMedicAgentProfileDetailsFragmentInteractionListener) {
            mListener = (OnAamarMedicAgentProfileDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imv_home:
                mListener.onAamarMedicAgentProfileDetailsFragmentInteractionListener();
                break;
            case R.id.imv_user_pic:
                try{
                    takePhoto();
                }catch (Exception e){
                    System.out.println(" CAMERA EXCEPTION "+e);
                }
                break;
            case R.id.tv_language:
                if (((BaseActivity)getActivity()).isDeviceOnline()) {
                    ((BaseActivity) getActivity()).pb_loader.setVisibility(View.VISIBLE);

                    UtilClass.getInstance().getLanguageListData(((BaseActivity) getActivity()), AgentProfileDetailsFragment.this,
                            ((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                }else{
                    mListener.onAamarMedicAgentProfileDetailsFragmentInteractionListener(getString(R.string.sorry_you_not_online_msg));
                }
                break;
            case R.id.btnSubmit:
                if (!emptyCheckValidation()) {
                    if (((BaseActivity) getActivity()).isDeviceOnline()) {
                        ((BaseActivity) getActivity()).pb_loader.setVisibility(View.VISIBLE);

                        /*System.out.println("Agent Id:"+((BaseActivity) getActivity()).getUserPreference(Config.AGENT_ID, ""));
                        System.out.println("Name:"+edt_name.getText().toString());
                        System.out.println("Email:"+edt_emailid.getText().toString());
                        System.out.println("Mobile:"+edt_mobileno.getText().toString());
                        System.out.println("Language Id:"+SelectedLanguageId);
                        System.out.println("Token:"+((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));*/

                        UtilClass.getInstance().updateAgentProfileData(((BaseActivity)getActivity()),AgentProfileDetailsFragment.this,
                                ((BaseActivity) getActivity()).getUserPreference(Config.AGENT_ID, ""),
                                edt_name.getText().toString(),edt_emailid.getText().toString(),edt_mobileno.getText().toString(),
                                SelectedLanguageId,((BaseActivity) getActivity()).getUserPreference(Config.AUTH_TOKEN, ""));
                    }
                }
                break;
        }
    }

    private boolean emptyCheckValidation()
    {
        if(edt_name.getText().length() < 1)
        {
            mListener.onAamarMedicAgentProfileDetailsFragmentInteractionListener(getString(R.string.Please_enter_the_user_name));
            return true;
        }

        if(edt_mobileno.getText().length() < 10)
        {
            mListener.onAamarMedicAgentProfileDetailsFragmentInteractionListener(getString(R.string.Please_enter_tendigit_mb));
            return true;
        }

        if(edt_emailid.getText().length() < 1)
        {
            mListener.onAamarMedicAgentProfileDetailsFragmentInteractionListener(getString(R.string.Please_enter_emailid));
            return true;
        }else if (!edt_emailid.getText().toString().trim().matches(emailPattern))
        {   mListener.onAamarMedicAgentProfileDetailsFragmentInteractionListener(getString(R.string.Please_enter_valid_email));
            return true;
        }

        return false;
    }

    @Override
    public void onSuccess(Object response, String which_method) {
        ((BaseActivity) getActivity()).pb_loader.setVisibility(View.GONE);
        if (which_method.equalsIgnoreCase("updateAgentProfileData"))
        {
            Response<com.inqube.aamarmedic.model.agentprofileupdate.MSG> res = (Response<com.inqube.aamarmedic.model.agentprofileupdate.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());

            String lang_name = tv_lang.getText().toString().trim().toLowerCase();
            switch (lang_name)
            {
                case Config.ENGLISH:
                    ((BaseActivity) getActivity()).saveDataPreference(Config.SELECTED_LANGUAGE, Config.ENGLISH);
                    UtilClass.getInstance().setLocale(getActivity(), "en");
                    ((BaseActivity)getActivity()).saveUserPreference(Config.LANGUAGE_ID, SelectedLanguageId);
                    break;
                case Config.SPANISH:
                    ((BaseActivity) getActivity()).saveDataPreference(Config.SELECTED_LANGUAGE, Config.SPANISH);
                    UtilClass.getInstance().setLocale(getActivity(), "es");
                    ((BaseActivity)getActivity()).saveUserPreference(Config.LANGUAGE_ID, SelectedLanguageId);
                    break;
            }
            startActivity(new Intent(getActivity(), AgentDashboardMainFragmentActivity.class));
            getActivity().finish();
        }

        if (which_method.equalsIgnoreCase("getAgentProfileDetailsData")) {
            btnSubmit.setEnabled(true);
            Response<com.inqube.aamarmedic.model.agentprofiledetails.MSG> res = (Response<com.inqube.aamarmedic.model.agentprofiledetails.MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            if (res.body()!=null)
            {
                listProfile = res.body().getResult();
                if (listProfile.size()>0)
                {
                    edt_name.setText(listProfile.get(0).getName());
                    edt_mobileno.setText(listProfile.get(0).getMobile());
                    edt_emailid.setText(listProfile.get(0).getEmail());
                    if (listProfile.get(0).getLanguageId()!=null)
                    {
                        tv_lang.setText(listProfile.get(0).getLanguageId().getLanguageName());
                    }
                }
            }
        }

        if (which_method.equalsIgnoreCase("getLanguageListData")) {
            Response<com.inqube.aamarmedic.model.languagelist.MSG> res = (Response<MSG>) response;
            Gson gson = new Gson();
            String json = gson.toJson(res.body());
            listLang = res.body().getResult();
            if (listLang.size() > 0) {
                al_dialog_list = new ArrayList<>();
                for (int i = 0; i < listLang.size(); i++) {
                    BaseModelClass bm = new BaseModelClass("" + listLang.get(i).get_id(), listLang.get(i).getLanguageName(), "0");
                    al_dialog_list.add(bm);
                }

                if (al_dialog_list.size() > 0) {
                    StateDailog stateDailog = new StateDailog(((BaseActivity) getActivity()), al_dialog_list, this, Config.LANGUAGE_ID);
                    stateDailog.show();
                }
            }
        }
    }

    @Override
    public void onFailure(ReturnResponse response) {
        btnSubmit.setEnabled(true);
        mListener.onAamarMedicAgentProfileDetailsFragmentInteractionListener (response.getMsg());
    }

    @Override
    public void onResponseFailure() {
        btnSubmit.setEnabled(true);
        mListener.onAamarMedicAgentProfileDetailsFragmentInteractionListener (getString(R.string.please_try_again));
    }

    @Override
    public void onResponseFailure(String msg) {

    }

    @Override
    public void onReturn(String position) {

    }

    @Override
    public void onStateReturn(String position) {
        String str[] = position.split("-");
        //System.out.println("str[2]:"+str[2]);
        if (str[2].equalsIgnoreCase(Config.LANGUAGE_ID)) {
            String selectedLanguage = listLang.get(Integer.parseInt(str[1])).getLanguageName();
            SelectedLanguageId = ""+listLang.get(Integer.parseInt(str[1])).get_id();
            tv_lang.setText(" "+selectedLanguage);
            //System.out.println("SelectedLanguageId:"+SelectedLanguageId);
        }
    }

    public interface OnAamarMedicAgentProfileDetailsFragmentInteractionListener
    {
        void onAamarMedicAgentProfileDetailsFragmentInteractionListener();
        void onAamarMedicAgentProfileDetailsFragmentInteractionListener(String msg);
        void onAamarMedicUserInfoHomeFragmentInteractionListener();
    }

    public void takePhoto() {
       /* final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Config.MY_PERMISSIONS_REQUEST_CAMERA);*/

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
        intent.setDataAndType(uri,"image/*");
        // intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), Config.MY_PERMISSIONS_REQUEST_CAMERA);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //System.out.println( "RETURN "+requestCode+"  "+resultCode +" "+data.getDataString());
        switch (requestCode) {
            case Config.MY_PERMISSIONS_REQUEST_CAMERA:
                if (resultCode == Activity.RESULT_OK) {

                    Uri uri = data.getData();
                    if (uri != null) {
                        String uriString = uri.toString();
                        if (uriString.contains("content://")) {

                            //.out.println(" URI STRING " + uriString);
                            File myFile;
                            String displayName = null;
                            fileUri = Uri.parse(uriString);
                            //System.out.println("fileUri:"+fileUri);

                            myFile = FileUtils.getFile(getActivity(), uri);
                            String path = myFile.getAbsolutePath();
                            //System.out.println("path:"+path);
                            Cursor cursor = null;
                            try {
                                cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                }
                            } catch (Exception e) {
                                //System.out.println(" IMAGE EXCEPTION " + e.toString());
                            } finally {
                                cursor.close();
                            }


                            getActivity().getContentResolver().notifyChange(fileUri, null);
                            ContentResolver cr = getActivity().getContentResolver();
                            final Bitmap[] bitmap = new Bitmap[1];
                            try {
                                bitmap[0] = MediaStore.Images.Media
                                        .getBitmap(cr, fileUri);

                                bitmap[0] = UtilClass.getInstance().modifyOrientation(bitmap[0], path);
                                bitmap[0] = UtilClass.getInstance().scaleBitmap(bitmap[0], 150, 300);

                                imv_user_img.setImageBitmap(bitmap[0]);
                                imageUri.add(0, fileUri);

                                String filename = getRealPathFromUri(getActivity(), fileUri).substring(path.lastIndexOf("/") + 1);
                                //System.out.println("filename: "+filename);
                                try {

                                    JSONObject file = new JSONObject();
                                    file.put("file_title", filename);
                                    imageArray.put(file);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                        /*Thread t=new Thread(new AgentFarmerInfoFragment.MyThread(bitmap[0], new AgentFarmerInfoFragment.Callback() {
                            @Override
                            public void callback() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CircleTransform ct=new CircleTransform();
                                        bitmap[0] =ct.transform(bitmap[0]);
                                        imv_profile_pic.setImageBitmap(bitmap[0]);
                                    }
                                });
                            }
                        }));
                        t.start();*/
                                Toast.makeText(getActivity(), fileUri.toString(),
                                        Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), getString(R.string.failed_to_load), Toast.LENGTH_SHORT)
                                        .show();
                                Log.e("Camera", e.toString());
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    interface Callback {
        void callback(); // would be in any signature
    }

    class MyThread implements Runnable {

        Callback c;
        Bitmap bitmap;

        public MyThread(Bitmap bitmap, Callback c) {
            this.c = c;
            this.bitmap=bitmap;
        }

        public void run() {

            this.c.callback(); // callback
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Config.MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            //System.out.println(" SELECTE DURI ");
            setUI(view);
        }
    }

    @Override
    public void onRequestPermissionsResult(@NonNull int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Config.MY_PERMISSIONS_REQUEST_CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setUI(view);
        } else {
            Intent startMain = new Intent(getActivity(), SignInActivity.class);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            getActivity().finish();
        }
    }
}
