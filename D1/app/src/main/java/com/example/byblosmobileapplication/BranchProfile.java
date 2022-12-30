package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BranchProfile extends AppCompatActivity {

    private DatabaseReference databaseServices;
    private EditText address, phoneNumber;
    private String userName;
    public static final Pattern valid_address_verification = Pattern.compile("^\\d{1,5},.*$",Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_profile);

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");

    }


    public void onClickCompleteBranch(View view){
        databaseServices= FirebaseDatabase.getInstance().getReference("Branches");
        address=findViewById(R.id.branchAddressField);
        phoneNumber=findViewById(R.id.branchNumberField);
        String sAddress = address.getText().toString();
        String sPhoneNum = phoneNumber.getText().toString();

        if(validation(sAddress,sPhoneNum)){
            Branch branch=new Branch(userName,sPhoneNum,sAddress);

            databaseServices.child(userName).setValue(branch);
            Toast.makeText(this, "Completed Branch", Toast.LENGTH_SHORT).show();
            reset();
            finish();

            Intent intent = new Intent(this, BranchPage.class);
            startActivity(intent);
        }
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

        if(sPhoneNum.length()!=10){
            phoneNumber.setError("Phone number must have 10 digits");
            reset();
            isValid=false;
        }

        Matcher matcher = valid_address_verification.matcher(sAddress.trim());
        if (!matcher.find()){
            isValid=false;
            address.setError("Example: 123, Street");

        }
        return  isValid;
    }

    private void reset(){
        phoneNumber.setText("");
        address.setText("");
    }

}