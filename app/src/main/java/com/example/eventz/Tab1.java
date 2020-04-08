package com.example.eventz;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class Tab1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageButton btnI1, btnI2, btnI3, btnI4;
    Button btn1, btn2, btn3, btn4;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
    }

    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        btnI1 = (ImageButton) view.findViewById(R.id.imageEvent1);
        btnI2 = (ImageButton) view.findViewById(R.id.imageEvent2);
        btnI3 = (ImageButton) view.findViewById(R.id.imageEvent3);
        btnI4 = (ImageButton) view.findViewById(R.id.imageEvent4);
        btn1 = (Button) view.findViewById(R.id.btnEvent1);
        btn2 = (Button) view.findViewById(R.id.btnEvent2);
        btn3 = (Button) view.findViewById(R.id.btnEvent3);
        btn4 = (Button) view.findViewById(R.id.btnEvent4);

        btnI1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context this_activity = getActivity();
                startActivity(new Intent(this_activity, Event1.class));
            }
        });

        btn1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context this_activity = getActivity();
                startActivity(new Intent(this_activity, Event1.class));
            }
        });

        btnI2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context this_activity = getActivity();
                startActivity(new Intent(this_activity, Event2.class));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context this_activity = getActivity();
                startActivity(new Intent(this_activity, Event2.class));
            }
        });

        btnI3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context this_activity = getActivity();
                startActivity(new Intent(this_activity, Event3.class));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context this_activity = getActivity();
                startActivity(new Intent(this_activity, Event3.class));
            }
        });

        btnI4.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context this_activity = getActivity();
                startActivity(new Intent(this_activity, Event4.class));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context this_activity = getActivity();
                startActivity(new Intent(this_activity, Event4.class));
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
