package com.example.studentinformationsystem.StudentPort;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.studentinformationsystem.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfile extends Fragment {

    private CircleImageView ProfileImage;
    private TextView dsName,dsRegno,dsClass,dsSec,dsGender,dsPname,dsMnum,dsEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_student_myprofile, container, false);
        ProfileImage = (CircleImageView)root.findViewById(R.id.sProfileImage);
        dsName = (TextView) root.findViewById(R.id.stvname);
        dsRegno = (TextView)root.findViewById(R.id.stvregno);
        dsClass = (TextView)root.findViewById(R.id.stvclass);
        dsSec = (TextView)root.findViewById(R.id.stvsec);
        dsGender = (TextView)root.findViewById(R.id.stvgender);
        dsPname = (TextView)root.findViewById(R.id.stvparent);
        dsMnum = (TextView)root.findViewById(R.id.stvmno);
        dsEmail = (TextView)root.findViewById(R.id.stvemail);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Student").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StudentProfile userProfile = dataSnapshot.getValue(StudentProfile.class);
                dsName.setText(userProfile.getStuName());
                dsRegno.setText("Reg No :  "+userProfile.getStuRegisterNumber());
                dsClass.setText("Standard :  "+userProfile.getStuStandard());
                dsSec.setText("Section :  "+userProfile.getStuSection());
                dsGender.setText("Gender :  "+userProfile.getStuGender());
                dsPname.setText("Parent Name :  "+userProfile.getStuParentName());
                dsMnum.setText(userProfile.getStuMobileNumber());
                dsEmail.setText(userProfile.getStuEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }
}