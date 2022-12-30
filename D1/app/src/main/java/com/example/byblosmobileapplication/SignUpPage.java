package com.example.byblosmobileapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class SignUpPage extends AppCompatActivity {
    //Instance variables
    // These are variable that the user enters in the sign up pages
    private EditText username;
    private EditText password;
    private EditText email;
    private  EditText confirmPassword;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

//        // Finding the ID of each of the text that the user has entered and turn them into string, so that they can be stored in the firebase
//        username=findViewById(R.id.etName2);
//        password=findViewById(R.id.etPassword2);
//        email=findViewById(R.id.etEmail2);
//        confirmPassword= findViewById(R.id.etConfirmPassword);
//
//        spinner=findViewById(R.id.etSpinner);
//        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.roles, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner.setAdapter(adapter);

    }
    public void onClickRegister(View view){

        // Finding the ID of each of the text that the user has entered and turn them into string, so that they can be stored in the firebase
        username=findViewById(R.id.etName2);
        password=findViewById(R.id.etPassword2);
        email=findViewById(R.id.etEmail2);
        confirmPassword= findViewById(R.id.etConfirmPassword);

        spinner=findViewById(R.id.etSpinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        String inputRole=spinner.getSelectedItem().toString();
        String inputName=username.getText().toString();
        String inputPSW=password.getText().toString();
        String inputEmail = email.getText().toString();
        String inputPSW2 = confirmPassword.getText().toString();

        //Checking the validity of the information that the user has entered
        boolean emailValid = validEmail(inputEmail);
        boolean notEmpty = emptyFields(inputEmail,inputName,inputPSW,inputPSW2);
        boolean passwordMatch = matchPassword(inputPSW,inputPSW2);


        // Setting up the information that the user has entered into firebase, then leading them to the login page
        if(emailValid&& notEmpty&& passwordMatch){
            Account account;
            // User is a customer
            if(inputRole.equals("Customer")) {
                account = new CustomerAccount(inputName, inputEmail, inputPSW, inputRole);
                account.register();
                resetText();
                openLoginPage();
            }
            // User is an employee
            else if (inputRole.equals("Employee")){
                account = new EmployeeAccount(inputName, inputEmail, inputPSW, inputRole);
                account.register();
                resetText();
                openLoginPage();
            }

        }


    }

    // This check the validity of the email that the user has entered. They can only enter gmail, hotmail, and outlook cmails
    private boolean validEmail(String email){

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }
        else{
            this.email.setError("invalid email");
            return false;
        }
    }

    // Checks what infomation is still left blank
    private boolean emptyFields(String email, String name, String password, String password2){
        if(email.isEmpty()) {
            this.email.setError("Missing email");
        }
        if(name.isEmpty()) {
            this.username.setError("Missing username");
        }
        if(password.isEmpty()) {
            this.password.setError("Missing Password");
        }
        if(password2.isEmpty()) {
            this.confirmPassword.setError("Missing Password");
        }

        return (!email.isEmpty() && !name.isEmpty() && !password.isEmpty()&& !password2.isEmpty());

    }

    // See if both passwords matches
    private boolean matchPassword(String password, String password2){
        if(!password.equals(password2)){
            this.password.setError("Password doesn't match");
            this.confirmPassword.setError("Password doesn't match");
        }
        return password.equals(password2);
    }

    //Reset all of the textboxes
    private void resetText(){
        username.setText("");
        password.setText("");
        email.setText("");
        confirmPassword.setText("");

    }

    // Takes the user back to the login page
    private void openLoginPage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

}