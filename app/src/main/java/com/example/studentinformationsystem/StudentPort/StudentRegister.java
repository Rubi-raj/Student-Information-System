package com.example.studentinformationsystem.StudentPort;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentinformationsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentRegister extends AppCompatActivity {

    private TextView bottomText;
    private ProgressDialog progressDialog;
    public EditText Name, RegisterNumber, ParentName, MobileNumber, Email, Password;
    private Spinner Standard, Section;
    private RadioGroup Gender;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    String sName, sRegisterNumber, sStandard, sSection, sGender, sParentName, sEmail, sMobileNumber, sPassword, id, username, imageURL, status;
    private StorageReference storageReference;
    private CircleImageView Picture;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);
        setupUIViews();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "select picture"), PICK_IMAGE);
            }
        });
        bottomText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(StudentRegister.this, StudentLogin.class));
            }
        });

        Standard = (Spinner) findViewById(R.id.sstandard);
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("I");
        list1.add("II");
        list1.add("III");
        list1.add("IV");
        list1.add("V");
        list1.add("VI");
        list1.add("VII");
        list1.add("VIII");
        list1.add("IX");
        list1.add("X");
        list1.add("XI");
        list1.add("XII");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list1);
        Standard.setAdapter(adapter1);

        Section = (Spinner) findViewById(R.id.ssection);
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("A");
        list2.add("B");
        list2.add("C");
        list2.add("D");
        list2.add("E");
        list2.add("F");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list2);
        Section.setAdapter(adapter2);

            progressDialog = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String s_email = Email.getText().toString().trim();
                    String s_password = Password.getText().toString().trim();

                    progressDialog.setMessage("📝Registering...!");
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(s_email, s_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                sendUserData();
                                sendEmailVerification();
                                progressDialog.setMessage("Registered Successful..!");
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(StudentRegister.this, "📝Registration failed🙄", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null)
            imageUri = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            Picture.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setupUIViews() {
        Name = (EditText) findViewById(R.id.sname);
        RegisterNumber = (EditText) findViewById(R.id.sregNo);
        Standard = (Spinner) findViewById(R.id.sstandard);
        Section = (Spinner) findViewById(R.id.ssection);
        Gender = (RadioGroup) findViewById(R.id.sivgender);
        ParentName = (EditText) findViewById(R.id.sparentName);
        MobileNumber = (EditText) findViewById(R.id.smobileNumber);
        Email = (EditText) findViewById(R.id.semail);
        Password = (EditText) findViewById(R.id.spassword);
        btnRegister = (Button) findViewById(R.id.sbtnRegister);
        bottomText = (TextView) findViewById(R.id.sbottomText);
        Picture = (CircleImageView) findViewById(R.id.suploadimg);
    }

    private Boolean validate() {
        Boolean result = false;

        sName = Name.getText().toString().trim();
        sRegisterNumber = RegisterNumber.getText().toString().trim();
        sStandard = Standard.getSelectedItem().toString();
        sSection = Section.getSelectedItem().toString();
        int rid = Gender.getCheckedRadioButtonId();
        RadioButton radioButton;
        radioButton = (RadioButton) findViewById(rid);
        sGender = radioButton.getText().toString();
        sParentName = ParentName.getText().toString().trim();
        sMobileNumber = MobileNumber.getText().toString().trim();
        sEmail = Email.getText().toString().trim();
        sPassword = Password.getText().toString().trim();
        username = sName;
        imageURL="default";
        status = "offline";


        if (sName.isEmpty() || sRegisterNumber.isEmpty() || sParentName.isEmpty() || sMobileNumber.isEmpty() || sEmail.isEmpty() || sPassword.isEmpty()) {
            Toast.makeText(StudentRegister.this, "📝Please enter all the Details", Toast.LENGTH_SHORT).show();
        } else if (imageUri == null) {
            Toast.makeText(StudentRegister.this, "Pic Profile picture from Gallery", Toast.LENGTH_SHORT).show();
        } else if (sMobileNumber.length() != 10) {
            MobileNumber.setError("Invalid📱Mobile Number");
            MobileNumber.requestFocus();
        } else if (!sEmail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            Email.setError("Invalid Email Id");
            Email.requestFocus();
        } else if (sPassword.length() < 6) {
            Password.setError("📱Minimum 6 digits");
            Password.requestFocus();
        } else {
            result = true;
        }
        return result;
    }

    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(StudentRegister.this, "Verification mail sent!📩", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(StudentRegister.this, StudentLogin.class));
                    } else {
                        Toast.makeText(StudentRegister.this, "Verification mail not send", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Student").child(firebaseAuth.getUid());
        id=firebaseAuth.getUid();
        StorageReference imageReference = storageReference.child("Student").child("Profile Pictures").child(firebaseAuth.getUid());
        UploadTask uploadTask = imageReference.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentRegister.this, "Image not Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.setMessage("Uploading Image..!");
            }
        });
        StudentProfile studentProfile = new StudentProfile(sName, sRegisterNumber, sStandard, sSection, sGender, sParentName, sMobileNumber, sEmail, id, username, imageURL, status);
        databaseReference.setValue(studentProfile);
    }
}
