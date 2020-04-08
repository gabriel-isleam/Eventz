package com.example.eventz;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
LOGIN PAGE
 */
public class MainActivity extends AppCompatActivity {

    EditText emailID, password;
    Button loginButton;
    TextView signUp;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailID = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        loginButton =  findViewById(R.id.button);
        signUp = findViewById(R.id.textView2);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null) {
                    Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT);
                    Intent i = new Intent(MainActivity.this, HomePageActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT);
                }
            }
        };
        loginButton.setOnClickListener(new View.OnClickListener() {
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
                else if(email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields are empty!", Toast.LENGTH_SHORT);
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);

                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if(!task.isSuccessful()) {
                              //CONTUL NU EXISTA => afisare mesaj corespunzator
                              Toast toast = Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG);
                              toast.show();
                              progressBar.setVisibility(View.GONE);
                          }
                          else {
                              //CONTUL NU EXISTA => intram in homepage
                              Toast toast = Toast.makeText(getApplicationContext(), "Sucessful login!", Toast.LENGTH_LONG);
                              toast.show();
                              startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                          }
                      }
                  });
                }
            }
        });

        //apasare buton signup => trecem in pagina de signup
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
