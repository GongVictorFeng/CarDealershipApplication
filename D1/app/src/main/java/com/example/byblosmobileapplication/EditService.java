package com.example.byblosmobileapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditService extends AppCompatActivity {
    CheckBox clientName, dateOfBirth,email, pickUpTime, driverLicense, returnDate,moveStartLocation, moveEndLocation, numMovers,carType ,purpose,maxNumKM, address,numBoxes;
    EditText serviceName, cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

    }


    public void onClickEditService(View view){
        serviceName = findViewById(R.id.editTextView1);
        cost = findViewById(R.id.editTextView2);
        String sName = serviceName.getText().toString();


        

        clientName = findViewById(R.id.editCheckBox1);
        dateOfBirth = findViewById(R.id.editCheckBox2);
        email = findViewById(R.id.editCheckBox3);
        driverLicense = findViewById(R.id.editCheckBox4);
        pickUpTime = findViewById(R.id.editCheckBox5);
        returnDate = findViewById(R.id.editCheckBox6);
        moveStartLocation = findViewById(R.id.editCheckBox7);
        moveEndLocation = findViewById(R.id.editCheckBox8);
        numMovers = findViewById(R.id.editCheckBox9);
        carType = findViewById(R.id.editCheckBox10);
        purpose = findViewById(R.id.editCheckBox11);
        maxNumKM = findViewById(R.id.editCheckBox12);
        address = findViewById(R.id.editCheckBox13);
        numBoxes = findViewById(R.id.editCheckBox14);
        serviceExist(sName);


    }

    private void update(FirebaseDatabase database, String sName){
        try {
            int price = Integer.parseInt(cost.getText().toString());
            DatabaseReference newHourlyRef = database.getReference("Service/" + sName + "/Hourly Wage");
            newHourlyRef.setValue(price);

            insertReq(clientName, database, "/Required Name", sName);
            insertReq(dateOfBirth, database, "/Required Date of Birth", sName);
            insertReq(email, database, "/Required Email", sName);
            insertReq(driverLicense, database, "/Required Driver License Type", sName);
            insertReq(pickUpTime, database, "/Required Pick Up Time & Date", sName);
            insertReq(returnDate, database, "/Required Return Date", sName);
            insertReq(moveStartLocation, database, "/Required Start Moving Destination", sName);
            insertReq(moveEndLocation, database, "/Required End Moving Destination", sName);
            insertReq(numMovers, database, "/Required Number of Movers", sName);
            insertReq(carType, database, "/Required Car Type", sName);
            insertReq(purpose, database, "/Required purpose of rental", sName);
            insertReq(maxNumKM, database, "/Required Max number of KM driven", sName);
            insertReq(address, database, "/Required Addess", sName);
            insertReq(numBoxes, database, "/Required Number of boxes", sName);
            Toast.makeText(this.getApplicationContext(), "Service was Updated", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ServicePage.class);
            startActivity(intent);
        }
        catch (IllegalArgumentException e){
            cost.setError("Invalid integer");
        }
    }


    private void serviceExist(String serverName){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataRef= database.getReference().child("Service");
        dataRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                boolean isExist=false;
                for(DataSnapshot item1 : snapshot1.getChildren()) {
                    if (item1.getKey().toString().equals(serverName)) {


                        System.out.println(item1);
                        System.out.println(serverName);

                        update(database, serverName);
                        return;
                    }
                }
               errorMSG();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void errorMSG(){
        serviceName.setError("Service doesn't exist");
        reset();
    }

    private void insertReq(CheckBox check, FirebaseDatabase database, String root, String name ){
        DatabaseReference req = database.getReference("Service/"+name+"/"+root );
        if(check.isChecked()){
            req.setValue(true);
            check.setChecked(false);
        }
        else{
            req.setValue(false);
        }
    }


    private void reset(){
        serviceName.setText("");
        cost.setText("");

    }
}