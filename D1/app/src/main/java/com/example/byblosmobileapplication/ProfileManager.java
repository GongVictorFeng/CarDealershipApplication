package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileManager extends AppCompatActivity {
    DatabaseReference databaseServices;
    EditText address, phoneNumber;
    private String userName;
    private Branch branch;
    String startTimeWeekday, startTimeWeekend, endTimeWeekday,endTimeWeekend;
    public static final Pattern valid_address_verification = Pattern.compile("^\\d{1,5},.*$",Pattern.CASE_INSENSITIVE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_manager);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/Branches/"+userName);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()){
                    if(item.getKey().equals("endTimeWeekend")){
                        endTimeWeekend = item.getValue().toString();
                        System.out.println(endTimeWeekend);
                    }
                    else if(item.getKey().equals("startTimeWeekend")){
                        startTimeWeekend = item.getValue().toString();
                    }
                    else if(item.getKey().equals("endTimeWeekday")){
                        endTimeWeekday = item.getValue().toString();
                    }
                    else if(item.getKey().equals("startTimeWeekday")){
                        startTimeWeekday = item.getValue().toString();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void onClickEdit(View view){
        databaseServices=FirebaseDatabase.getInstance().getReference("Branches");
        address = findViewById(R.id.addressTextView);
        phoneNumber = findViewById(R.id.phoneNumberTextView);

        String sAddress = address.getText().toString();
        String sPhoneNum = phoneNumber.getText().toString();

        if (validation(sAddress,sPhoneNum)) {
            branch = new Branch(userName, sPhoneNum, sAddress);
            databaseServices.child(userName).setValue(branch);
            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
            reset();

            finish();
            Intent intent = new Intent(this, EmployeePage.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        }


    }

    private void reset(){
        phoneNumber.setText("");
        address.setText("");
    }

    public boolean validation(String sAddress, String sPhoneNum){
        boolean isValid= true;
        if(sAddress.isEmpty()){
            address.setError("Please enter valid address");
            reset();
            isValid=false;
        }

        if(sPhoneNum.isEmpty()){
            phoneNumber.setError("Please enter valid phone number");
            reset();
            isValid=false;
        }

        if(sPhoneNum.length()!=9){
            phoneNumber.setError("Phone number must have 9 digits");
            reset();
            isValid=false;
        }
        branch=new Branch(userName,sPhoneNum,sAddress);
        branch.setEndTimeWeekend(endTimeWeekend);
        branch.setEndTimeWeekday(endTimeWeekday);
        branch.setStartTimeWeekend(startTimeWeekend);
        branch.setStartTimeWeekday(startTimeWeekday);

        System.out.println(endTimeWeekday);
        databaseServices.child(userName).setValue(branch);
        Toast.makeText(this,"Profile updated",Toast.LENGTH_SHORT).show();
        reset();

        finish();
        Intent intent = new Intent(this, EmployeePage.class);
        intent.putExtra("userName", userName);
        startActivity(intent);


        Matcher matcher = valid_address_verification.matcher(sAddress.trim());
        if (!matcher.find()){
            isValid=false;
            address.setError("Example: 123, Street");

        }
        return  isValid;
    }
}