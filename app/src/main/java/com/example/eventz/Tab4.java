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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Tab4 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth mFirebaseAuth;

    Button logoutButton; //BUTONUL DE LOGOUT

    private String mParam1, mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab4() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tab4, container, false);
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        System.out.println("email = " + user.getEmail());

        TextView mail_addr = (TextView) view.findViewById(R.id.adrEmail);
        mail_addr.setText(user.getEmail());
        //APASARE BUTON LOGOUT
        logoutButton = (Button) view.findViewById(R.id.logoutbutton);

        logoutButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                FirebaseUser user = mFirebaseAuth.getCurrentUser();
                System.out.println("email =---------------" + user.getEmail());
                FirebaseAuth.getInstance().signOut();
                Context this_activity = getActivity();
                //redirectare catre pagina de login (MainActivity)
                startActivity(new Intent(this_activity, MainActivity.class));
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
