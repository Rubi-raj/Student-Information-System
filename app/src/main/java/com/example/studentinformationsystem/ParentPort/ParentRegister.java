package com.example.studentinformationsystem.ParentPort;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentinformationsystem.R;
import com.example.studentinformationsystem.StaffPort.StaffRegister;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ParentRegister extends AppCompatActivity {

    private TextView bottomText;
    private ProgressDialog progressDialog;
    private EditText Name, StudentName, StudentRegisterNumber, MobileNumber, Email, Password;
    private Spinner Subject;
    private RadioGroup Gender;
    private Button btnRegister;
    String pName, pStudentName, pStudentRegisterNumber, pGender, pMobileNumber, pEmail, pPassword;
    private CircleImageView ProfilePic;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private static final int PICK_IMAGE = 2;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_register);
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
                startActivity(new Intent(ParentRegister.this, ParentLogin.class));
            }
        });

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
                                Toast.makeText(ParentRegister.this, "📝Registration failed🙄", Toast.LENGTH_SHORT).show();
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
        bottomText = (TextView) findViewById(R.id.pbtmtext);
        Name = (EditText) findViewById(R.id.pname);
        StudentName = (EditText) findViewById(R.id.pstudentName);
        StudentRegisterNumber = (EditText) findViewById(R.id.pstudentRegisterNumber);
        Gender = (RadioGroup) findViewById(R.id.pGender);
        MobileNumber = (EditText) findViewById(R.id.pmobileNumber);
        Email = (EditText) findViewById(R.id.pemail);
        Password = (EditText) findViewById(R.id.ppassword);
        btnRegister = (Button) findViewById(R.id.pbtnRegister);
        ProfilePic = (CircleImageView) findViewById(R.id.puploadimg);
    }

    private Boolean validate() {
        Boolean result = false;
        pName = Name.getText().toString().trim();
        pStudentName = StudentName.getText().toString().trim();
        pStudentRegisterNumber = StudentRegisterNumber.getText().toString().trim();
        int rid = Gender.getCheckedRadioButtonId();
        RadioButton radioButton;
        radioButton = (RadioButton) findViewById(rid);
        pGender = radioButton.getText().toString();
        pMobileNumber = MobileNumber.getText().toString().trim();
        pEmail = Email.getText().toString().trim();
        pPassword = Password.getText().toString().trim();

        if (pName.isEmpty() || pStudentName.isEmpty() || pStudentRegisterNumber.isEmpty() || pMobileNumber.isEmpty() || pEmail.isEmpty() || pPassword.isEmpty()) {
            Toast.makeText(ParentRegister.this, "📝Please enter all the Details", Toast.LENGTH_SHORT).show();
        } else if (imageUri == null) {
            Toast.makeText(ParentRegister.this, "Pic Profile picture from Gallery", Toast.LENGTH_SHORT).show();
        } else if (pMobileNumber.length() != 10) {
            MobileNumber.setError("Invalid📱Mobile Number");
        } else if (!pEmail.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
            Email.setError("Invalid Email Id");
        } else if (pPassword.length() < 6) {
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
                        Toast.makeText(ParentRegister.this, "Verification mail sent!📩", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(ParentRegister.this, ParentLogin.class));
                    } else {
                        Toast.makeText(ParentRegister.this, "Verification mail not send", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Parent").child(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child("Parent").child("Profile Pictures").child(firebaseAuth.getUid());
        UploadTask uploadTask = imageReference.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ParentRegister.this, "Image not Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.setMessage("Uploading Image..!");
            }
        });
        ParentProfile parentProfile = new ParentProfile(pName, pStudentName, pStudentRegisterNumber, pGender, pMobileNumber, pEmail);
        databaseReference.setValue(parentProfile);
    }
}
