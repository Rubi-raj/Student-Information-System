package com.example.studentinformationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.studentinformationsystem.ParentPort.ParentLogin;
import com.example.studentinformationsystem.StaffPort.StaffLogin;
import com.example.studentinformationsystem.StudentPort.StudentLogin;

public class MainActivity extends AppCompatActivity {

    private ImageView student;
    private ImageView staff;
    private ImageView parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        student = (ImageView) findViewById(R.id.student);
        staff = (ImageView) findViewById(R.id.staff);
        parent = (ImageView) findViewById(R.id.parent);

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StudentLogin.class));
            }
        });
        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, StaffLogin.class));
            }
        });
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ParentLogin.class));
            }
        });
    }
}