package com.example.studentinformationsystem.StaffPort;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class StaffRegister extends AppCompatActivity {

    private TextView bottomText;
    private ProgressDialog progressDialog;
    public EditText Name, StaffId, MobileNumber, Email, Password;
    private Spinner Subject;
    private RadioGroup Gender;
    private Button btnRegister;
    String tName, tStaffId, tSubject, tGender, tMobileNumber, tEmail, tPassword;
    private CircleImageView ProfilePic;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private static final int PICK_IMAGE = 2;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_register);
        setupUIViews();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        ProfilePic.setOnClickListener(new View.OnClickListener() {
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
                startActivity(new Intent(StaffRegister.this, StaffLogin.class));
            }
        });

        Subject = (Spinner) findViewById(R.id.tsubject);
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("Tamil");
        list1.add("English");
        list1.add("Mathematics");
        list1.add("Science");
        list1.add("Social Science");
        list1.add("Physics");
        list1.add("Chemistry");
        list1.add("Botany");
        list1.add("Zoology");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list1);
        Subject.setAdapter(adapter1);

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
                                Toast.makeText(StaffRegister.this, "📝Registration failed🙄", Toast.LENGTH_SHORT).show();
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
            ProfilePic.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setupUIViews() {
        bottomText = (TextView) findViewById(R.id.tbtmtext);
        Name = (EditText) findViewById(R.id.tname);
        StaffId = (EditText) findViewById(R.id.tstaffid);
        Gender = (RadioGroup) findViewById(R.id.tGender);
        MobileNumber = (EditText) findViewById(R.id.tmnum);
        Email = (EditText) findViewById(R.id.temail);
        Password = (EditText) findViewById(R.id.tpass);
        btnRegister = (Button) findViewById(R.id.tbtnRegister);
        ProfilePic = (CircleImageView) findViewById(R.id.tuploadimg);
    }

    private Boolean validate() {
        Boolean result = false;
        tName = Name.getText().toString().trim();
        tStaffId = StaffId.getText().toString().trim();
        tSubject = Subject.getSelectedItem().toString();
        int rid = Gender.getCheckedRadioButtonId();
        RadioButton radioButton;
        radioButton = (RadioButton) findViewById(rid);
        tGender = radioButton.getText().toString();
        tMobileNumber = MobileNumber.getText().toString().trim();
        tEmail = Email.getText().toString().trim();
        tPassword = Password.getText().toString().trim();

        if (tName.isEmpty() || tStaffId.isEmpty()|| tMobileNumber.isEmpty() || tEmail.isEmpty() || tPassword.isEmpty()) {
            Toast.makeText(StaffRegister.this, "📝Please enter all the Details", Toast.LENGTH_SHORT).show();
        } else if (imageUri == null) {
            Toast.makeText(StaffRegister.this, "Pic Profile picture from Gallery", Toast.LENGTH_SHORT).show();
        } else if (tMobileNumber.length() != 10) {
            MobileNumber.setError("Invalid📱Mobile Number");
        } else if (!tEmail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            Email.setError("Invalid Email Id");
        } else if (tPassword.length() < 6) {
            Password.setError("📱Minimum 6 digits");
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
                        Toast.makeText(StaffRegister.this, "Verification mail sent!📩", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(StaffRegister.this, StaffLogin.class));
                    } else {
                        Toast.makeText(StaffRegister.this, "Verification mail not send", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Staff").child(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child("Staff").child("Profile Pictures").child(firebaseAuth.getUid());
        UploadTask uploadTask = imageReference.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StaffRegister.this, "Image not Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.setMessage("Uploading Image..!");
            }
        });
        StaffProfile staffProfile = new StaffProfile(tName, tStaffId, tSubject, tGender, tMobileNumber, tEmail);
        databaseReference.setValue(staffProfile);
    }
}
