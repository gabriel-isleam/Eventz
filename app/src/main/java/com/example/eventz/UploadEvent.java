package com.example.eventz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;

public class UploadEvent extends AppCompatActivity {
    /* path-ul pt. Firebase Storage-ul unde stocam imaginile */
    String Storage_Path = "images/";
    String Database_Path = "event_list";
    /* ne trebuie pt. a extrage id-ul userului care adauga evenimentul*/
    FirebaseAuth mFirebaseAuth;

    /* referinta catre storage si catre baza de date event_list*/
    StorageReference storageReference;
    DatabaseReference databaseReference;

    Button ChooseButton, UploadButton;
    EditText eventNameEditText;
    EditText eventDescriptionEditText;
    EditText eventDateEditText;
    EditText eventLocationEditText;
    EditText eventTicketsEditText;
    EditText eventStudentPriceEditText;
    EditText eventAdultPriceEditText;
    ImageView SelectImage;

    Uri FilePathUri;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_event);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);
        mFirebaseAuth = FirebaseAuth.getInstance();

        ChooseButton = (Button) findViewById(R.id.ButtonChooseImage);
        UploadButton = (Button) findViewById(R.id.ButtonUploadImage);
        eventNameEditText = (EditText) findViewById(R.id.ImageNameEditText);
        eventDescriptionEditText = (EditText) findViewById(R.id.DescriptionEditText);
        eventDateEditText = (EditText) findViewById(R.id.HourEditText);
        eventLocationEditText = (EditText) findViewById(R.id.LocationEditText);
        eventTicketsEditText = (EditText) findViewById(R.id.TicketsEditText);
        eventAdultPriceEditText = (EditText) findViewById(R.id.AdultPriceEditText);
        eventStudentPriceEditText = (EditText) findViewById(R.id.StudentPriceEditText);
        SelectImage = (ImageView) findViewById(R.id.ShowImageView);

        progressDialog = new ProgressDialog(UploadEvent.this);

        // Adaugare Click listener pt. Choose image button
        ChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
            }
        });

        // Adaugare Click listener pt. Upload event button.
        UploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadEventToFirebaseStorage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            try {
                // inserare imagine selectata in Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                // incarcare imagine in ImageView.
                SelectImage.setImageBitmap(bitmap);
                ChooseButton.setText("Image Selected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * functie ce det. daca data si ora sunt introduse corect
    */
    public static boolean checkDateAndTime(String s) {
        String delims = " ";
        String[] tokens = s.split(delims);

        if (tokens.length != 3)
            return false;

        String date = tokens[0], hour = tokens[2];
        String delims2 = "-", delims3 = ":";
        String[] tokens2 = date.split(delims2);
        String[] tokens3 = hour.split(delims3);

        if (tokens2.length != 3 || tokens3.length != 2) {
            return false;
        }
        for (int i = 0; i < tokens2.length; i++) {
            boolean numeric = true;
            numeric = tokens2[i].matches("\\d+");
            if (!numeric || (tokens2[i].length() != 2 && i != 2) || (tokens2[i].length() != 4 && i == 2)) {
                return false;
            }
        }
        for (int i = 0; i < tokens3.length; i++) {
            boolean numeric = true;
            numeric = tokens3[i].matches("\\d+");
            if (!numeric || tokens3[i].length() != 2) {
                return false;
            }
        }
        return true;
    }
    public void UploadEventToFirebaseStorage() {
        final String eventName = eventNameEditText.getText().toString().trim();
        final String eventNameLower = eventName.toLowerCase();
        final String description = eventDescriptionEditText.getText().toString().trim();
        final String date = eventDateEditText.getText().toString().trim();
        final String location = eventLocationEditText.getText().toString().trim();
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();
        final String tickets_no = eventTicketsEditText.getText().toString().trim();
        final String student_price = eventStudentPriceEditText.getText().toString().trim();
        final String adult_price = eventAdultPriceEditText.getText().toString().trim();

        if (eventName.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty() ||
                tickets_no.isEmpty() || student_price.isEmpty() || adult_price.isEmpty() || FilePathUri == null) {
            Toast.makeText(UploadEvent.this, "Please insert all requested data", Toast.LENGTH_LONG).show();
        } else if (!checkDateAndTime(date)) {
            Toast.makeText(UploadEvent.this, "Please insert date & time in the specified format",
                    Toast.LENGTH_LONG).show();
        } else {
            progressDialog.setTitle("Event is Uploading...");
            progressDialog.show();
            final StorageReference storageRef2 = storageReference.child(Storage_Path + FilePathUri.getLastPathSegment());
            storageRef2.putFile(FilePathUri).continueWithTask(new Continuation < UploadTask.TaskSnapshot, Task < Uri >> () {
                @Override
                public Task < Uri > then(@NonNull Task < UploadTask.TaskSnapshot > task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return storageRef2.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener < Uri > () {
                @Override
                public void onComplete(@NonNull Task < Uri > task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String userId = user.getUid();
                        /* poza s-a uploadat => oprim progressbar-ul*/
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Event Uploaded Successfully ", Toast.LENGTH_LONG).show();
                        @SuppressWarnings("VisibleForTests")
                        Event eventInfo = new Event(eventName, eventNameLower, downloadUri.toString(), description, date, location, userId, tickets_no, student_price, adult_price);
                        String eventId = databaseReference.push().getKey();
                        /* adaugare eveniment nou in baza de date*/
                        databaseReference.child(eventId).setValue(eventInfo);
                        /* adaugare eveniment in lista de evenimente a userului avand acelasi key id */
                        databaseReference = FirebaseDatabase.getInstance().getReference("user_events");
                        databaseReference.child(userId).child(eventId).setValue(eventInfo);


                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(UploadEvent.this, "Could't load event, please try again", Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(UploadEvent.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}