package com.example.studentinformationsystem.StaffPort;

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
    private TextView dsName,dsStaffId,dsSubject,dsGender,dsMnum,dsEmail;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_staff_myprofile, container, false);

        ProfileImage = (CircleImageView)root.findViewById(R.id.tProfileImage);
        dsName = (TextView)root.findViewById(R.id.ttvname);
        dsStaffId =(TextView)root.findViewById(R.id.ttvstaffid);
        dsSubject =(TextView)root.findViewById(R.id.ttvsubject);
        dsGender =(TextView)root.findViewById(R.id.ttvgender);
        dsMnum = (TextView)root.findViewById(R.id.ttvmno);
        dsEmail = (TextView)root.findViewById(R.id.ttvemail);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        StorageReference storageReference =firebaseStorage.getReference();
        storageReference.child("Staff/Profile Pictures").child(firebaseAuth.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(ProfileImage);
            }
        });
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Staff").child(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StaffProfile staffProfile = dataSnapshot.getValue(StaffProfile.class);
                dsName.setText(staffProfile.getTeName());
                dsStaffId.setText("Staff ID :  "+staffProfile.getTeStaffId());
                dsSubject.setText("Subject :  "+staffProfile.getTeSubject());
                dsGender.setText("Gender :  "+staffProfile.getTeGender());
                dsMnum.setText(staffProfile.getTeMnum());
                dsEmail.setText(staffProfile.getTeEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }

}
