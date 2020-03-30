package com.example.studentinformationsystem.ParentPort;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentinformationsystem.ForgotPassword;
import com.example.studentinformationsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ParentLogin extends AppCompatActivity {
    private TextView bottomText, ForgotPass;
    private Button Login;
    private TextView UserName;
    private TextView Password;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_login);

        UserName = (TextView) findViewById(R.id.puserName);
        Password = (TextView) findViewById(R.id.ppassword);
        Login = (Button) findViewById(R.id.pbtnLogin);
        bottomText = (TextView) findViewById(R.id.ploginbottomText);
        ForgotPass = (TextView) findViewById(R.id.pforgotPass);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            finish();
            startActivity(new Intent(ParentLogin.this, Parent.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUser = UserName.getText().toString();
                String sPass = Password.getText().toString();
                if (sUser.isEmpty()) {
                    UserName.setError("Enter UserName");
                } else if (!sUser.matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {
                    UserName.setError("Invalid Email Id");
                } else if (sPass.isEmpty()) {
                    Password.setError("Enter Password");
                } else if (sPass.length() < 6) {
                    Password.setError("📱Minimum 6 digits");
                } else {
                    validate(UserName.getText().toString(), Password.getText().toString());
                }
            }
        });
        bottomText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentLogin.this, ParentRegister.class));
            }
        });
        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ParentLogin.this, ForgotPassword.class));
            }
        });
    }

    private void validate(String name, String pass) {

        progressDialog.setMessage("Logging In...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(name, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    checkEmailVerification();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ParentLogin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        if (emailFlag) {
            finish();
            Toast.makeText(ParentLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ParentLogin.this, Parent.class));
        } else {
            Toast.makeText(this, "Please Verify your Email📧", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
