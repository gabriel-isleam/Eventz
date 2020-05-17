package com.example.eventz;

import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.app.Activity.RESULT_OK;

public class Tab4 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseAuth mFirebaseAuth;
    Button logoutButton; //BUTONUL DE LOGOUT
    TextView manage_profile;
    private ImageView mImageView;
    private static final int PICK_IMAGE = 1001;
    Uri mImageUri;
    ImageButton heart;
    DatabaseReference mDatabase;
    StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();


    private ImageView mImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference mProfileRef = mStorageReference.child("usersProf/"+ userUid + ".jpg");
        mProfileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(mImageView);
            }
        });
        TextView mail_addr = (TextView) view.findViewById(R.id.adrEmail);
        mImageView = (ImageView) view.findViewById(R.id.ProfileImage);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

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

        manage_profile = view.findViewById(R.id.manage_profile);
        manage_profile.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Context this_activity = getActivity();
                //redirectare catre pagina de settings
                startActivity(new Intent(this_activity, Settings.class));
            }
        });

        return view;

    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri FilePathUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), FilePathUri);
               //mImageView.setImageBitmap(bitmap);

                upload(FilePathUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void upload(Uri uri) {

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference fileRef = mStorageReference.child("usersProf/"+ userUid + ".jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(mImageView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
                //Toast.makeText(getContext(), "Image uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Image failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}