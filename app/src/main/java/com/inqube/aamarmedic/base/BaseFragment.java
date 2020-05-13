package com.inqube.aamarmedic.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


public class BaseFragment extends Fragment {
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState, int layout) {
        // Inflate the layout for this fragment
        view= inflater.inflate(layout, container, false);
        view.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                /** Intercepts touches from going through. */
                return true;
            }
        });
        return view;
    }
}