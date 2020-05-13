package com.inqube.aamarmedic.coronavirusfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.inqube.aamarmedic.R;
import com.inqube.aamarmedic.agentfragment.AgentDashboardFragment;
import com.inqube.aamarmedic.base.BaseActivity;
import com.inqube.aamarmedic.base.BaseActivityWithoutMenu;
import com.inqube.aamarmedic.base.BaseFragment;
import com.inqube.aamarmedic.doctorfragment.DoctorAppointmentFragment;
import com.inqube.aamarmedic.util.AllInterfaces;
import com.inqube.aamarmedic.util.ReturnResponse;

public class AboutCoronaVirusFragment extends BaseFragment implements AllInterfaces.RetrofitResponseToActivityOrFrament,
        View.OnClickListener  {

    private WebView wv;
    private View view;
    private boolean resume;
    private ImageView imv_home, imv_menu;
    private TextView tv_menu_title;

    private OnAamarMedicCoronaVirusFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_about_corona_virus, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!resume) {
            ((BaseActivity)getActivity()).pb_loader = (ProgressBar) view.findViewById(R.id.pb_loader);
            setUI(view);
        }
        resume = true;
    }

    private void setUI(View view) {

        wv =(WebView)view.findViewById(R.id.wv_content);

        tv_menu_title = (TextView)((BaseActivity)getActivity()).findViewById(R.id.tv_menu_title);
        tv_menu_title.setText(getString(R.string.covid_19));

        imv_menu = (ImageView)((BaseActivity)getActivity()).findViewById(R.id.imv_menu);
        imv_menu.setVisibility(View.GONE);

        imv_home = (ImageView)((BaseActivity)getActivity()).findViewById(R.id.imv_home);
        imv_home.setVisibility(View.VISIBLE);

        imv_home.setOnClickListener(this);

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //wv.loadUrl("file:///android_asset/aboutcovid19.html");
        wv.loadUrl("http://128.199.165.116/epoc-new/public/corona/");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_home:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fm_container, new AgentDashboardFragment(), "AgentDashboardFragment")
                        .addToBackStack("AgentDashboardFragment")
                        .commit();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAamarMedicCoronaVirusFragmentInteractionListener) {
            mListener = (OnAamarMedicCoronaVirusFragmentInteractionListener) context;
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
    public void onSuccess(Object response, String which_method) {

    }

    @Override
    public void onFailure(ReturnResponse response) {
        mListener.onAamarMedicCoronaVirusFragmentInteractionListener(response.getMsg());
    }

    @Override
    public void onResponseFailure() {
        mListener.onAamarMedicCoronaVirusFragmentInteractionListener(getString(R.string.please_try_again));
    }

    @Override
    public void onResponseFailure(String msg) {

    }

    public interface OnAamarMedicCoronaVirusFragmentInteractionListener
    {
        void onAamarMedicCoronaVirusFragmentInteractionListener();
        void onAamarMedicCoronaVirusFragmentInteractionListener(String msg);
    }
}
