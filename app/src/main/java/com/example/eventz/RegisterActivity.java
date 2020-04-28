package com.example.eventz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.*;


/*
REGISTER PAGE
 */
public class RegisterActivity extends AppCompatActivity {
    EditText emailID, password;
    Button registerButton;
    TextView login;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.editText3);
        password = findViewById(R.id.editText4);
        registerButton =  findViewById(R.id.signupbutton);
        login = findViewById(R.id.textView3);
        progressBar = (ProgressBar)findViewById(R.id.progressbar2);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailID.getText().toString();
                String pwd = password.getText().toString();

                if(email.isEmpty()) {
                    emailID.setError("Please enter email");
                    emailID.requestFocus();
                }
                else if(pwd.isEmpty()) {
                    password.setError("Please insert password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty() ) {
                    Toast.makeText(RegisterActivity.this, "Fields are empty!", Toast.LENGTH_SHORT);
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);

                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if(!task.isSuccessful()){
                                if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "You are already registered!", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else {
                                    Toast toast = Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                            else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Sign up succesful!", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP| Gravity.LEFT, 0, 0);
                                toast.show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            }
                        }
                    });
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }
}
