package com.example.studentinformationsystem.StaffPort;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.studentinformationsystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class StaffHomeFragment extends Fragment {

    private EditText RegNum, Date, Message;
    private Button Send;
    private Spinner MsgFor, Title;
    DatePickerDialog.OnDateSetListener setListener;
    private DatabaseReference databaseReference;
    String tRegNum, tDate, tMsgFor, tTitle, tMessage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_staff_home, container, false);

        RegNum = (EditText) root.findViewById(R.id.t2regNum);
        Date = (EditText) root.findViewById(R.id.t2date);
        Message = (EditText) root.findViewById(R.id.t2message);
        Send = (Button) root.findViewById(R.id.t2btnsend);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        Date.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        Title = (Spinner) root.findViewById(R.id.t2title);
        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("HomeWork");
        list1.add("Exams");
        list1.add("Announcements");
        list1.add("Holidays");
        list1.add("Others");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, list1);
        Title.setAdapter(adapter1);

        MsgFor = (Spinner) root.findViewById(R.id.t2msgfor);
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("Student");
        list2.add("Parent");
        list2.add("Both");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, list2);
        MsgFor.setAdapter(adapter2);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Message");
        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    StaffMessage staffMessage = new StaffMessage(tRegNum, tDate, tMsgFor, tTitle, tMessage);
                    databaseReference.child(tMsgFor).child(tTitle).child(tRegNum).push().setValue(staffMessage);
                    Toast.makeText(getContext(), "Message sent to "+tRegNum, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    private Boolean validate() {

        Boolean result = false;
        tRegNum = RegNum.getText().toString().trim();
        tDate = Date.getText().toString().trim();
        tMsgFor = MsgFor.getSelectedItem().toString();
        tTitle = Title.getSelectedItem().toString();
        tMessage = Message.getText().toString().trim();

        if (tRegNum.isEmpty() || tDate.isEmpty() ||tMessage.isEmpty()){
            Toast.makeText(getContext(), "📝Please enter all the Details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }
}