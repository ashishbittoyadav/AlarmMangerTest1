package com.headspire.alarmmangertest;


import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneInformation extends Fragment {

    private TextView textView;
    private Operation operation;
    public PhoneInformation()
    {

    }
    @SuppressLint("ValidFragment")
    public PhoneInformation(Operation operation) {
        // Required empty public constructor
        this.operation=operation;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_phone_information, container, false);
        textView=view.findViewById(R.id.phone_state_information);
        textView.setText(operation.phoneStateLoad());
        // Inflate the layout for this fragment
        return view;
    }

}
