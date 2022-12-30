package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ApproveRejectRequest extends AppCompatActivity {
    ListView listView, listViewApproved, listViewRejected;
    ArrayList<String> myList = new ArrayList<>();
    ArrayList<String> approvedRequest = new ArrayList<>();
    ArrayList<String> rejectedRequest = new ArrayList<>();
    DatabaseReference myRefItems;
    DatabaseReference myRefSubItems;
    public final static String EXTRA_REQUEST = "com.example.byblosmobileapplication.EXTRAREQUEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_reject_request);

        listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);
        listView.setAdapter(myArrayAdapter);

        listViewApproved = (ListView) findViewById(R.id.listViewApproved);
        ArrayAdapter<String> approveRequestAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, approvedRequest);
        listViewApproved.setAdapter(approveRequestAdapter);

        listViewRejected = (ListView) findViewById(R.id.listViewRejected);
        ArrayAdapter<String> rejectRequestAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rejectedRequest);
        listViewRejected.setAdapter(rejectRequestAdapter);


        myRefItems = FirebaseDatabase.getInstance().getReference("/Service Request");
        myRefItems.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item : snapshot.getChildren()){
                    String requestName = item.getKey().toString();
                    String value = item.getKey().toString();
                    for(DataSnapshot subItem : item.getChildren()){
                        if(subItem.getKey().equals("status") && subItem.getValue().equals("Approved")){
                            approvedRequest.add(requestName);
                            approveRequestAdapter.notifyDataSetChanged();
                        }

                        else if(subItem.getKey().equals("status") && subItem.getValue().equals("Reject")){
                            rejectedRequest.add(requestName);
                            rejectRequestAdapter.notifyDataSetChanged();
                        }
                        value = value + "\n " + subItem.getKey()+ ": " + subItem.getValue();
                    }
                    if(!myList.contains(value)){
                        myList.add(value);
                        myArrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startNextActivity(listView.getItemAtPosition(i).toString().split("\n")[0]);
            }
        });
    }

    private void startNextActivity(String item){
        finish();
        Intent intent = new Intent(this, ApproveOrReject.class);
        intent.putExtra(EXTRA_REQUEST, item );
        startActivity(intent);
    }

    public void onClickHome(View view){
        finish();
        Intent intent = new Intent(this, EmployeePage.class);
        startActivity(intent);
    }


}