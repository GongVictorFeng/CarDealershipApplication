package com.example.byblosmobileapplication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Account {
    protected String username;
    protected String email;
    protected String password;
    protected String role;


    public Account(String username, String email, String password, String role){
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void register() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference newUserRoleRef = database.getReference("user/" + username + "/role");
        DatabaseReference newUserEmailRef = database.getReference("user/" + username + "/email");
        DatabaseReference newUserPasswordRef = database.getReference("user/" + username + "/password");

        newUserRoleRef.setValue(role);
        newUserEmailRef.setValue(email);
        newUserPasswordRef.setValue(password);

    }
}
