package com.example.byblosmobileapplication;

import java.util.ArrayList;

public class Branch {
    private String employee;
    private String phoneNumber;
    private String address;
    private String startTimeWeekday;
    private String startTimeWeekend;
    private String endTimeWeekday;
    private String endTimeWeekend;


    public Branch(String employee, String phoneNumber, String address){
        this.employee=employee;
        this.phoneNumber=phoneNumber;
        this.address=address;
        startTimeWeekday = "9:00";
        startTimeWeekend = "11:00";
        endTimeWeekday = "5:00";
        endTimeWeekend = "4:00";

    }



    public String getStartTimeWeekday() {
        return startTimeWeekday;
    }

    public String getStartTimeWeekend() {
        return startTimeWeekend;
    }

    public String getEndTimeWeekday() {
        return endTimeWeekday;
    }

    public String getEndTimeWeekend() {
        return endTimeWeekend;
    }

    public String getEmployee() {
        return employee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStartTimeWeekday(String startTimeWeekday) {
        this.startTimeWeekday = startTimeWeekday;
    }

    public void setStartTimeWeekend(String startTimeWeekend) {
        this.startTimeWeekend = startTimeWeekend;
    }

    public void setEndTimeWeekday(String endTimeWeekday) {
        this.endTimeWeekday = endTimeWeekday;
    }

    public void setEndTimeWeekend(String endTimeWeekend) {
        this.endTimeWeekend = endTimeWeekend;
    }

}
