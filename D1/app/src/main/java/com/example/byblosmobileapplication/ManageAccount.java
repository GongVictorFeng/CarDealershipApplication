package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManageAccount extends AppCompatActivity {
    private EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userName = findViewById(R.id.accountName);

        setContentView(R.layout.activity_manage_account);
    }

    public void onClickButton(View view){
        userName = findViewById(R.id.accountName);
        String inputUser = userName.getText().toString();
        int pressID = view.getId();
        switch (pressID){
            case R.id.btnHome:
                Intent intent = new Intent(this, AdminPage.class);
                startActivity(intent);
                break;
            case R.id.btnDelete:
                if(isValid(inputUser)){
                    onClickDelete(inputUser);
                    if(!userName.getText().toString().isEmpty()){
                        Toast.makeText(this,"Account doesn't exist",Toast.LENGTH_SHORT).show();
                        userName.setText("");
                    }
                }
                else{
                    userName.setError("Empty Field");
                }
                break;

        }

    }

    public void onClickDelete(String account){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef= database.getReference().child("user");
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item : snapshot.getChildren()){
                    if(item.getKey().equals(account)){
                        deleteAccount(account);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void deleteAccount(String username){
            DatabaseReference dAccount = FirebaseDatabase.getInstance().getReference("user").child(username);
            dAccount.removeValue();
            Toast.makeText(this.getApplicationContext(), "Account deleted", Toast.LENGTH_SHORT).show();
            userName.setText("");


    }


    private boolean isValid(String input){
        return !input.isEmpty();
    }


}