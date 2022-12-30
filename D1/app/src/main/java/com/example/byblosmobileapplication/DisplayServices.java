package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayServices extends AppCompatActivity {
    public static ListView listView;
    public static ArrayList<String> arrayList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_services);
        listView = findViewById(R.id.list);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef= database.getReference().child("Service");
        dataRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot item : snapshot.getChildren()) {

                    add(item);
                }
                show( );
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        System.out.println(arrayList.size());


    }

    private void show(){
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }


    private void add(DataSnapshot item){
        if(!(arrayList.contains(item.getKey().toString()))){
            arrayList.add(item.getKey().toString());
        }

    }

    public void onClickReset(View view){
        arrayList = new ArrayList();
        listView.removeAllViewsInLayout();
        Intent intent = new Intent(this, AdminPage.class);
        startActivity(intent);
    }


}