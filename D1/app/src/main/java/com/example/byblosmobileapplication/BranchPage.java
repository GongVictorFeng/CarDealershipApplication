package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BranchPage extends AppCompatActivity {

    ListView listviewServices;
    ArrayList<ServiceClass> serviceList;
    private String userName;
    ArrayList<String> branchAttris;
    ArrayList<ServiceClass> serviceListAdmin;
    private TextView address;
    private TextView phoneNum;
    private TextView workingHourWD;
    private TextView workingHourWK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_page);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

        getBranch();
        getServiceList();
        ServiceClass branch=serviceListAdmin.get(0);
        address = findViewById(R.id.branchAddress);
        phoneNum = findViewById(R.id.branchPhoneNum);
        workingHourWD = findViewById(R.id.branchWeekdayWorkingHour);
        workingHourWK = findViewById(R.id.branchWeekendWorkingHour);

        address.setText("Address: " + branch.getServiceName());
//        phoneNum.setText("Phone Number: " + branch.getPhoneNumber());
//        workingHourWD.setText("Weekday Hours: " + branch.getStartTimeWeekday() + " - " + branch.getEndTimeWeekday());
//        workingHourWK.setText("Weekend Hours: " + branch.getStartTimeWeekend() + " - " + branch.getEndTimeWeekend());
    }

    @Override
    protected void onStart() {
        super.onStart();
        listviewServices = findViewById(R.id.listViewService);
        serviceList = new ArrayList<ServiceClass>();
        FirebaseDatabase databaseEM = FirebaseDatabase.getInstance();
        DatabaseReference dataRefEM = databaseEM.getReference().child("EmService");
        dataRefEM.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ServiceClass newService = postSnapshot.getValue(ServiceClass.class);
                    serviceList.add(newService);
                }

                ServiceList servicesAdapter = new ServiceList(BranchPage.this, serviceList);

                listviewServices.setAdapter(servicesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getBranch() {
        branchAttris = new ArrayList<String>();
        FirebaseDatabase databaseBranch = FirebaseDatabase.getInstance();
        DatabaseReference dataRefBranch = databaseBranch.getReference().child("Branches");

        dataRefBranch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchAttris.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String str = postSnapshot.getValue(String.class);
                    branchAttris.add(str);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void getServiceList() {
        serviceListAdmin = new ArrayList<ServiceClass>();
        FirebaseDatabase databaseAdmin = FirebaseDatabase.getInstance();
        DatabaseReference dataRefAdmin = databaseAdmin.getReference().child("Services");

        dataRefAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceListAdmin.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    ServiceClass newService = postSnapshot.getValue(ServiceClass.class);
                    serviceListAdmin.add(newService);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
















//                    if(item.getKey().equals("address")){
//        branch.setAddress(item.getValue().toString());
//    }
//                    else if(item.getKey().equals("phoneNumber")){
//        branch.setPhoneNumber(item.getValue().toString());
//    }
//                    else if(item.getKey().equals("endTimeWeekend")){
//        branch.setEndTimeWeekend(item.getValue().toString());
//    }
//                    else if(item.getKey().equals("startTimeWeekend")){
//        branch.setStartTimeWeekend(item.getValue().toString());
//    }
//                    else if(item.getKey().equals("endTimeWeekday")){
//        branch.setEndTimeWeekday(item.getValue().toString());
//    }
//                    else if(item.getKey().equals("startTimeWeekday")){
//        branch.setStartTimeWeekday(item.getValue().toString());
//    }
//}